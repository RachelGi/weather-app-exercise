package com.climacell.weather_app.configuration;


import java.io.IOException;

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

import com.climacell.weather_app.ex.JobCompletionNotificationListener;
import com.climacell.weather_app.ex.WeatherFromCsv;
import com.climacell.weather_app.ex.WeatherItemProcessor;
import com.climacell.weather_app.model.Weather;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;


	@Bean
	@StepScope
	public FlatFileItemReader<WeatherFromCsv> reader(@Value("#{jobParameters[fileName]}") String fileName) throws IOException {
		System.err.println(fileName);
		return new FlatFileItemReaderBuilder<WeatherFromCsv>().name("weatherItemReader")
				.resource(new ClassPathResource("data/" + fileName + ".csv")).delimited()
				.names(new String[] {"longitude", "latitude", "forecastTimeFromString", "temperature", "precipitation"})
				.linesToSkip(1)
				.fieldSetMapper(new BeanWrapperFieldSetMapper<WeatherFromCsv>() {
					{
						setTargetType(WeatherFromCsv.class);
					}
				}).build();
	}

	@Bean
	public MongoItemWriter<Weather> writer(MongoTemplate mongoTemplate) {
		return new MongoItemWriterBuilder<Weather>().template(mongoTemplate).collection("weather")
				.build();
	}


	@Bean
	public WeatherItemProcessor processor() {
		WeatherItemProcessor c = new WeatherItemProcessor();
		return c;
	}


	@Bean
	public Step step1( MongoItemWriter<Weather> itemWriter)
			throws Exception {
		return this.stepBuilderFactory.get("step1").<WeatherFromCsv, Weather>chunk(600).reader(reader(null))
				.processor(processor()).writer(itemWriter).build();
	}

//	@Bean
//	public Step step1(FlatFileItemReader<WeatherFromCsv> itemReader, MongoItemWriter<Weather> itemWriter)
//			throws Exception {
//		return this.stepBuilderFactory.get("step1").<WeatherFromCsv, Weather>chunk(100).reader(itemReader)
//				.processor(processor()).writer(itemWriter).build();
//	}
	@Bean
	public Job updateWeatherJob(JobCompletionNotificationListener listener, Step step1)
			throws Exception {

		return this.jobBuilderFactory.get("updateWeatherJob").incrementer(new RunIdIncrementer())
				.listener(listener).start(step1).build();
	}

}