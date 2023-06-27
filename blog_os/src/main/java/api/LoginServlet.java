package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");


        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();

        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            resp.getWriter().write("1 账户或密码有误");
            resp.getWriter().write(username + "   " + password);
            return;
        }
        User user = userDao.selectByUserName(username);
        if (user == null) {
            resp.getWriter().write("2 账户或密码有误");
            resp.getWriter().write(username + "   " + password);
            return;
        }
        if (!user.getPassward().equals(password)) {
            resp.getWriter().write("3 账户或密码有误");
            resp.getWriter().write(username + "   " + password);
            return;
        }
        //为当前的用户创建一个session对话。
        //参数使true的意思是：如果会话对象不存在，就返回一个新的会话对象。
        //若参数是false的意思是：如果会话对象不存在，就返回一个null

        HttpSession session = req.getSession(true);
        //将获得的user对象存入session中
        session.setAttribute("user", user);
        //返回一个重定向的报文，跳转到博客首页。
        resp.sendRedirect("blog_list.html");
    }


    //强制登录
    //判断当前用户是否登录。
    //如果当前用户已经登录了，那就拥有当前用户的会话。会话中保存着当前的用户。

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //有当前的会话就返回当前的会话，没有就返回null.
        HttpSession session = req.getSession(false);

        if (session == null) {
            //创建一个空对象
            User user = new User();
            //将其转化为jason格式的对象。
            String json = objectMapper.writeValueAsString(user);
            resp.setContentType("application/json;charset-utf8");
            resp.getWriter().write(json);
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            //创建一个空对象
            user = new User();
            //将其转化为jason格式的对象。
            String json = objectMapper.writeValueAsString(user);
            resp.setContentType("application/json;charset-utf8");
            resp.getWriter().write(json);
            return;
        }
        //有当前的用户
        //200:请求得到处理
        String json = objectMapper.writeValueAsString(user);
        resp.setContentType("application/json;charset-utf8");
        resp.getWriter().write(json);
    }
}
