package org.example;

import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Indexer {
    static Connection connection=null;

    //CONSTRUCTOR STORING IMPORTANT DETAILS IN SQL SERVERE DATABASE
     Indexer(Document document, String url){
        // document contains all related data present in web page, but take the important ones and store in db
        String title=document.title();
        String link=url;
        String text=document.text();
        // save these data to db in mysql
        try{
            connection= DataBaseConnector.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Insert into pages values(?,?,?)");
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,link);
            preparedStatement.setString(3,text);
            preparedStatement.executeUpdate();

        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
