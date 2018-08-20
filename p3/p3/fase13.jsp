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
        <h2>Anio=  ${fase13.anio}, Película= ${fase13.pelicula}</h2>
        <h2>Selecciona un actor:</h2><br>
        <form>
            <input type='hidden' value="${fase13.pass}" name='p' id='p'>
            <fieldset>
                <c:set var="i" value="${1}"/>
                <c:forEach var="actor" items="${fase13.actores}">
                        <input type="radio" id="${i}" name="act" value="${actor.nombre}" checked><label for="${i}">${i}.- ${actor.nombre}</label><br>
                        <c:set var="i" value="${i+1}"/>
                </c:forEach>
            </fieldset>
            <input type='hidden' value="${fase13.pelicula}" name='pelicula' id='pelicula'>
            <input type='hidden' value='14' name='fase' id='fase'>
            <input type='hidden' value="${fase13.anio}" name='anio' id='anio'>
            <input type='submit' value='Enviar' id='enviar'>
            <input id='atras' type='submit' value='Atrás' onClick='form.fase.value=12'>
            <input id='inicio' type='submit' value='Inicio' onClick='form.fase.value=0'>
        </form>
        <h5> Marcos Lago Ramilo </h5>
    </body>
</html>