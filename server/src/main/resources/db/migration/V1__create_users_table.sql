-- V1__create_user_table.sql
-- Create user table with UUIDv4 primary key

-- Enable pgcrypto extension for UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create user table
CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(32) UNIQUE NOT NULL,
    display_name VARCHAR(50),
    password_hash TEXT NOT NULL,
    profile_picture TEXT,
    profile_description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);