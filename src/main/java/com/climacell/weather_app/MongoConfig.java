//package com.climacell.weather_app;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.data.mongodb.MongoDatabaseFactory;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoConfig {
//	 @Autowired
//	    private Environment env;
//
//	    @Bean
//	    public MongoDatabaseFactory mongoDbFactory() {
//	        return new MongoDatabaseFactory(new MongoClientURI(env.getProperty("spring.data.mongodb.uri")));
//	    }
//
//		@Bean
//		public MongoTemplate mongoTemplate() {
//			MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
//
//			return mongoTemplate;
//
//	    }
//}