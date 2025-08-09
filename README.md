Travel Application Backend
This is the backend service for a full-featured travel application built using Java Spring Boot. It handles various functionalities such as user management, booking reservations, itinerary planning, and more. The project uses Maven for dependency management and includes Docker and Docker Compose configurations for easy deployment and containerization.
Features

RESTful APIs for travel-related operations (e.g., searching hotels, booking etc.).
Integration with databases (e.g., PostgreSQL for development).
Error handling and logging.
Docker support for containerized deployment.

Prerequisites
Before you begin, ensure you have the following installed:

Java JDK 17 or higher
Maven 3.6+
Docker (for containerized setup)
Docker Compose (included with Docker Desktop)
A database server (PostgreSQL)

Getting Started
Clone the Repository
bashgit clone https://github.com/your-username/jaunts.git
cd jaunts
Build the Project with Maven
To compile and package the application:
bashmvn clean install
This will resolve all Maven dependencies and build the JAR file.
Run the Application Locally
You can run the Spring Boot application directly:
bashmvn spring-boot:run
Or, run the packaged JAR:
bashjava -jar target/travel-app-backend-1.0.0.jar
The application will start on http://localhost:8080 by default. Check the application.properties or application.yml file for configuration options like port, database URL, etc.
Database Configuration

For production, configure PostgreSQL in src/main/resources/application.properties:

textspring.datasource.url=jdbc:postgresql://localhost:5432/traveldb
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

if you dont want to this, just work with docker;

Docker Setup
Build Docker Image
First, build the Docker image using the provided Dockerfile:
bashdocker build -t jaunts:latest .
Run with Docker Compose
Use Docker Compose to spin up the application along with dependencies (e.g., database):
bashdocker-compose up -d
This will start the backend service and any linked services (e.g., PostgreSQL container). Access the app at http://localhost:8080.
To stop:
bashdocker-compose down

License
This project is licensed under the MIT License - see the LICENSE file for details.
Contact
For questions or issues, open a GitHub issue or contact me.
