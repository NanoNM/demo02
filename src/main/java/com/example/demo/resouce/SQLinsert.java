package com.example.demo.resouce;

import com.example.demo.provider.MySQLProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Controller
public class SQLinsert {
    @Autowired
    private MySQLProvider mySQLProvider;
    public int insertIntoUserinfo(long id,String accid,String name,String token){
        Connection connection = null;
        int n;
        try {
            String sql = "insert into user value(?,?,?,?,?,?) ON DUPLICATE KEY UPDATE  ID=?;";
            connection = mySQLProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            preparedStatement.setString(2,accid);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,token);
            Date date = new Date();
            long time = date.getTime();
            preparedStatement.setLong(5,time);
            preparedStatement.setLong(6,time+259200000L);
            preparedStatement.setLong(7,id);
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            n=-1;
            System.err.println("code: 01 服务器异常!! 此处异常来源于com.example.demo.resouce.SQLinsert 可能原因 数据库写入异常 检查SQL语句!");
            e.printStackTrace();
        }
        return n;
    }
    public String getCToken(String token){
        Connection connection = null;
        try{
            String sql = "select * from user where token=?;";
            connection = mySQLProvider.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return resultSet.getString(3);
            }
        }catch (Exception e){
            System.err.println("code: 02 服务器异常!!此处异常来源于com.example.demo.resouce.SQLinsert 可能原因 浏览器返回token异常或 token数据库比对异常!!");
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("code 03 服务端异常 此异常来源于 com.example.demo.resouce的 getCToken 方法 可能原因:数据库连接关闭异常!!");
                }
            }
        }
        return null;
    }
}
