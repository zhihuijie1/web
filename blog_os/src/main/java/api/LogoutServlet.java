package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //点击客户端的注销按钮来注销，注销的方式有两种：一种是删除session 另一种是删除user
        //session没有删除的功能，可以设置时间进行自动删除，但是不满足我们的需要。
        //通过删除user来注销。
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf8");

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.getWriter().write("错误操作");
            return;
        }
        session.removeAttribute("user");
        //然后跳转到登录页面
        resp.sendRedirect("login.html");
    }
}
