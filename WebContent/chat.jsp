<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
    <%@page import="java.util.ArrayList" %>
    <%@page import="Modelo.Mensaje" %>
    <%@page import="Modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Messaging Web</title>
<!-- Bootstrap core CSS -->
  <link href="https://augustobrigadaw.000webhostapp.com/resources2/bootstrap.css" rel="stylesheet">
<!-- Custom styles for this template -->
  <link href="https://augustobrigadaw.000webhostapp.com/resources2/css/shop-homepage.css" rel="stylesheet">

  
  <%Boolean tipo = (Boolean) request.getAttribute("tipo");
  	request.setAttribute("nombre","nombre");
  	%>
 <%
  	boolean match = false;
 	List<String>contactos = new ArrayList<String>();

  	Usuario user = (Usuario) session.getAttribute("usuario");
  	List<Mensaje>enviados = user.getEnviados();
  	for (Mensaje m: enviados){
  		if(contactos.isEmpty()){
  			contactos.add(m.getReceptor().getNombre());
  		}
  		else{
  			match = false;
			for(int i=0;i<contactos.size();i++){
				if(contactos.get(i).equals(m.getReceptor().getNombre())){
					match = true;
				}
			}
			if(!match){
				contactos.add(m.getReceptor().getNombre());
			}
  		}
  	}
  %>

</head>
<body style="background-color:#c9c8c5;">
	<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container">
      
      <div class="collapse navbar-collapse" id="navbarResponsive">
      	<label class="nav-item" style="left:0px;font-size:2.5em;">MESSAGING WEB</label>
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="/Messaging_Web/">
            	<img src="https://www.nicepng.com/png/full/266-2660273_expand-slideshow-white-back-icon-png.png" width="20" height="30"/>
            </a>
          </li>

          <li class="nav-item active">
            <a class="nav-link" href="#" disabled>Mensajes
            	<span class="sr-only">(current)</span>
            </a>
          </li>
          <li class="nav-item">
          	<a class="nav-link" href="Controlador?accion=Perfil">Perfil</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  
  <div id="panel_general" style="position:relative;">
   <div id="panel_contenido" style="min-height:85vh;">
        <h3 class="text-center text-black pt-5 pb-2">Bandeja de <% if(tipo){%>SALIDA<% }else{ %>ENTRADA<% } %></h3>
        <div id="div_formulario1">
               <form id="chat"  action="Controlador" method="post">
               	<div id="menu_izquierda" class="col-lg-3" style="float:left;">
               	<p><b>aqui quiero hacer un div no muy grande que quede a la izquierda</b></p>
               	<div id="botonVer">
							<% if(tipo){ %>
								<input type="submit" name="accion" value="Recibidos" class="btn btn-dark btn-md" style="width:50%;">
							<% }else{ %>
								<input type="submit" name="accion" value="Enviados" class="btn btn-dark btn-md" style="width:50%;">
							<% } %>
				</div>
				<div id="botonRedactar" class="pt-2 pb-2">
					<input type="submit" name="accion" value="Nuevo Mensaje" class="btn btn-light btn-md" style="width:50%;"/>
				</div>
				
				<select id="selectNombre" name="selectNombre" class="form-control" style="width:50%">
					<option value="nombre" disabled>Selecciona un usuario</option>
					<% for(String s: contactos){ %>
					<option value="<%=s%>"><%=s%></option>
					<% } %>
				</select>
               	</div>
                	<div id="tabla" style="position:relative;max-height:550px;overflow:auto;display:block;">       
                           <table class="table table-hover table-striped table-light" caption="titulo de la tabla">
                           <%  
                           		if(tipo){
                           %>
  						   	<thead>
    							<tr>
      								<th scope="col">ID</th>
      								<th scope="col">Destinatario</th>
     								<th scope="col">Contenido</th>
     								<th scope="col">Fecha</th>
    							</tr>
  							 </thead>
  							 <tbody>
    							<%
    							List<Mensaje> listaMensajes =(List<Mensaje>) request.getAttribute("mensajes");
    							for(Mensaje m : listaMensajes){
    							%>
    							<tr>
    								<td><%=m.getCodigo()%></td>
    								<td><%=m.getReceptor().getNombre()%></td>
    								<td><%=m.getContenido()%></td>
    								<td><%=m.getFecha()%></td>
    								<td><input class="form-control" type="submit" formaction="Controlador?idMensaje=<%=m.getCodigo()%>" name="accion" value="Borrar"></td>
    							</tr>
    							<%}%>
  							 </tbody>
  							 <% //cierre del if
  							 	}
                           		else{ %>
                           	<thead>
    							<tr>
      								<th scope="col">ID</th>
      								<th scope="col">Autor</th>
     								<th scope="col">Contenido</th>
     								<th scope="col">Fecha</th>
    							</tr>
  							 </thead>
  							 <tbody>
    							<%
    							List<Mensaje> listaMensajes =(List<Mensaje>) request.getAttribute("mensajes");
    							for(Mensaje m : listaMensajes){
    							%>
    							<tr>
    								<td><%=m.getCodigo()%></td>
    								<td><%=m.getEmisor().getNombre()%></td>
    								<td><%=m.getContenido()%></td>
    								<td><%=m.getFecha()%></td>
    							</tr>
    							<%}%>
  							 </tbody>
  							 <% }//cierre del else %>
							</table>
							<%if(request.getAttribute("error2")!=null) { %>
                            	<label style="color:red"><%=request.getAttribute("error2") %></label>
                            	<% } %>
					</div>
								
            </form> 
        </div>
    	<div id="div_formulario2" class="align-content-center pt-5 pb-4">
    		<form id="mensaje" action="Controlador" method="post">
    			<div class="col-md-6" style="position:relative;left:25%;">
    				<label for="nombre" class="badge badge-light">Para:</label>
                    <input type="text" id="nombre" name="nombre" placeholder="nombre" value="<%=request.getParameter("selectNombre")%>" class="form-control"></input>
                    <br />
                    <label for="contenido" class="badge badge-light">Mensaje:</label>
                    <input type="text" id="contenido" name="contenido" placeholder="contenido" class="form-control"/>
                    <br />
                    <%if(request.getAttribute("error")!=null) { %>
                            	<label style="color:red" ><%=request.getAttribute("error") %></label>
                            	<% } %>
    				<input type="submit" name="accion" value="Enviar" class="btn btn-light btn-sm" >
    				<button type="button" class="close" aria-label="Close">
  						<span aria-hidden="true">&times;</span>
					</button>
    			</div>
    			
    		</form>
    	</div>
    </div>
	<!-- Footer -->
  <footer class="py-4 bg-light" style="position:relative;width:100%;bottom:0;">
    		<div class="container">
      			<p class="m-0 text-center text-black">Copyright &copy; Messaging Web 2020</p>
   		 </div>
    <!-- /.container -->
  </footer>
	</div>

</body>
</html>