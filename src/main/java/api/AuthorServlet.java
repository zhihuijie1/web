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

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogId = req.getParameter("blogId");
        if (blogId == null) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("参数非法, 缺少 blogId");
            return;
        }
        // 根据 blogId 查询 Blog 对象
        BlogDao blogDao = new BlogDao();
        Blog blog = blogDao.selectById(Integer.parseInt(blogId));
        if (blog == null) {
            // 博客不存在.
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("没有找到指定博客: blogId = " + blogId);
            return;
        }
        // 根据 blog 中的 userId 找到对应的用户信息
        UserDao userDao = new UserDao();
        User author = userDao.selectById(blog.getUserId());
        String respJson = objectMapper.writeValueAsString(author);
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write(respJson);
        System.out.println("yesy");

        System.out.println("sdsadfsdgfskjdfkjjk xcv ");
    }
}
