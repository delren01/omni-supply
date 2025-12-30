OmniSupply | Full-Stack Inventory Management System

OmniSupply is a robust, full-stack inventory management solution designed to streamline supply chain workflows, ensure data integrity, and provide real-time visibility into inventory levels. This project demonstrates the implementation of a scalable back-end architecture integrated with modern cloud-based database management.

Key Features
Inventory Tracking: Real-time monitoring of stock levels with automated updates for additions, removals, and modifications.

Secure Authentication: Implementation of secure session handling and user access controls to ensure data privacy.

Data Integrity: Robust validation logic to prevent inventory discrepancies and ensure reliable record-keeping.

RESTful Architecture: A modular API design that facilitates seamless communication between the front-end and the back-end services.

Tech Stack
Back-End: Java, Spring Boot

Database: Supabase (PostgreSQL)


Architecture: MVC (Model-View-Controller) Architectural Pattern 


Tools: VS Code , GitHub for version control, Maven

Technical Implementation
Database Management
OmniSupply utilizes a structured PostgreSQL database (via Supabase) to manage complex data relationships. This includes optimized schema designs for efficient retrieval and storage of inventory records, mirroring production-level data modeling principles.


Security & Compliance
Reflecting a commitment to secure development, the system handles data using secure authentication protocols. It follows meticulous debugging and quality control standards to ensure the platform remains stable and secure.

Version Control & Workflow
The development lifecycle was managed using GitHub for version control. This project follows Agile methodologies, focusing on modular code maintainability and iterative improvements.


Getting Started
Prerequisites
JDK 17 or higher

Maven

Supabase API Credentials

Installation
Clone the repository: git clone https://github.com/YourUsername/OmniSupply.git

Navigate to the project directory: cd OmniSupply

Configure your environment variables in application.properties with your Supabase credentials.

Build the project: mvn clean install

Run the application: mvn spring-boot:run
