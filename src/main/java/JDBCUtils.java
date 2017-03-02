import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by rboyko on 01.03.17.
 */
public class JDBCUtils {
    public static Connection getConnection(String dburl){
        Optional<Connection> connection=Optional.empty();
        try {
            connection= Optional.of(DriverManager.getConnection(dburl));
        } catch (SQLException e) {
            e.printStackTrace();
            connection.ifPresent(a -> {
                try {
                    a.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
        }
        return connection.get();
    }
}
