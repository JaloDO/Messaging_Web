<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
    <%@page import="Modelo.Mensaje" %>
    <%@page import="Modelo.Usuario" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!-- Bootstrap core CSS -->
  <link href="https://augustobrigadaw.000webhostapp.com/resources2/bootstrap.css" rel="stylesheet">
<!-- Custom styles for this template -->
  <link href="https://augustobrigadaw.000webhostapp.com/resources2/css/shop-homepage.css" rel="stylesheet">
  
</head>
<body style="background-color:#c9c8c5;">
	<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
    <div class="container">
      
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <a class="nav-link" href="/Fruteria-ADO/listadoVegetales2/0">
            	<img src="https://www.nicepng.com/png/full/266-2660273_expand-slideshow-white-back-icon-png.png" width="20" height="30"/>
            </a>
          </li>

          <li class="nav-item active">
            <a class="nav-link" href="#" disabled>Acceso
            	<span class="sr-only">(current)</span>
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  
  <div id="general_container" style="position:relative;">
   <div id="CHAT" style="min-height:85vh;">
        <h3 class="text-center text-black pt-5">CHAT</h3>
        <div class="container">
            <div id="chat-row" class="row justify-content-center align-items-center">
                    <div id="chat-box" class="col-md-12" 
                    style="position:relative;height:500px;overflow:auto;display:block;">
                        <form id="chat-form" class="form" action="chat" method="post">        
                           <table class="table table-hover table-striped table-light" caption="titulo de la tabla">
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
    							</tr>
  							 </tbody>
							</table>
                        </form>
                    </div>
            </div>
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