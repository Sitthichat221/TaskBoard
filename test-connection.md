# Database Connection Test Guide

## 1. Health Check Test
Test the health check endpoint to verify database connection:

```bash
curl https://your-backend-url.onrender.com/actuator/health
```

Expected response:
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    }
  }
}
```

## 2. API Endpoints Test

### Test User Registration:
```bash
curl -X POST https://your-backend-url.onrender.com/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Test User Login:
```bash
curl -X POST https://your-backend-url.onrender.com/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

## 3. Database Connection Test

### Using psql (if available):
```bash
psql "postgresql://taskboard_db_x1dh_user:gIK0Jo6B0tkwS0Hrg7yuhvhDey0cDa91@dpg-d363noeuk2gs738pu96g-a.singapore-postgres.render.com/taskboard_db_x1dh"
```

### Test SQL queries:
```sql
-- Check if tables exist
\dt

-- Check users table
SELECT * FROM users LIMIT 5;

-- Check tasks table
SELECT * FROM tasks LIMIT 5;
```

## 4. Browser Test

1. Open browser
2. Go to: `https://your-backend-url.onrender.com/actuator/health`
3. Should see JSON response with "status": "UP"

## 5. Frontend Test

1. Open frontend URL
2. Try to register a new user
3. Try to login
4. Check if data is saved/retrieved correctly

## Troubleshooting

### If health check fails:
- Check Cloud Render logs for errors
- Verify environment variables are set correctly
- Check database status in Cloud Render dashboard

### If API calls fail:
- Check CORS configuration
- Verify JWT secret key is set
- Check application logs for specific errors

### If database connection fails:
- Verify database URL format
- Check database credentials
- Ensure database is running and accessible

