# Feedback Game Api in Java

## Requirements
Install :

[Java JDK8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Api Test
- Run microfeedback-0.0.1-SNAPSHOT.jar available in target
- Open a browser and clic on [API Documentation](http://localhost:9090/swagger-ui.html#/), you should be able to find the API documentation
- You can access to the h2 database : [H2 Database] (http://localhost:9090/h2-console/), please enter `http://localhost:9090/h2-console/` in the JDBC URL and Connect

- Download the Postman test collection => [Postman Test Collection](https://www.getpostman.com/collections/6ae54ad0dce5928f6a46)
Before running the test collection, please restart the api, the data will be insert to provide an interesting test environment.


## Documentations
You can find the API documentation generated using Swagger 2 => [API Documentation](http://localhost:9090/swagger-ui.html#/)
you will find all the routes, payloads documentation and the model documentation.


** Maven and Spring Boot: **
I used maven to import spring-boot-starter-parent as parent. Its permits to not manage all the dependencies version and compatibilities
Maven will also manage to import the artifacts:
- spring-boot-starter-web which will import hibernate to manage data, tomcat to run the app without having to deploye the code, logging for the logs and Jackson to parse JSON and link Java classes and Json content
- spring-boot-starter-data-jpa, which will import jpa to permit to use JPA annotation.
- h2, which is a light data base. I choose it because it is easy to set, Spring Boot auto-setup the data base, the connection to it. It uses a data.sql file to create and initialize the context with data.
- Swagger, to create an in-code documentation of all API and Model, it needs to be setup by SwaggerConfig (com.game.microfeedback.configuration)

** Model and Database: **
My data base is created based on the @Entity annotation on each classes of my model (com.game.microfeedback.model).
We have:
- Session, represents a game session identified by its id
- User, identified by its id
- Feedback, which contains a rating, a comment, and the ids of the Session and the User of the Feedback

I used Spring annotation to limit the rating from 1 to 5 and to define a unique constraints on User.id and Session.id. This will avoid to create multiple feedback for the same session and same user
@ManyToOne annotation on User and Session attributes permits to define the relation between the tables.

** Controller Layer: **
Each class of the model gets its own controller. It permits to define the link between Rest Route and call to the Database.
I tried to keep any business definition in the Service Layer.

** Dao Layer **
My Dao classes all extends JPARepository interface. This permits to Spring to deduct the query for common actions like findById, findAll.
It not need to create DaoImpl classes with JPARepository.
To manage the sorting I used Pageable object to set on which i want it sorted and how many result, this permits to manage the HTTP endpoint for the last 15 feedbacks

** Service Layer: **
To allow the filtering by rating, I created a service called by my controller to sort the list of the last 15 feedbacks by rating by using Comparator object.
I only created a service for Feedback, as Session and User only used classic CRUD process, they did not need business logic.

** Exceptions: **
Exceptions are raised when we cannot find an element in database and when the param of the route /feedbacks are not corrects.



