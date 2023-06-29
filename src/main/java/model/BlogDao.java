package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 通过这个类, 封装针对 博客表 的基本操作
// 此处暂时不涉及到修改博客~~ (修改也可以通过 删除/新增 )
public class BlogDao {
    // 1. 新增一个博客.
    public void add(Blog blog) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 构造 SQL
            String sql = "insert into blog values(null, ?, ?, ?, ? )";
            statement = connection.prepareStatement(sql);
            statement.setString(1, blog.getTitle());
            statement.setString(2, blog.getContent());
            statement.setTimestamp(3, blog.getPostTimestamp());
            statement.setInt(4, blog.getUserId());
            // 3. 执行 sql
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }

    // 2. 根据博客 id 来查询指定博客 (博客详情页中)
    public Blog selectById(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1. 和数据库建立连接
            connection = DBUtil.getConnection();
            // 2. 构造 SQL
            String sql = "select * from blog where blogId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, blogId);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集合. 由于 blogId 在 blog 表中是唯一的. (主键)
            //    此时的查询结果, 要么是没有查到任何数据, 要么只有一条记录!!
            //    此处可以不使用 while, 直接 if 判定即可.
            if (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                return blog;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 5. 释放必要的资源
            DBUtil.close(connection, statement, resultSet);
        }
        return null;
    }

    // 3. 直接查询出数据库中所有的博客列表 (用于博客列表页)
    public List<Blog> selectAll() {
        List<Blog> blogs = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1. 和服务器建立连接.
            connection = DBUtil.getConnection();
            // 2. 构造 SQL 语句
            String sql = "select * from blog order by postTime desc";
            statement = connection.prepareStatement(sql);
            // 3. 执行 SQL
            resultSet = statement.executeQuery();
            // 4. 遍历结果集合
            while (resultSet.next()) {
                Blog blog = new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                // 注意这里的正文!!! 在博客列表页中, 不需要把整个正文内容都显示出来!!
                String content = resultSet.getString("content");
                if (content == null) {
                    content = "";
                }
                if (content.length() >= 100) {
                    content = content.substring(0, 100) + "...";
                }
                blog.setContent(content);
                blog.setPostTime(resultSet.getTimestamp("postTime"));
                blog.setUserId(resultSet.getInt("userId"));
                blogs.add(blog);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return blogs;
    }

    // 4. 删除指定博客
    public void delete(int blogId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            // 1. 和数据库建立连接.
            connection = DBUtil.getConnection();
            // 2. 构造 SQL
            String sql = "delete from blog where blogId = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, blogId);
            // 3. 执行 SQL
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            // 4. 关闭
            DBUtil.close(connection, statement, null);
        }
    }
}
