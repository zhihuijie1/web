package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    //1: userID查询用户信息

    public User selectById(int userid) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where userID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassward(resultSet.getString("passward"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
            return user;
        }

    }

    //2：根据用户名来查询用户的信息

    public User selectByUserName(String userName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from user where userName = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setUserName(resultSet.getString("userName"));
                user.setPassward(resultSet.getString("passward"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, preparedStatement, resultSet);
            return user;
        }
    }
}
