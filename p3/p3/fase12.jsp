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
        <h2>Selecciona una película:</h2>
        <form>
            <input type='hidden' value="${fase12.pass}" name='p'>
            <br>
            <fieldset>
                <c:set var="i" value="${1}"/>
                <c:forEach var="pelicula" items="${fase12.peliculas}">
                    <input type="radio" id="${i}" name="pelicula" value="${pelicula.titulo}" checked><label for="${i}">${i}.- ${pelicula.titulo} (${pelicula.duracion})</label><br>
                    <c:set var="i" value="${i+1}"/>
                </c:forEach>                    
            </fieldset>
            <input type='hidden' value="${fase12.anio}" name='anio' id='anio'>
            <button type="submit" name="fase" class="send" value="13">Enviar</button> <br>
            <input id='atras' type='submit' value='Atrás' onClick='form.fase.value=11'>
            <input id='inicio' type='submit' value='Inicio' onClick='form.fase.value=0'>
        </form>
        <h5> Marcos Lago Ramilo </h5>
    </body>
</html>