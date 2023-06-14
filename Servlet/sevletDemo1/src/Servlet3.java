import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/s3")
public class Servlet3 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取客户端请求的完整地址(http开始到？之前的内容).
        String url = req.getRequestURL().toString();
        System.out.println("客户端的完整地址是：" + url);

        //获取客户端请求资源的具体名字(URI)(从站点名开始往后到？之前).
        String uri = req.getRequestURI().toString();
        System.out.println("客户端请求部分的URI是：" + uri);

        //获取请求行中的全部参数部分（？后面的部分）
        String queryString = req.getQueryString();
        System.out.println("请求行中的参数部分: " + queryString);

        // 获取指定名称的参数，返回字符串
        String uname = req.getParameter("uname");
        System.out.println("uname的参数值：" + uname);

        // 获取指定名称参数的所有参数值，返回数组
        String[] hobbys = req.getParameterValues("hobby");
        System.out.println("获取指定名称参数的所有参数值：" + Arrays.toString(hobbys));

        //获取客户端的请求方式
        String method = req.getMethod();
        System.out.println("获取客户端的请求方式: " + method);

        //获取http的版本号
        String protocol = req.getProtocol();
        System.out.println("http的版本号: " + protocol);

        // 获取webapp名字 （站点名）
        String webapp = req.getContextPath();
        System.out.println("获取站点名字：" + webapp);
    }
}