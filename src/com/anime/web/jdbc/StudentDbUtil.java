package com.anime.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;

	public StudentDbUtil(DataSource dataSource) {
		
		this.dataSource = dataSource;
	}
	
	public List<Student>getStudents()throws Exception{
		List<Student>students=new ArrayList<>();
		
		
		Connection myconn=null;
		Statement mystmt=null;
		ResultSet myrs=null;
		
		try {
		
		//1.generate a connection pool
		myconn=dataSource.getConnection();
		
		//2.create a statement 
		mystmt=myconn.createStatement();
		String sql="select * from student order by last_name";
		
		//3.execute sql query
		myrs=mystmt.executeQuery(sql);
		
		//4.process the resultset
		while(myrs.next()) {
			
			int id=myrs.getInt("id");
			String firstname=myrs.getString("first_name");
			String lastname=myrs.getString("last_name");
			String email=myrs.getString("email");
			
			
			Student temp= new Student(id,firstname,lastname,email);
			
			
			students.add(temp);
			
			
		}
		
			
			return students;
		}finally {
		//5.close the JDBC objects
		
			close(myconn,mystmt,myrs);
		}
		
		
	}

	private void close(Connection myconn, Statement mystmt, ResultSet myrs) {
		// TODO Auto-generated method stub
		
		
			try {
				if(myrs!=null)myrs.close();
				
				if(mystmt!=null)mystmt.close();
				
				if(myconn!=null)myconn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void addStudent(Student theStudent)throws Exception {
		// create sql for insert
		Connection myconn = null;
		PreparedStatement mystmt = null;
		
		try {
			//get db connection
			myconn=dataSource.getConnection();
			
		String sql="insert into student "+"(first_name, last_name, email)"+
		"values(?,?,?)";
		mystmt=myconn.prepareStatement(sql);
		
		//set the param values for the student
		mystmt.setString(1, theStudent.getFirstName());
		mystmt.setString(2, theStudent.getLastName());
		mystmt.setString(3, theStudent.getEmail());
		
		//execute the sql insert
		mystmt.execute();
		
		}finally {
		//clean up jdbc objects
			close(myconn,mystmt,null);
		}
	}

	public Student getStudent(String thestudentId) throws Exception {
         Student theStudent =null;
         
         Connection myconn=null;
         PreparedStatement mystmt=null;
         ResultSet myrs=null;
         int studentId;
         
        try { 
        	
        	//change string to int 
        	studentId= Integer.parseInt(thestudentId);
        	
        	//get connection to database
        	myconn=dataSource.getConnection();
        	
        	//create sql to get selected student
        	String sql="select * from student where id=?";
        	
        	//create prepared statement
        	mystmt=myconn.prepareStatement(sql);
        	
        	//set params
        	mystmt.setInt(1, studentId);
        	
        	
        	//execute statement
        	myrs=mystmt.executeQuery();
        	
        	//retrieve data from result set row
        	if(myrs.next()) {
        		String firstName=myrs.getString("first_name");
        		String lastName=myrs.getString("last_name");
        		String email=myrs.getString("email");
        		theStudent=new Student(studentId,firstName,lastName,email);
        	}
        	else {
        		throw new Exception ("could not find student id"+studentId);
        	}
         
         
         
		return theStudent;
        }
        finally {
        	close(myconn,mystmt,myrs);
        }
	}

	

	public void updateStudent(Student theStudent) throws Exception {
		Connection myconn=null;
		PreparedStatement mystmt =null;
		try {
		//get db connection
		myconn=dataSource.getConnection();
		
		//create sql update statement
		String sql="update student "+" set  first_name=?,last_name=?,email=? "+" where id=?";
		
		//prepare statement
		mystmt=myconn.prepareStatement(sql);
		
		// set the params
		mystmt.setString(1, theStudent.getFirstName());
		mystmt.setString(2, theStudent.getLastName());
		mystmt.setString(3, theStudent.getEmail());
		mystmt.setInt(4, theStudent.getId());
		
		//execute the statement
		mystmt.execute();
		
		}finally {
			close(myconn,mystmt,null);
		}
		
		
	}

	public void deleteStudent(String studentId) throws Exception {
		Connection myconn = null;
		PreparedStatement mystmt = null;
		
		try {
		int id=Integer.parseInt(studentId);
		
		//get a connection to database
		myconn=dataSource.getConnection();
		
		//create a sql statement to delete the student in db
		String sql="delete from student where id=?";
		
		//prepare statement
		mystmt=myconn.prepareStatement(sql);
		
		//set the params
		mystmt.setInt(1, id);
		
		//execute query
		mystmt.execute();
		}
		finally {
			close(myconn,mystmt,null);
			
		}
		
	}
}
