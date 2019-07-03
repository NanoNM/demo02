package com.example.demo.Controller;

import com.example.demo.provider.MySQLProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class LoginOut {
    @Autowired
    private MySQLProvider mySQLProvider;
    @GetMapping("/loginout")
    public String loginOut(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")){
                    Connection connection= getConnect();
                    if(connection!=null){
                        long userID= selectName(connection,cookie.getValue().trim());
                        deleteUser(userID);

                    }else{
                        throw new RuntimeException("数据库连接异常");
                    }
                }
            }
        }
        request.getSession().setAttribute("user",null);
        return "redirect:/";
    }
    private Connection getConnect(){
        Connection connection = null;
        try {
            connection=mySQLProvider.getConnection("userinfo");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }
    private long selectName(Connection connection,String token){
        String sql = "select id from user where token=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,token);
            ResultSet resultSet = preparedStatement.executeQuery();
            long str = -1;
            while(resultSet.next()){
                str = resultSet.getLong("id");
                break;
            }
            return str;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void deleteUser(long id){
        String sql = "delete from user where id=?";
        Connection connection = getConnect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            int n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
