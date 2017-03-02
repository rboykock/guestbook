import javax.servlet.*;
import java.util.Set;

/**
 * Created by rboyko on 28.02.17.
 */
public class GuestBookListener implements ServletContextListener {

    protected static final String DBURL="jdbc:h2:file:/tmp/guestbook";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.print("Load JDBC driver ... ");
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Done");
        ServletContext servletContext=sce.getServletContext();
        servletContext.setAttribute("dburl",DBURL);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
