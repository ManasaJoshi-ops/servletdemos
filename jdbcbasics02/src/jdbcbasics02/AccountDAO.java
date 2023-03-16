package jdbcbasics02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {
public static void main(String[] args) {
	try( Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/my_db", "root","MYPASS");
		 Statement statement=conn.createStatement();	
		 ResultSet rs=statement.executeQuery("select * from account");) {
	while(rs.next()) {
		int accnum=rs.getInt(1);
		String lastname=rs.getString(2);
		String firstname=rs.getString(3);
		int bal=rs.getInt(4);
		System.out.println(accnum+"|"+lastname+"|"+firstname+"|"+bal);
		
	}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	}
}

