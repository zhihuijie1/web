import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//在完成好了一切代码的编写后，还需要向服务器说明，特定请求对应特定资源。
//简而言之就是告诉服务器在/s1这个地方可以找到Servlet1从而实现service方法。
//使用@WebServlet注解将一个继承于javax.servlet.http.HttpServlet的类标注为可以处理用户请求的 Servlet。
@WebServlet("/s1")
public class Servlet1 extends HttpServlet {
    /*
    满足servlet规范只能让我们的类可以接收请求，想要对请求进行处理，就要重写service方法。
    后续当我们调用Servlet的时候会自动的调用service方法。
    将请求封装到 HttpServletRequest req对象中，将响应封装到 HttpServletResponse resp对象中。
    */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("hello servlet");
        //响应一句话给浏览器。
        resp.getWriter().write("hello world");
    }
}

/*
servlet的工作流程是：
浏览器会根据获取的资源路径匹配到真实的路径
然后服务器会创建Servlet对象，然后调用service方法并生成request 与 response对象，用于处理请求与响应。
这里注意当第一次创建Servlet对象时，会调用init方法进行初始化操作。
调用service完毕后返回服务器 由服务器将response缓冲区的数据取出，以http响应的格式发送给浏览器
*/