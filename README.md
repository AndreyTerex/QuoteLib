
# QuoteLib - A Quote Library Web Application

QuoteLib is a full-featured web application built with Java and the Spring MVC framework. It allows users to manage a collection of quotes, which are organized by category. The application is fully containerized with Docker and designed for easy setup and deployment.

## Features

- **Full CRUD Operations:** Create, Read, Update, and Delete quotes.
- **Category Management:** Assign a category to each quote.
- **Pagination:** The main list of quotes is paginated for easy navigation.
- **Robust Error Handling:** Implements both local form validation and a global exception handler for a smooth user experience.
- **Clean UI:** A simple and clean user interface styled with CSS.
- **Containerized:** Uses Docker and Docker Compose for a consistent and reliable runtime environment.

## Technology Stack

- **Backend:** Java 17, Spring MVC 6, Spring Framework
- **Frontend:** Thymeleaf, HTML, CSS
- **Database:** PostgreSQL
- **Build/Runtime:** Maven (as part of the Docker build), Docker, Docker Compose

## Prerequisites

To run this project, you must have the following installed on your local machine:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## How to Run the Application

Running the application is straightforward thanks to Docker Compose. Follow these steps:

1.  **Clone the repository** to your local machine.

2.  **Navigate to the root directory** of the project in your terminal.

3.  **Run the application using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    - The `--build` flag tells Docker Compose to build the application image from the `Dockerfile` before starting the services.
    - This command will start two containers: `quote-db` (the PostgreSQL database) and `quote-app` (the Spring application).
    - The application container will wait until the database is fully initialized and healthy before starting up.

4.  **Access the application:**
    Once the services are running, open your web browser and navigate to:
    [http://localhost:8080/](http://localhost:8080/)

    You will be automatically redirected to the main quotes list at `http://localhost:8080/quotes`.

## How to Stop the Application

To stop the running containers, press `Ctrl + C` in the terminal where `docker-compose up` is running. To remove the containers and the database volume, you can run:

```bash
docker-compose down -v
```

