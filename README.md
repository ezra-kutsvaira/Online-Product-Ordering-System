# 🛒 Online Product Ordering System Web Application

A Java web application for managing online product browsing, cart operations, and checkout using **Servlets, JSP, JDBC, and MySQL**.

## 🚀 Features

- 🔐 Session-based user flow with cart persistence during browsing
- 👥 Role-oriented modules (**ADMIN**, **USER**)

### 🛠️ Admin can:
- ➕ Add products
- ✏️ Update products
- 🗑️ Delete products
- 📋 View all products

### 🛍️ User can:
- 👀 View available products
- 🛒 Add products to cart
- ❌ Remove products from cart
- ✅ Place an order

- 🧩 JDBC integration with `PreparedStatement` for safer SQL operations
- 🔁 Transactional checkout logic to save order + order items and update stock
- 🐳 Dockerized application deployment with MySQL support

## 🧰 Tech Stack

- ☕ Java Servlets
- 🖥️ JSP + JSTL (no scriptlets)
- 🔌 JDBC
- 🐬 MySQL
- 🌐 Apache Tomcat
- 🐳 Docker / Docker Compose
- 📦 Maven

## 📁 Project Structure

```text
src/main/java/com/example/ops/
  model/
  dao/
  servlet/
  util/

src/main/webapp/WEB-INF/views/
  admin/
    product-list.jsp
    product-form.jsp
  user/
    product-list.jsp
    cart.jsp
    order-success.jsp
```

## 🗄️ Database Tables

The application expects these tables in your MySQL database:

- `products`
- `orders`
- `order_items`

| Table | Purpose |
|---|---|
| `products` 📦 | stores product catalog data (name, description, price, stock) |
| `orders` 🧾 | stores order header details (customer, date, total) |
| `order_items` 🔗 | stores items per order (`order_id` + `product_id` + quantity + unit_price) |

## ⚡ Quick Start with Docker

### Prerequisites
- Docker
- Docker Compose

### Start the full stack (app + MySQL)

```bash
docker compose up --build
```

The Docker setup publishes:
- App container port `8080` to host `8080`
- MySQL container port `3306` to host `3307`

The application will be available at:

- **App:** http://localhost:8080

### Stop containers

```bash
docker compose down
```

## 🔧 Configuration

The application database connection is configurable through environment variables:

- `DB_URL` (default: `jdbc:mysql://localhost:3306/ops_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`)
- `DB_USER` (default: `ops_user`)
- `DB_PASSWORD` (default: `ops_password`)

In Docker Compose, these are already provided for the app service.

## 🔧 Local non-Docker development

You can build manually with:

```bash
mvn clean package
```

Deploy the generated WAR (`target/ops.war`) to Tomcat 10.

## 📝 Notes

- This project is designed for learning/demo purposes.
- Input fields and SQL are handled via `PreparedStatement`-based DAO methods.
- JSP pages use EL/JSTL only (no Java scriptlets).
- You can extend the project with authentication/authorization for production use.

## 📄 License

This project is for educational use.

