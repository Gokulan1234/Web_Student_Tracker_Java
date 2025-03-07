package com.anime.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
	
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			studentDbUtil=new StudentDbUtil(dataSource);
		}
		catch(Exception exc){
			throw new ServletException(exc);
			
		}
	}





	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	try {
		// read the command parameter
		String theCommand= request.getParameter("command");
		
		//if the command is missing default to the listing students
		
		if(theCommand==null) {
			theCommand="List";
		}
		
		//route to the appropriate method
		switch(theCommand) {
		case "List":
			listStudents(request,response);
			break;
			
		
			
		case "LOAD":
			loadStudent(request,response);
			break;
			
		case "UPDATE":
			updateStudent(request,response);
			
		case "DELETE":
			deleteStudent(request,response);
			break;
			default:
				listStudents(request,response);
		}
		
		
		
	} catch (Exception exc) {
		// TODO Auto-generated catch block
		throw new ServletException(exc);
	}
	
	}





	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student id from the form data
		String studentId=request.getParameter("studentId");
		
		//delete the student from database
		studentDbUtil.deleteStudent(studentId);
		
		//send them back to list students page
		listStudents(request,response);
		
	}





	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// read student info from form data
		int studentId=Integer.parseInt(request.getParameter("studentId"));
				String firstName=request.getParameter("firstName");
				String lastName=request.getParameter("lastName");
				String email=request.getParameter("email");
				
				//create a new student object
				Student theStudent=new Student(studentId,firstName,lastName,email);
				
				//add the student to the database
				studentDbUtil.updateStudent(theStudent);
				
				//send back to main page
				listStudents(request,response);
	}





	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		//read the student id from form data
		String thestudentId=request.getParameter("studentId");
		
		
		//get student from database (dbutil)
		Student theStudent=studentDbUtil.getStudent(thestudentId);
		
		//place student in the request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		//send to jsp page:update-student-jsp.form
		RequestDispatcher dispatcher= request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
		
	}





	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info from form data
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		//create a new student object
		Student theStudent=new Student(firstName,lastName,email);
		
		//add the student to the database
		studentDbUtil.addStudent(theStudent);
		
		//send back to main page
		response.sendRedirect(request.getContextPath()+"/StudentControllerServlet?command=LIST");
	}





	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
	
		//1.get students from db util
		List<Student>students=studentDbUtil.getStudents();
		
		//2.add studets to the request
		
		request.setAttribute("STUDENT_LIST", students);
		
		//3.send to jsp page 
		RequestDispatcher dispatcher=request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		try {
			// read the command parameter
			String theCommand= request.getParameter("command");
			
			//if the command is missing default to the listing students
			
			if(theCommand==null) {
				theCommand="List";
			}
			
			//route to the appropriate method
			switch(theCommand) {
			case "ADD":
				addStudent(request,response);
				break;
						default:
					listStudents(request,response);
			}
	
	
	}
		 catch (Exception exc) {
				// TODO Auto-generated catch block
				throw new ServletException(exc);
			}

}
}
