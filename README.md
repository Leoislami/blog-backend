## Blog-Backend-Service

<br />
<div align="center"> 
<img src="src/main/resources/backend-development.jpeg" alt="Logo">
</div>

Currently, at HFTM in Grenchen, I am attending the Distributed Systems course, where I am learning how to develop a backend blog system.

<!-- Table of Contents -->
<details>
  <summary><strong>Table of Contents</strong></summary>
  <ol>
    <li><a href="#Introduction">Introduction</a></li>
    <li><a href="#Description of Individual Files">Description of Individual Files</a></li>
    <li><a href="#API Overview">API Overview</a>
      <ul>
        <li><a href="#Description">Description</a></li>
        <li><a href="#Entities">Entities</a></li>
        <li><a href="#Endpoints">Endpoints</a></li>
      </ul>
    </li>
    <li><a href="#Access Concept">Access Concept</a>
      <ul>
        <li><a href="#General">General</a></li>
        <li><a href="#Roles">Roles</a></li>
        <li><a href="#Access Rights">Access Rights</a></li>
        <li><a href="#Users">Users</a></li>
      </ul>
    </li>
  </ol>
</details>

 -------------------------------------------------------------------------------------------------------
 ## Introduction


Welcome to this example project for a blog backend service, implemented with Quarkus and RESTEasy Reactive. In this project, I have created a RESTful interface through which blog posts can be managed.

To start the project, you need to clone the repository and navigate to the project folder in the terminal. There, enter the command ./mvnw quarkus:dev to start the service in development mode. If everything is configured correctly, the service should be accessible at http://localhost:8080.

 -------------------------------------------------------------------------------------------------------
 ## DescriDescription of Individual Filesption

 **Boundary**: 

 This layer is the interface between the system and users or external systems. It is responsible for accepting input from users or external systems and returning appropriate responses. In a web service, for example, the boundary classes could be the REST controllers that handle HTTP requests.

**Control**: 

This layer contains the business logic of the application. It processes the data received from the boundary layer, interacts with the entity and repository classes to read and write data, and returns results to the boundary layer. Control classes typically represent use cases or operations that the system can perform.

**Entity**:

This layer represents the data models or "things" in the application, often in the form of classes or objects. They usually contain data and sometimes behaviors closely associated with these data. In many applications, entity classes correspond to tables in a database and the objects or records stored in these tables.

**Repository**:

This layer is responsible for accessing the data source (usually a database), retrieving and storing entities, and performing queries. Repository classes hide the details of data access technologies and provide a more abstract or object-oriented interface to the rest of the application.

Together, these four layers provide a clear structure and separation of responsibilities in the application, making the code better organized and easier to understand, test, and maintain.

**DTOs and Mappers**:

DTOs (Data Transfer Objects) are used to pass data between processes or across layers of an application. They are generally used for transferring data over a network or for sending data from your domain model to a view. Mappers are interfaces responsible for mapping the DTOs to the Entity objects and vice versa. This simplifies the conversion between these two types of objects and keeps the code clean and organized.

Together, these layers provide a clear structure and separation of responsibilities in the application, making the code better organized and easier to understand, test, and maintain.

 -------------------------------------------------------------------------------------------------------

# API Overview


### Description
Our blog service API is designed to provide an interactive platform for content consumption and creation. As a guest, one can view blog entries, familiarize themselves with the range of topics, styles, and perspectives available. As an author, one has the ability to create and publish blog entries, providing an opportunity for self-expression and discussion. Additionally, the service offers registration options, allowing visitors to transition into contributing members of the community.

 -------------------------------------------------------------------------------------------------------

### Entities

The system revolves around three key entities: Entry, Author, and Comment.

The first entity, 'Entry', is designed to represent a blog post. This entity encapsulates vital information such as title, content, likes, and comments. It effectively encapsulates what a blog post signifies in the digital world.

The second entity is the 'Author'. This entity carries personal details including firstName, lastName, accountName, and crucially, a list of entries that the author has created. This entity establishes a clear relationship between authors and their blog entries, enabling efficient data organization.

The third entity is 'Comment'. This allows the users to interact with the blog posts by leaving their opinions, queries or thoughts. Each comment is linked to a specific entry and an author, promoting a clear and organized structure.

The blog system, as suggested by its name, functions like a typical blog application, maintaining a clear and coherent connection between these three entities.

 -------------------------------------------------------------------------------------------------------

### Endpoints

The API defines several endpoints to interact with the system:
```sh
GET: This fetches all entries, authors, or comments, offering a comprehensive view of the database.
```
```sh
POST: This method is used to create a new entry, author, or comment, providing an effective way to add content.
```
```sh
GET /{id}: This retrieves a specific entry, author, or comment, enabling precise data access.
```
```sh
PUT /{id}: This method is used for updating the attributes of an existing entry, author or comment, thereby ensuring data accuracy and relevance.
```
```sh
DELETE/{id}: This deletes a specific entry, author, or comment, ensuring the integrity and relevancy of the data in the database.
```

Through these endpoints, the blog system offers an extensive and functional interface for users to read, create, update, and manage content.

 -------------------------------------------------------------------------------------------------------

## Access Concept

### General

All GET endpoints, excluding sensitive ones such as authentication, are public, meaning no authentication is required to access them. However, all other methods are private, necessitating authentication for access.

### Roles

Three roles are envisioned within the system:

1. Author
2. User
3. Admin

## Access Rights

Guests are only granted access to GET methods, thereby ensuring they can view content but not alter it. Authors or users, once authenticated, have full control over their own entries, able to use all methods applicable to their content.

If a user is not the author of a dataset, they need to authenticate successfully to add comments to an existing entry, while the control over the main content of the entry remains with the original author.

Administrators are granted direct access to all methods, barring delete and patch methods, providing them substantial control and management potential.

## Users

Currently, the following users exist, each with their designated role:

1. leonis: Admin, Author, User
2. alice: Author, Admin
3. jack: User


The users' interaction with the system and the actions they can perform are regulated by their assigned roles, ensuring a well-structured, manageable, and efficient system


 -------------------------------------------------------------------------------------------------------