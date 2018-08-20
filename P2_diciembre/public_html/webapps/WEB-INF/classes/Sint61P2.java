//Shiny and new version
    

//                               .,,,,                                    
//                         /((  ((((/ (((((,.                             
//                      (((,  ((((  ((((( (((((*                          
//                    (((.  /(((  /(((/ /(((((/(((                        
//                  (((   /((,  .(((* *((((,,(((((/,                      
//                 ((   .((    (((   ((((. ((((( (((/                     
//                /   ,((    (((   ((((  ((((/ (((((*(                    
//                   (/    (((   /((/  /(((, /((((./((.                   
//                .(/    ((/   /((.  *(((  *(((( .((((/                   
//                *    /((   *((.  .(((   ((((  ((((/ (                   
//                   /(/   /((    ((/   (((,  ((((  (((                   
//                 /((   ,((.   ((*   (((,  /(((  ,((((                   
//               /(/   *((    ((*   (((   /(((   (((/ ,                   
//             /(/   .((    ((*   (((   .((/   /((,  (                    
//             ,   ,((    ((*   (((   ,((*   ,((   ((,                    
//                ((    ((,   /((    ((,    ((   *((.                     
//                    ((,   /((   ,((,    ((   ,(((                       
//                   ,,   /((    ((.    ((    (((                         
//                      /(/   .((.    ((    ((                            
//                           ((.                                          
//                         ,(                                             
                                                                        
                                                                        
                                                                        
