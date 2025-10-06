

-----

````markdown
# 🌐 Omni-Engage Platform

Omni-Engage is a demo omni-channel engagement system that enables agents to manage customer conversations across multiple platforms — Email, Facebook, Twitter, Instagram, WhatsApp, and more.

---

## 🛠️ System Architecture

The platform consists of 3 main components:

| Component | Description | Technology | Port |
| :--- | :--- | :--- | :--- |
| 🔼 **Agent Service** | Handles conversations, agents, and messages | Spring Boot | `8081` |
| ▶️ **Ingestion Service** | Handles incoming requests from multiple channels and routes them to agents | Spring Boot | `8080` |
| 💻 **Agent Dashboard** | Web UI for agents to manage conversations and agents | React | `3000` |
| 🛢️ **MySQL Database** | Stores conversation, agent, and message data | MySQL | `3307` |

---

## 🚀 Run Locally (Without Docker)

### 1️⃣ Agent Service
```bash
cd agent-service
# Copy the example properties file to create your own configuration
cp src/main/resources/application-example.properties src/main/resources/application.properties
# Run the service
./mvnw spring-boot:run
````

### 2️⃣ Ingestion Service

```bash
cd ingestion-service
# Copy the example properties file to create your own configuration
cp src/main/resources/application-example.properties src/main/resources/application.properties
# Run the service
./mvnw spring-boot:run
```

### 3️⃣ Agent Dashboard (Frontend)

```bash
cd agent-dashboard
npm install
npm start
```

### 🐘 MySQL

Make sure your MySQL server is running locally with the following credentials:

```text
Database: omni_engage
User: omni
Password: omni123
```

-----

## 🐳 Run Using Docker (Recommended)

### Prerequisites

  - Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)
  - Ensure the Docker Engine is running

### Steps

1.  **Clone the repository**

    ```bash
    git clone [https://github.com/Pranjalj704/omni-engage.git](https://github.com/Pranjalj704/omni-engage.git)
    cd omni-engage
    ```

2.  **Copy the example environment file**

    ```bash
    cp .env.example .env
    ```

    *Note: You may need to modify the `.env` file with your specific configurations.*

3.  **Build and start all containers**

    ```bash
    docker compose up -d --build
    ```

