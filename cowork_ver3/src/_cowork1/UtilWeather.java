package _cowork1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UtilWeather {

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");		
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://woori-fisa2.cfnz7hfzq9bn.ap-northeast-2.rds.amazonaws.com/I_HATE_JAVA";
		String id = "admin";
		String pw = "woorifisa2!";
		
		return DriverManager.getConnection(url, id, pw);
	}

	public static void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException{ // 순서대로
		rs.close();
		stmt.close();
		conn.close();
	}
	public static void close(Connection conn, Statement stmt) throws SQLException{ // 순서대로
		stmt.close();
		conn.close();
	}

}
