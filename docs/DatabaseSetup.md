# Database Setup Plan for Brasil Burger

## Overview
The application requires a properly configured PostgreSQL database before it can run. The following steps outline how to set up the database using the SQL script we've created.

## Steps
1. **Fix the SQL script**:
   - Correct the typo in the CommandeItems foreign key constraint.

2. **Execute the SQL script**:
   - Execute the `001_InitialCreate.sql` script against the PostgreSQL database.
   - This will create all necessary tables and relationships.

3. **Test the application**:
   - Run the application again.
   - Verify that it connects to the database and the seed data is inserted.

## Execution
We'll need to modify the `ApplicationDbContext.cs` file to handle the case where the tables don't exist yet. The seed data method should check if the tables exist before attempting to query them.

## Follow-up
Once the database is properly set up and the application runs successfully, we'll have completed the task of correcting errors and getting the application to run.
