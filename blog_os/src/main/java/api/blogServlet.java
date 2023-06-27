package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/blog")
public class blogServlet extends HttpServlet {
    //ObjectMapper -> 是Jackson库中的一个类，主要集成了java对象与json格式字符串相互转化的方法
    //其中的writeValueAsString方法 -> 将java对象转化为json格式的字符串。
    //其中的readValue方法 -> 将json格式的字符串转化为java对象。
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        BlogDao blogDao = new BlogDao();

        String blogId = req.getParameter("blogId");

        if (blogId == null) {
            //博客列表页
            //查找所有的博客
            List<Blog> blogs = blogDao.selectAll();


            //将list类型的数组，转换为json格式下的数组。
            String json = objectMapper.writeValueAsString(blogs);

            resp.setContentType("application/json;charset=utf8");

            resp.getWriter().write(json);

        } else {
            //博客详情页
            int blogID = Integer.parseInt(blogId);
            System.out.println("blogServlet.blogID--> " + blogID);
            Blog blog = blogDao.select(blogID);

            String s = objectMapper.writeValueAsString(blog);

            resp.setContentType("appliction/json;charset=utf-8");

            resp.getWriter().write(s);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //修改编码与解码格式。
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf8");


        //先判断用户是否登录。
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.getWriter().write("用户未登录");
            resp.sendRedirect("login.html");
            return;
        }
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.getWriter().write("用户未登录");
            resp.sendRedirect("login.html");
            return;
        }

        //获取博客信息。
        String blogId = req.getParameter("blogId");

        String title = req.getParameter("title");

        String content = req.getParameter("content");

        if (title == null || " ".equals(title) || content == null || "".equals(content)) {
            resp.getWriter().write("信息有误");
            return;
        }

        //String postTime = req.getParameter("postTime");
        int userId = user.getUserID();


        //构造博客。
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setPostTime(new Timestamp(System.currentTimeMillis()));
        blog.setUserId(userId);

        //将博客添加到数据库。
        BlogDao blogDao = new BlogDao();
        blogDao.add(blog);
        //跳转。
        resp.sendRedirect("blog_list.html");
    }
}