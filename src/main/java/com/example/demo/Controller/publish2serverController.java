package com.example.demo.Controller;

import com.example.demo.provider.MySQLProvider;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
public class publish2serverController {
    @Autowired
    private GitHubBackCodeController gitHubBackCodeController;
    @Autowired
    private MySQLProvider mySQLProvider;
    @Value("${MySQLDataBasePublish}")
    String MySQLDataBasePublish;
    @GetMapping("/publish2server")
    public String pushToSQL(HttpServletRequest request){
        String inputtitle = request.getParameter("inputtitle");
        String discreation = request.getParameter("discreation");
        String inputlib = request.getParameter("inputlib");
        long timestamp = System.currentTimeMillis();
        long ID = gitHubBackCodeController.getID();
        Connection connection = null;
        try{
            String sql = "insert into articles value(?,?,?,?,?)";
            connection = mySQLProvider.getConnection(MySQLDataBasePublish);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,inputtitle);
            preparedStatement.setString(2,discreation);
            preparedStatement.setString(3,inputlib);
            preparedStatement.setLong(4,timestamp);
            if(ID != -1) {
                preparedStatement.setLong(5, ID);
            }else{
                throw new Exception("ID为空");
            }
            int n = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("code 04 服务器异常 此处异常来源于com.example.demo.Controller.publish2serverController 此异常可能是因为 问题提交数据库失败导致");
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "index";
    }
}
