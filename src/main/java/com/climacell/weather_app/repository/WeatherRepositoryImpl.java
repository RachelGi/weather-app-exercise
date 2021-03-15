package com.climacell.weather_app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.climacell.weather_app.model.WeatherSummarize;
import com.climacell.weather_app.model.Weather;

@Repository
public class WeatherRepositoryImpl implements WeatherRepositoryCustom{

	@Autowired
	MongoTemplate mongoTemplate; 

	@Override
	public List<Weather> findByLongitudeAndLatitude(Double longitude, Double latitude) {
		Query q = new Query(); 
		q.withHint("location_index");//TODO
		q.addCriteria(new Criteria().andOperator(Criteria.where("longitude").is(longitude),Criteria.where("latitude").is(latitude)));

		return mongoTemplate.find(q, Weather.class);
	}

	@Override
	public WeatherSummarize getMinWeatherAtLocation(Double longitude, Double latitude) {
		GroupOperation grpOpe = Aggregation.group("longitude").min("temperature").as("temperature").min("precipitation").as("precipitation");
		return getAggregationForSummarizeWeather(longitude, latitude, grpOpe);
	}

	@Override
	public WeatherSummarize getMaxWeatherAtLocation(Double longitude, Double latitude) {
		GroupOperation grpOpe = Aggregation.group("longitude").max("temperature").as("temperature").max("precipitation").as("precipitation");
		return getAggregationForSummarizeWeather(longitude, latitude, grpOpe);

	}

	@Override
	public WeatherSummarize getAverageWeatherAtLocation(Double longitude, Double latitude) {
		GroupOperation grpOpe = Aggregation.group("longitude").avg("temperature").as("temperature").avg("precipitation").as("precipitation");
		WeatherSummarize weatherSummarize =  getAggregationForSummarizeWeather(longitude, latitude, grpOpe);
		weatherSummarize.setPrecipitation(roundToOnDigit(weatherSummarize.getPrecipitation()));
		weatherSummarize.setTemperature(roundToOnDigit(weatherSummarize.getTemperature()));
		return weatherSummarize;
	}

	private WeatherSummarize getAggregationForSummarizeWeather(Double longitude, Double latitude,
			GroupOperation grpOpe) {
		MatchOperation matchAgg = Aggregation.match(new Criteria().
				andOperator(Criteria.where("longitude").is(longitude),Criteria.where("latitude").is(latitude)));
		ProjectionOperation projectionOpe = Aggregation.project().andExclude("_id");
		Aggregation aggregation = Aggregation.newAggregation(matchAgg,
				grpOpe, projectionOpe);
		AggregationResults<WeatherSummarize> t = mongoTemplate.aggregate(aggregation, Weather.class, WeatherSummarize.class);
		return t.getMappedResults().get(0);
	}


	private double roundToOnDigit(double val) {
		return Math.round(val * 10) / 10.0; 
	}
	//MIN

	//    List<? extends Bson> pipeline = Arrays.asList(
	//            new Document()
	//                    .append("$match", new Document()
	//                            .append("longitude", 3.4)
	//                            .append("latitude", 2.3)
	//                    ), 
	//            new Document()
	//                    .append("$group", new Document()
	//                            .append("_id", new Document()
	//                                    .append("field1", "$longitude")
	//                                    .append("field2", "$latitude")
	//                            )
	//                            .append("minTemp", new Document()
	//                                    .append("$min", "$temperature")
	//                            )
	//                            .append("minPrecipitation", new Document()
	//                                    .append("$min", "$precipitation")
	//                            )
	//                    ), 
	//            new Document()
	//                    .append("$project", new Document()
	//                            .append("_id", 0.0)
	//                    )
	//    );
	//    
	//    collection.aggregate(pipeline)
	//            .allowDiskUse(false)
	//            .forEach(processBlock);
	//    
}
