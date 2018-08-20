<%@ page import = "beans.*, javax.servlet.http.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
    <head>
        <meta charset='utf-8'>
        <title>Servicio Consulta Peliculas</title>
        <link href='P3M/mml.css' rel='stylesheet' type='text/css' >
    </head>

    <body>
        <h1>Servicio de consulta de películas</h1>
        <h2>Bienvenido a este servicio</h2>
        <a href="P3M?errores=si&p=${fase0.pass}">Pulsa aqui para ver los ficheros erroneos</a> <br>
        <form method='GET' >
            <h3>Acciones disponibles:</h3>
            <fieldset>
                <input type='radio' id='consulta' name="fase" value="11" checked><label>Consulta 1: Filmografía de un miembro del reparto</label>
                <input type="radio" id="docs" name="fase" value="15"><label>Ver documentos fuente</label> <br> <br>
            </fieldset>
            <input type='hidden' value='${fase0.pass}' name='p' id='pass'> 
            <input type='submit' value='Enviar' id='enviar'>   
        </form>
        <h5> Marcos Lago Ramilo </h5>
    </body>
</html>