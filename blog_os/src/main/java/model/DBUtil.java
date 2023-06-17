package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtil {
    //封装数据库
    //将对数据库的操作进行封装，这样就只需连接一次数据库。


    /**
     * 1：通过dataSource来确定数据库的位置。
     */
    private static DataSource dataSource = new MysqlDataSource();

    static {
        ((MysqlDataSource) dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/blog_system?characterEncoding=UTF-8&useSSL=false");
        ((MysqlDataSource) dataSource).setDatabaseName("root");
        ((MysqlDataSource) dataSource).setPassword("171612cgj");
    }

    /**
     * 2：与数据库建立连接
     * 注意Connection是一个“有连接”，即先检查一下连接道路是否通畅，如果通畅就连接。如果连接出现问题。就抛出异常。
     * 注意这个连接需要管理，不用的时候就关闭这个连接，否则会一直占用着资源。
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    /**
     * 关闭数据库资源
     */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
