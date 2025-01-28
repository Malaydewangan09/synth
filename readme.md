# Synth
<p align="center">
  <img src="assets/synth-logo.svg" alt="Synth Email Logo" width="200"/>
</p>

An AI-powered email management system that seamlessly integrates a modern web client with a robust Spring Boot backend, offering intelligent email handling and management capabilities.

## 🌟 Overview
Synth Email Suite is a comprehensive email management solution with two core components:
- **Synth Client**: A cutting-edge Next.js frontend featuring AI-powered email capabilities
- **Synth Server**: A secure Spring Boot backend providing seamless Gmail API integration

## ⚡️ Core Features

### Frontend (Synth Client)
- **AI Writing Assistant**: Generate contextual email drafts using Google Gemini
- **Smart Analytics**: 
  - Intelligent email summarization
  - Priority scoring based on content and sender
  - Email sentiment analysis
- **Enhanced UX**:
  - Lightning-fast search with advanced filtering
  - Keyboard-first navigation for power users
  - Clean, minimalist interface

### Backend (Synth Server)
- **Gmail Integration**:
  - Secure API communication
  - Real-time email synchronization
  - Draft management system
- **Security**: 
  - OAuth2 authentication flow
  - Secure session handling
  - Rate limiting and protection

## 🚀 Getting Started

### Prerequisites
- Node.js 18+
- Java 17+
- Gmail Account
- Google Cloud Project with:
  - Gmail API enabled
  - Gemini API enabled
  - OAuth 2.0 credentials configured

### Frontend Setup
```bash
# Install dependencies
cd frontend
npm install

# Configure environment
cp .env.example .env.local

# Add required environment variables
NEXT_PUBLIC_GEMINI=your_gemini_api_key

# Start development server
npm run dev
```

### Backend Setup
```bash
# Navigate to backend directory
cd backend

# Copy configuration template
cp src/main/resources/application.example.yml src/main/resources/application.yml

# Add OAuth credentials to application.yml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your_client_id
            client-secret: your_client_secret

# Run the server
./mvnw spring-boot:run
```

## 🛠️ Tech Stack

### Frontend
- Next.js 14 with React
- Tailwind CSS for styling
- Google Gemini AI integration
- shadcn/ui Component Library
- TypeScript for type safety

### Backend
- Spring Boot 3
- Spring Security with OAuth 2.0
- Gmail API integration
- PostgreSQL for data persistence
- Swagger for API documentation

## 🔒 Security Features
- OAuth 2.0 authentication flow
- CSRF protection mechanisms
- Secure cookie handling
- Environment variable management
- Rate limiting
- XSS protection

## 🐳 Docker Setup
```bash
# Build frontend
cd frontend
docker build -t synth-frontend .

# Build backend
cd ../backend
docker build -t synth-backend .

# Run services
docker-compose up
```

## 🚢 Deployment
- Frontend deployed on Vercel
- Backend hosted on AWS ECS
- Database on AWS RDS
- CI/CD through GitHub Actions

## 💻 Development

### Local Development
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- API Docs: http://localhost:8080/swagger-ui.html

### Development Best Practices
- Follow conventional commits
- Write unit tests for new features
- Update documentation for API changes
- Use feature branches for development

## 🧪 Testing
```bash
# Frontend tests
cd frontend
npm run test

# Backend tests
cd backend
./mvnw test
```

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Submit a pull request

Feel free to open issues for bug reports or feature requests!

## ⚡ Performance Optimization
- Frontend:
  - Code splitting
  - Image optimization
  - Lazy loading
- Backend:
  - Connection pooling
  - Caching strategies
  - Request rate limiting

---

<p align="center">
  Crafted with ❤️ by Malay Dewangan
</p>
