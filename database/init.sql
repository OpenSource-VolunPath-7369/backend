-- Volunpath Database Initialization Script
-- This script creates the database and initializes it with basic structure

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS volunpath_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE volunpath_db;

-- Note: Tables will be created automatically by Hibernate with ddl-auto=update
-- This script is for reference and manual setup if needed

-- The following tables will be created automatically:
-- - users (from IAM context)
-- - roles (from IAM context)
-- - user_roles (many-to-many relationship)
-- - volunteers (from Volunteers context)
-- - organizations (from Organizations context)
-- - publications (from Publications context)
-- - messages (from Communication context)
-- - notifications (from Communication context)
-- - faqs (from Communication context)
-- - enrollments (if needed)

-- You can run this script manually if you want to ensure the database exists
-- before starting the Spring Boot application

