-- Drop the table if it exists (optional, you can remove this in production)
DROP TABLE IF EXISTS greek_gods;

-- Create the greek_gods table
CREATE TABLE greek_gods (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
); 