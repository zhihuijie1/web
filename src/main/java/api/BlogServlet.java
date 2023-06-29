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
public class BlogServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 尝试获取一下 queryString 中的 blogId 字段.
        BlogDao blogDao = new BlogDao();
        String blogId = req.getParameter("blogId");
        if (blogId == null) {
            // queryString 不存在, 说明这次请求是获取博客列表
            List<Blog> blogs = blogDao.selectAll();
            // 需要把 blogs 转成符合要求的 json 格式字符串.
            String respJson = objectMapper.writeValueAsString(blogs);
            resp.setContentType("application/json; charset=utf8");
            resp.getWriter().write(respJson);
        } else {
            // queryString 存在, 说明本次请求获取的是指定 id 的博客.
            Blog blog = blogDao.selectById(Integer.parseInt(blogId));
            if (blog == null) {
                System.out.println("当前 blogId = " + blogId + " 对应的博客不存在!");
            }
            String respJson = objectMapper.writeValueAsString(blog);
            resp.setContentType("application/json; charset=utf8");
            resp.getWriter().write(respJson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 发布博客
        // 读取请求, 构造 Blog 对象, 插入数据库中即可!!
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前未登录, 无法发布博客!");
            return;
        }
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前未登录, 无法发布博客!");
            return;
        }
        // 确保登录之后, 就可以把作者给拿到了.

        // 获取博客标题和正文
        req.setCharacterEncoding("utf8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || "".equals(title) || content == null || "".equals(content)) {
            resp.setContentType("text/html; charset=utf8");
            resp.getWriter().write("当前提交数据有误! 标题或者正文为空!");
            return;
        }

        // 构造 Blog 对象
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(user.getUserId());
        // 发布时间, 在 java 中生成 / 数据库中生成 都行
        blog.setPostTime(new Timestamp(System.currentTimeMillis()));
        // 插入数据库
        BlogDao blogDao = new BlogDao();
        blogDao.add(blog);

        // 跳转到博客列表页
        resp.sendRedirect("blog_list.html");
    }
}
