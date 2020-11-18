import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
       Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/test?useUnicode=true&characterEncoding=UTF-8", "root", "admin");
       String sql="";
        PreparedStatement prepareStatement = connection.prepareStatement("");
        boolean execute = prepareStatement.execute();

    }
}
