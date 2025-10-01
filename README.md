# 📚 Feminist Library

## 1) Project Description
The **Feminist Library** is a console application developed in **Java 21** with a **PostgreSQL** database connection.  
Its purpose is to manage the book inventory of a community library.  
The system allows users to **add, edit, delete, list, and search books** by different criteria.  

This project is developed as a team following agile methodologies and applying the **MVC architecture** along with the **DAO pattern**.

---

## 2) Objectives
- Modernize the library management system.  
- Facilitate access to the book inventory.  
- Allow searches by title, author, or genre.  
- Learn how to integrate **Java + PostgreSQL** under good architecture practices.  

---

## 3) Technologies and Tools

| Technology / Tool         | Version / Use |
|----------------------------|---------------|
| **Language**               | Java 21 |
| **Database**               | PostgreSQL |
| **Dependency Manager**     | Maven |
| **IDE / Editor**           | Visual Studio Code |
| **Version Control**        | Git / GitHub |
| **Task Management**        | Trello or Jira |
| **Testing**                | JUnit 5, Mockito |

---

## 4) Installation and Usage

### 🔹 Clone the repository

```
git clone https://BibliotecaFeminista/BibliotecaFeminista-/biblioteca-feminista.git

cd biblioteca-feminista
```
### 🔹 Build the project
```
mvn clean install
```

### 🔹 Run the application
```
mvn exec:java -Dexec.mainClass="com.biblioteca.app.Main"
```

## 5) Project Architecture
```
src/
 ├─ main/java/com/biblioteca/
 │   ├─ app/           # Main + CLI
 │   ├─ controller/    # Handles requests
 │   ├─ view/          # Terminal interface
 │   ├─ service/       # Business logic
 │   ├─ dao/           # Interfaces + JDBC impl.
 │   ├─ model/         # Entities
 │   └─ config/        # Database connection
 └─ test/java/...      # Unit tests with JUnit and Mockito

```
## 6) Team

Project developed by a team of 4 members (FemCoders - P7):

- 👩‍💻 **Product Owner (P.O.)** → [Daniella Pacheco - GitHub](https://github.com/DaniPacheco8)  
- 👩‍💻 **Scrum Master (S.M.)** → [Erika P. Montoya - GitHub](https://github.com/DevErika)  
- 👩‍💻 **Developer** → [Luisa Moreno - GitHub](https://github.com/LuMorenoM)  
- 👩‍💻 **Developer** → [Sofia Toro - GitHub](https://github.com/sofiatoroviafara01) 



