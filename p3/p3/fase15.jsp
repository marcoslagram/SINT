<%@ page import = "beans.*, javax.servlet.http.*" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
    <head>
        <meta charset="UTF-8">
        <title>Servicio de consulta de películas</title>
        <link href='p3/mml.css' rel='stylesheet' type='text/css' >
    </head>
    <body>
        <h3>Seleccione formato del documento</h3>
        <form>
            <input type='hidden' value="${fase15.pass}" name='p'>
            <input type="radio" id="html" name="formato" value="html" checked><label for="html">HTML</label><br>
            <input type="radio" id="rss" name="formato" value="rss"><label for="rss">RSS</label><br><br>

            <h3> Seleccione el archivo a convertir</h3>
            <c:set var="i" value="${1}"/>
            <c:forEach var="anio" items="${fase15.anios}">
                    <input type="radio" id="${i}" name="anio" value="${anio.anio}" checked><label for="${i}">${i}.- ${anio.anio}</label><br>
                    <c:set var="i" value="${i+1}"/>
                </c:forEach>
            <button type="submit" name="archivo" class="send" value="true">Enviar</button> <br>
            <input id='atras' type='submit' value='Atrás' onClick='form.fase.value=0'>    
        </form>
    </body>
</html>
