<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" encoding="UTF-8" indent="yes"/>


<xsl:template match="Movies">
    <html>
    <head>
        <title>Información del documento <xsl:value-of select="Anio"/></title>
        <link rel="stylesheet" type="text/css" href="p3/mml.css"/>
    </head>
    <body>
        <h1> Servicio de consulta de películas</h1>
        <h2>Año<xsl:value-of select="Anio"></h2>
        <xsl:for-each select="Película">
        <xsl:sort select="Titulo"/>
            <li>Título: <xsl:value-of select="Titulo/text()"><li>
            <li>ip: <xsl:value-of select="@ip">
            <li>País - Lang: <xsl:value-of select="../Pais"> - <xsl:value-of select="../@lang"><xsl:value-of select="@langs"><li>
            <li>Géneros: <xsl:for-each select="Generos/Genero"><li>
            <li>Duración: <xsl:value-of select="../Duracion"><li>
            <xsl:for-each select="Reparto">
                <li>Nombre: <xsl:value-of select="Nombre"><li>
                <li>Personaje: <xsl:value-of select="Personaje"> <li>
                <li>Oscar: <xsl:value-of select="Oscar"><li>
                <li>Ciudad País: <xsl:value-od select="text()[normalize-space()]"><li>
                <li>Otra Película: <xsl:value-of select="OtraPelicula/@anio">
                <li>Título otra película: <xsl:value-of select="OtraPelicula/Titulo"><li>
                <li>MML: <xsl:value-of select="OtraPelicula/MMl"><li>
            </xsl:for-each>
        </xsl:for-each>
        <form>
            <button type="submit" name="fase" class="nav_back" value="15">Atrás</button>
        </form>
        <br>
        <h5> Marcos Lago Ramilo </h5>
    </body>
    </html>
</xsl:template>
</xsl:stylesheet>