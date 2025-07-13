# Core-Sport Website / Ứng dụng Core-Sport

> **Core-Sport** là một nền tảng thương mại điện tử chuyên về thể thao, được xây dựng trên Spring Boot, hỗ trợ REST API, bảo mật JWT, kết nối PostgreSQL & Redis, cùng OpenAPI (Swagger) và các tính năng tiêu chuẩn cho một website bán hàng trực tuyến.

## 🚀 Features / Tính năng

- Quản lý người dùng (đăng ký / đăng nhập) với JWT authentication  
- CRUD sản phẩm, danh mục, giỏ hàng  
- Xử lý đơn hàng và thanh toán  
- Tìm kiếm & phân trang sản phẩm  
- Caching với Redis (danh sách sản phẩm, giỏ hàng)  
- OpenAPI / Swagger UI dành cho API docs  
- Validation, Exception Handling chuẩn Spring Boot  

## 🛠️ Tech Stack / Công nghệ sử dụng

- **Framework:** Spring Boot 3.5.x  
- **Database:** PostgreSQL  
- **Cache:** Redis  
- **Security:** Spring Security + JWT  
- **ORM:** Spring Data JPA + Hibernate  
- **Mapping:** MapStruct, Lombok  
- **API Docs:** Springdoc OpenAPI (Swagger UI)  
- **Build:** Maven  
- **Container:** Docker & Docker Compose  

## ⚙️ Prerequisites / Yêu cầu môi trường

1. Java 17+  
2. Maven 3.8+  
3. Docker & Docker Compose (nếu chạy bằng container)  
4. Git  

## 🚀 Getting Started / Khởi động dự án

### 1. Clone repository

```bash
git clone https://github.com/meKayEnTi/Core-Sport.git
cd Core-Sport
```

### 2. Chạy container của postgres và redis
```bash
docker-compose up -d
# Dịch vụ sẽ chạy:
# - PostgreSQL: 5432
# - Redis:      6379
```

### 3. Chạy dự án bằng Maven (Local)
```bash
# Build và chạy
# - Ứng dụng:   http://localhost:8080
# - Swagger UI: http://localhost:8080/swagger-ui/index.html
mvn clean spring-boot:run
```

