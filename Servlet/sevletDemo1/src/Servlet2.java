import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/s2")
public class Servlet2 extends HttpServlet {
    //构造器
    public Servlet2() {
        System.out.println("这是一个构造方法");
    }

    //初始化方法
    @Override
    public void init() throws ServletException {
        System.out.println("init -> 进行serlet的准备工作");
    }

    //就绪/调用/服务方法
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("service -> 进行服务");
    }

    //销毁方法
    @Override
    public void destroy() {
        System.out.println("servlet实例已被销毁");
    }
}
/*
 servlet的生命周期：
 首先web客户端向servlet容器发送请求，servlet容器接收请求，然后利用反射机制来创建servlet对象。
 然后调用init方法用于检查和设置所需的资源，加载和准备数据。
 然后再实例化HttpServletRequest 实现类对象、HttpServletResponse 实现类对象
 调用servlet中的service方法，将HttpServletRequest 实现类对象、HttpServletResponse 实现类对象当作参数供给service方法使用。
 然后在service方法中调用HttpServletRequest 实现类对象的方法来获取http的请求信息，通过HttpServletResponse 实现类对象的方法来处理请求
 信息并生成响应数据
 servlet容器将响应结果返回web客户端。


*/
