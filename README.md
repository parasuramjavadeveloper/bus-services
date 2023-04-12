## BusServices gives you the Top 10 Bus Lines and its BusStops by consuming TrafikLab External API's data 

## Technologies/Tools Used
Java 11,
Intellij Idea for Development,
Maven Build Tool ,
Springboot with REST APIs,
Lombok Libraries and
Swagger Documentation

## Setup & Run Instructions

mvn clean install - it will build application

mvn spring-boot:run - it will run the application

## Swagger Documentation

Below API gives you the Top 10 BusLines and its BusStopNames
## Localhost URL
http://localhost:9091/swagger-ui/index.html#/bus-line-controller/busLineServiceDetails

## DEV URL
http://3.22.116.126:9091/swagger-ui/index.html#/bus-line-controller/busLineServiceDetails

## Challenge/Requirement
The task is to write an application to find out which bus lines that have the most bus stops on their route, and to present the top 10 bus lines in a nice and formatted way in a web browser.
The web page should show the names of every bus stop for each of the bus lines in the top 10 list.
There are no requirements how the bus stops are sorted.
To accomplish the task please use the Trafiklab’s open API (https://www.trafiklab.se/). You can find more information about the specific API on the documentation page: https://www.trafiklab.se/api/sl-hallplatser-och-linjer-2.
You can register your own account at Trafiklab.
The data should be automatically gathered from API for each run of the application.
Language 
We would like you to use Java for the back-end.
You are free to choose the version of JavaScript and Javascript. 
You can use external libraries if you would like to.
## Delivery 
We would like you to share your code with us in advance so we can review it before we invite you to discuss the solution. 
The solution should include the source code, the build script and instructions to run the application in MacOS/Unix or Windows.

## Implementation 

On Application Startup reading TrafikLab APIs and compute the Top 10 BusLines and its BusStopNames and stores in the SpringBoot Cache Store.
Developed the REST API with SpringBoot application and Swagger Documentation added for the API to Get the Top 10 BusLines and its BusStops.

