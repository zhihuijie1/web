import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//cookie生命周期的测试。
@WebServlet("/st2")
public class Servlet02 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //创建cookie对象
        Cookie c1 = new Cookie("asdsad","王正捷");
        //设置当前cookie对象的存活时间:10秒
        c1.setMaxAge(10);
        //向客户端响应该cookie对象
        resp.addCookie(c1);

        //创建cookie对象
        Cookie c2 = new Cookie("hhh爱上顶","kkk");
        //设置当前cookie对象的存活时间:10秒
        c1.setMaxAge(-1);
        //向客户端响应该cookie对象
        resp.addCookie(c2);

        //创建cookie对象
        Cookie c3 = new Cookie("ghfgh","耶耶耶");
        //设置当前cookie对象的存活时间:10秒
        c1.setMaxAge(0);
        //向客户端响应该cookie对象
        resp.addCookie(c3);
    }
}
