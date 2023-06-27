package api;

import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码与解码格式
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf8");
        resp.setCharacterEncoding("utf-8");

        //判断用户是否登录。
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
        //找到blogId
        String blogId = req.getParameter("blogId");

        //删除这篇博客
        BlogDao blogDao = new BlogDao();
        blogDao.delete(Integer.parseInt(blogId));

        //跳转
        resp.sendRedirect("blog_list.html");
    }
}