//                            /                                           
//                     .      @.                                          
//             @@,@@#  .##%@. @.  @  #@#%  @   ###@(                      
//            .@   @   (@(&@  @.  @ ,@     @  .@#(@,                      
//             @..     @   @( @.  @ .@     @  @*  (@                      
//             .@**@% ...*.   .   .   .*.  .  ..*,                        
//            @*              @% %@      @@            ,@,                
// @,       @&  ,@@@@  &@@@@  @% %@  %@@@@@  @@@@.  @@@/@, #@@@%          
//     ,/*     ,@*      (&@@  @% %@ %@   @@  ,%@@@ @@  .@,&@%%%@&         
//              @@ .@%&@  &@  @% %@ ,@/  @@ @@  @@ @@  &@,*@* .%(    
    

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class Sint61P2 extends HttpServlet {
    
    //Constants
    private final static String PASS = "SanJunipero";
    private final static String INIT_DOC = "http://gssi.det.uvigo.es/users/agil/public_html/SINT/17-18/mml2001.xml";
    
    //SystemWide vars (static)
    private static ArrayList<SAXParseException> errors = new ArrayList<SAXParseException>();
    private static ArrayList<SAXParseException> warnings = new ArrayList<SAXParseException>();
    private static ArrayList<SAXParseException> fatalerrors = new ArrayList<SAXParseException>();
    private static Map<String, Document> validos = new TreeMap<String, Document>();
    // private static int init_counter = 0;
    //SystemWide var (non static)
    // private boolean inicial = true;
    private PrintWriter out = null;

    private String passIntroducida = null;
    private boolean modoAuto = false;

    // Strings de direccion relativa non absoluta
	private static String host = null;
    private static int port = 0;
    


    public void init(ServletConfig config) {
        //init_counter++;
		ServletContext ctx = config.getServletContext();
		try {
            parseXML(ctx.getResource("/P2M/mml.xsd"));
            //parseXML(ctx.getResource("/mml.xsd"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text");
		res.setCharacterEncoding("UTF-8");
		out = res.getWriter();
        req.setCharacterEncoding("UTF-8");
        
        port = req.getServerPort();

        passIntroducida = req.getParameter("p");
        if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("si"))){ //(req.getParameter("p") != null) && 
            modoAuto = true;
        } else{
            modoAuto = false;
        }
        
        if (req.getParameter("p") == null){
            if (modoAuto){
                autoNonP();
            } else {
                mostrarHeader();
                nonP();
            }
        }

        if (!passIntroducida.equals(PASS)){
            if (modoAuto){
                autoMalP();
            } else {
                mostrarHeader();
                nonCoincideP();
                out.println(PASS);
                out.println(passIntroducida);
            }
        }
        else{
            if ((req.getParameter("errores") != null)&& (req.getParameter("errores").equals("si"))){
                if(modoAuto){
                    autoMostrarErros();
                } else{
                    mostrarHeader();
                    mostrarErros();
                }
            }else if ((req.getParameter("fase") == null) || (req.getParameter("fase").equals("0"))){
                if(modoAuto){
                    autoMostrarFase0();
                } else{
                    mostrarHeader();
                    mostrarFase0();
                }
            } else if (req.getParameter("fase").equals("11")){
                if (modoAuto){
                    autoMostrarFase11();
                } else {
                    mostrarHeader();
                    mostrarFase11();
                }
            }else if ((req.getParameter("fase").equals("12")) && (req.getParameter("anio") != null)){
                if (modoAuto){
                    autoMostrarFase12(req.getParameter("anio"));
                } else {
                    mostrarHeader();
                    mostrarFase12(req.getParameter("anio"));
                }
            } else if ((req.getParameter("fase").equals("13")) && (req.getParameter("anio") != null) && (req.getParameter("pelicula") != null)){
                if (modoAuto){
                    autoMostrarFase13(req.getParameter("anio"), req.getParameter("pelicula"));
                } else {
                    mostrarHeader();
                    mostrarFase13(req.getParameter("anio"), req.getParameter("pelicula"));
                }
            }else if ((req.getParameter("fase").equals("14")) && (req.getParameter("anio") != null) && (req.getParameter("pelicula") != null) && (req.getParameter("act") != null)){
                if (modoAuto){
                    autoMostrarFase14(req.getParameter("anio"), req.getParameter("pelicula"), req.getParameter("act"));
                } else {
                    mostrarHeader();
                    mostrarFase14(req.getParameter("anio"), req.getParameter("pelicula"), req.getParameter("act"));
                }
            } else {
                if(modoAuto){
                    autoMostrarFase0();
                } else{
                    mostrarHeader();
                    mostrarFase0();
                }
            }

        }

        //out.println(validos.toString());
        //out.println(init_counter);
    }

    public static void parseXML(URL mmlUrl) throws Exception {

        LinkedList<URL> toParse = new LinkedList<URL>();
        LinkedList<URL> parsed = new LinkedList<URL>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        XPath xpath = XPathFactory.newInstance().newXPath();        
        SchemaFactory xsdFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema xsd = null;
        try {
            xsd = xsdFactory.newSchema(mmlUrl);
        } catch(Exception e) {
            e.printStackTrace();
        }
        //schema for validation of docs
        factory.setSchema(xsd);
        
        try {
            docBuilder = factory.newDocumentBuilder();
        } catch(Exception e) {
            e.printStackTrace();
        }
        docBuilder.setErrorHandler(new XML_ErrorHandler(warnings, errors, fatalerrors));
        URL initUrl = null;
		try {
			initUrl = new URL(INIT_DOC);
		} catch (MalformedURLException e) {
			//this can't happen unless you're a bug in the matrix
        }
        
        URLConnection docSource = null;
        toParse.add(initUrl);
        while(!toParse.isEmpty()) {
            Document doc = null;
            NodeList outrosMMLs = null;
            String anio = null;
            try {
                docSource = toParse.pop().openConnection();
                doc = docBuilder.parse(docSource.getInputStream(), docSource.getURL().toString());
                parsed.add(docSource.getURL());
                anio = (String) xpath.evaluate("/Movies/Anio/text()", doc, XPathConstants.STRING);
                outrosMMLs = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto/OtraPelicula/MML", doc, XPathConstants.NODESET);
            } catch (Exception e) {
                //esta excepción ocorre cando o documento é incorrecto ou
                //non existe a url que referencia dito documento
                //ou as marmotas rillaron os cables da internete
                parsed.add(docSource.getURL());
                continue;
            }
            validos.put(anio, doc);
            //exploramos todos os documentos dispoñibles a partires deste recén parseado
            for (int i = 0; i < outrosMMLs.getLength(); i++) {
                String mml = null;
                try {
                    mml = (String) xpath.evaluate("text()", outrosMMLs.item(i), XPathConstants.STRING);
                } catch(Exception e) {
                    continue;
                }

                URL novoMML = null;
                try {
                    novoMML = new URL(docSource.getURL(), mml);
                } catch(Exception e) {
                    e.printStackTrace();
                    continue;
                }

                if (!parsed.contains(novoMML) && !toParse.contains(novoMML)) {
                    toParse.add(novoMML);
                }
            }
        }
    }
    
    public static class XML_ErrorHandler extends DefaultHandler {
            
        private List<SAXParseException> warnings;
        private List<SAXParseException> errors;
        private List<SAXParseException> fatalerrors;

        public XML_ErrorHandler(List<SAXParseException> warnings, List<SAXParseException> errors,
                List<SAXParseException> fatalerrors) {
            super();
            this.warnings = warnings;
            this.errors = errors;
            this.fatalerrors = fatalerrors;
        }

        public void warning(SAXParseException warning) throws SAXException {
            warnings.add(warning);
            throw(warning);
        }

        public void error(SAXParseException error) throws SAXException {
            errors.add(error);
            throw(error);
        }

        public void fatalError(SAXParseException fatal) throws SAXException {
            fatalerrors.add(fatal);
            throw(fatal);
        }

        public List<SAXParseException> getWarnings() {
            return warnings;
        }

        public List<SAXParseException> getErrors() {
            return errors;
        }

        public List<SAXParseException> getfatalerrors() {
            return fatalerrors;
        }

    }
        
    public List<Anio> getC1Anios(){
        LinkedList<Anio> toRet = new LinkedList<Anio>();
        for (String key : validos.keySet()) {
            toRet.add(new Anio(key));
        }
        return toRet;
    }

    public List<Pelicula> getC1Peliculas(String Anio){
        LinkedList<Pelicula> toRet = new LinkedList<Pelicula>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList peliculas = null;
        try {
            peliculas = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula", validos.get(Anio), XPathConstants.NODESET);
        } catch(Exception e) {
            return toRet;
        }
        for (int i = 0; i < peliculas.getLength(); i++) {
            try {
                Pelicula pelicula = new Pelicula();
                pelicula.setTitulo((String) xpath.evaluate("Titulo/text()", peliculas.item(i), XPathConstants.STRING));
                pelicula.setDuracion((String) xpath.evaluate("Duracion/text()", peliculas.item(i), XPathConstants.STRING));
                String langs = ((String) xpath.evaluate("@langs", peliculas.item(i), XPathConstants.STRING));
                //out.println(langs);
                if (langs == null) {
                    //aviso = langs; // Borrar luego
                    pelicula.setBandasonora((String) xpath.evaluate("../@lang", peliculas.item(i), XPathConstants.STRING));
                } else {
                    //aviso = langs; // Borrar luego
                    pelicula.setBandasonora((String) xpath.evaluate("@langs", peliculas.item(i), XPathConstants.STRING));
                }
                toRet.add(pelicula);
            } catch(Exception e) {

            }
        }
        Collections.sort(toRet);
        return toRet;
    }

    public List<Actor> getC1Actores(String Anio, String Pelicula){
        LinkedList<Actor> toRet = new LinkedList<Actor>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList actores = null;
        try {
            actores = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula[Titulo='"+Pelicula+"']/Reparto", validos.get(Anio), XPathConstants.NODESET);
            for(int i = 0; i < actores.getLength(); i++){
                Actor actor = new Actor();
                actor.setNombre((String) xpath.evaluate("Nombre/text()", actores.item(i), XPathConstants.STRING));
                actor.setCiudadPais((String) xpath.evaluate("text()[normalize-space()]", actores.item(i), XPathConstants.STRING));
                toRet.add(actor);
            }
        } catch(Exception e){
            return toRet;
        }
        return toRet;
    }

    public List<Filmografia> getC1Filmografia(String Anio, String Pelicula, String Actor){
        LinkedList<Filmografia> toRet = new LinkedList<Filmografia>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        for (Document doc : validos.values()) {
            NodeList filmografias = null;
            try {
                filmografias = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto[Nombre='"+ Actor +"']", doc, XPathConstants.NODESET);
                for (int i = 0; i < filmografias.getLength(); i++){
                    Filmografia filmografia = new Filmografia();
                    
                    filmografia.setTitulo((String) xpath.evaluate("../Titulo/text()", filmografias.item(i), XPathConstants.STRING));
                    if((xpath.evaluate("Oscar/text()", filmografias.item(i), XPathConstants.STRING)) != null) {
                        filmografia.setOscar((String) xpath.evaluate("Oscar/text()", filmografias.item(i), XPathConstants.STRING));
                    } else{
                        filmografia.setOscar(null);
                    }
                    filmografia.setPersonaje((String) xpath.evaluate("Personaje/text()", filmografias.item(i), XPathConstants.STRING));
                    toRet.add(filmografia);
                }
            } catch (Exception e ){
                return toRet;
            }
        }
        Collections.sort(toRet);
        return toRet;
    }

    public void mostrarHeader(){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servicio Consulta Peliculas</title>");
        out.println("<link href='P2M/mml.css' rel='stylesheet' type='text/css' >");
        out.println("</head>");
    }

    public void nonP(){
        out.println("<body>");
        out.println(" NO SE ECUENTRA PARAMETRO P, escriba el parametro en la URL");
        out.println("</body>");
        out.println("</html>");
    }

    public void nonCoincideP(){
        out.println("<body>");
        out.println(" NO COINCIDE LA CONTRASEÑA");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase0(){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Bienvenido a este servicio</h2>");
        out.println(String.format("<a href='?errores=si&p=%s'>  Pulsa aqui para ver los ficheros erroneos</a><br>", passIntroducida));
        out.println("<h2>Fase 0  Selecciona una consulta</h2>");
        out.println("<form method='GET' >");
        out.println("<fieldset>");
        out.println("<input type='radio' id='consulta' checked><label>Consulta 1: Filmografía de un miembro del reparto</label><br><br>");
        out.println("</fieldset>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<input type='hidden' value='11' name='fase' id='fase'>");
        out.println("<input type='submit' value='Enviar' id='enviar'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarErros(){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");

        out.println("<h2> Se han encontrado " + warnings.size() + " ficheros con warnings. </h2>");
        for (int i = 0; i < warnings.size(); i++){
            out.println("<p>" + warnings.get(i) + "</p>");
        }
        out.println("<h2> Se han encontrado " + errors.size() + " ficheros con errores. </h2>");
        for (int i = 0; i < errors.size(); i++){
            out.println("<p>" + errors.get(i) + "</p>");
        }
        out.println("<h2> Se han encontrado " + fatalerrors.size() + " ficheros con errores fatales. </h2>");
        for (int i = 0; i < fatalerrors.size(); i++){
            out.println("<p>" + fatalerrors.get(i) + "</p>");
        }
        out.println("<br>");
        out.println("<form>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<input type='hidden' value='0' name='fase' id='fase'>");
        out.println("<input type='submit' value='Atras' id='atras'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase11(){ //Pasar como parametro a lista de anos válidos
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Selecciona un año:</h2>");
        out.println("<form>"); //Dúbidas acerca disto
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<br>");
        out.println("<fieldset>");
        List<Anio> listaAnios = getC1Anios();
        for (int indice = 0; indice < listaAnios.size(); indice++) {
            out.println("<input type=\"radio\" name=\"anio\" value=\""
                + listaAnios.get(indice).getAnio() + "\" checked>"+ (indice+1) + ".- " + listaAnios.get(indice).getAnio());
            out.println("<br>");
        }
        out.println("</fieldset>");
        out.println("<input type='hidden' value='12' name='fase' id='fase'>");
        out.println("<input type='submit' value='Enviar' id='enviar'>");
        out.println("<input id='atras' type='submit' value='Atrás' onClick='form.fase.value=0'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase12(String anio){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Anio=" + anio + "</h2>");
        out.println("<h2>Selecciona una película:</h2><br>");
        out.println("<form>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<fieldset>");
        List<Pelicula> listaPeliculas = getC1Peliculas(anio);
        for (int indice = 0; indice < listaPeliculas.size(); indice++) {
            out.println("<input type=\"radio\" name=\"pelicula\" value=\"" + listaPeliculas.get(indice).getTitulo() + "\" checked>" + (indice +1) + ".- "+ listaPeliculas.get(indice).getTitulo()  + " ("
            + listaPeliculas.get(indice).getDuracion() + ")");
            out.println("<br>");
        }
        out.println("</fieldset>");
        out.println("<input type='hidden' value='"+ anio +"' name='anio' id='anio'>");
        out.println("<input type='hidden' value='13' name='fase' id='fase'>");
        out.println("<input type='submit' value='Enviar' id='enviar'>");
        out.println("<input id='atras' type='submit' value='Atrás' onClick='form.fase.value=11'>");
        //irInicio();
        out.println("<input id='inicio' type='submit' value='Inicio' onClick='form.fase.value=0'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase13(String anio, String pelicula){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Anio=" + anio + ", Película= " + pelicula + "</h2>");
        out.println("<h2>Selecciona un actor:</h2><br>");
        out.println("<form>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<fieldset>");
        List<Actor> listaActores = getC1Actores(anio, pelicula);
        //out.println(listaActores.size());
        for (int indice = 0; indice < listaActores.size(); indice++) {
            out.println("<input type=\"radio\" name=\"act\" value=\""
                    + listaActores.get(indice).getNombre() + "\" checked>"+ (indice + 1) +".- " + listaActores.get(indice).getNombre() + " (" + listaActores.get(indice).getCiudadPais() + ")");
            out.println("<br>");
        }
        out.println("</fieldset>");
        out.println("<input type='hidden' value='"+ pelicula +"' name='pelicula' id='pelicula'>");
        out.println("<input type='hidden' value='14' name='fase' id='fase'>");
        out.println("<input type='hidden' value='"+ anio +"' name='anio' id='anio'>");
        out.println("<input type='submit' value='Enviar' id='enviar'>");
        out.println("<input id='atras' type='submit' value='Atrás' onClick='form.fase.value=12'>");
        //irInicio();
        out.println("<input id='inicio' type='submit' value='Inicio' onClick='form.fase.value=0'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase14(String anio, String pelicula, String actor){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Anio=" + anio + ", Película= " + pelicula + ", Act= " + actor + "</h2>");
        out.println("<h2>Selecciona una pelicula:</h2><br>");
        out.println("<form>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<fieldset>");
        List<Filmografia> listaFilmografia = getC1Filmografia(anio, pelicula, actor);
        out.println("<h3> El personaje es: "+ listaFilmografia.get(0).getPersonaje() +"</h3>");
        for (int indice = 0; indice < listaFilmografia.size(); indice++) {
            out.println("&bull; " + (indice + 1) + ". " + "<b>Titulo</b>=" + listaFilmografia.get(indice).getTitulo());
            if (listaFilmografia.get(indice).getOscar() != null){
                out.println("<b>Óscar</b>="+ listaFilmografia.get(indice).getOscar());
            }
            out.println("<br>");
        }
        out.println("</fieldset>");
        out.println("<input type='hidden' value='13' name='fase' id='fase'>");
        out.println("<input type='hidden' value='"+ anio +"' name='anio' id='anio'>");
        out.println("<input type='hidden' value='"+ pelicula +"' name='pelicula' id='pelicula'>");
        out.println("<input id='atras' type='submit' value='Atrás' onClick='form.fase.value=13'>");
        //irInicio();
        out.println("<input id='inicio' type='submit' value='Inicio' onClick='form.fase.value=0'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    /*
    //Botón para volver a Inicio
    public void irInicio(){
        out.println("<form>");
        out.println("<input type='hidden' value='0' name='fase' id='fase'>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<input type='submit' value='Inicio' id='inicio'>");
        out.println("</form>");         
    }
    */

    //Mostra as pantallas en modo auto
    public void autoNonP(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no passwd</wrongRequest>");
    }

    public void autoMalP(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>bad passwd</wrongRequest>");
    }

    public void autoMostrarErros(){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<errores>");
        out.println("<warnings>");
        for (int i = 0; i < warnings.size(); i++){
            out.println("<warning>" + warnings.get(i) + "</warning>");
        }
        out.println("</warnings>");
        out.println("<errors>");
        for (int i = 0; i < errors.size(); i++){
            out.println("<error>");
            out.println("<file>" + errors.get(i).getSystemId() + "</file>");
            out.println("<cause>"+ errors.get(i).getMessage() +"</cause>");
            out.println("</error>");
        }
        out.println("</errors>");
        out.println("<fatalerrors>");
        for (int i = 0; i < fatalerrors.size(); i++){
            out.println("<fatalerror>" + fatalerrors.get(i) + "</fatalerror>");
        }
        out.println("</fatalerrors>");
        out.println("</errores>");
    }

    public void autoMostrarFase0(){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<service>");
        out.println("<status>OK</status>");
        out.println("</service>");
    }

    public void autoMostrarFase11(){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<anios>");
        List<Anio> listaAnios = getC1Anios();
        for(int indice = 0; indice < listaAnios.size(); indice ++){
            out.println(String.format("<anio>%s</anio>", listaAnios.get(indice).getAnio()));
        }
        
        out.println("</anios>");
    }

    public void autoMostrarFase12(String anio){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<peliculas>");
        List<Pelicula> listaPeliculas = getC1Peliculas(anio);
        for (int indice = 0; indice < listaPeliculas.size(); indice++){
            out.println(String.format("<pelicula duracion='"+ listaPeliculas.get(indice).getDuracion() +"' langs='"+ listaPeliculas.get(indice).getBandasonora() +"'>"+ listaPeliculas.get(indice).getTitulo() +"</pelicula>"));
        }
        //out.println("<aviso>"+ aviso +"</aviso>"); //Borrar luego
        out.println("</peliculas>");
    }

    public void autoMostrarFase13(String anio, String pelicula){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<reparto>");
        List<Actor> listaActores = getC1Actores(anio, pelicula);
        for (int indice = 0; indice < listaActores.size(); indice++){
            out.println(String.format("<act ciudad='"+ listaActores.get(indice).getCiudadPais() +"'>"+ listaActores.get(indice).getNombre() +"</act>")); //Antes listaReparto
        }
        
        out.println("</reparto>");
    }

    public void autoMostrarFase14(String anio, String pelicula, String actor){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        List<Filmografia> listaFilmografia = getC1Filmografia(anio, pelicula, actor);
        out.println(String.format("<filmografia nombre='"+ actor +"' personaje='"+ listaFilmografia.get(0).getPersonaje() +"'>"));
        for(int indice = 0; indice < listaFilmografia.size(); indice++){
            out.println(String.format("<film oscar='"+ listaFilmografia.get(indice).getOscar() +"'>"+ listaFilmografia.get(indice).getTitulo() +"</film>"));
        }
        
        out.println("</filmografia>");
    }
    
    public class Anio {
		String anio;
		// LinkedList<String> peliculas;

		public Anio() {
		}

		public Anio(String anio) {
			this.anio = anio;
			// this.peliculas = peliculas;
		}

		public void setAnio(String anio) {
			this.anio = anio;
		}

		public String getAnio() {
			return anio;
		}

	}

	public class Pelicula implements Comparable<Pelicula> {
		String titulo;
		String duracion;
		String bandasonora;
		LinkedList<Actor> actor;

		public Pelicula() {
        }

		public Pelicula(String titulo, String duracion, String bandasonora) {
			this.titulo = titulo;
			this.duracion = duracion;
			this.bandasonora = bandasonora;
        }
        
        public boolean equals(Object otro) {
            if (otro == null) return false;
            if (otro instanceof Pelicula) {
                return  titulo.equals(((Pelicula) otro).titulo);
            }
            return false;
        }

        @Override
        public int compareTo(Pelicula otra) {
            if (duracion.equals(otra.duracion)) {
                return -titulo.compareTo(otra.titulo);
            } else {
                return new Integer(duracion).compareTo(new Integer(otra.duracion));
            }
        }

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setDuracion(String duracion) {
			this.duracion = duracion;
		}

		public String getDuracion() {
			return duracion;
		}

		public void setBandasonora(String bandasonora) {
			this.bandasonora = bandasonora;
		}

		public String getBandasonora() {
			return bandasonora;
		}

		public LinkedList<Actor> getActor() {
			return actor;
		}

	}

	public class Actor implements Comparable<Actor>{
		String nombre;
		String personaje;
		String oscar;
		String ciudad_pais;
		LinkedList filmografia;

		public Actor() {
		}

		public Actor(String nombre, String personaje, String oscar, String ciudad_pais,
				LinkedList<String> filmografia) {
			this.nombre = nombre;
			this.personaje = personaje;
			this.oscar = oscar;
			this.ciudad_pais = ciudad_pais;
			this.filmografia = filmografia;
		}

        public boolean equals(Object otro) {
            if (otro == null) return false;
            if (otro instanceof Actor) {
                return  nombre.equals(((Actor) otro).nombre);
            }
            return false;
        }

        @Override
        public int compareTo(Actor otro) {
            return nombre.compareTo(otro.nombre);
        }

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getNombre() {
			return nombre;
		}

		public void setPersonaje(String personaje) {
			this.personaje = personaje;
		}

		public String getPersonaje() {
			return personaje;
		}

		public void setOscar(String oscar) {
			this.oscar = oscar;
		}

		public String getOscar() {
			return oscar;
		}

		public void setCiudadPais(String ciudad_pais) {
			this.ciudad_pais = ciudad_pais;
		}

		public String getCiudadPais() {
			return ciudad_pais;
		}

		public void setFilmografia(LinkedList<String> filmografia) {
			this.filmografia = filmografia;
		}

		public LinkedList getFilmografia() {
			return filmografia;
		}
	}

	public class Filmografia implements Comparable<Filmografia>{

		String titulo;
        String oscar;
        String personaje;

		public Filmografia() {
		}

		public Filmografia(String titulo) {
			this.titulo = titulo;
		}

		public Filmografia(String titulo, String oscar, String personaje) {
			this.titulo = titulo;
            this.oscar = oscar;
            this.personaje = personaje;
		}

        public boolean equals(Object otro) {
            if (otro == null) return false;
            if (otro instanceof Filmografia) {
                return  oscar.equals(((Filmografia) otro).oscar);
            }
            return false;
        }

        @Override
        public int compareTo(Filmografia otro) {
            return oscar.compareTo(otro.oscar);
        }

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setOscar(String oscar) {
			this.oscar = oscar;
		}

		public String getOscar() {
			return oscar;
        }
        
        public String getPersonaje() {
            return personaje;
        }

        public void setPersonaje(String personaje) {
            this.personaje = personaje;
        }

	}
}