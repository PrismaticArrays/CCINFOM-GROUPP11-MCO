# CCINFOM-GROUPP11-MCO
By Marc Ian Dumaran and Mikaela Gabrielle Herrera

## How to compile and run the program
1. Have the database imported in MySQL using the SQL-DATABASE-UPDATED.sql file
2. Download and extract every .java and .jar file to one folder
3. IMPORTANT: Edit DBConnection.java lines 14 and 15 to the correct username and password for the database connection
4. Run in command line `javac Driver.java`
5. Run in command line `java -cp .;mysql-connector-j-9.1.0.jar Driver`
6. If recompilation is needed, be sure to delete the old .class files since the compiler seems to skip them even if the .java files changed
