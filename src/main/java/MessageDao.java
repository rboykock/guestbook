import java.util.List;

/**
 * Created by rboyko on 01.03.17.
 */
public interface MessageDao {
    void save(Message message);
    List<Message> findAllMessage();
    void deleteById(int id);
    void destroy();
}
