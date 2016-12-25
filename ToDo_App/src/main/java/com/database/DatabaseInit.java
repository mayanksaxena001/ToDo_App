package com.database;

import java.io.IOException;
import java.io.InputStream;
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
	public void init() {

		System.out.println("Executing sql script...");

		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
			// statement.executeUpdate("SELECT usename FROM " + getConnection()
			// + ".pg_users;");
			for (String sql : retrieveSqls()) {
				System.out.println(sql);
				statement.executeUpdate(sql);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}
	
	private static Connection getConnection() throws URISyntaxException, SQLException {
	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    System.out.println(dbUrl);
	    return DriverManager.getConnection(dbUrl);
	}
	
	/*private static String getConnection() throws URISyntaxException, SQLException {
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));

	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
	    System.out.println(dbUri.getPath().substring(1));

	    return dbUri.getPath().substring(1);
	}*/
	
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
