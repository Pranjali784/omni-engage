

````markdown
# 🌐 Omni-Engage Platform

**Omni-Engage** is a demo **Omni-channel engagement system** that enables agents to manage customer conversations across multiple platforms — Email, Facebook, Twitter, Instagram, WhatsApp, and more.

---

## 🧩 System Architecture

The platform consists of **3 main components**:

| Component | Description | Technology | Port |
|------------|--------------|-------------|------|
| 🧑‍💻 **Agent Service** | Handles conversations, agents, and messages | Spring Boot | `8081` |
| 📥 **Ingestion Service** | Handles incoming requests from multiple channels and routes them to agents | Spring Boot | `8080` |
| 💬 **Agent Dashboard** | Web UI for agents to manage conversations and agents | React | `3000` |
| 🗄️ **MySQL Database** | Stores conversation, agent, and message data | MySQL | `3307` |

---

## 🚀 Run Locally (Without Docker)

### 1️⃣ Agent Service
```bash
cd agent-service
cp src/main/resources/application-example.properties src/main/resources/application.properties
./mvnw spring-boot:run
````

### 2️⃣ Ingestion Service

```bash
cd ingestion-service
cp src/main/resources/application-example.properties src/main/resources/application.properties
./mvnw spring-boot:run
```

### 3️⃣ Agent Dashboard (Frontend)

```bash
cd agent-dashboard
npm install
npm start
```

### 4️⃣ MySQL

Make sure your MySQL server is running locally with:

```
Database: omni_engage
User: omni
Password: omni123
```

---

## 🐳 Run Using Docker (Recommended)

### Prerequisites

* Install [Docker Desktop](https://www.docker.com/products/docker-desktop/)
* Ensure Docker Engine is running

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/Pranjali784/omni-engage.git
cd omni-engage

# 2. Copy example env file
cp .env.example .env

# 3. Build and start all containers
docker compose up -d --build
```

### Verify

Once all containers are up:

| Service                     | URL                                                                            |
| --------------------------- | ------------------------------------------------------------------------------ |
| 💬 Frontend                 | [http://localhost:3000](http://localhost:3000)                                 |
| 🧑‍💻 Agent API             | [http://localhost:8081](http://localhost:8081)                                 |
| 📥 Ingestion API            | [http://localhost:8080](http://localhost:8080)                                 |
| ❤️ Health Check (Agent)     | [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health) |
| ❤️ Health Check (Ingestion) | [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) |

### Stop All Containers

```bash
docker compose down
```

If you want to clear MySQL data (reset DB):

```bash
docker compose down -v
```

---

## ⚙️ Tech Stack

**Backend:** Java, Spring Boot, JPA/Hibernate
**Frontend:** React, Axios
**Database:** MySQL
**DevOps:** Docker, Docker Compose
**Version Control:** Git + GitHub
**Build Tools:** Maven & npm

---

## 🧠 Features

✅ Agent management (availability, status)
✅ Conversation creation, closing, reopening
✅ Message history per channel
✅ Filtering by agent, channel, or status
✅ Real-time updates (polling every 10s)
✅ Actuator health checks for monitoring
✅ Fully containerized microservices

---

## ☁️ Future Enhancements

* Deploy on **AWS ECS / EKS (Kubernetes)**
* Add **Redis caching**
* Integrate **JWT Authentication**
* Implement **WebSockets** for live chat updates
* Add **CI/CD with GitHub Actions**

---

## 🧑‍💼 Author

**Pranjali Srivastava**
💼 Java Developer | 💬 Full-Stack Enthusiast
📍 Chennai, India
📧 [Connect on LinkedIn](https://www.linkedin.com/in/pranjali784/)

---


---

```
