import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by rboyko on 01.03.17.
 */
public class MessageDaoIml implements MessageDao {

    protected static final String SQL_INSERT="INSERT INTO message (name,message,date,rating) VALUES (?,?,?,?)";
    protected static final String SQL_SELECT="SELECT * FROM message";
    protected static final String SQL_DELETE="DELETE FROM message WHERE id=?";

    protected Connection connection;

    public MessageDaoIml(Connection connection) {
        this.connection=connection;
    }

    @Override
    public void save(Message message) {
        Optional<PreparedStatement> preparedStatement=Optional.empty();
        try {
            preparedStatement=Optional.of(connection.prepareStatement(SQL_INSERT));
            preparedStatement.get().setString(1,message.getName());
            preparedStatement.get().setString(2,message.getMessage());
            preparedStatement.get().setLong(3,message.getDate());
            preparedStatement.get().setByte(4,message.getRating());
            preparedStatement.get().executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            preparedStatement.ifPresent(a -> {
                try {
                    a.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
        }
        preparedStatement.ifPresent(a -> {
            try {
                a.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<Message> findAllMessage() {
        Optional<Statement> statement= Optional.empty();
        Optional<ResultSet> resultSet=Optional.empty();
        List<Message>messages=new ArrayList<>();
        try {
            statement=Optional.of(connection.createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
            statement.ifPresent(a -> {
                try {
                    a.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
        }
        try {
            resultSet=Optional.of(statement.get().executeQuery(SQL_SELECT));
            while (resultSet.get().next()){
                Message message=new Message();
                message.setId(resultSet.get().getInt("id"));
                message.setName(resultSet.get().getString("name"));
                message.setMessage(resultSet.get().getString("message"));
                message.setDate(resultSet.get().getLong("date"));
                message.setRating(resultSet.get().getByte("rating"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        statement.ifPresent(a -> {
            try {
                a.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return messages;
    }

    @Override
    public void deleteById(int id) {
        Optional<PreparedStatement> preparedStatement=Optional.empty();
        try {
            preparedStatement=Optional.of(connection.prepareStatement(SQL_DELETE));
            preparedStatement.get().setInt(1,id);
            preparedStatement.get().executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            preparedStatement.ifPresent(a -> {
                try {
                    a.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public void destroy(){
        if(connection!=null)
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
