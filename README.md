# OmniSupply | Full-Stack Inventory Management System

OmniSupply is a robust, full-stack inventory management solution designed to streamline supply chain workflows, ensure data integrity, and provide real-time visibility into inventory levels.

## Key Features

* **Inventory Tracking:** Real-time monitoring of stock levels with automated updates for additions, removals, and modifications.
* **Secure Authentication:** Implementation of secure session handling and user access controls to ensure data privacy.
* **Data Integrity:** Robust validation logic to prevent inventory discrepancies and ensure reliable record-keeping.
* **RESTful Architecture:** A modular API design that facilitates seamless communication between the front-end and the back-end services.

## Tech Stack

* **Back-End:** Java, Spring Boot
* **Database:** Supabase (PostgreSQL)
* **Architecture:** MVC (Model-View-Controller)
* **Tools:** VS Code, GitHub, Maven

## Technical Implementation

### Database Management
OmniSupply utilizes a structured **PostgreSQL** database (via Supabase) to manage complex data relationships. This includes optimized schema designs for efficient retrieval and storage of inventory records.

### Security & Compliance
The system follows meticulous debugging and quality control standards to ensure the platform remains stable and secure, mirroring production-level data modeling principles.

## Getting Started

1. **Clone the repository:** `git clone https://github.com/YourUsername/OmniSupply.git`

2. **Configure Environment:** Add your Supabase credentials to the `application.properties` file.

3. **Build and Run:** `mvn clean install`  
   `mvn spring-boot:run`
