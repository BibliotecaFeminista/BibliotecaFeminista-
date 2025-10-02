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
| **Dependency Manager**     | Maven 4.0.0 |
| **IDE / Editor**           | Visual Studio Code |
| **Version Control**        | Git / GitHub |
| **Task Management**        | Trello |
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
## 5) How it works (User's perspective)

- Access the main menu from the terminal.
- List books to view the catalog (without descriptions for faster scanning).
- Search by title, author, or genre to find specific items.
- Add a book by entering title, authors, ISBN (unique), genres, and a short description (≤ 200 chars).
- Edit a book to update any of its fields or relationships (authors/genres).
- Delete a book when it should no longer appear in the catalog.
- All operations are validated in the service layer and persisted in PostgreSQL through DAO implementations.

## 6) Flowchart
Link to the system flowchart (Main menu → use cases)

👉 [Flowchart](https://www.figma.com/board/J97MpUWzVfZ5bkb9NXFibO/Flowchart---Biblioteca-Feminista?node-id=4-1077&t=QFfz4bxlXtz2eAVJ-0) 

## 7) Project Architecture
The project is monolithic and follows MVC with DAO.
Packages: controller coordinates requests, service (if added) holds business rules, model contains entities and DAO code, view renders the CLI, config manages DB connections.

```
BibliotecaFeminista-
├── .git
├── .gitignore
├── README.md
├── pom.xml
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── biblioteca_feminista
│   │               ├── App.java
│   │               ├── config
│   │               │   └── DBManager.java
│   │               ├── controller
│   │               │   └── BookController.java
│   │               ├── model
│   │               │   ├── Book.java
│   │               │   ├── BookDaoImpl.java
│   │               │   └── BookDaoInterface.java
│   │               └── view
│   │                   └── BookView.java
│   └── test
│       └── java
│           └── com
│               └── biblioteca_feminista
│                   ├── AppMockitoTest.java
│                   └── AppTest.java
└── target
```
## 8) Team

Project developed by a team of 4 members (FemCoders - P7):

- 👩‍💻 **Product Owner (P.O.)** → [Daniella Pacheco - GitHub](https://github.com/DaniPacheco8)  
- 👩‍💻 **Scrum Master (S.M.)** → [Erika P. Montoya - GitHub](https://github.com/DevErika)  
- 👩‍💻 **Developer** → [Luisa Moreno - GitHub](https://github.com/LuMorenoM)  
- 👩‍💻 **Developer** → [Sofia Toro - GitHub](https://github.com/sofiatoroviafara01) 



