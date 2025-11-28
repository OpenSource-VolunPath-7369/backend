-- Insert Roles Script
-- This script inserts the required roles into the roles table
-- Run this script if the roles table is empty

USE sql10807517;

-- Insert roles if they don't exist
INSERT IGNORE INTO roles (name) VALUES ('ROLE_VOLUNTEER');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ORGANIZATION_ADMIN');
INSERT IGNORE INTO roles (name) VALUES ('ROLE_ADMIN');

-- Verify the roles were inserted
SELECT * FROM roles;


