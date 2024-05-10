-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

-- Create articles table
CREATE TABLE articles (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    abstract_text TEXT NOT NULL,
    publication_date TIMESTAMP WITH TIME ZONE NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id)
);

-- Insert sample users
INSERT INTO users (username, password, enabled) VALUES
('user1', 'pass1', TRUE),
('user2', 'pass2', TRUE);

-- Insert sample articles using the user IDs directly
-- Dates are provided in ISO 8601 format including the timezone
INSERT INTO articles (title, abstract_text, publication_date, user_id) VALUES
('Exploring Spring Boot', 'A deep dive into the capabilities and features of Spring Boot for building modern microservices.', '2024-01-01T00:00:00+00', 1),
('Advanced Java Techniques', 'Explore advanced Java techniques for optimizing performance and scalability.', '2024-01-02T00:00:00+00', 1),
('Microservices with Spring Boot', 'Learn how to structure a scalable and maintainable microservice architecture with Spring Boot.', '2024-01-03T00:00:00+00', 2),
('API Design Best Practices', 'Best practices for designing robust, effective APIs using Spring Boot.', '2024-01-04T00:00:00+00', 2),
('Spring Security Essentials', 'An overview of Spring Security and how to implement security in your applications effectively.', '2024-01-05T00:00:00+00', 1),
('Reactive Programming in Java', 'Dive into reactive programming with Java and Spring to build highly responsive applications.', '2024-01-06T00:00:00+00', 1),
('Database Optimization Strategies', 'Techniques and strategies for optimizing database interactions in enterprise Java applications.', '2024-01-07T00:00:00+00', 2),
('Cloud-Native Java Applications', 'Building and deploying cloud-native applications using Spring Boot and cloud infrastructure.', '2024-01-08T00:00:00+00', 2),
('Continuous Integration in DevOps', 'Integrating continuous integration practices into your DevOps workflow for better software delivery.', '2024-01-09T00:00:00+00', 1),
('Performance Tuning in Spring', 'A guide to performance tuning your Spring applications for maximum efficiency.', '2024-01-10T00:00:00+00', 2);
