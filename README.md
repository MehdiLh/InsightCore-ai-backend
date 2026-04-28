# InsightCore_AI Backend

An intelligent dashboard backend for analyzing sales data and team performance, allowing managers to "chat" with their data and receive future forecasts using AI-powered analytics.

## 🚀 Overview

InsightCore_AI is a Spring Boot application that provides:
- **AI-powered data analysis** through natural language queries
- **Sales data management** with PostgreSQL persistence
- **Interactive chat interface** for data exploration
- **Chart generation** for visual analytics
- **Session-based conversation memory**

## 🛠 Tech Stack

- **Java 17** - Core language
- **Spring Boot 3.4.5** - Application framework
- **Spring AI** - AI integration with OpenAI/OpenRouter
- **Spring WebFlux** - Reactive web support
- **Spring Data JPA** - Database persistence
- **PostgreSQL** - Production database
- **H2** - Test database
- **Lombok** - Code generation
- **Maven** - Build tool

## 📁 Project Structure

```
src/main/java/com/app/InsightCore_AI/
├── InsightCoreAiApplication.java     # Main application class
├── config/                           # Configuration classes
│   ├── AiConfig.java                # AI configuration
│   └── WebConfig.java               # Web configuration
├── controller/                       # REST controllers
│   ├── ChatController.java          # AI chat endpoints
│   └── SaleController.java          # Sales data endpoints
├── dto/                             # Data transfer objects
│   ├── AiAnalysisResponse.java      # AI response format
│   ├── AiQueryPlan.java             # Query planning
│   ├── ChartData.java               # Chart data structure
│   ├── DateRange.java               # Date range filter
│   ├── QueryResult.java             # Query execution result
│   ├── SaleRequestDTO.java          # Sale request format
│   ├── SaleResponseDTO.java         # Sale response format
│   └── SalesStatsDTO.java           # Sales statistics
├── enums/                           # Enumerations
│   └── MessageRole.java             # Chat message roles
├── model/                           # JPA entities
│   ├── Chat.java                    # Chat session entity
│   └── Sale.java                    # Sales data entity
├── repository/                      # Data repositories
│   ├── ChatRepository.java          # Chat data access
│   └── SaleRepository.java          # Sales data access
├── services/                        # Business logic
│   ├── AiAnalysisService.java       # AI analysis processing
│   ├── AiDataService.java          # Data retrieval for AI
│   ├── AiMemoryService.java         # Conversation memory
│   ├── AiOrchestratorService.java   # AI workflow coordination
│   ├── AiPlanService.java           # Query planning
│   ├── ChartService.java            # Chart data generation
│   ├── PromptService.java           # AI prompt management
│   └── SaleService.java             # Sales data management
└── Mapper/                          # Data mapping
    └── SaleMapper.java              # Sale entity mapping
```

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL database
- OpenRouter/OpenAI API key

### 1. Database Setup
```sql
CREATE DATABASE insightcore_ai;
CREATE USER postgres WITH PASSWORD 'sa';
GRANT ALL PRIVILEGES ON DATABASE insightcore_ai TO postgres;
```

### 2. Environment Configuration
Copy the example environment file:
```bash
cp .env.example .env
```

Update `.env` with your configuration:
```env
OPENAI_API_KEY=YOUR_OPENAI_API_KEY
OPENAI_BASE_URL=https://openrouter.ai/api
OPENAI_MODEL=openai/gpt-oss-120b:free
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/insightcore_ai
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=sa
```

### 3. Run the Application
```bash
# Using Maven wrapper
./mvnw spring-boot:run

# Or using Maven
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### AI Chat Analysis
- **POST** `/api/ai/analyze`
- **Parameters**: 
  - `sessionId` (query param) - Session identifier for conversation context
  - `message` (request body) - Natural language query
- **Response**: `AiAnalysisResponse` with AI insights and chart data

### Sales Data Management
- **GET** `/api/sales` - Retrieve all sales records
- **POST** `/api/sales` - Create new sales record
- **GET** `/api/sales/stats` - Get sales statistics
- **GET** `/api/sales/chart` - Generate chart data

## 🧠 AI Architecture

The AI system follows a 4-step orchestration pattern:

1. **Planning** (`AiPlanService`) - Extracts and validates query intent
2. **Data Retrieval** (`AiDataService`) - Fetches relevant data from database
3. **Fact Building** - Constructs structured facts for AI analysis
4. **Analysis** (`AiAnalysisService`) - Generates insights using AI models

### Conversation Memory
- Session-based chat history persistence
- Context maintenance across interactions
- JSON-serialized conversation storage

## 🔧 Configuration

### AI Configuration
- **Provider**: OpenRouter (configurable)
- **Models**: Supports various OpenAI-compatible models
- **Base URL**: Configurable API endpoint
- **Debug**: Spring AI logging enabled

### Database Configuration
- **Production**: PostgreSQL with JPA/Hibernate
- **Testing**: H2 in-memory database
- **DDL Auto**: Update schema automatically
- **SQL Logging**: Enabled for debugging

## 🧪 Testing

Run the test suite:
```bash
./mvnw test
```

## 📊 Features

### Core Capabilities
- **Natural Language Queries**: Ask questions about your sales data in plain English
- **Data Visualization**: Automatic chart generation for query results
- **Forecasting**: AI-powered predictions and trend analysis
- **Session Management**: Persistent conversation context
- **Multi-turn Dialogues**: Follow-up questions with context awareness

### Data Analysis Types
- Sales performance metrics
- Trend analysis over time ranges
- Product performance comparisons
- Revenue forecasting
- Statistical summaries

## 🔒 Security Considerations

- Environment variables for sensitive configuration
- Database credentials externalized
- API keys managed through `.env` file
- SQL injection prevention through JPA

## 🚀 Deployment

### Docker (Recommended)
```dockerfile
# Build with Maven
FROM openjdk:17-jdk-slim
COPY target/InsightCore_AI-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Environment Variables for Production
- `OPENAI_API_KEY` - AI service API key
- `OPENAI_BASE_URL` - AI service endpoint
- `OPENAI_MODEL` - AI model identifier
- `SPRING_DATASOURCE_URL` - Database connection string
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 🆘 Support

For issues and questions:
- Check the application logs for debugging information
- Verify database connectivity and configuration
- Ensure AI API credentials are valid and accessible
- Review Spring AI debug logs for model interaction details
