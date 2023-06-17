package model;

import com.sun.org.apache.bcel.internal.generic.DUP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装这个类的目的是：针对blog对象进行增删查改工作。
 */
public class BlogDao {

    //1:新增一份博客。
    public void add(Blog blog) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into blog values(null,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, blog.getTitle());
            preparedStatement.setString(2, blog.getContent());
            preparedStatement.setTimestamp(3, blog.getPostTimestamp());
            preparedStatement.setInt(4, blog.getUserId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(connection, preparedStatement, null);
        }
    }

    //2:删除一份博客

    public void delete(int blogId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1：与数据库进行连接
            connection = DBUtil.getConnection();
            //2:构造sql
            String sql = "Delete from blog where blogId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, blogId);
            //3:执行sql
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
        }
    }

    //3:查数据库中所有的博客

    public List<Blog> selectAll() {
        List<Blog> list = new ArrayList<Blog>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            String sql = "select * from blog";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                list.add(blog);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
            return list;
        }

    }

    //4：就查找一篇博客

    public Blog select(int blogId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Blog blog = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * form blog where blogId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, blogId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
            return blog;
        }
    }
}
