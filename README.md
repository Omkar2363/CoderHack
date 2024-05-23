
## CoderHack(coding platform) LeaderBoard APIs :

Welcome to the CoderHack Leaderboard API.
This is a RESTful API service built with Spring Boot to manage the leaderboard for the coding platform (CoderHack). It uses MongoDB to persist user data.
 
## Requirements

- Java   : Java 8 or higher version required
- Gradle : Spring Boot Gradle Plugin 3.2.5 used
- MongoDB: version 6.0.8 used


## Setup 

To run the application follow the following steps :

#### 1. Clone the repository :

    https://github.com/Omkar2363/CoderHack.git

#### 2. Navigate to the project directory :

    cd CoderHack

#### 3. Build the project :

    gradle build 

#### 4. Run the application :

    gradle bootrun
## EndPoints

  The API will be available at "http://localhost:8080/coderHack"

- `GET /users`: Retrieve a list of all registered users.
- `GET /users/{userId}`: Retrieve the details of a specific user.
- `POST /users`: Register a new user to the contest.
- `PUT /users/{userId}`: Update the score of a specific user.
- `DELETE /users/{userId}`: Deregister a specific user from the contest. 
## Postman Collection

A Postman collection has been included to test the API endpoints. Import the collection into Postman and start testing the API.

https://www.postman.com/omkar2363/workspace/coderhack/collection/28208818-4dda2964-fde4-42c9-8980-d04947cd6a38?action=share&creator=28208818

## Testing

The project includes basic JUnit test cases to verify the API operations. You can run the tests using the following command:

    gradle test
## Project Structure

The project follows a layered architecture approach:

- `entity` : Contains the User entity class.
- `controller`: Handles incoming HTTP requests and responses.
- `service`: Contains the business logic for managing users and leaderboards.
- `repository`: Interacts with the MongoDB database.
- `exception` : Contains the custom exceptions and exception handler classes. 
## Validation and Error Handing

- Basic validation is implemented for all fields (e.g., Score > 0).
- Common errors are handled, and appropriate HTTP status codes are returned (e.g., 404 for User not found).

## Badges

Badges are awarded based on the user's score:

- 1 <= Score < 30 : Code Ninja
- 30 <= Score < 60 : Code Champ
- 60 <= Score <= 100 : Code Master

Note : A user can have a maximum of three unique badges.

## Licence

All the copy rights belongs to Omkar Yadav.