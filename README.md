# 🌐 **Omni-Engage — Unified Customer Engagement Platform**

A modern **microservices-based omni-channel engagement system** built with:

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-blue.svg?logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Boot-3.3.x-6DB33F.svg?logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/React-18-61DAFB.svg?logo=react&logoColor=white">
  <img src="https://img.shields.io/badge/TiDB_Cloud-DistributedSQL-red.svg?logo=tidb&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-Active-blue.svg?logo=docker">
  <img src="https://img.shields.io/badge/Microservices-Architecture-critical.svg">
</p>


**Omni-Engage** provides a unified dashboard where support agents can manage conversations from **Facebook, WhatsApp, Instagram, Email, Chat, and more** — all in one place.

---

**Omni-Engage** is a next-generation **cloud-native omni-channel engagement platform** engineered for organizations that require **high availability**, **scalable microservices**, and **real-time customer interaction management**.

Powered by a modern **microservices architecture**—built on **Spring Boot**, **React**, and **TiDB Distributed SQL**—Omni-Engage consolidates customer conversations across **Facebook, WhatsApp, Instagram, Email, Chat, and Telephony** into one **unified, intelligent dashboard**.

Designed for **mission-critical customer support operations**, Omni-Engage provides:

* ⚡ **Low-latency APIs** optimized for enterprise scale
* 🔄 **High-throughput ingestion pipelines** for multi-channel workloads
* 🧠 **Intelligent routing & agent workload balancing**
* 🔍 **Advanced filtering, auditability, and analytics-ready event streaming**
* ☁️ **Production cloud deployments on Render & Vercel with CI/CD**
* 🌐 **Horizontally scalable distributed SQL backend powered by TiDB Cloud**

Inspired by platforms like **Zendesk**, **Freshdesk**, **Intercom**, and **Salesforce Service Cloud**, Omni-Engage demonstrates how leading enterprises architect **modern omni-channel systems** in 2025 and beyond.

> **Omni-Engage is not just a project — it is a complete enterprise product blueprint built using real-world architectural patterns.**

---

## 📚 **Table of Contents**

