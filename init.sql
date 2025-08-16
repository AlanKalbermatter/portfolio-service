-- Initialize the portfolio database
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create sequences for auto-increment IDs
CREATE SEQUENCE IF NOT EXISTS personal_info_seq START 1;
CREATE SEQUENCE IF NOT EXISTS experience_seq START 1;
CREATE SEQUENCE IF NOT EXISTS project_seq START 1;
CREATE SEQUENCE IF NOT EXISTS skill_seq START 1;

-- Grant permissions to the portfolio user
GRANT ALL PRIVILEGES ON DATABASE portfolio_db TO portfolio_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO portfolio_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO portfolio_user;
