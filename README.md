# Web Student Tracker

A simple CRUD-based Java web application to manage student data using JSP, Servlets, and JDBC. Developed and tested in a Linux environment.

## 💡 Project Overview

This project allows users to:
- ➕ Add a new student
- 📝 Update existing student details
- ❌ Delete a student
- 📋 List all students

## 🛠️ Tech Stack

- Java (JDK 8+)
- JSP (JavaServer Pages)
- Servlets
- JDBC (Java Database Connectivity)
- MySQL Database
- Apache Tomcat (9+)
- Linux (Ubuntu-based environment)



## ⚙️ Setup Instructions

1. **Clone the Repository**

```bash
git clone https://github.com/Gokulan1234/Web_Student_Tracker_Java.git

2. Import into Eclipse or VS Code



As a dynamic web project (if using Eclipse, enable Java EE perspective)


3. Setup MySQL Database



Create a database: student_tracker

Create a table: student with columns:


CREATE TABLE student (
  id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(45),
  last_name VARCHAR(45),
  email VARCHAR(100)
);

4. Configure DB Credentials



Open StudentDbUtil.java and update DB user, password, and url as needed


5. Run on Apache Tomcat



Deploy the project on Tomcat 9+

Visit: http://localhost:8080/Web_Student_Tracker_Java/list-students.jsp



✅ Features Implemented

Follows MVC architecture

CRUD operations with JDBC and MySQL

PreparedStatement to avoid SQL Injection

Deployed and tested in Linux OS
