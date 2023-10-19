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
    <li><a href="#Setup Instructions">Setup Instructions</a></li>
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
       <li><a href="#Quarkus and Containerization">Quarkus and Containerization</a>
      <ul>
        <li><a href="#Creating and Pushing Image to GitHub">Creating and Pushing Image to GitHub</a></li>
        <li><a href="#Starting the Service">Starting the Service</a></li>
        <li><a href="#Authentication Setup">Authentication Setup</a></li>
      </ul>
    </li>
       <li><a href="#Deepening Database Connection">Deepening Database Connectionn</a>
      <ul>
        <li><a href="#Configuring and Starting MySQL Database in a Container">Configuring and Starting MySQL Database in a Container</a></li>
      </ul>
    </li>
    <li><a href="#Quarkus Deep Dive">Quarkus Deep Dive</a>
      <ul>
        <li><a href="#Native Image Creation">Native Image Creation</a></li>
        <ul>
          <li><a href="#Compile and Package the Application">Compile and Package the Application</a></li>
          <li><a href="#Running the Native Docker Image">Running the Native Docker Image</a></li>
          <li><a href="#Pushing the Native Docker Image to GitHub Packages">Pushing the Native Docker Image to GitHub Packages</a></li>
          <li><a href="#Benefits of Native Images">Benefits of Native Images</a></li>
        </ul>
      </ul>
    </li>
  </ol>
</details>

 -------------------------------------------------------------------------------------------------------
 ## Introduction


Welcome to this example project for a blog backend service, implemented with Quarkus and RESTEasy Reactive. The system provides a RESTful interface for managing blog posts. In addition to the latest Java standards and tools, we also use Lombok and Mapstruct to reduce boilerplate code and MySQL for data persistence.


 -------------------------------------------------------------------------------------------------------
 ## Setup Instructions

Prerequisites:

JDK 17
Maven
MySQL


Follow these steps to set up and run the project:

Clone the repository to your local machine.


Navigate to the project folder in your terminal.


Run ```./mvnw quarkus:dev ``` to start the service in development mode.


Access the service at http://localhost:8080.


 -------------------------------------------------------------------------------------------------------
 ## Description of Individual Filesption

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

Our blog service API is designed to offer an interactive platform for content consumption and creation. Built on standard RESTful principles, it includes endpoints for managing Entries, Authors, and Comments


### Description

Our API facilitates both reading and creating content. As a guest, you can view blog entries, exploring a wide range of topics, styles, and perspectives. As an author, you gain the ability to create and publish blog entries, fostering self-expression and discussion. The service also includes registration features, enabling visitors to become contributing members of the community.

 -------------------------------------------------------------------------------------------------------

### Entities

The system revolves around three core entities: Entry, Author, and Comment.

**Entry**: 

Represents a blog post, encapsulating information like the title, content, likes, and comments.

**Author**: 

Stores personal details including the first name, last name, account name, and a list of entries created by the author. This entity maps the relationship between authors and their blog entries.

**Comment**: 

Allows users to interact with blog posts by leaving their opinions or thoughts. Each comment is tied to a specific entry and author, establishing a clear structure.

 -------------------------------------------------------------------------------------------------------

### Endpoints

The API defines several endpoints for interacting with the system:
```sh
GET /entries, GET /authors, GET /comments: Fetch all entries, authors, or comments respectively.
```
```sh
POST /entries, POST /authors, POST /comments: Create a new entry, author, or comment.
```
```sh
GET /entries/{id}, GET /authors/{id}, GET /comments/{id}: Retrieve a specific entry, author, or comment.
```
```sh
PUT /entries/{id}, PUT /authors/{id}, PUT /comments/{id}: Update an existing entry, author, or comment.
```
```sh
DELETE /entries/{id}, DELETE /authors/{id}, DELETE /comments/{id}: Delete a specific entry, author, or comment.
```

These endpoints allow users to read, create, update, and manage content in a structured and efficient manner.

 -------------------------------------------------------------------------------------------------------

## Access Concept

Our system uses role-based access control. There are four types of roles: Guest, User, Author, and Admin. Each role has different permissions.

 -------------------------------------------------------------------------------------------------------

### Publicly Accessible Methods

```GET /entries:``` Allows fetching all blog entries.
```GET /authors:``` Allows fetching all authors.
```GET /comments:``` Allows fetching all comments.

 -------------------------------------------------------------------------------------------------------

### General

All GET endpoints, excluding sensitive ones such as authentication, are public, meaning no authentication is required to access them. However, all other methods are private, necessitating authentication for access.

### Roles

Four roles are envisioned within the system:

1. ```Guest:``` A guest can access all public endpoints but needs to authenticate to create or modify content.
2. ```Author:``` An author can create new blog entries and edit or delete their own entries.
3. ```User:``` A user can add comments to blog entries but cannot create or modify entries.
4. ```Admin:``` An admin has full access to all endpoints and can edit or delete entries and comments from any user.

### Methods Requiring Login or Specific User Role

- ```POST /entries, PUT /entries/{id}, DELETE /entries/{id}:``` Require the "Author" or "Admin" role.
- ```POST /comments, PUT /comments/{id}, DELETE /comments/{id}:``` Requires the "User", "Author", or "Admin" role.
- ```POST /authors, PUT /authors/{id}, DELETE /authors/{id}:``` Require the "Admin" role.

## Access Rights

Guests are only granted access to GET methods, thereby ensuring they can view content but not alter it. Authors or users, once authenticated, have full control over their own entries, able to use all methods applicable to their content.

If a user is not the author of a dataset, they need to authenticate successfully to add comments to an existing entry, while the control over the main content of the entry remains with the original author.

Administrators are granted direct access to all methods, barring delete and patch methods, providing them substantial control and management potential.

