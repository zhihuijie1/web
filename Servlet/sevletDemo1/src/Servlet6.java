
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/s6")
public class Servlet6 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应信息的编码格式。
        resp.setCharacterEncoding("UTF-8");
        //进行响应。
        resp.getWriter().println("你好啊");
        //设置浏览器的编码格式
        resp.setContentType("text/html;charset=UTF-8");

        byte[] unames = req.getParameter("uname").getBytes("ISO-8859-1");

        new String(unames,"UTF-8");

        req.setCharacterEncoding("UTF-8");

    }
}
