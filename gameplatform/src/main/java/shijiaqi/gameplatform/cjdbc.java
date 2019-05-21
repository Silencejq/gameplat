package shijiaqi.gameplatform;

import java.sql.*;

public class cjdbc {
	Connection conn = null;
	Statement stmt = null;

	cjdbc() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/game?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false",
					"root", "");
			stmt = conn.createStatement();
		} catch (SQLException e) {
		} catch (Exception e) {
		}
	}
}
