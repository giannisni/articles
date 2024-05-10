## Preparing the Application

Before running the application with Docker Compose, you need to build the executable JAR file using Maven. Follow these steps to prepare your application:

1. **Install Java and Maven:**
    - Ensure that Java JDK 17 and Maven are installed on your machine.
    - You can check the installations by running `java -version` and `mvn -version` in your terminal.

2. **Build the Application:**
    - Navigate to the root directory of your project where the `pom.xml` file is located.
    - Run the following Maven command to clean any previous builds and compile a new JAR file:

      ```bash
      mvn clean install
      ```

   This command compiles the application and packages it into a JAR file located in the `target/` directory of your project. It also runs any tests associated with the project and ensures that all dependencies are properly resolved.

## Running the Application with Docker Compose

Once you have built the JAR file, you can run the application along with its PostgreSQL database using Docker Compose. Follow these steps:

1. **Build and Start Services:**
    - Ensure you are in the root directory of the project where the `docker-compose.yml` file is located.
    - Use the following command to build the Docker image for the application and start the services:

      ```bash
      docker-compose up --build
      ```

   This command builds the Docker image if it hasn't been built or if there are changes, and starts the containers defined in the `docker-compose.yml` file.

2. **Access the Application:**
    - With the containers running, the Articles API will be accessible at `http://localhost:8080/`.
    - Use the API endpoints to interact with the application as described in the API documentation.

3. **Stopping the Services:**
    - To stop and remove the Docker Compose services, use the following command:

      ```bash
      docker-compose down
      ```

   This command stops the containers, networks, and any volumes created by Docker Compose unless configured otherwise.

# Sample API Calls
This section provides examples of how to populate the database with initial data using the API. These examples will help you understand how to interact with the API to perform various actions such as registering users and creating articles.

## Register Users

To register users, you can use the following API calls. These calls assume that your application allows user registration without prior authentication:

```bash
curl -X POST http://localhost:8080/api/users/register -H "Content-Type: application/json" -d '{
    "username": "user1",
    "password": "pass1"
}'
```

## Create Articles

After registering users, you can create articles by making POST requests to the articles endpoint. Ensure that you replace `user1` and `Base64EncodedPassword1` with the actual username and Base64-encoded password of the user:

```bash
curl -X POST http://localhost:8080/api/articles \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n user1:pass1 | base64)" \
  -d '{
    "title": "Exploring Spring Boot",
    "abstractText": "A deep dive into the capabilities and features of Spring Boot for building modern microservices.",
    "publicationDate": "2024-01-01T00:00:00",
    "authors": ["Marie", "Claire"],
    "tags": ["Spring Boot", "Java", "Microservices"]
}'


curl -X POST http://localhost:8080/api/articles \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n user1:pass1 | base64)" \
  -d '{
    "title": "Advanced Java Techniques",
    "abstractText": "Explore advanced Java techniques for optimizing performance and scalability.",
    "publicationDate": "2024-01-02T00:00:00",
    "authors": ["John", "Doe"],
    "tags": ["Java", "Performance", "Optimization"]
}'

curl -X POST http://localhost:8080/api/articles \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(echo -n user1:pass1 | base64)" \
  -d '{
    "title": "Microservices with Spring Boot",
    "abstractText": "Learn how to structure a scalable and maintainable microservice architecture with Spring Boot.",
    "publicationDate": "2024-01-03T00:00:00",
    "authors": ["Alice" ,"Johnson"],
    "tags": ["Microservices", "Spring Boot", "Architecture"]
}'



```

## UserController API Calls

### 1. Register a User
- **POST** `/api/users/register`
    - Body: JSON (`application/json`)
      ```json
      {
        "username": "newuser",
        "password": "password123",
      }
      ```


Each endpoint should handle JSON data and provide the appropriate HTTP status codes and responses based on the action's outcome. Proper authentication and authorization mechanisms should be in place, especially for creating, updating, and deleting resources.



# API Endpoints

## ArticleController API Calls

## Authentication
All API requests require the use of Basic Authentication. You must replace `username` and `password` with the credentials of the registered user performing the request.


### 1. Get All Articles
- **GET** `/api/articles`
- Query parameters can include `author`, `tag`, `keyword`, `month`, `year`, `page`, `size`.
- Example: `GET /api/articles?page=0&size=10&keyword=tech`

### 2. Download Articles as CSV
- **GET** `/api/articles/csv`
- Query parameters can include `ids`, `author`, `tag`, `keyword`, `month`, `year`.
- Example: `GET /api/articles/csv?tag=science`

### 3. Get Article by ID
- **GET** `/api/articles/{id}`
- Example: `GET /api/articles/1`

### 4. Create an Article
- **POST** `/api/articles`
- Body: JSON (`application/json`)
  ```json
  {
    "title": "New Article",
    "content": "Content of the article",
    "tags": ["tag1", "tag2"]
  }

### 5. Update an Article
- **PUT** `/api/articles/{id}`
    - Body: JSON (`application/json`)
      ```json
      {
        "title": "Updated Title",
        "content": "Updated content",
        "tags": ["updatedTag"]
      }
      ```

### 6. Delete an Article
- **DELETE** `/api/articles/{id}`


## CommentController API Calls

### 1. Create a Comment
- **POST** `/api/comments`
    - Body: JSON (`application/json`)
      ```json
      {
        "text": "This is a comment",
        "articleId": 1
      }
      ```

### 2. Update a Comment
- **PUT** `/api/comments/{id}`
    - Body: JSON (`application/json`)
      ```json
      {
        "text": "Updated comment text"
      }
      ```

### 3. Delete a Comment
- **DELETE** `/api/comments/{id}`

### 4. Get Comments by Article
- **GET** `/api/comments/article/{articleId}`
    - Example: `GET /api/comments/article/1`

### 5. Get Comment by ID
- **GET** `/api/comments/{id}`
    - Example: `GET /api/comments/5`
