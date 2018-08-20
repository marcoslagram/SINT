<%@ page import = "p3beans.*, javax.servlet.http.*" %>
<%@ page import = "org.xml.sax.SAXParseException" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta charset="UTF-8">
        <title> Consulta: mostrar errores </title>
        <link rel="stylesheet" type="text/css" href="P3M/mml.css">
    </head>
    <body>
        <header id="logo">Servicio de consulta de películas</header>
        <h2>Errores:</h2>
            <form class="formulario" id="form" action="P3EA" method="GET" enctype="application/x-www-form-urlencoded">
                <h4>Se han encontrado ${errores.warnings.size()} ficheros con warnings:</h4>
${tab}${tab}${tab}${tab}
                <ul>
<c:forEach var="warn" items="${errores.warnings}">
${tab}${tab}${tab}${tab}<li>${warn.toString()}</li>
</c:forEach>
${tab}${tab}${tab}${tab}
                </ul>
                <h4>Se han encontrado ${errores.errors.size()} ficheros con errores:</h4>
                <ul>
<c:forEach var="error" items="${errores.errors}">
${tab}${tab}${tab}${tab}<li>${error.toString()}</li>
</c:forEach>
${tab}${tab}${tab}${tab}
                </ul>
                <h4>Se han encontrado ${errores.fatalerrors.size()} ficheros con errores fatales:</h4>
                <ul>
<c:forEach var="fatal" items="${errores.fatalerrors}">
${tab}${tab}${tab}${tab}<li>${fatal.toString()}</li>
</c:forEach>
${tab}${tab}${tab}${tab}
                </ul>
         </form>

         <form class="nav_bar" id="nav" action="P3M" method="GET" enctype="application/x-www-form-urlencoded">
            <input type='hidden' name='p' value='${errores.pass}'>
            <button type="submit" name="fase" class="nav_back" value="0">Atrás</button>
         </form>


    </body>
</html>