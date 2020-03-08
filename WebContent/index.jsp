<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
   <div id="login" style="min-height:85vh;">
        <h3 class="text-center text-black pt-5"> <br/>LOGIN<br/></h3>
        <div class="container">
            <div id="login-row" class="row justify-content-center align-items-center">
                <div id="login-column" class="col-md-6">
                    <div id="login-box" class="col-md-12">
                        <form id="login-form" class="form" action="controlador" method="post">        
                            <div class="form-group">
                                <label for="username" class="badge badge-dark">Usuario:</label><br>
                                <input type="text" id="username" name="username" class="form-control"/>             
                            </div>
                            <div class="form-group">
                                <label for="password" class="badge badge-dark">Password:</label><br>
                                <input type="password" id="password" name="password"  class="form-control"/> 
                            </div>
                            <div class="form-group text-center">
                               
                                <input type="submit" name="accion" value="Login" class="btn btn-dark btn-md" >
                            </div>
                            <div class="form-group text-center">
                               
                                <a href="/Fruteria-ADO/signUp" class="badge badge-pill badge-dark" value="ir a registro">Todav�a no tengo cuenta</a>
                            </div>
                        </form>
                        
                    </div>
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