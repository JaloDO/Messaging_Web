package com.jsp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modelo.Conector;
import Modelo.Usuario;



/**
 * Servlet implementation class Controlador
 */
@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Conector chat;
	
	
       
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			chat = new Conector();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Controlador() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession sesion = request.getSession();
		String accion = request.getParameter("accion");
		Usuario u;
		
		switch(accion) {
		case "Login":
			u = new Usuario();
			u.setNombre(request.getParameter("username"));
			u.setPassword(request.getParameter("password"));
			System.out.println("No llega al metodo");
			u = chat.iniciarSesion(u);
			System.out.println("Pasa de chat iniciar sesion");
			if(u!=null) {
				System.out.println("No es nulo");
				sesion.setAttribute("usuario", u);
				
				RequestDispatcher pagina = request.getRequestDispatcher("chat.jsp");
				pagina.forward(request, response);
			}else {
				System.out.println("No coje el usuario");
			}
			break;
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
