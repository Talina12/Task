import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://devcustomerbi.database.windows.net:1433;"
                        + "database=AdventureWorks;"
                        + "user=EladClient@devcustomerbi;"
                        + "password=EladClntTargets2020!;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();) {
               // Create and execute a SELECT SQL statement.
               String selectSql = "SELECT TOP 10 Title, FirstName, LastName from SalesLT.Customer";
               resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(2) + " " + resultSet.getString(3));
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}