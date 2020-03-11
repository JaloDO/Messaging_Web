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
		RequestDispatcher pagina = null;
		request.setAttribute("tipo", false);
		request.setAttribute("oculto", false);
		
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
				cargarChat(request, response, u);
			}else {
				request.setAttribute("error", "Datos de inicio de sesión incorrectos");
				System.out.println("No coje el usuario");
				pagina = request.getRequestDispatcher("index.jsp");
				pagina.forward(request, response);
			}
			break;
			
		case "Ver Enviados":
			u = (Usuario) sesion.getAttribute("usuario");
			request.setAttribute("tipo", true);
			cargarChat(request, response, u);
			break;
		case "Ver Recibidos":
			u = (Usuario) sesion.getAttribute("usuario");
			request.setAttribute("tipo", false);
			cargarChat(request, response, u);
			break;
			
		case "Borrar":
			int id = Integer.parseInt(request.getParameter("idMensaje"));
			System.out.println("ID: " + id);
			
			u = (Usuario) sesion.getAttribute("usuario");
			m = chat.existeMensaje(Integer.parseInt(request.getParameter("idMensaje")));
			if(m!=null) {
				if(chat.borrarMensaje(m)) {
					System.out.println("Mensaje borrado");
				}
				else {
					System.out.println("No ha borrado el mensaje");
					request.setAttribute("error2", "Error al borrar mensaje");
				}
			}
			else {
				System.out.println("No ha encontrado el mensaje");
				request.setAttribute("error2", "Error al borrar mensaje");
			}
			cargarChat(request, response, u);
			break;

		case "Nuevo Mensaje":
			u = (Usuario) sesion.getAttribute("usuario");
			request.setAttribute("oculto", false);
			System.out.println("nombre: "+request.getParameter("nombre"));
			request.setAttribute("nombre", request.getParameter("nombre"));
			cargarChat(request, response, u);
			break;

		case "Enviar":
			u = (Usuario) sesion.getAttribute("usuario");
			String contenido = request.getParameter("contenido");
			String nombre = request.getParameter("nombre");
			//Hay que comprobar que no esten vacï¿½os
			if(!contenido.equals("") && !nombre.equals("")) {
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
						sesion.setAttribute("usuario", u);
					}
					else {
						System.out.println("No ha enviado el mensaje");
						request.setAttribute("error", "Error al enviar mensaje");
					}
				}
				else {
					System.out.println("No ha encontrado el usuario");
					request.setAttribute("error", "No se ha encontrado el usuario");
				}
			}
			else {
				System.out.println("Campos vacios");
				request.setAttribute("error", "No puede haber campos vacíos");
			}
			request.setAttribute("tipo", true);
			cargarChat(request, response, u);
			
			break;
			
		case "Perfil":
			request.setAttribute("error", "");
			pagina = request.getRequestDispatcher("modificar.jsp");
			pagina.forward(request, response);
			break;
			
		case "Modificar":
			request.setAttribute("error", "");
			u = (Usuario) sesion.getAttribute("usuario");
			String p1 = request.getParameter("password1");
			System.out.println(request.getParameter("password1"));
			System.out.println(request.getParameter("password2"));
			String p2 = request.getParameter("password2");
			if(p1.equals(p2) && !p1.equals("")) {
				System.out.println("p1 = p2");
				if(!p1.equals(u.getPassword())) {
					System.out.println("p1 != password");
					u.setPassword(p1);
					if(chat.modificarUsuario(u)) {
						System.out.println("modificar contraseña");
						u = chat.iniciarSesion(u);
						sesion.setAttribute("usuario", u);
					}
					else {
						request.setAttribute("error", "Error al modificar el usuario");
					}
				}else {
					request.setAttribute("error", "Error, No puede ser el mismo Password");
				}
			}else {request.setAttribute("error", "Error, Password no coincide");}
			
			pagina = request.getRequestDispatcher("modificar.jsp");
			pagina.forward(request, response);
			break;
			
		case "ocultarForm":
			u = (Usuario) sesion.getAttribute("usuario");
			request.setAttribute("oculto", true);
			cargarChat(request, response, u);
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
	
	private void cargarChat(HttpServletRequest request, HttpServletResponse response, Usuario usuario){
		
		try {
			//false para recibidos, true para enviados
			List<Mensaje> mensajes;
			boolean tipoMensaje = (boolean) request.getAttribute("tipo");
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
