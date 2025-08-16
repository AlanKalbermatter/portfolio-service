# 🎨 Alan Kalbermatter Portfolio API

A comprehensive REST API for managing portfolio data including projects, personal information, work experience, and skills.

## 🚀 Tech Stack

- **Backend**: Spring Boot 3.2
- **Database**: PostgreSQL (production) / H2 (development)
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **Deployment**: Railway (recommended)

## 📦 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose (optional)

### Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/AlanKalbermatter/portfolio-service.git
   cd portfolio-service
   ```

2. **Run with H2 Database (Development)**
   ```bash
   mvn spring-boot:run
   ```
   - Application runs on: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`

3. **Run with Docker Compose (PostgreSQL)**
   ```bash
   docker-compose up -d
   ```

## 🌐 API Endpoints

### Base URL
- **Development**: `http://localhost:8080`
- **Production**: `https://your-app.railway.app`

### 🚀 Projects (`/api/projects`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/projects` | Get all projects |
| `GET` | `/api/projects/featured` | Get featured projects |
| `GET` | `/api/projects/{id}` | Get project by ID |
| `GET` | `/api/projects/search?query={query}` | Search projects |
| `GET` | `/api/projects/technology/{tech}` | Projects by technology |
| `POST` | `/api/projects` | Create project |
| `PUT` | `/api/projects/{id}` | Update project |
| `DELETE` | `/api/projects/{id}` | Delete project |

### 👤 Personal Info (`/api/personal-info`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/personal-info` | Get personal information |
| `POST` | `/api/personal-info` | Create personal info |
| `PUT` | `/api/personal-info` | Update personal info |

### 💼 Experience (`/api/experiences`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/experiences` | Get all experiences |
| `GET` | `/api/experiences/current` | Get current experiences |
| `GET` | `/api/experiences/{id}` | Get experience by ID |
| `GET` | `/api/experiences/search/company?company={name}` | Search by company |
| `GET` | `/api/experiences/search/position?position={title}` | Search by position |
| `POST` | `/api/experiences` | Create experience |
| `PUT` | `/api/experiences/{id}` | Update experience |
| `DELETE` | `/api/experiences/{id}` | Delete experience |

### 🛠️ Skills (`/api/skills`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/skills` | Get all skills |
| `GET` | `/api/skills/category/{category}` | Skills by category |
| `GET` | `/api/skills/{id}` | Get skill by ID |
| `POST` | `/api/skills` | Create skill |
| `PUT` | `/api/skills/{id}` | Update skill |
| `DELETE` | `/api/skills/{id}` | Delete skill |

### 📊 Portfolio Summary (`/api/portfolio`)
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/portfolio` | Complete portfolio data |

## 📋 Data Models

### Project
```json
{
  "id": 1,
  "title": "Portfolio Website",
  "description": "Full description...",
  "shortDescription": "Brief description...",
  "imageUrl": "https://...",
  "githubUrl": "https://github.com/...",
  "liveUrl": "https://...",
  "technologies": ["React", "Spring Boot"],
  "startDate": "2024-01-01",
  "endDate": "2024-03-01",
  "isFeatured": true,
  "displayOrder": 1
}
```

### Personal Info
```json
{
  "id": 1,
  "fullName": "Alan Kalbermatter",
  "title": "Full Stack Developer",
  "email": "alan@example.com",
  "phone": "+1234567890",
  "location": "City, Country",
  "summary": "Passionate developer...",
  "linkedinUrl": "https://linkedin.com/in/...",
  "githubUrl": "https://github.com/...",
  "portfolioUrl": "https://...",
  "resumeUrl": "https://..."
}
```

### Experience
```json
{
  "id": 1,
  "company": "Tech Company",
  "position": "Senior Developer",
  "description": "Job responsibilities...",
  "startDate": "2023-01-01",
  "endDate": "2024-01-01",
  "isCurrent": false,
  "location": "Remote",
  "technologies": ["Java", "React"]
}
```

### Skill
```json
{
  "id": 1,
  "name": "Java",
  "category": "BACKEND",
  "proficiencyLevel": 90,
  "yearsOfExperience": 5
}
```

## 🚢 Deployment

### Railway Deployment (Recommended)

1. **Connect GitHub Repository**
   - Go to [Railway](https://railway.app)
   - Create new project from GitHub repo
   - Select this repository

2. **Environment Variables**
   Railway will automatically detect and set:
   - `DATABASE_URL` (PostgreSQL addon)
   - `SPRING_PROFILES_ACTIVE=railway`

3. **Database Setup**
   - Add PostgreSQL addon in Railway
   - Database will be automatically connected

4. **Deploy**
   - Railway automatically builds and deploys
   - Uses the `Dockerfile` for containerization

### Alternative: Docker Deployment

```bash
# Build image
docker build -t portfolio-service .

# Run with environment variables
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=railway \
  -e DATABASE_URL=your_postgres_url \
  portfolio-service
```

## 🛠️ Development

### Profiles
- `dev`: H2 in-memory database (default)
- `docker`: PostgreSQL with Docker Compose
- `railway`: Production PostgreSQL

### CORS Configuration
Configured for common frontend frameworks:
- React (localhost:3000)
- Angular (localhost:4200)
- Production domains (*.railway.app, *.vercel.app, *.netlify.app)

## 📝 API Usage Examples

### Frontend Integration (React/JavaScript)

```javascript
// API service
const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

const projectsApi = {
  getAll: () => fetch(`${API_BASE_URL}/api/projects`).then(res => res.json()),
  getFeatured: () => fetch(`${API_BASE_URL}/api/projects/featured`).then(res => res.json()),
  create: (data) => fetch(`${API_BASE_URL}/api/projects`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  }).then(res => res.json())
};

// Usage
const featuredProjects = await projectsApi.getFeatured();
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Alan Kalbermatter**
- GitHub: [@AlanKalbermatter](https://github.com/AlanKalbermatter)
- LinkedIn: [Alan Kalbermatter](https://linkedin.com/in/alankalbermatter)

---

Built with ❤️ using Spring Boot
