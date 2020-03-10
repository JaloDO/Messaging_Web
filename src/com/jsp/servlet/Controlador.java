package com.jsp.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Modelo.Conector;
import Modelo.Mensaje;
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
		Mensaje m;
		
		switch(accion) {
		case "Login":
			u = new Usuario();
			u.setNombre(request.getParameter("username"));
			u.setPassword(request.getParameter("password"));
			u = chat.iniciarSesion(u);
			if(u!=null) {
				sesion.setAttribute("usuario", u);
				request.setAttribute("usuario", u);
				//false para recibidos, true para enviados
				cargarChat(request, response, false, u);
			}else {
				System.out.println("No coje el usuario");
			}
			break;
		case "Enviados":
			u = (Usuario) sesion.getAttribute("usuario");
			cargarChat(request, response, true, u);
			break;
			//creo que hay que hacer un solo cargar, y tener el atributo en el request
			//cuando se pulsa el boton, se hace:
			/*
			 * boolean flag = request.getParameter("flag");
			 * flag = !flag;
			 * request.setAttribute("flag", flag);
			 * y luego en el controlador se llama a cargarChat(request, response, getParameter("flag"), u);
			 */
		case "Recibidos":
			u = (Usuario) sesion.getAttribute("usuario");
			cargarChat(request, response, false, u);
			break;
		case "Borrar":
			int id = Integer.parseInt(request.getParameter("idMensaje"));
			System.out.println("ID: " + id);
			
			u = (Usuario) sesion.getAttribute("usuario");
			m = chat.existeMensaje(Integer.parseInt(request.getParameter("idMensaje")));
			if(m!=null) {
				if(chat.borrarMensaje(m)) {
					cargarChat(request, response, true, u);
				}
				else {
					System.out.println("No ha borrado el mensaje");
				}
			}
			else {
				System.out.println("No ha encontrado el mensaje");
			} 
			break;
		
		/*case "Enviar":
			m = new Mensaje();
			u = (Usuario) sesion.getAttribute("usuario");
			String contenido = request.getParameter("contenido");
			String nombre = request.getParameter("nombre");
			if(!contenido.isBlank() || !nombre.isBlank()) {
				m.setContenido(contenido);
				Usuario destino = new Usuario();
				destino.setNombre(nombre);
				destino = chat.obtenerDestinatario(destino);
				System.out.println("Usuario destino: "+destino.toString());
				
				if(destino!=null){
					m.setEmisor(u);
					m.setReceptor(destino);
					m.setFecha(new Date());
					chat.enviarMensaje(m);
					cargarChat(request, response, false, u);
				}
				break;
			}
			break;*/

		case "Nuevo Mensaje":
			u = (Usuario) sesion.getAttribute("usuario");
			request.setAttribute("nombre", request.getParameter("selectNombre"));
			cargarChat(request, response, true, u);
			break;

		case "Enviar":
			u = (Usuario) sesion.getAttribute("usuario");
			String contenido = request.getParameter("contenido");
			String nombre = request.getParameter("nombre");
			//Hay que comprobar que no esten vac�os
			m = new Mensaje();
			m.setEmisor(u);
			Usuario receptor = new Usuario();
			receptor.setNombre(nombre);
			receptor = chat.obtenerDestinatario(receptor);
			if(receptor!=null) {
				m.setReceptor(receptor);
				m.setContenido(contenido);
				m.setFecha(new Date());
				if(chat.enviarMensaje(m)) {
					//recargar usuario
					u = chat.iniciarSesion(u);
					cargarChat(request, response, true, u);
				}
				else {
					System.out.println("No ha enviado el mensaje");
				}
			}
			else {
				System.out.println("No ha encontrado el usuario");
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
	
	private void cargarChat(HttpServletRequest request, HttpServletResponse response, Boolean tipoMensaje, Usuario usuario){
		
		try {
			//false para recibidos, true para enviados
			List<Mensaje> mensajes;
			if(tipoMensaje) {
				mensajes = chat.mensajesEnviados(usuario);
			}
			else {
				mensajes = chat.mensajesRecibidos(usuario);
			}
			request.setAttribute("mensajes", mensajes);
			request.setAttribute("tipo", tipoMensaje);
			RequestDispatcher pagina = request.getRequestDispatcher("chat.jsp");
			pagina.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
