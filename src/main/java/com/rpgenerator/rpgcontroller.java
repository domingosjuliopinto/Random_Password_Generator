package com.rpgenerator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.sql.*;
@Controller
public class rpgcontroller {
	public String usernameglobal="";
	
	@GetMapping("/login")
	@ResponseBody
	
	
	public ModelAndView htmlView(@RequestParam(name="username",required=false,defaultValue="0") String username,Model model,
			                      @RequestParam(name="password",required=false,defaultValue="0") String password) throws ClassNotFoundException, SQLException
	{
		rpg login = new rpg();
		usernameglobal=username;
		login.setUsername(username);
		login.setPassword(password);
		
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/oopm","root","");
			PreparedStatement stmt = con.prepareStatement("select * from userinfo where username=? and password=?");
			stmt.setString(1,login.getUsername());
			stmt.setString(2, login.getPassword());
			ResultSet rs = stmt.executeQuery();
			PreparedStatement stmt1 = con.prepareStatement("select pword from randomp where userid=(select userid from userinfo where username=? )");
			stmt1.setString(1, login.getUsername());
			ResultSet rs1 = stmt1.executeQuery();
			if (rs1.next()) {
			String password1 = rs1.getString("pword");
			login.setPword(password1);}
			if (rs.next()) {
				 ModelAndView mav1=new ModelAndView();
				 mav1.addObject("login",login);
	        	   mav1.setViewName("afterlogin.html");
	        	   return mav1;
			}
			
			
			else {
				 ModelAndView mav = new ModelAndView();
				 mav.setViewName("index.html");
				 return mav;
			}		
	}
	
	@GetMapping("/Generate")
	@ResponseBody
	public ModelAndView html1View(@RequestParam(name="number",required=false,defaultValue="0") int number,Model model)
	{
		
		
		rpg pg = new rpg();
		pg.setNumber(number);
		pg.setUsername(usernameglobal);
        pg.setGenpass(rpgenerator.generatePassword(number,pg)); 
		ModelAndView mav2 = new ModelAndView();
		mav2.addObject("pg", pg);
		mav2.setViewName("main.html");
		return mav2;
	}
	

	

}
