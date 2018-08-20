<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" encoding="UTF-8" indent="yes"/>

<xsl:template match="Movies">

<rss version="2.0">
<channel>
    <title>Servicio de consulta de películas</title>
    <link>http://localhost:7061/sint61/P3M</link>
    <description>Práctica 3 de sint61</description>

    <Anio><xsl:value-of select="Anio"/></Anio>

    <xsl:for-each select="Película">
    <xsl:sort select="Titulo"/>
    <item>
        <xsl:sort select="Titulo"/>
        <title>Año <xsl:value-of select="Anio"></title>
        <link>http://gssi.det.uvigo.es/users/agil/public_html/SINT/17-18/</link>
        <description><xsl:value-of select="Titulo/text()">, película de <xsl:value-of select="../Pais"> con una duración de: <xsl:for-each select="Generos/Genero"> minutos.
        El género(s) de la película es: <xsl:for-each select="Generos/Genero">;
        Teien como reparto a:
            <xsl:for-each select="Reparto">
                <xsl:value-of select="Nombre"> como <xsl:value-of select="Personaje">
                cuya ciudad-país es <xsl:value-od select="text()[normalize-space()]">.
                Ha ganado el siguiente Óscar: <xsl:value-of select="Oscar">.
                Ha protagonizado otra película el año <xsl:value-of select="OtraPelicula/@anio"> titulada <xsl:value-of select="OtraPelicula/Titulo">
                En el archivo: <xsl:value-of select="OtraPelicula/MMl">:
            </xsl:for-each>
        </description>
    </item>
</channel>
</rss>