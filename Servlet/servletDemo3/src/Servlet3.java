import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet("/st3")
public class Servlet3 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie c1 = new Cookie(URLEncoder.encode("我喜欢"), "王正捷");

        resp.addCookie(c1);

        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                System.out.println(URLDecoder.decode(c.getName()) + " -> " + c.getValue());
            }
        }

        System.out.println("---------------------");
        //获取session对象
        HttpSession session = req.getSession();
        //获取session的唯一标识符
        String id = session.getId();
        System.out.println(id);
        //判断当前的session对象是否是新的。
        boolean aNew = session.isNew();
        System.out.println(aNew);
    }
}
