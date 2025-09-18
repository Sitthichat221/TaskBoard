# TaskBoard Backend - Cloud Render Deployment Guide

## Prerequisites
- GitHub repository with your code
- Cloud Render account
- PostgreSQL database (can be created through Cloud Render)

## Deployment Steps

### 1. Prepare Your Repository
Make sure your repository contains:
- `Dockerfile` (already configured)
- `render.yaml` (already configured)
- `application-prod.properties` (already configured)
- `env.example` (template for environment variables)

### 2. Deploy to Cloud Render

#### Option A: Using render.yaml (Recommended)
1. Connect your GitHub repository to Cloud Render
2. Cloud Render will automatically detect the `render.yaml` file
3. The service and database will be created automatically

#### Option B: Manual Setup
1. Create a new Web Service in Cloud Render
2. Connect your GitHub repository
3. Set the following environment variables:

**Required Environment Variables:**
```
SPRING_PROFILES_ACTIVE=prod
PORT=8080
DATABASE_URL=<your-database-connection-string>
DATABASE_USERNAME=<your-database-username>
DATABASE_PASSWORD=<your-database-password>
JWT_SECRET_KEY=<your-jwt-secret-key>
CORS_ALLOWED_ORIGINS=<your-frontend-domain>
HIBERNATE_DDL_AUTO=validate
```

### 3. Database Setup
1. Create a PostgreSQL database in Cloud Render
2. The database connection details will be automatically provided as environment variables
3. The application will automatically create tables on first startup

### 4. Environment Variables Configuration

#### Database Variables (Auto-provided by Cloud Render)
- `DATABASE_URL`: Connection string to your PostgreSQL database
- `DATABASE_USERNAME`: Database username
- `DATABASE_PASSWORD`: Database password

#### Application Variables (Set manually)
- `JWT_SECRET_KEY`: Generate a secure random string (recommended: 64+ characters)
- `CORS_ALLOWED_ORIGINS`: Comma-separated list of allowed frontend domains
- `JWT_EXPIRATION_TIME`: JWT token expiration time in milliseconds (default: 3600000 = 1 hour)

### 5. Health Check
The application includes health check endpoints:
- Health check: `/actuator/health`
- Info endpoint: `/actuator/info`

### 6. Security Considerations
- The application runs as a non-root user in the container
- JWT secret key should be strong and unique
- CORS origins should be properly configured
- Database connections use SSL in production

### 7. Monitoring
- Health checks are configured in the Dockerfile
- Application logs are available in Cloud Render dashboard
- Database performance can be monitored through Cloud Render

## Troubleshooting

### Common Issues:
1. **Database Connection Failed**: Check if database is created and environment variables are set
2. **CORS Errors**: Verify `CORS_ALLOWED_ORIGINS` includes your frontend domain
3. **JWT Token Issues**: Ensure `JWT_SECRET_KEY` is set and consistent
4. **Health Check Fails**: Check application logs for startup errors

### Logs:
- View application logs in Cloud Render dashboard
- Check database connection logs
- Monitor health check status

## Production Checklist
- [ ] Database created and accessible
- [ ] Environment variables configured
- [ ] JWT secret key generated
- [ ] CORS origins configured
- [ ] Health check passing
- [ ] Application accessible via public URL
- [ ] Frontend can connect to backend API
