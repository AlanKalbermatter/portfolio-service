# Railway Deployment Guide

## 🚀 Environment Variables Configuration

### Required Environment Variables for Railway

Set these variables in your Railway project dashboard under the **Variables** tab:

#### **Database Configuration (Required)**
```
DATABASE_URL=postgresql://username:password@host:port/database
```
*Note: Railway will automatically provide this when you add a PostgreSQL service*

#### **Application Configuration (Required)**
```
SPRING_PROFILES_ACTIVE=railway
PORT=8080
```

#### **Security Configuration (Recommended)**
```
CORS_ALLOWED_ORIGINS=https://your-frontend-domain.vercel.app,https://your-frontend-domain.netlify.app
DDL_AUTO=update
```

#### **Logging Configuration (Optional)**
```
SQL_LOG_LEVEL=ERROR
APP_LOG_LEVEL=INFO
SECURITY_LOG_LEVEL=WARN
```

## 📋 Step-by-Step Railway Deployment

### 1. Prepare Your Repository
```bash
# Ensure all changes are committed
git add .
git commit -m "Prepare for Railway deployment"
git push origin main
```

### 2. Create Railway Project
1. Go to [Railway.app](https://railway.app)
2. Click **"New Project"**
3. Select **"Deploy from GitHub repo"**
4. Choose your `portfolio-service` repository

### 3. Add PostgreSQL Database
1. In your Railway project dashboard, click **"New Service"**
2. Select **"Database"** → **"PostgreSQL"**
3. Railway will automatically create the database and provide `DATABASE_URL`

### 4. Configure Environment Variables
In your Railway service settings → **Variables** tab, add:

**Essential Variables:**
- `SPRING_PROFILES_ACTIVE` = `railway`
- `CORS_ALLOWED_ORIGINS` = `https://your-frontend-url.com` (replace with your actual frontend URL)

**Optional but Recommended:**
- `DDL_AUTO` = `update`
- `APP_LOG_LEVEL` = `INFO`

### 5. Deploy
Railway will automatically deploy when you push to your main branch.

## 🔒 Security Best Practices

### Environment Variables to NEVER Hard-Code:
- ❌ Database passwords
- ❌ Database usernames
- ❌ Database URLs
- ❌ API keys
- ❌ JWT secrets
- ❌ CORS origins for production

### What's Now Secure:
- ✅ All database credentials are parameterized
- ✅ CORS origins are configurable
- ✅ Logging levels are configurable
- ✅ DDL auto mode is configurable
- ✅ Environment files are gitignored

## 🔍 Monitoring Your Deployment

### Health Check Endpoint
Your application exposes a health check at:
```
https://your-app-name.railway.app/actuator/health
```

### Available Monitoring Endpoints
- `/actuator/health` - Application health status
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics

## 🏗️ Local Development Setup

### 1. Create Local Environment File
```bash
cp .env.example .env
```

### 2. Edit .env with Your Local Values
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=portfolio_db
DB_USERNAME=portfolio_user
DB_PASSWORD=your_secure_password
SPRING_PROFILES_ACTIVE=docker
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:4200
```

### 3. Start with Docker Compose
```bash
docker-compose up -d
```

## 🚨 Troubleshooting

### Common Issues:

1. **Database Connection Issues**
   - Verify `DATABASE_URL` is set correctly
   - Ensure PostgreSQL service is running

2. **CORS Issues**
   - Add your frontend domain to `CORS_ALLOWED_ORIGINS`
   - Format: `https://domain1.com,https://domain2.com`

3. **Health Check Failures**
   - Check application logs in Railway dashboard
   - Verify database connectivity

### Debug Commands:
```bash
# Check environment variables
railway logs

# Connect to your database
railway connect postgresql
```

## 📝 Next Steps

1. **Set up your frontend deployment** (Vercel/Netlify)
2. **Configure CORS** with your actual frontend URL
3. **Set up custom domain** (optional)
4. **Configure monitoring** and alerts
5. **Set up CI/CD** for automated deployments
