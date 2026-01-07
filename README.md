This repository contains a Java-based backend web service for a Todo Application. 
This project serves as a technical benchmark to explore the balance between Vanilla Java and Lightweight Frameworks,focusing on optimization, resource management, and deployment efficiency.

🎯 Project Goals & Philosophy
The core of this project is a "Hybrid" architecture. Instead of relying on a "magic" heavyweight framework (like Spring Boot) from the start, this version is built to understand exactly what happens under the hood.

Why this approach?
-Dependency Control: To avoid "Dependency Hell" and keep the final binary as slim as possible.

-Optimization: To measure if a manual, fine-tuned implementation of the database context and routing is significantly faster and lighter than a full-stack framework.

-Educational Purpose: This is part of a multi-stage research project. After this, I will implement the same application using Spring Boot to compare metrics such as startup time, memory footprint, and CPU usage.

🛠Tech stack:
-Language: Java (LTS)

-Server: Embedded Servlet Container (Tomcat/Jetty) - focused on handling HTTP requests with low overhead.

-Persistence: PostgreSQL with a custom Hybrid DB Context. I’ve implemented manual mapping to handle relationships (like User -> Todo with ON DELETE CASCADE) to maintain full control over SQL execution.

-Infrastructure: The service is designed to be deployed on a Linux Virtual Machine hosted on a Proxmox hypervisor, simulating a professional production environment.

🚀Deployment Enviroment
Unlike many local projects, this backend is built with "Infrastructure as Code" principles in mind:

-Virtualization: Hosted on Proxmox VE.

-OS: Debian/Ubuntu Server (headless).

-Monitoring: Focused on analyzing how Java interacts with Linux kernel resources (Thread management and Socket handling).

📈 Research Roadmap (Updated)
This project is the middle ground of a three-step evolutionary study:

-Hybrid Version (Current): Manual DB Context + Lightweight Servlet handling + Custom JWT security + Background Mailer.

-Spring Boot Version (Upcoming): Full-scale ORM (JPA/Hibernate) and Spring Security to compare developer productivity vs. execution overhead.

-Complete Vanilla Version (Planned): A future iteration developed entirely in Vanilla Java, using raw Sockets and manual HTTP parsing, to reach the absolute peak of optimization.

🔐 Security & Identity Management (In Progress)
To maintain the "Hybrid/Lightweight" philosophy, I am implementing a custom security layer:

-Authentication & Authorization: Currently being implemented using JWT (JSON Web Tokens). This allows the service to remain stateless and scalable, which is ideal for a Linux VM deployment.

-Role-Based Access Control: Ensuring that users can only manage their own Todo lists, preventing unauthorized data access.

📧 Automated Notification System (Planned)
One of the core features of this backend will be an Email Reminder Service:

-Scheduled Tasks: A background worker will scan the database for upcoming deadlines.

-Email Integration: A dedicated mail service using JavaMail API (SMTP) to send reminders to users a specific number of hours before a task expires.

-Logic: This implements a "Reminder Service" that calculates the delta between the current time and the task's due_date, triggering an alert for a scheduled expiration.

🏷️ Smart Category System (Use Case)
A key feature of this application is its dual-layer categorization system, which allows for both standardization and high levels of personalization:

-Global Admin Categories: The system comes with pre-defined, "static" categories managed by the Administrator (e.g., Urgent, Work, Personal). These ensure a baseline organization for every user.

-Custom User Categories: To enhance the user experience, I've implemented a feature where users can create their own custom categories. This allows for total personalization—from specific project tags like Dev-Research to more casual or "goliardic" phrases (e.g., Chill, Don't Panic, Random Stuff).

-Relational Integrity: In the backend, this is handled through a relational mapping that checks for global categories first, then filters for categories specifically owned by the user_id of the requester.
