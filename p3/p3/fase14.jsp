<%@ page import = "beans.*, javax.servlet.http.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset='utf-8'>
        <title>Servicio Consulta Peliculas</title>
        <link href='P3M/mml.css' rel='stylesheet' type='text/css' >
    </head>
    <body>
        <h1>Servicio de consulta de películas</h1>
        <h2>Anio= ${fase14.anio}, Película= ${fase14.pelicula}, Act= ${fase14.actor}</h2>
        <h2>Selecciona una pelicula:</h2><br>
        <form>
            <input type='hidden' value="${fase14.pass}" name='p' id='p'>
            <fieldset>
                    <h3> El personaje es: ${fase14.personaje}"</h3>
                    <c:set var="i" value="${1}"/>
                    <c:forEach var="filmografia" items="${fase14.filmografia}">
                        &bull; ${i}. <b>Titulo</b>=${filmografia.titulo} 
                        <c:if test="${filmografia.oscar == null || filmografia.oscar.trim().isEmpty()}">
                            <br>
                        </c:if>
                        <c:if test="${filmografia.oscar.isEmpty() == false}">
                            <b> Óscar</b>= ${filmografia.oscar}<br>
                        </c:if>
                        <c:set var="i" value="${i+1}"/>
                    </c:forEach>
            </fieldset>
            <input type='hidden' value='13' name='fase' id='fase'>
            <input type='hidden' value="${fase14.anio}" name='anio' id='anio'>
            <input type='hidden' value="${fase14.pelicula}" name='pelicula' id='pelicula'>
            <input id='atras' type='submit' value='Atrás' onClick='form.fase.value=13'>
            <input id='inicio' type='submit' value='Inicio' onClick='form.fase.value=0'>
        </form>
        <h5> Marcos Lago Ramilo </h5>
    </body>
</html>