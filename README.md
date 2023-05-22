## Blog-Backend-Service

<br />
<div align="center"> 
<img src="src/main/resources/backend-development.jpeg" alt="Logo">
</div>

Currently, at HFTM in Grenchen, I am attending the Distributed Systems course, where I am learning how to develop a backend blog system.

<!-- Table of Contents -->
<details>
  <summary><bold>Table of Contents<bold></summary>
  <ol>
    <li><a href="#Introduction">Introduction</a></li>
    <li><a href="#Description of Individual Files">Description of Individual Files</a></li>

  </ol>
</details>

 -------------------------------------------------------------------------------------------------------
 ## Introduction


Welcome to this example project for a blog backend service, implemented with Quarkus and RESTEasy Reactive. In this project, I have created a RESTful interface through which blog posts can be managed.

To start the project, you need to clone the repository and navigate to the project folder in the terminal. There, enter the command ./mvnw quarkus:dev to start the service in development mode. If everything is configured correctly, the service should be accessible at http://localhost:8080.

 -------------------------------------------------------------------------------------------------------
 ## Description

 **Boundary**: 

 This layer is the interface between the system and users or external systems. It is responsible for accepting input from users or external systems and returning appropriate responses. In a web service, for example, the boundary classes could be the REST controllers that handle HTTP requests.

**Control**: 

This layer contains the business logic of the application. It processes the data received from the boundary layer, interacts with the entity and repository classes to read and write data, and returns results to the boundary layer. Control classes typically represent use cases or operations that the system can perform.

**Entity**:

This layer represents the data models or "things" in the application, often in the form of classes or objects. They usually contain data and sometimes behaviors closely associated with these data. In many applications, entity classes correspond to tables in a database and the objects or records stored in these tables.

**Repository**:

This layer is responsible for accessing the data source (usually a database), retrieving and storing entities, and performing queries. Repository classes hide the details of data access technologies and provide a more abstract or object-oriented interface to the rest of the application.

Together, these four layers provide a clear structure and separation of responsibilities in the application, making the code better organized and easier to understand, test, and maintain.

 -------------------------------------------------------------------------------------------------------