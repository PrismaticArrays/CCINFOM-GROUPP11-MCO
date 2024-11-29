import java.sql.*;

class DBConnection {
    public Connection connection;
    public PreparedStatement statement;
    public ResultSet rs;

    public DBConnection() throws Exception{
        this.connection = null;

//////////////////////////  IMPORTANT: change username and password depending on database connection  ///////////////////////////////////
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String username = "root";
            String password = "12345";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
           this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms", username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        
        statement = this.connection.prepareStatement("CREATE OR REPLACE VIEW view_attractions AS SELECT * FROM attractions");
        statement.execute();

        statement = this.connection.prepareStatement("CREATE OR REPLACE VIEW view_visitors AS SELECT * FROM visitors");
        statement.execute();

        statement = this.connection.prepareStatement("CREATE OR REPLACE VIEW view_tickets AS SELECT * FROM tickets");
        statement.execute();

        statement = this.connection.prepareStatement("CREATE OR REPLACE VIEW view_staff AS SELECT * FROM staff");
        statement.execute();

        statement = this.connection.prepareStatement("CREATE OR REPLACE VIEW view_transactions AS SELECT * FROM transactions");
        statement.execute();

        statement = this.connection.prepareStatement("CREATE OR REPLACE VIEW view_assignments AS SELECT * FROM assignments");
        statement.execute();
    }

    public void printResult() throws Exception{
        ResultSetMetaData metadata = this.rs.getMetaData();

        int columnCount = metadata.getColumnCount();

        for(int i=1; i<=columnCount; i++){
            System.out.printf("%-20s", metadata.getColumnLabel(i));
        }
        System.out.println();

        while(rs.next()){
            for(int i=1; i<=columnCount; i++){
                System.out.printf("%-20s", rs.getString(metadata.getColumnLabel(i)));
            }
            System.out.println();
        }

    }

    public void executeSQL(String sql) throws Exception{
        rs = statement.executeQuery(sql);
    }

    //used for SQL that does not return anything (like insertion)
    public void executeSQLNoRS(String sql) throws Exception{
        statement = this.connection.prepareStatement(sql);
        statement.execute();
    }

    public void exit() throws Exception{
        this.statement.close();
        this.connection.close();
    }

    //returns string from of value at certain column ONLY WORKS FOR 1st ROW
    public String getFirstValueAtColumn(String columnName) throws Exception{
        rs.next();
        return rs.getString(columnName);
    }

    public boolean isEmptyRS() throws Exception{
        return !rs.isBeforeFirst();
    }

   
}