* [🧩 Architecture Overview](#-architecture-overview)
* [🚀 Getting Started](#-getting-started)
* [⚙️ Tech Stack](#️-tech-stack)
* [🧠 Core Features](#-core-features)
* [📦 Deployment Guide](#-deployment-guide)
* [📈 Future Enhancements](#-future-enhancements)
* [👩‍💼 Author](#-author)

---

# 🧩 **Architecture Overview**

Omni-Engage follows a **scalable microservices architecture** with clear separation of concerns.

### 🏗 **System Components**

| Component              | Responsibility                                              | Technology     | Deployment      |
| ---------------------- | ----------------------------------------------------------- | -------------- | --------------- |
| **Agent Service**      | Agents, conversations, messages, assignment logic           | Spring Boot 3  | Render / Docker |
| **Ingestion Service**  | Receives messages from external channels (FB, IG, WhatsApp) | Spring Boot 3  | Render / Docker |
| **Agent Dashboard**    | Web UI for agents                                           | React 18       | Vercel          |
| **Distributed SQL DB** | Stores conversations, agents, messages                      | **TiDB Cloud** | Serverless      |

### 🖼 Architecture Diagram (Conceptual)

```
   Facebook / IG / WhatsApp / Email
                │
       ┌────────▼─────────┐
       │ Ingestion Service │
       └────────┬─────────┘
                │ REST
       ┌────────▼──────────┐
       │   Agent Service    │─── TiDB Cloud (MySQL-Compatible)
       └────────┬──────────┘
                │ REST APIs
       ┌────────▼─────────┐
       │  React Dashboard  │ (Vercel)
       └───────────────────┘
```

---

# 🚀 **Getting Started**

### ✅ **Prerequisites**

* Docker Desktop (recommended)
* Node 18+
* Java 17
* Maven 3.9+
* TiDB Cloud / MySQL Database

---

## 🐳 **Run Entire System with Docker (Recommended)**

```bash
git clone https://github.com/Pranjali784/omni-engage.git
cd omni-engage

cp .env.example .env

docker compose up -d --build
```

### Access Services

| Service                    | URL                                                                            |
| -------------------------- | ------------------------------------------------------------------------------ |
| **Dashboard (Frontend)**   | [http://localhost:3000](http://localhost:3000)                                 |
| **Agent API**              | [http://localhost:8081](http://localhost:8081)                                 |
| **Ingestion API**          | [http://localhost:8080](http://localhost:8080)                                 |
| **Agent Health Check**     | [http://localhost:8081/actuator/health](http://localhost:8081/actuator/health) |
| **Ingestion Health Check** | [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) |

### Stop Services

```bash
docker compose down
```

### Reset Database

```bash
docker compose down -v
```

---

## 💻 **Run Locally Without Docker**

<details>
<summary>Click to expand</summary>

### 1️⃣ Start Agent Service

```bash
cd agent-service
cp src/main/resources/application-example.properties src/main/resources/application.properties
./mvnw spring-boot:run
```

### 2️⃣ Start Ingestion Service

```bash
cd ingestion-service
./mvnw spring-boot:run
```

### 3️⃣ Start React Dashboard

```bash
cd agent-dashboard
npm install
npm start
```

### 4️⃣ Database Config (TiDB Cloud or MySQL)

```
DB: omni_engage_db
User: omni
Password: omni123
```

</details>

---

# ⚙️ **Tech Stack**

### **Backend**

* Java 17
* Spring Boot 3.3
* JPA/Hibernate
* REST APIs
* Actuator

### **Frontend**

* React 18
* Axios
* Bootstrap / Custom CSS

### **Database**

* TiDB Cloud (Recommended: Serverless Tier)
* MySQL 8 Compatible

### **DevOps**

* Docker Compose
* Render (Backend)
* Vercel (Frontend)
* GitHub

---

# 🧠 **Core Features**

### 🎧 **Agent Management**

* Manage availability
* Auto-assignment with locking
* Prevents race conditions using SQL `FOR UPDATE`

### 💬 **Conversation Management**

* Create / Close / Reopen conversations
* Messages linked with timestamps
* Channel-specific sorting

### 🌐 **Omni-channel Support**

Works for:

* WhatsApp
* Facebook
* Instagram
* Email
* In-App Chat

### 🔍 **Filtering**

Filter conversations by:

* Channel
* Agent
* Status (OPEN / CLOSED)

### ♻ **Real-Time Updates**

Polling & auto-refresh integrated.

### 📊 **Production-Ready**

* Actuator health checks
* Environment-based configuration
* Containerized microservices

---

# 📦 **Deployment Guide**

### ✔ Frontend → Deploy on **Vercel**

Just connect the repo & set:

```
REACT_APP_AGENT_API=https://your-agent-service.onrender.com
REACT_APP_INGESTION_API=https://your-ingestion-service.onrender.com
```

### ✔ Backend → Deploy on **Render**

Use Java buildpacks or Docker.

Set environment variables:

```
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
```

### ✔ Database → Use **TiDB Cloud**

* MySQL-compatible
* Scalable
* Serverless
* Perfect for chat/real-time workloads

---

# 📈 **Future Enhancements (2025 Roadmap)**

* [ ] Replace polling with **WebSockets / SSE**
* [ ] Add **JWT Authentication**
* [ ] Deploy using **AWS ECS/EKS or Kubernetes**
* [ ] Add **Redis Caching**
* [ ] Multi-Agent Routing / Load Balancing
* [ ] AI Agent Assistant (LLM Integration)
* [ ] Real-time analytics dashboards using ClickHouse

---

# 👩‍💼 **Author**

**Pranjali Srivastava**
*Java Backend Engineer | Microservices | Cloud-Native Systems*
📍 Chennai, India

<p align="center">
  <a href="https://www.linkedin.com/in/pranjali784/" target="_blank">
    <img src="https://img.shields.io/badge/LinkedIn-Pranjali%20Srivastava-0077B5?style=for-the-badge&logo=linkedin&logoColor=white">
  </a>
</p>

---

> 🚀 *Omni-Engage represents a fully modern, scalable, production-grade microservices system built using today’s best practices.*

---
