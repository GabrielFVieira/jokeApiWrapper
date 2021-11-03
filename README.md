# jokeApiWrapper
A wrapper application for the JokeApi.
Learn more about the [JokeAPI](https://jokeapi.dev/)

## To run the application:
- Install docker, if its not already installed
- Download the project
- Inside the project folder open the command prompt
- Run "mvn clean install" to generate the .jar file
- Run "docker-compose up" to build the docker image of the application and download the mysql:5.7 image used to provide the needed database

After that a docker container should start, inside it there must be two services:
- An instance of mysql with a single database called 'jokeapi' running at the port 3306
- An instance of the jokeApiWrapper running at the port 8088

## To test the application:
  After the docker container start, open the following URL in your browser: "http://localhost:8088/". \
  It should redirect you to the Swagger UI page that has all the endpoints of the api and the description of the possible parameters for each one of them. \
  From that page is possible to test all the endpoints, but if you want to test it from Insomnia, Postman or any other tool, please read the following section.

## Endpoints:
### GET [/joke](http://localhost:8088/joke)
Endpoint used to get a new joke from the JokeAPI
#### Query Parameters:
  - type -> OPTIONAL. Used to filter the type of joke, the possible values are: 'single', 'twopart'
  - categories -> OPTIONAL. Used to filter the category of the joke, could a single value or a array of strings
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)

#### Return:
  Json object with the following format:
  ```javascript
  {
    "id": 1,
    "joke": "Sample joke"
  }
  ```
  
### GET [/joke/{category}/top](http://localhost:8088/joke/Any/top)
Endpoint used to get the top rated jokes from a category

### Path Parameters:
  - category -> Used to filter for the category. If you want to get the top jokes among all categories use 'Any'.

#### Query Parameters:
  - amount -> OPTIONAL. Used to define how many jokes will be returned. Default: 5
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)

#### Return:
  Json object with the following format:
  ```javascript
  [
    {
      "id": 1,
      "joke": "Sample joke"
    }
  ]
  ```
      
### GET [/joke/{id}](http://localhost:8088/joke/1)
Endpoint used to fetch a specific seen joke by it's id

### Path Parameters:
  - id -> The desired joke id

#### Query Parameters:
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)

#### Return:
  Json object with the following format:
  ```javascript
  {
    "id": 1,
    "joke": "Sample joke"
  }
  ```     
  
### GET [/joke/{id}/rate](http://localhost:8088/joke/1/rate)
Endpoint used get all rates of a seen joke

### Path Parameters:
  - id -> The desired joke id

#### Query Parameters:
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)

#### Return:
  Json object with the following format:
  ```javascript
  [
    {
      "id": 1,
      "joke": "Sample joke"
    }
  ]
  ```  
  
### POST /joke{id}/rate
Endpoint used rate a seen joke

### Path Parameters:
  - id -> The desired joke id

#### Json Body:
  - rating -> Your rating to the joke
  - comment -> OPTIONAL. Some commentary about the joke
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)

  Example body:
  ```javascript
  {
    "rating": 4,
    "comment": "The best joke I have seen in my whole life"
  }
  ```  

#### Return:
  Empty response    
  
### GET [/summary](http://localhost:8088/summary)
Return a summary with the top 5 jokes per category and a list with the unrated seen jokes. \
The unseen jokes might me paginated, based on the amount of unrated jokes seen.

#### Query Parameters:
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)
  - page -> OPTIONAL. Used to navigate throw the pages of the unrated seen jokes
  - size -> OPTIONAL. Defines the max size of the unrated jokes seen list. Default: 10, Max: 30

### GET [/summary/unrated](http://localhost:8088/summary/unrated)
Get only the unrated jokes seen portion of the summary

#### Query Parameters:
  - lang -> OPTIONAL. Defines the language of the joke. The default value is 'en'(english)
  - page -> OPTIONAL. Used to navigate throw the pages
  - size -> OPTIONAL. Defines the max size of the joke list. Default: 10, Max: 30
  
### GET [/categories](http://localhost:8088/categories)
List all the available categories

#### Return:
  Json object with the following format:
  ```javascript
  [
    "Any",
    "Misc",
    "Programming",
    "Dark",
    "Pun",
    "Spooky",
    "Christmas"
  ]
  ```  
