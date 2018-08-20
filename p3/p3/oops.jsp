<%@ page import = "beans.*, javax.servlet.http.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title> Consulta: mostrar errores </title>
        <link rel="stylesheet" type="text/css" href="p3/mml.css">
    </head>
    <body>
        <header id="logo">Servicio de consulta de películas</header>
        <div class="error">
            <h1><font size="96">:-(</font></h1>
            <h2>Ha sucedido un problema al obtener los datos y/0 no se disponía la información solicitada.</h2>
        </div>
        <form>
            <input id='atras' type='submit' value='Inicio' onClick='form.fase.value=0'>
        </form>
    </body>
</html>