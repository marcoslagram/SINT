<%@ page import = "beans.*, javax.servlet.http.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset='utf-8'>
        <title>Servicio Consulta Peliculas</title>
        <link href='p3/mml.css' rel='stylesheet' type='text/css' >
    </head>
    <body>
        <h1>Servicio de consulta de películas</h1>
        <h2>Selecciona un año:</h2>
        <form>
            <input type='hidden' value="${fase11.pass}" name='p'>
            <br>
            <fieldset>
                <c:set var="i" value="${1}"/>
                <c:forEach var="anio" items="${fase11.anios}">
                    <input type="radio" id="${i}" name="anio" value="${anio.anio}" checked><label for="${i}">${i}.- ${anio.anio}</label><br>
                    <c:set var="i" value="${i+1}"/>
                </c:forEach>
            </fieldset>
            <button type="submit" name="fase" class="send" value="12">Enviar</button> <br>
            <input id='atras' type='submit' value='Atrás' onClick='form.fase.value=0'>
        </form>
        <h5> Marcos Lago Ramilo </h5>
    </body>
</html>