package com.database;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInit {

	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	public void init(){
		try {
			getConnection();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Executing sql script...");
		try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()) {
               for(String sql: retrieveSqls()) {
                   statement.executeUpdate(sql);
                   System.out.println(sql);
               }
           } catch (SQLException | IOException e) {
               System.out.println(e.getMessage());
               return ;
           }
	}
	
	private static Connection getConnection() throws URISyntaxException, SQLException {
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
	    System.out.println(dbUrl);

	    return DriverManager.getConnection(dbUrl, username, password);
	}
	
	 private static Connection getMasterConnection() throws SQLException {
	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","root","root");
	    }
	
	private static String[] retrieveSqls() throws IOException {
        InputStream stream = DatabaseInit.class.getResourceAsStream("/sql/todo_table_sql.sql");
        String sqlFileContent = new Scanner(stream).useDelimiter("\\A").next();
        stream.close();
        //replace all comments and send back the set of SQLs
        return sqlFileContent.replaceAll("(--.*)|(((/\\*)+?[\\w\\W]+?(\\*/)+))","").split(";");
    }
}
