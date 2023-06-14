import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/st1")
public class Servlet01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建一个cookie
        Cookie cookie = new Cookie("name", "1234");
        Cookie cookie1 = new Cookie("name", "12");
        //将cookie响应到客户端。
        resp.addCookie(cookie);
        resp.addCookie(cookie1);

        //cookie的获取
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                System.out.println(c.getName() + " -> " + c.getValue());
            }
        }
    }
}
