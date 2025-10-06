# ---------- Build (React) ----------
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
# Build-time API base URLs (override in docker-compose build args)
ARG REACT_APP_AGENT_API_BASE=http://localhost:8081
ARG REACT_APP_INGESTION_API_BASE=http://localhost:8080
ENV REACT_APP_AGENT_API_BASE=${REACT_APP_AGENT_API_BASE}
ENV REACT_APP_INGESTION_API_BASE=${REACT_APP_INGESTION_API_BASE}
COPY . .
RUN npm run build

# ---------- Serve with Nginx ----------
FROM nginx:1.27-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
HEALTHCHECK --interval=30s --timeout=3s --retries=5 \
  CMD wget -qO- http://localhost:80 || exit 1
