package org.tektutor;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.sql.*;

@RestController
public class HelloController {

	public String readGreetingMsgFromDB() {
		String url = "jdbc:mysql://mysql:3306/tektutor";
		String username = "root";
		String password = "root@123";

		String query = "select * from greeting";
		String msg = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection connection = DriverManager.getConnection(url,username,password);
			Statement statement   = connection.createStatement();
			ResultSet resultSet   = statement.executeQuery(query);
			resultSet.next();

			msg = resultSet.getString("message"); 

			statement.close();
			connection.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return msg;

	}

	@RequestMapping("/")
	public String sayHello() {
		//return "Hello Springboot Microservice v1.0";
		return readGreetingMsgFromDB();
	}

}
