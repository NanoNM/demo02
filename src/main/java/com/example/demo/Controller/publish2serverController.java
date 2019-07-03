package com.example.demo.Controller;

import com.example.demo.provider.MySQLProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value="publish2server", method= RequestMethod.POST)
    public String pushToSQL(HttpServletRequest request){
        String inputtitle = request.getParameter("inputtitle");
        String discreation = request.getParameter("discreation");
        String inputlib = request.getParameter("inputlib");
        long timestamp = System.currentTimeMillis();
        long ID = gitHubBackCodeController.getID();
        Connection connection = null;
        try{
            if(inputtitle=="" || discreation=="" || inputlib==""){
                throw new RuntimeException("写点东西再提交好么");
            }
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
                throw new Exception("先登录好吗?");
            }
            int n = preparedStatement.executeUpdate();
        }catch (Exception e){
            String error =e.toString();
            request.getSession().setAttribute("error",error);
            return "publish";
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        request.getSession().setAttribute("success","发布成功!!");
        return "publish";
    }
}
