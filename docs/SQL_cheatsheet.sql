SELECT * FROM mydb.users;

# Select a limited amount of rows starting from a specific row
SELECT * FROM mydb.users LIMIT 0, 4;

# Add a new item to the table
INSERT INTO users (username, password, first_name, last_name, credit_info, phone_number, email, description, tags, user_score) 
VALUES ("Fuzzy", "Hello World", "Alex", "Parkson", null, null, "NyMail@intermail.com", null, null, 4310);

# Delete a row by ID from the table
DELETE FROM users WHERE ID=7;