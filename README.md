# weather-app-exercise

Web service that will give us the weather forecast for a
specific location
Each file has 5 columns:
Longitude, Latitude, forecast time, Temperature, Precipitation
The service will have 2 routes
/weather/data - returns the weather forecast in a specific location for a specific time
/weather/summarize - returns the max,min,avg weather data (from all the files) for a specific location
1. Temperature in Celsius
2. Precipitation Rate in mm/hr


  Programming language: Java
  DataBase : MongoDB
  Hosting provider: Heroku


AssumptionS : 
1. The mock data from the CSV file is loaded at the beginning of the application
2. For a given time and location (longitude and latitude), there are no more than one weather forecast 
3. The format of the CSV file to load cannot be modified



# Things that need to be optimized
1- Split the application into micro-services : 
  a. Service to load the CSV file
  b. Service to retrieve and display data 
  
2- Use a message queue to load the file and run as a background job the CSV loader