## Users

Currently, the following users exist, each with their designated role:

1. leonis: Admin, Author, User
2. alice: Author, User
3. jack: User


The users' interaction with the system and the actions they can perform are regulated by their assigned roles, ensuring a well-structured, manageable, and efficient system


 -------------------------------------------------------------------------------------------------------

## Quarkus and Containerization

### Creating and Pushing Image to GitHub

Set up a blog network using the following command:

```sh
docker network create blog-nw
```


Generate a Container IMAGE.

Execute the following command:

```sh
./mvnw package
```

Build the Docker image using the following command:
```sh
docker build -f .\src\main\docker\Dockerfile.jvm -t ghcr.io/leoislami/blog-backend:latest .
```

Run the Docker container and add it to the blog-nw network with:

```sh
docker run --name blog-backend -p 8080:8080 --network blog-nw blog-backend
```

Push the Docker Image to GitHub!

Generate a token in GitHub under github.com > Settings > Developer Settings > Personal Access Tokens.

Log in to the registry as follows:

```sh
docker login ghcr.io -u Leoislami
```

Insert the copied token as a password.
Push the image with:

```sh
docker push ghcr.io/leoislami/blog-backend:latest 
```
 -------------------------------------------------------------------------------------------------------

### Starting the Service
Pull the image:

```sh
docker pull ghcr.io/leoislami/blog-backend:latest 
```

Run the image using docker-compose in the project directory.

```sh
docker-compose -f docker-compose.yaml up
```

A Docker network named blog-nw will be set up, running the Keycloak and the database containers with MySQL within it.

 -------------------------------------------------------------------------------------------------------

### Authentication Setup
Retrieve a token from Keycloak where the Access Token URL is:

```sh
https://app.please-open.it/auth/realms/1d2633a1-982e-4e31-a05b-ec8ff48fe146/protocol/openid-connect/token
```

The client secret, client id, username, password, and grant type are as follows:

```sh
Client Secret: Z5ZkDDci5mocnX6olY2bWy5wkXWWRMay
Client ID: backend-service
Username: leonis
Password: leonis
Grant Type: password
```

Swagger UI can now be accessed at:

```sh
http://localhost:8080/q/swagger-ui/#/
```

Insert the generated Leonis-Token in the Authentication and perform some requests.

 -------------------------------------------------------------------------------------------------------

## Deepening Database Connection

### Configuring and Starting MySQL Database in a Container

To simulate and test the database configuration accurately, the MySQL database will be initiated locally using a container. This strategy will also be applied when the database is eventually hosted in the cloud.

Since Quarkus will be running in a container as well, it is crucial to ensure that all containers are operating within the same network. If it isnt created allready till now, then the following command creates the necessary network, `blog-nw`:

```sh
docker network create blog-nw
```

To initiate the first MySQL container within this network, execute the following command:

```sh
docker run --name blog-mysql -p 3306:3306 --network blog-nw -e MYSQL_ROOT_PASSWORD=vs4tw -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbuser -e MYSQL_DATABASE=blogdb -d mysql:8.0
```

Accessing SQL Environment of the Container:


Execute the following commands to access the SQL environment of the container:

```sh
docker exec -it blog-mysql bash
mysql -u dbuser -p
show databases;
use blogdb
show tables;
```

#### Integrating MySQL with Quarkus

To enable Quarkus to communicate with the MySQL container, you first need to add the 
`quarkus-jdbc-mysql`
 dependency to your project. This can be done by adding the following line to your `pom.xml` file:
```sh
    <dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-jdbc-mysql</artifactId>
    </dependency>
```

After adding the dependency, you'll need to configure the 
`application.properties` file to specify the connection details for the MySQL container. 
Add or update the following lines:

```sh
  quarkus.hibernate-orm.database.generation=update
  quarkus.datasource.db-kind=mysql
  %dev.quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/blogdb
  %prod.quarkus.datasource.jdbc.url=jdbc:mysql://blog-mysql:3306/blogdb
  quarkus.datasource.username=dbuser
  quarkus.datasource.password=dbuser
```

With these configurations in place, this Quarkus project can now connect to the MySQL container in Dev mode, offering a seamless development experience with instant feedback on changes made to the database.

 -------------------------------------------------------------------------------------------------------

## Quarkus Deep Dive


### Native Image Creation

Quarkus offers the ability to compile applications into native executables, resulting in faster startup times and reduced memory footprint compared to traditional JVM-based applications. This is achieved using GraalVM's Ahead-of-Time (AOT) compilation.

Creating a Native Docker Image
To create a native Docker image for your application, follow the steps below:

#### Compile and Package the Application:

- Use the following command to compile your application into a native executable and package it into a Docker image:

```sh
./mvnw package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true -Dquarkus.container-image.name=blogbackend-native-image
```

#### Running the Native Docker Image:

- Once the image is created, you can run it using:

```sh
docker run -p 8080:8080 blogbackend-native-image:0.1
```

#### Pushing the Native Docker Image to GitHub Packages:

- Log in to the GitHub Container Registry:

```sh
docker login ghcr.io -u Leoislami
```
Insert the copied token as a password.
Push the image with:

- Push the Image:

```sh
docker push ghcr.io/leoislami/blogbackend-native-image:0.1

```

#### Benefits of Native Images
- `Fast Startup:` 
Native images start almost instantly, making them ideal for serverless applications and other scenarios where rapid startup is essential.

- `Reduced Memory Footprint:` 
Consumes significantly less memory compared to traditional JVM-based applications.

- `Smaller Image Size:` 
Without the need for a JVM, the Docker image size is reduced, leading to faster deployment times.


For more in-depth information on Quarkus and native images, refer to the official Quarkus guide on building native images.

 -------------------------------------------------------------------------------------------------------