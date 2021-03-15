package com.climacell.weather_app.configuration;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.climacell.weather_app.model.Weather;
import com.climacell.weather_app.parserUtils.JobCompletionNotificationListener;
import com.climacell.weather_app.parserUtils.WeatherFromCsv;
import com.climacell.weather_app.parserUtils.WeatherItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	private static final int CHUNCK_SIZE = 400;
	private static final String WEATHER_COLLECTION_NAME = "weather";
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;


	/**
	 * Configure Reader to Read the CSV file of weather data
	 * The reader skip the first line of the file
	 * @param resourceFileName- the file name from the resource
	 * @return the reader
	 * @throws IOException
	 */
	@Bean
	@StepScope
	public FlatFileItemReader<WeatherFromCsv> reader(@Value("#{jobParameters[resourceFileName]}") String resourceFileName) throws IOException {
		List<String> weatherFieldsName = 
		Arrays.asList(WeatherFromCsv.class.getFields()).stream().map(f->f.getName()) .collect(Collectors.toList());
		return new FlatFileItemReaderBuilder<WeatherFromCsv>().name("weatherItemReader")
				.resource(new ClassPathResource(resourceFileName)).delimited()
				.names(weatherFieldsName.toArray(new String[0]))
				.linesToSkip(1)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<WeatherFromCsv>() {
					{
						setTargetType(WeatherFromCsv.class);
					}
				}).build();
	}

	/**
	 * Configuration of the writer
	 * @param mongoTemplate
	 * @return
	 */
	@Bean
	public MongoItemWriter<Weather> writer(MongoTemplate mongoTemplate) {
		return new MongoItemWriterBuilder<Weather>().template(mongoTemplate).collection(WEATHER_COLLECTION_NAME)
				.build();
	}


	/**
	 * Init the processor that will help to convert the  data in the csv file into weather object
	 * @return WeatherItemProcessor
	 */
	@Bean
	public WeatherItemProcessor processor() {
		return new WeatherItemProcessor();
	}


	/**
	 * Configure the step 1
	 * The CSV file will be parse and save to database  in chunks of
	 * {@link BatchConfiguration#CHUNCK_SIZE} 
	 * @param itemWriter
	 * @return
	 * @throws Exception
	 */
	@Bean
	public Step step1( MongoItemWriter<Weather> itemWriter)
			throws Exception {
		return this.stepBuilderFactory.get("step1").<WeatherFromCsv, Weather>chunk(CHUNCK_SIZE).reader(reader(null))
				.processor(processor()).writer(itemWriter).build();
	}
	@Bean
	public Job updateWeatherJob(JobCompletionNotificationListener listener, Step step1)
			throws Exception {

		return this.jobBuilderFactory.get("updateWeatherJob").incrementer(new RunIdIncrementer())
				.listener(listener).start(step1).build();
	}

}