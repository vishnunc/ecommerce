# spring-ecommerce
## 20-Dec-2019:: Pipeline is triggered based on this edit
##
#
Introductory E-Commerce Web App built on Spring 3.2 using JavaConfig.
## Technology Stack

|  Technology           | Version           |
| --------------------- | ----------------- |
|   JDK                 | 1.7               |
|   IntelliJ Idea       | 13.0 Enterprise   |
|   Maven               | 3.0.+             |
|   Git                 | 1.8               |
|   Spring              | 3.2.5.RELEASE     |
|   Spring Security     | 3.2.0.RELEASE     |
|   Hibernate           | 4.1.0.Final       |
|   H2 SQL Embedded DB  | 1.3.160           |
|   log4j               | 1.2.16            |
|   JUnit               | 4.11              |
|   Tomcat              | 7.0.47            |
|   Thymeleaf           | 2.1.2.RELEASE     |
#
Theming and direction was taken from the [Spring Tutorials](https://spring.io/guides/tutorials/web/) on *Implementing
 a Web Application*.

Project architecture uses DAO Pattern and Life Preserver Pattern to separate Core Domain concerns from Integration
components.

```
java

    + config // Spring Configuration
    |
    + core
    |   |
    |   + domain // Value Objects (Maps Persistence VO to Service VO)
    |   |
    |   + service // System Service layer used by the different system components to access the core
    |
    + persistence // The main package handling persistence through Hibernate using the DAO Pattern
    |   |
    |   + domain // Value Objects (Maps Persistence VO to DB)
    |   |
    |   + repository // DAO Contracts and Implementations
    |
    + web
        |
        + controller // Package for the Spring MVC Controllers
        |
        + domain // Value Objects that are mapped to the View (Maps Service VO to View VO)
```
## 3. Configuration

There is an ```import.sql``` in the production resources folder. It contains SQL queries to populate the database with
default values.

This includes the users:
* acme (Company user)
* johnd (Person user)

### 3.1 Build Project

```
mvn clean package
```

### 3.2 Build and Deploy Project into Embedded Tomcat Server

```
mvn clean
mvn tomcat7:run-war
```
