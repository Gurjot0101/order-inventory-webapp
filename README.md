
# 📦 Order & Inventory Management Web App

> A full-stack web application for managing orders and inventory — built with **Spring Boot** on the backend and **React** on the frontend.

---

## 🚀 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | React.js, Axios, React Router |
| **Backend** | Spring Boot, Spring Data JPA, Spring Security |
| **Database** | MySQL / PostgreSQL |
| **Authentication** | JWT (JSON Web Tokens) |
| **Build Tools** | Maven (backend), npm / Vite (frontend) |

---

## ✨ Features

- 📋 **Order Management** — Create, view, update, and delete orders
- 🗃️ **Inventory Tracking** — Monitor stock levels, add/remove products
- 🔐 **Authentication & Authorization** — Secure JWT-based login with role-based access (Admin / User)
- 📊 **Dashboard** — Overview of current inventory stats and recent orders
- 🔄 **RESTful API** — Clean API layer separating frontend and backend concerns
- 📱 **Responsive UI** — Works seamlessly across desktop and mobile

---

## 🗂️ Project Structure

```
order-inventory-webapp/
│
├── backend/                   # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/
│   │   │   │       ├── controller/    # REST Controllers
│   │   │   │       ├── service/       # Business Logic
│   │   │   │       ├── repository/    # JPA Repositories
│   │   │   │       ├── model/         # Entity Classes
│   │   │   │       ├── dto/           # Data Transfer Objects
│   │   │   │       └── security/      # JWT & Spring Security Config
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
│
└── frontend/                  # React Application
    ├── src/
    │   ├── components/        # Reusable UI Components
    │   ├── pages/             # Page-level Components
    │   ├── services/          # Axios API Calls
    │   ├── context/           # Auth Context / State Management
    │   └── App.jsx
    ├── public/
    └── package.json
```

---

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Node.js 18+ & npm
- MySQL or PostgreSQL
- Maven 3.8+

---

### ⚙️ Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/Gurjot0101/order-inventory-webapp.git
   cd order-inventory-webapp/backend
   ```

2. **Configure the database** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/order_inventory_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   # JWT Secret
   app.jwt.secret=your_jwt_secret_key
   app.jwt.expiration=86400000
   ```

3. **Create the database**
   ```sql
   CREATE DATABASE order_inventory_db;
   ```

4. **Run the Spring Boot application**
   ```bash
   mvn spring-boot:run
   ```
   The backend starts at `http://localhost:8080`

---

### 🖥️ Frontend Setup

1. **Navigate to the frontend directory**
   ```bash
   cd ../frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure API base URL** in `.env` or the Axios config:
   ```env
   REACT_APP_API_URL=http://localhost:8080/api
   ```

4. **Start the development server**
   ```bash
   npm start
   ```
   The frontend runs at `http://localhost:3000`

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login and receive JWT token (Role Based Admin/User) | No |

### Orders
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/orders` | Get all orders | Yes |
| GET | `/api/orders/{id}` | Get order by ID | Yes |
| POST | `/api/orders` | Create a new order | Yes |
| PUT | `/api/orders/{id}` | Update an order | Yes |
| DELETE | `/api/orders/{id}` | Delete an order | Yes (Admin) |

### Inventory / Products
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/products` | Get all products | Yes |
| GET | `/api/products/{id}` | Get product by ID | Yes |
| POST | `/api/products` | Add a new product | Yes (Admin) |
| PUT | `/api/products/{id}` | Update product details | Yes (Admin) |
| DELETE | `/api/products/{id}` | Delete a product | Yes (Admin) |

---

## 🔒 Authentication Flow

```
Client                        Server
  |                              |
  |-- POST /auth/login --------> |
  |                              | Validate credentials
  |<-- JWT Token ---------------|
  |                              |
  |-- GET /api/orders           |
  |   Authorization: Bearer JWT  |
  |                              | Validate token
  |<-- Orders Data -------------|
```

All protected routes expect the header:
```
Authorization: Bearer <your_jwt_token>
```

---

## 🧪 Running Tests

```bash
# Backend unit and integration tests
cd backend
mvn test

# Frontend tests
cd frontend
npm test
```

---

## 🚢 Deployment

### Backend (JAR)
```bash
mvn clean package
java -jar target/order-inventory-webapp-0.0.1-SNAPSHOT.jar
```

### Frontend (Build)
```bash
npm run build
# Serve the /build folder via Nginx, Apache, or any static host
```

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

---

## 👤 Author

**Gurjot Singh**  
[![GitHub](https://img.shields.io/badge/GitHub-Gurjot0101-181717?style=flat&logo=github)](https://github.com/Gurjot0101)
