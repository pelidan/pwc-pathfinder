# pwc-pathfinder

### Assessment:

```
Your task is to create a simple Spring Boot service, that is able to calculate any possible land
route from one country to another. The objective is to take a list of country data in JSON format
and calculate the route by utilizing individual countries border information.
Specifications:
● Spring Boot, Maven
● Data link: https://raw.githubusercontent.com/mledoze/countries/master/countries.json
● The application exposes REST endpoint /routing/{origin}/{destination} that
returns a list of border crossings to get from origin to destination
● Single route is returned if the journey is possible
● Algorithm needs to be efficient
● If there is no land crossing, the endpoint returns HTTP 400
● Countries are identified by cca3 field in country data
● HTTP request sample (land route from Czech Republic to Italy):
    ○ GET /routing/CZE/ITA HTTP/1.0 :
        {
        "route": ["CZE", "AUT", "ITA"]
        }
        
Expected deliveries:
1. Source code
2. Instructions on how to build and run the application
```

### Instructions how to build and run the application:
You must have a JAVA_HOME environment variable set pointing to an existing Java installation directory

Linux/OS X:
```
./mvnw clean install spring-boot:run
```

Windows:
```
mvnw clean install spring-boot:run
```

Application runs on localhost port 8080.
Example URL for request: 
```
http://localhost:8080/routing/CZE/ITA
```
