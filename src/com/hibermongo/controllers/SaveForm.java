package com.hibermongo.controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.TransactionManager;

import com.hibermongo.entities.User;


/**
 * Servlet implementation class SaveForm
 */
@WebServlet("/SaveForm")
public class SaveForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = new User();
		u.setName(request.getParameter("name"));
		u.setAge(Integer.valueOf(request.getParameter("age")));

		try {
			TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-jpa-tutorial");
			tm.begin();
			
			EntityManager em = emf.createEntityManager();
			
			em.persist(u);
			List<User> list =  em.createNativeQuery("db.User.find({})", User.class).getResultList();
			StringBuilder sb = new StringBuilder();
			sb.append("Successfuly persisted the value into DB \n\n");
			sb.append("These are the documents present in the collection \n\n");
			for(User user : list)
			{
				sb.append("Name : "+ user.getName() + " \n");
				sb.append("Age : "+user.getAge() + " \n");
				sb.append("Id : "+user.getId() +" \n");
			}
			em.flush();
			em.close();
			tm.commit();
			response.getWriter().append(sb);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
