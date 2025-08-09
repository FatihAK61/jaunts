# 🌍 Jaunts - Travel Application Backend

[![Java](https://img.shields.io/badge/Java-17+-orange?style=flat-square&logo=java)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat-square&logo=apache-maven)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Supported-2496ED?style=flat-square&logo=docker)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=flat-square&logo=postgresql)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](https://opensource.org/licenses/MIT)

> A robust, scalable backend service for a comprehensive travel application built with Java Spring Boot. Perfect for
> managing bookings, itineraries, and travel-related operations with enterprise-grade reliability.

## 📋 Table of Contents

- [🛠️ Tech Stack](#️-tech-stack)
- [📋 Prerequisites](#-prerequisites)
- [🚀 Quick Start](#-quick-start)
- [🐳 Docker Setup](#-docker-setup)
- [🗄️ Database Configuration](#️-database-configuration)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)
- [📞 Contact](#-contact)

## 🛠️ Tech Stack

| Technology        | Purpose               | Version |
|-------------------|-----------------------|---------|
| **Java**          | Programming Language  | 17+     |
| **Spring Boot**   | Application Framework | 3.x     |
| **Maven**         | Dependency Management | 3.6+    |
| **PostgreSQL**    | Database              | Latest  |
| **Docker**        | Containerization      | Latest  |
| **JPA/Hibernate** | ORM                   | Latest  |

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your system:

```bash
☑️ Java JDK 17 or higher
☑️ Maven 3.6+
☑️ Docker & Docker Compose
☑️ PostgreSQL (if running locally)
☑️ Git
```

### Verify Installation

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Docker version
docker --version
docker-compose --version
```

## 🚀 Quick Start

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/FatihAK61/jaunts.git
cd jaunts
```

### 2️⃣ Build the Project

```bash
# Clean and install dependencies
mvn clean install
```

### 3️⃣ Run Locally

**Option A: Using Maven**

```bash
mvn spring-boot:run
```

**Option B: Using JAR**

```bash
java -jar target/jaunts-1.0.0.jar
```

🎉 **The application will be available at:** `http://localhost:1461`

## 🐳 Docker Setup

### Quick Deploy with Docker Compose (Recommended)

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

### Manual Docker Build

```bash
# Build the Docker image
docker build -t jaunts:latest .

# Run the container
docker run -p 1461:6161 jaunts:latest
```

### Docker Services

| Service        | Port | Description         |
|----------------|------|---------------------|
| **jaunts-app** | 1461 | Main application    |
| **postgres**   | 5432 | PostgreSQL database |

## 🗄️ Database Configuration

### For Local Development

Update `src/main/resources/application.properties`:

```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/traveldb
spring.datasource.username=yourusername
spring.datasource.password=yourpassword

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Docker Environment

When using Docker Compose, the database configuration is handled automatically through environment variables defined in
`docker-compose.yml`.

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add some amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Guidelines

- Follow Java coding standards
- Write unit tests for new features
- Update documentation as needed
- Use meaningful commit messages

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

```
MIT License - feel free to use this project for personal and commercial purposes.
```

## 📞 Contact

Got questions or suggestions? We'd love to hear from you!

- 🐛 **Issues**: [GitHub Issues](https://github.com/FatihAK61/jaunts/issues)
- 💬 **Discussions**: [GitHub Discussions](https://github.com/FatihAK61/jaunts/discussions)

---

<div align="center">

**⭐ If you find this project helpful, please give it a star! ⭐**

Made with ❤️ by Fatih AK

[⬆ Back to Top](#-jaunts---travel-application-backend)

</div>