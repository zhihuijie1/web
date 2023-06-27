package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userInfo")
public class UserServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        String blogId = req.getParameter("blogId");

        if(blogId == null) {
            resp.getWriter().write("不存在blogId");
            return;
        }
        int bd = Integer.parseInt(blogId);

        BlogDao blogDao = new BlogDao();

        Blog blog = blogDao.select(bd);

        if(blog == null) {
            resp.getWriter().write("不存在blog");
            return;
        }

        UserDao userDao = new UserDao();

        User user = userDao.selectById(blog.getUserId());

        String s = objectMapper.writeValueAsString(user);

        resp.setContentType("application/json;charset=utf8");

        resp.getWriter().write(s);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码与解码格式
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        //获取用户的信息
        String username = req.getParameter("username");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");

        //验证信息
        if(username == null || "".equals(username) || password1 == null || "".equals(password1) || password2 == null || "".equals(password2)) {
            resp.getWriter().write("输入的信息有误");
            return;
        }
        System.out.println("验证信息正确");
        if(!password1.equals(password2)) {
            resp.getWriter().write("两次密码不一样");
            return;
        }

        System.out.println("密码匹配正确");
        //构造user对象
        User user = new User();
        user.setUserName(username);
        user.setPassward(password1);

        System.out.println(user);

        //插入到数据库中
        UserDao userDao = new UserDao();
        userDao.insertUser(user);
        System.out.println("插入成功");
        //跳转
        resp.sendRedirect("login.html");
    }
}
