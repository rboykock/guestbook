import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by rboyko on 27.02.17.
 */
@WebServlet("/guestbook")
public class GuestBook extends HttpServlet {

    protected MessageDao messageDao;
    protected Connection connection;

    @Override
    public void init() throws ServletException {
        ServletContext context=getServletContext();
        this.connection=JDBCUtils.getConnection((String)context.getAttribute("dburl"));
        messageDao=new MessageDaoIml(connection);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameterMap().containsKey("action")){
            String actionParam=req.getParameter("action");
            if(actionParam.equals("del")){
                if (req.getParameterMap().containsKey("id"))
                    messageDao.deleteById(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("/guestbook");
                return;
            }
        }
        List<Message> messages=messageDao.findAllMessage();
        req.setAttribute("messages",messages);
        RequestDispatcher requestDispatcher=req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message message=new Message();
        message.setName(req.getParameter("name"));
        message.setMessage(req.getParameter("message"));
        message.setRating(Byte.valueOf(req.getParameter("rating")));
        message.setDate(new Timestamp(new Date().getTime()).getTime());
        messageDao.save(message);

        resp.sendRedirect("/guestbook");
    }

    @Override
    public void destroy() {
        messageDao.destroy();
    }
}
