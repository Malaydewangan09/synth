```markdown:/Users/malay/Downloads/synth-email/README.md
# Synth Email Suite

<p align="center">
  <img src="assets/synth-logo.png" alt="Synth Email Logo" width="200"/>
</p>

An AI-powered email management system that combines a modern web client with a robust Spring Boot backend.

## 🌟 Overview

Synth Email Suite consists of two main components:

- **Synth Client**: A Next.js frontend with AI-powered email features
- **Synth Server**: A Spring Boot backend handling Gmail API integration

## ⚡️ Core Features

### Frontend (Synth Client)
- AI-powered email draft generation using Gemini
- Smart email summarization
- Intelligent priority scoring
- Advanced search and filtering
- Keyboard-first navigation
- Modern, minimal UI

### Backend (Synth Server)
- Secure Gmail API integration
- OAuth2 authentication
- Email CRUD operations
- Draft management
- Real-time email sync

## 🚀 Getting Started

### Prerequisites
- Node.js 18+
- Java 17+
- Gmail Account
- Google Cloud Project with:
  - Gmail API enabled
  - Gemini API enabled
  - OAuth 2.0 credentials

### Frontend Setup

1. Install dependencies:
```bash
cd frontend
npm install
```

2. Configure environment:
```bash
cp .env.example .env.local
```

Add your Gemini API key:
```env
NEXT_PUBLIC_GEMINI=your_gemini_api_key
```

3. Start development server:
```bash
npm run dev
```

### Backend Setup

1. Configure application properties:
```bash
cd backend
cp src/main/resources/application.example.yml src/main/resources/application.yml
```

2. Add your Google OAuth credentials to `application.yml`:
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your_client_id
            client-secret: your_client_secret
```

3. Run the server:
```bash
./mvnw spring-boot:run
```

## 🛠️ Tech Stack

### Frontend
- Next.js 14
- Tailwind CSS
- Google Gemini AI
- shadcn/ui Components

### Backend
- Spring Boot 3
- Spring Security
- Gmail API
- OAuth 2.0

## 📝 Development

### Frontend Development
Frontend runs on http://localhost:3000

### Backend Development
Backend API runs on http://localhost:8080

### API Documentation
Backend API documentation available at http://localhost:8080/swagger-ui.html

## 🔒 Security

- OAuth 2.0 authentication
- CSRF protection
- Secure cookie handling
- Environment variable management

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

<p align="center">
  Made with ❤️ by [Your Name]
</p>
```

Would you like me to add:
1. Docker setup instructions
2. CI/CD configuration
3. Deployment guide
4. Testing instructions
5. Contribution guidelines

Let me know what additional sections would be helpful!
