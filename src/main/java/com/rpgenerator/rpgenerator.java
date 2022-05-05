package com.rpgenerator;
import java.sql.*;
import java.util.Random;
public class rpgenerator {
	  
		   public static String generatePassword(int number, rpg pg) {
		      String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		      String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
		      String specialCharacters = "!@#$";
		      String numbers = "1234567890";
		      String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
		      Random random = new Random();
		      
		      char[] password = new char[number];

		      password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
		      password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
		      password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		      password[3] = numbers.charAt(random.nextInt(numbers.length()));
		   
		      for(int i = 4; i< number ; i++) {
		         password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		      }
		      System.out.println(password);
		      try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				Connection con = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oopm","root","");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PreparedStatement stmt = null;
				
				try {
					stmt = con.prepareStatement("update randomp set pword=? where userid=(select userid from userinfo where username=? )");
					stmt.setString(1,String.valueOf(password));
					stmt.setString(2,pg.getUsername());
					int rs = stmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
		      return String.valueOf(password);
		     
		   }
		  


}



