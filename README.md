# Omni-Engage Platform

Omni-Engage is a demo **Omni-channel engagement system** with 3 components:

- **agent-service** → Spring Boot (port 8081)
- **ingestion-service** → Spring Boot (port 8080)
- **agent-dashboard** → React frontend (port 3000)

---

## 🚀 How to Run Locally

### 1. Agent Service
```bash
cd agent-service
cp src/main/resources/application-example.properties src/main/resources/application.properties
./mvnw spring-boot:run

cd ingestion-service
cp src/main/resources/application-example.properties src/main/resources/application.properties
./mvnw spring-boot:run

cd agent-dashboard
npm install
npm start




