package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.PlayerDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/playerList")
public class PlayerServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //修改编码与解码格式。
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf8");


        String playerId = req.getParameter("playerId");

        System.out.println("PlayerServlet  "+ playerId);

        PlayerDao playerDao = new PlayerDao();
        List<Blog> list = playerDao.selectAll(Integer.parseInt(playerId));

        String s = objectMapper.writeValueAsString(list);
        resp.setContentType("application/json;charset=utf8");
        resp.getWriter().write(s);
    }
}
