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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Set;

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

//import com.sun.org.apache.xpath.internal.NodeSet;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.namespace.QName;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class sint61P2 extends HttpServlet {

    //Constants
    private final static String PASS = "StJunipero";
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

    static String message;

    private static ArrayList<String> listaFichWarn = new ArrayList<String>();
    private static ArrayList<String> listaFichError = new ArrayList<String>();
    private static ArrayList<String> listaFichFatal = new ArrayList<String>();

    public void init(ServletConfig config) {
        //init_counter++;
		ServletContext ctx = config.getServletContext();
		try {
            parseXML(ctx.getResource("mml.xsd"));
            //parseXML(ctx.getResource("/mml.xsd"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    //    res.setContentType("text");
		res.setCharacterEncoding("UTF-8");
		out = res.getWriter();
        req.setCharacterEncoding("UTF-8");

        port = req.getServerPort();

        passIntroducida = req.getParameter("p");
        if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("si"))){
            modoAuto = true;
	    res.setContentType("application/xml");
        } else{
            modoAuto = false;
	    res.setContentType("text/html");
        }

        if (req.getParameter("p") == null){
            if (modoAuto){
                autoNonP();
		return;
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
            if ((req.getParameter("pfase") == null) || (req.getParameter("pfase").equals("01"))){
                if(modoAuto){
                    autoMostrarFase01();
                } else{
                    mostrarHeader();
                    mostrarFase01();
                }
            } else if ((req.getParameter("pfase").equals("02"))){
            	if(modoAuto) {
            		autoMostrarErros();
            	} else {
            		mostrarHeader();
                    mostrarErros();
            	}
            } else if (req.getParameter("pfase").equals("21")){
                if (modoAuto){
                    autoMostrarFase21();
                } else {
                    mostrarHeader();
                    mostrarFase21();
                }
            }else if ((req.getParameter("pfase").equals("22"))){
                if (modoAuto){
                    if(req.getParameter("plang") == null){
                        autoNonPlang();
                    } else{
                        autoMostrarFase22(req.getParameter("plang"));
                    }
 
                } else {
                    if (req.getParameter("plang") == null){
                        mostrarHeader();
                        nonPlang();
                    } else{
                        mostrarHeader();
                        mostrarFase22(req.getParameter("plang"));
                    }
                }
            } else if ((req.getParameter("pfase").equals("23"))){
                if (modoAuto){
                    if(req.getParameter("plang") == null){
                        autoNonPlang();
                    } else if (req.getParameter("pact") == null){
                        autoNonPact();
                    } else {
                        autoMostrarFase23(req.getParameter("plang"), req.getParameter("pact"));
                    }                    
                } else {
                    if(req.getParameter("plang") == null){
                        mostrarHeader();
                        nonPlang();
                    } else if (req.getParameter("pact") == null){
                        mostrarHeader();
                        nonPact();
                    } else {
                        mostrarHeader();
                        mostrarFase23(req.getParameter("plang"), req.getParameter("pact"));
                    }
                    
                }
            }else if ((req.getParameter("pfase").equals("24"))){
                if (modoAuto){
                    if(req.getParameter("plang") == null){
                        autoNonPlang();
                    } else if (req.getParameter("pact") == null){
                        autoNonPact();
                    } else if (req.getParameter("ppais") == null){
                        autoNonPpais();
                    } else {
                        autoMostrarFase24(req.getParameter("plang"), req.getParameter("pact"), req.getParameter("ppais"));
                    }
                } else {
                    if(req.getParameter("plang") == null){
                        mostrarHeader();
                        nonPlang();
                    } else if (req.getParameter("pact") == null){
                        mostrarHeader();
                        nonPact();
                    } else if (req.getParameter("ppais") == null){
                        mostrarHeader();
                        nonPpais();
                    } else {
                        mostrarHeader();
                        mostrarFase24(req.getParameter("plang"), req.getParameter("pact"), req.getParameter("ppais"));
                    } 
                }
            } else {
                if(modoAuto){
                    autoMostrarFase01();
                } else{
                    mostrarHeader();
                    mostrarFase01();
                }
            }

        }

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
        //docBuilder.setErrorHandler(new XML_ErrorHandler(warnings, errors, fatalerrors));
        docBuilder.setErrorHandler(new XML_ErrorHandler(warnings, errors, fatalerrors){
            public void warning(SAXParseException spe){
                message = spe.getSystemId()+ "---"  + spe.toString();
                
                warnings.add(spe);
                for (int a = 0; a < warnings.size(); a++){
                    if (!listaFichWarn.contains(warnings.get(a).getSystemId())){
                        listaFichWarn.add(warnings.get(a).getSystemId());
                    }
                }
                
                //System.out.println();
            }
            public void fatalError(SAXParseException spe){
                message = spe.getSystemId()+ "---"  + spe.toString();
                fatalerrors.add(spe);
                
                for (int b = 0; b < fatalerrors.size(); b++){
                    if (!listaFichFatal.contains(fatalerrors.get(b).getSystemId())){
                        listaFichFatal.add(fatalerrors.get(b).getSystemId());
                    }
                    
                }
                
            }
            public void error(SAXParseException spe){
                message = spe.getSystemId()+ "---"  + spe.toString();
                errors.add(spe);
                
                for (int c = 0; c < errors.size(); c++){
                    if (!listaFichError.contains(errors.get(c).getSystemId())){
                        listaFichError.add(errors.get(c).getSystemId());
                    }
                }
                System.out.println("****Error: empty" + listaFichError.isEmpty());
                
            }
        });			
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
            //String idioma = null;
            try {
                docSource = toParse.pop().openConnection();
                doc = docBuilder.parse(docSource.getInputStream(), docSource.getURL().toString());
                parsed.add(docSource.getURL());
                anio = (String) xpath.evaluate("/Movies/Anio/text()", doc, XPathConstants.STRING);
                //idioma = (String) xpath.evaluate("/Movies/Pais/Pelicula/@langs", doc, XPathConstants.STRING);   //saca o atributo de langs
                /*if (idioma.equals(null)){                                                                       //se o atributo non aparece
                    idioma = (String) xpath.evaluate("/Movies/Pais/@lang", doc, XPathConstants.STRING);         //saca o atributo por defecto do Pais
                }*/
                outrosMMLs = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto/OtraPelicula/MML", doc, XPathConstants.NODESET);
            } catch (Exception e) {
                //esta excepción ocorre cando o documento é incorrecto ou
                //non existe a url que referencia dito documento
                //ou as marmotas rillaron os cables da internete
                parsed.add(docSource.getURL());
                continue;
            }
            
            //validos.put(idioma, doc);
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

            validos.put(anio, doc);
            //System.out.println("***Anio: " + anio);
            for(int z = 0; z < listaFichError.size(); z++){
                String linea = listaFichError.get(z);
                //System.out.println("+++++Lista fich Err: " + linea);
                String numero = linea.substring(62, 66);
                //System.out.println("+++++ Listra cortada: " + numero);
                if (validos.containsKey(numero)){
                    validos.remove(numero);
                }
            }
            //System.out.println("++++Nuevo validos: " + validos.size());

            
            
            //System.out.println("***** validos.size: " + validos.size());
            /*for (int z =0; z < validos.size(); z++){
                validos.remove(key, value)
            }*/
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

    
    public List<Lang> getC2Langs(){
    	LinkedList<Lang> toRet = new LinkedList<Lang>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList langs = null;
        List<String> langsArray = new ArrayList<String>(); //Array donde guardo los idiomas con duplicidades
        for(Document doc : validos.values()){
            try{
               langs =  (NodeList) xpath.evaluate("/Movies/Pais/Pelicula", doc, XPathConstants.NODESET);
               for (int i = 0; i < langs.getLength(); i++){
                   
                   String lang_juntos = (String) xpath.evaluate("@langs", langs.item(i), XPathConstants.STRING); //Devuelve el texto con los idiomas de 2 en 2
                    if (lang_juntos.equals(null) || lang_juntos.trim().isEmpty()){
                       lang_juntos = (String) xpath.evaluate("//@lang", doc, XPathConstants.STRING);
                    }
                    String[] lang_separados = lang_juntos.split(" ");
                    
                    //Meto los lang en un List<String> para poder eliminar los duplicados
                    for (int j=0; j<lang_separados.length; j++){
                        if (lang_separados.length > 1){
                            langsArray.add(lang_separados[j].toString()); 
                        }
                                  
                    }
               }  
               
            } catch(Exception e){

            }
        }
        
        //Hay que eliminar los elementos duplicados
        langsArray = langsArray.stream().distinct().collect(Collectors.toList());

        //Ya desduplicados, los meto en toRet
        for (int m = 0; m < langsArray.size(); m++){
            Lang lang = new Lang();
            lang.setLang(langsArray.get(m));
            toRet.add(lang);
        }
        //Ordenamos en orden alfabetico inverso
        Collections.sort(toRet);
    	return toRet;
    }

    
    public List<Act> getC2Acts(String plang){
    	LinkedList<Act> toRet = new LinkedList<Act>();
        LinkedList<Act> siOscar = new LinkedList<Act>();
        LinkedList<Act> noOscar = new LinkedList<Act>();
    	XPath xpath = XPathFactory.newInstance().newXPath();
    	Act thisAct = new Act();
        NodeList acts = null;
        NodeList actsLang = null;
        
        for(Document doc : validos.values()){
            try {
                acts = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula[contains(@langs, '" + plang + "')]/Reparto", doc, XPathConstants.NODESET);
                actsLang = (NodeList) xpath.evaluate("/Movies/Pais[@lang='" + plang + "']/Pelicula/Reparto", doc, XPathConstants.NODESET);
                for (int i = 0; i < acts.getLength(); i++) {
                    System.out.println("***** acts: " + acts.item(i).getTextContent());
                    try {
                        Act act = new Act();
                        boolean oscarizado = false;
                        NodeList oscars = null;
                        String nombreAct = ((String) xpath.evaluate("Nombre/text()", acts.item(i), XPathConstants.STRING));
                        act.setNombre(nombreAct);
                        act.setPersonaje((String) xpath.evaluate("Personaje/text()", acts.item(i), XPathConstants.STRING));
                        act.setCiudadPais(((String) xpath.evaluate("text()[normalize-space()]", acts.item(i), XPathConstants.STRING)).trim());
                        //El oscar no tiene que ver con el Lang, sino en todas las apariciones del Actor en todos los documentos
                        //Comprobar en todos los documentos todas las apariciones del actor y si tiene un oscar en cualquier pelicula poner True
                        //si no se encuentra ningun oscar en todos los documentos para ese Actor, poner false.
                        for(Document documents : validos.values()){
                            oscars = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto[Nombre='"+ nombreAct +"']/Oscar/text()", documents, XPathConstants.NODESET);
                            if (oscars.getLength() > 0){
                                oscarizado = true;
                            }
                        }
                        act.setTieneOscar(oscarizado);
                        toRet.add(act);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    
                }
                for (int i = 0; i < actsLang.getLength(); i++) {
                    //System.out.println("***** acts: " + acts.item(i).getTextContent());
                    try {
                        Act actLang = new Act();
                        boolean oscarizadoLang = false;
                        NodeList oscarsLang = null;
                        String nombreActLang = ((String) xpath.evaluate("Nombre/text()", actsLang.item(i), XPathConstants.STRING));
                        actLang.setNombre(nombreActLang);
                        actLang.setPersonaje((String) xpath.evaluate("Personaje/text()", actsLang.item(i), XPathConstants.STRING));
                        actLang.setCiudadPais(((String) xpath.evaluate("text()[normalize-space()]", actsLang.item(i), XPathConstants.STRING)).trim());
                        //El oscar no tiene que ver con el Lang, sino en todas las apariciones del Actor en todos los documentos
                        //Comprobar en todos los documentos todas las apariciones del actor y si tiene un oscar en cualquier pelicula poner True
                        //si no se encuentra ningun oscar en todos los documentos para ese Actor, poner false.
                        for(Document documents : validos.values()){
                            oscarsLang = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula/Reparto[Nombre='"+ nombreActLang +"']/Oscar/text()", documents, XPathConstants.NODESET);
                            if (oscarsLang.getLength() > 0){
                                oscarizadoLang = true;
                            }
                        }
                        actLang.setTieneOscar(oscarizadoLang);
                        toRet.add(actLang);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    
                }

            } catch (Exception e) {
                return toRet;
            }
        }
    	
    	//Ordeno alafabeticamente primero con Oscar y luego sin Oscar
        for (Act a : toRet){
            if (a.getTieneOscar()){
                siOscar.add(a);
            } else{
                noOscar.add(a);
            }
        }
        toRet.clear();

        
        Collections.sort(siOscar);
        for (int c = 0; c < (siOscar.size() - 1); c++) {
            if(siOscar.get(c).getNombre().equals(siOscar.get(c+1).getNombre())){
                siOscar.remove(c+1);
                c--;
            }
        }
        Collections.sort(noOscar);
        for (int c = 0; c < (noOscar.size() - 1); c++) {
            if(noOscar.get(c).getNombre().equals(noOscar.get(c+1).getNombre())){
                noOscar.remove(c+1);
                c--;
            }
        }
        
    	//toRet.add(thisAct);
    	for (Act a : siOscar) {
            toRet.add(a);
    	}
    	for (Act a : noOscar) {
    		toRet.add(a);
    	}
    	return toRet;
    }

 
    
    public List<Pais> getC2Paises(String plang, String pact){
    	LinkedList<Pais> toRet = new LinkedList<Pais>();
    	
    	XPath xpath = XPathFactory.newInstance().newXPath();
    	//Pais thisPais = new Pais();
        NodeList paises = null;
        NodeList paisessinlangs = null;
        for(Document doc : validos.values()){
            try {
                paises = (NodeList) xpath.evaluate("/Movies/Pais/Pelicula[contains(@langs, '" + plang + "') and @langs]/Reparto[Nombre='" + pact + "']/ancestor::Pais", doc, XPathConstants.NODESET);
                
                paisessinlangs = (NodeList) xpath.evaluate("/Movies/Pais[@lang='" + plang + "']/Pelicula[not(@langs)]/Reparto[Nombre='" + pact + "']/ancestor::Pais", doc, XPathConstants.NODESET);
                for (int i = 0; i < paises.getLength(); i++) {
                    //System.out.println("***** Paises.item: "+paises.item(i).getTextContent());
                    Pais pais = new Pais();
                    String nombrePais= null;
                    nombrePais = ((String) xpath.evaluate("@pais", paises.item(i), XPathConstants.STRING));
                    pais.setNombrePais(nombrePais);
                    pais.setIdiomaDefecto((String) xpath.evaluate("@lang", paises.item(i), XPathConstants.STRING));
                    //Hay que recorrer de nuevo todos los documentos
                    int numeroPeliculasEstePais = 0;
                    System.out.println("lonxitude de validos: " + validos.size());
                    for(Document documentos : validos.values()){
                        numeroPeliculasEstePais = numeroPeliculasEstePais + (((NodeList) xpath.evaluate("/Movies/Pais[@pais= '"+ nombrePais +"']/Pelicula", documentos, XPathConstants.NODESET)).getLength()); 
                        System.out.println((((NodeList) xpath.evaluate("/Movies/Pais[@pais= '"+ nombrePais +"']/Pelicula", documentos, XPathConstants.NODESET)).getLength()));
                        System.out.println("*** numeroPeliculasEstePais: " + numeroPeliculasEstePais);
                    }
                    //System.out.println("***HAGO el bucle");
                    System.out.println("Pais: " + pais.getNombrePais() + " as suas peliculas: " + numeroPeliculasEstePais);
                    pais.setNumeroPeliculas(numeroPeliculasEstePais);
                    /*if (pais.getNumeroPeliculas()>0){
                        
                    }*/
                    toRet.add(pais); 
                }

                //Cuento los paises sin langs
                for (int x = 0; x < paisessinlangs.getLength(); x++) {
                    //System.out.println("***** Paises.item: "+paises.item(i).getTextContent());
                    Pais pais = new Pais();
                    String nombrePais= null;
                    nombrePais = ((String) xpath.evaluate("@pais", paisessinlangs.item(x), XPathConstants.STRING));
                    pais.setNombrePais(nombrePais);
                    pais.setIdiomaDefecto((String) xpath.evaluate("@lang", paisessinlangs.item(x), XPathConstants.STRING));
                    //Hay que recorrer de nuevo todos los documentos
                    int numeroPeliculasEstePais = 0;
                    for(Document documentos : validos.values()){
                        numeroPeliculasEstePais = numeroPeliculasEstePais + (((NodeList) xpath.evaluate("/Movies/Pais[@pais= '"+ nombrePais +"']/Pelicula", documentos, XPathConstants.NODESET)).getLength()); 
                        //System.out.println("*** nodo: " );
                        
                    }
                    pais.setNumeroPeliculas(numeroPeliculasEstePais);
                    toRet.add(pais); 
                }

            } catch (Exception e) {
                return toRet;
            }
        }
        Collections.sort(toRet);
        for (int d = 0; d < (toRet.size() - 1); d++) {
            if(toRet.get(d).getNombrePais().equals(toRet.get(d+1).getNombrePais())){
                toRet.remove(d+1);
                d--;
            }
        }
    	return toRet;
    }


    
    public List<Pelicula> getC2Peliculas(String plang, String pact, String ppais){
    	LinkedList<Pelicula> toRet = new LinkedList<Pelicula>();
    	XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList peliculasPais = null;
        NodeList peliculasActorLangs = null;
        NodeList peliculas = null;

        NodeList peliculaLang = null;
        NodeList peliculaLangs = null;
        NodeList peliculasActorLang = null;

        String titulo = null;
        String ip = null;
        
        for(Document doc : validos.values()){
            try {
                peliculasPais = (NodeList) xpath.evaluate("/Movies/Pais[@pais='" + ppais + "']", doc, XPathConstants.NODESET); //Sacamos todas las películas que correspondan con el país
                
                peliculaLang = (NodeList) xpath.evaluate("/Movies/Pais[@pais='" + ppais + "' and @lang='" + plang +  "']", doc, XPathConstants.NODESET);
                //System.out.println("****PeliculaLang.name Node: " + peliculasPais.item(0).getNodeName());
                for (int a = 0; a < peliculasPais.getLength(); a++){
                    //peliculaLang = (NodeList) xpath.evaluate("/Movies/Pais[@lang='" + plang + "']/Pelicula", peliculasPais.item(a), XPathConstants.NODESET);
                    //System.out.println("***** plang: " + plang);
                    // @TODO
                    //NO FUNCIONA SI EL IDIOMA NO ESTA AL PRINCIPIO DE LANGS
                    peliculaLangs = (NodeList) xpath.evaluate("Pelicula[contains(@langs, '" + plang + "') and @langs]", peliculasPais.item(a), XPathConstants.NODESET);
                    
                    //System.out.println("**** peliculaLangs.length: " + peliculaLangs.getLength());
                    //Saco las peliculas con ese actor
                    for(int b = 0; b < peliculaLangs.getLength(); b++){
                        //System.out.println("***** pact: "+ pact);
                        //System.out.println("****** peliculaLangs"+peliculaLangs.item(b).getTextContent());
                        //System.out.println("***** peliculaLangs.nodeName: " + peliculaLangs.item(b).getNodeName()); //Pelicula
                        peliculasActorLangs = (NodeList) xpath.evaluate("Reparto[Nombre='" + pact + "']/ancestor::Pelicula", peliculaLangs.item(b), XPathConstants.NODESET);
                        //System.out.println("***** peliculaActorLangs." + peliculasActorLangs.);
                        //System.out.println("***** peliculaActorLangs.length: " + peliculasActorLangs.getLength());
                        for (int c = 0; c < peliculasActorLangs.getLength(); c++){
                            //System.out.println("****** peliculasActorLangS:" + peliculasActorLangs.item(c).getTextContent());
                            //System.out.println("****** peliculasActorLangS.nodeName:" + peliculasActorLangs.item(c).getNodeName());
                            Pelicula pelicula = new Pelicula();
                            titulo = (String) xpath.evaluate("Titulo/text()", peliculasActorLangs.item(c), XPathConstants.STRING);
                            //System.out.println("titulo: " + titulo);
                            pelicula.setTitulo(titulo);
                            ip = (String) xpath.evaluate("@ip", peliculasActorLangs.item(c), XPathConstants.STRING);
                            //System.out.println("ip: " + ip);
                            pelicula.setIp(ip);
                            if(!toRet.contains(pelicula)){
                                toRet.add(pelicula);
                            }
                            
                        }
                        /*for (int d = 0; d < peliculasActorLangs.getLength(); d++){
                            System.out.println("****** peliculasActorLangs: " + peliculasActorLangs.item(d).getTextContent());
                        }*/
                    }
                }


                for (int z = 0; z < peliculaLang.getLength(); z++){
                    peliculasActorLang = (NodeList) xpath.evaluate("Pelicula[not(@langs)]/Reparto[Nombre='" + pact + "']/ancestor::Pelicula", peliculaLang.item(z), XPathConstants.NODESET);
                    
                    for (int y = 0; y < peliculasActorLang.getLength(); y++){
                        //System.out.println("***** peliculasActorLang: " + peliculasActorLang.item(y).getTextContent());
                        Pelicula pelicula = new Pelicula();
                        titulo = (String) xpath.evaluate("Titulo/text()", peliculasActorLang.item(y), XPathConstants.STRING);
                        //System.out.println("titulo: " + titulo);
                        pelicula.setTitulo(titulo);
                        ip = (String) xpath.evaluate("@ip", peliculasActorLang.item(y), XPathConstants.STRING);
                        //System.out.println("ip: " + ip);
                        pelicula.setIp(ip);
                        if(!toRet.contains(pelicula)){
                            toRet.add(pelicula);
                        }
                    }
                    /*for (int x = 0; x < peliculaLang.getLength(); x++){
                        System.out.println("****** peliculasActorLang: " + peliculasActorLang.item(x).getTextContent());
                    }*/
                    
                }
            } catch (Exception e) {
                System.out.println("Ha ocurrido una excepcion en getC2Peliculas");
                e.printStackTrace();
            }

        }
    	/*try {
    		//peliculasPais = (NodeList) xpath.evaluate("/Movies/Pais[pais='" + ppais + "']/Pelicula", validos.get(plang), XPathConstants.NODESET); //Sacamos todas las películas que correspondan con el país
    		peliculas = (NodeList) xpath.evaluate("//Reparto[Nombre='" + pact + "']/ancestor::*", peliculasPais, XPathConstants.NODESET);
    		for (int i = 0; i < peliculas.getLength(); i++) {
    			Pelicula pelicula = new Pelicula();
    			pelicula.setTitulo((String) xpath.evaluate("/Titulo/text()", peliculas.item(i), XPathConstants.NODESET));
    			pelicula.setIp((String) xpath.evaluate("@ip/text()", peliculas.item(i), XPathConstants.NODESET));
    			toRet.add(pelicula);
    		}
    	} catch (Exception e) {
        }*/
        //System.out.println("toRet: " + toRet.size());
        Collections.sort(toRet);
        
        //System.out.println("****HACE EL RETURN");
        /*for (int d = 0; d < (toRet.size() - 1); d++) {
            if(toRet.get(d).getTitulo().equals(toRet.get(d+1).getTitulo())){
                toRet.remove(d+1);
                d--;
            }
        }*/
    	return toRet;
    }

    public void mostrarHeader(){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Servicio Consulta Peliculas</title>");
        out.println("<link href='./mml.css' rel='stylesheet' type='text/css' >");
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

    public void nonPlang(){
        out.println("<body>");
        out.println(" NO SE ECUENTRA PARAMETRO plang, escriba el parametro en la URL");
        out.println("</body>");
        out.println("</html>");
    }

    public void nonPact(){
        out.println("<body>");
        out.println(" NO SE ECUENTRA PARAMETRO pact, escriba el parametro en la URL");
        out.println("</body>");
        out.println("</html>");
    }

    public void nonPpais(){
        out.println("<body>");
        out.println(" NO SE ECUENTRA PARAMETRO ppais, escriba el parametro en la URL");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase01(){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Bienvenido a este servicio</h2>");
        out.println(String.format("<a href='?pfase=02&p=%s'>  Pulsa aqui para ver los ficheros erroneos</a><br>", passIntroducida));
        out.println("<h2>Fase 0  Selecciona una consulta</h2>");
        out.println("<form method='GET' >");
        out.println("<fieldset>");
        out.println("<input type='radio' id='consulta' checked><label>Películas de un actor/actriz, en un idioma, producidas en un país</label><br><br>");
        out.println("</fieldset>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<input type='hidden' value='21' name='pfase' id='pfase'>");
        out.println("<input type='submit' value='Enviar' id='enviar'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }
    
    public void mostrarErros(){
        out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2> Se han encontrado " + listaFichWarn.size() + " ficheros con warnings. </h2>");
        for(int j=0; j<listaFichWarn.size(); j++){
            for (int i = 0; i < warnings.size(); i++){
                if(listaFichWarn.get(j).equalsIgnoreCase(warnings.get(i).getSystemId()))
                    out.println("<p>&nbsp;&nbsp;&nbsp;&nbsp;" + warnings.get(i).toString() + "</p>");
            }
        }
        out.println("<h2> Se han encontrado " + listaFichError.size() + " ficheros con errores. </h2>");
        for (int j = 0; j < listaFichError.size(); j++){
            out.println(listaFichError.get(j));
            for (int i = 0; i < errors.size(); i++){
                if(listaFichError.get(j).equalsIgnoreCase(errors.get(i).getSystemId()))
                out.println("<p>&nbsp;&nbsp;&nbsp;&nbsp;" + errors.get(i).toString() + "</p>");
            }
        }
        out.println("<h2> Se han encontrado " + listaFichFatal.size() + " ficheros con errores fatales. </h2>");
        for(int j = 0; j < listaFichFatal.size(); j++){
            out.println(listaFichFatal.get(j));
            for (int i = 0; i < fatalerrors.size(); i++){
                if(listaFichFatal.get(j).equalsIgnoreCase(fatalerrors.get(i).getSystemId()))
                    out.println("<p>&nbsp;&nbsp;&nbsp;&nbsp;" + fatalerrors.get(i) + "</p>");
            }
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

    public void mostrarFase21() {
        out.println("<body>");
    	out.println("<h1>Servicio de consulta de películas</h1>");
    	out.println("<h2>Selecciona un idioma</h2>");
    	out.println("<form>");
    	out.println("<input type='hidden' value='" + passIntroducida + "' name='p' id='p'>");
    	out.println("<br>");
    	out.println("<fieldset>");
    	List<Lang> listaLangs = getC2Langs();
    	for (int indice = 0; indice < listaLangs.size(); indice++) {
    		out.println("<input type=\"radio\" name=\"plang\" value=\""
    				+ listaLangs.get(indice).getLang() + "\" checked>" + (indice+1) + ".- " + listaLangs.get(indice).getLang());
    		out.println("<br>");
    	}
    	out.println("</fieldset>");
    	out.println("<input type='hidden' value='22' name='pfase' id='pfase'>");
    	out.println("<input type='submit' value='Enviar' id='enviar'>");
    	out.println("<input id='atras' type='submit' value='Atras' onClick='form.pfase.value=01'>");
    	out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase22(String plang) {
    	out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Idioma=" + plang + "</h2>");
        out.println("<h2>Selecciona un Actor/Actriz:</h2><br>");
        out.println("<form>");
    	out.println("<input type='hidden' value='" + passIntroducida + "' name='p' id='p'>");
    	out.println("<br>");
    	out.println("<fieldset>");
        List<Act> listaActs = getC2Acts(plang);


    	for (int indice = 0; indice < listaActs.size(); indice++) {
    		out.println("<input type=\"radio\" name=\"pact\" value=\"" + listaActs.get(indice).getNombre() + "\" checked>" + (indice + 1)  + ".-" + listaActs.get(indice).getNombre() + 
    				" (" + listaActs.get(indice).getCiudadPais() + ") ");
    		if (listaActs.get(indice).getTieneOscar()) {
    			out.println("-- con óscar");
    		} else {
    			out.println("-- sin óscar");
    		}
    		out.println("<br>");
    	}
    	out.println("</fieldset>");
    	out.println("<input type='hidden' value='" + plang + "' name='plang' id='plang'>");
    	out.println("<input type='hidden' value='23' name='pfase' id='pfase'>");
    	out.println("<input type='submit' value'Enviar' id='enviar'>");
    	out.println("<input id='atras' type='submit' value='Atrás' onClick='form.pfase.value=21'>");
    	out.println("<input id='inicio' type='submit' value='Inicio' onClick='form.pfase.value=01'>");
    	out.println("</form>");
    	out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }

    public void mostrarFase23(String plang, String pact) {
    	out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Idioma=" + plang + ", Actor/Actriz=" + pact + "</h2>");
        out.println("<h2>Selecciona un país</h2>");
        out.println("<form>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<fieldset>");
        List<Pais> listaPaises = getC2Paises(plang, pact);
        for (int indice = 0; indice < listaPaises.size(); indice++) {
        	out.println("<input type=\"radio\" name=\"ppais\" value=\""
        			+ listaPaises.get(indice).getNombrePais() + "\" checked>" + (indice + 1) 
        			+ ".- " + listaPaises.get(indice).getNombrePais() + " (" 
        			+ listaPaises.get(indice).getNumeroPeliculas() + " pel&iacuteculas) -- idioma por defecto='"
        			+ listaPaises.get(indice).getIdiomaDefecto() + "'");
        	out.println("<br>");
        }
        out.println("</fieldset>");
    	out.println("<input type='hidden' value='" + plang + "' name='plang' id='plang'>");
    	out.println("<input type='hidden' value='" + pact + "' name='pact' id='pact'>");
    	out.println("<input type='hidden' value='24' name='pfase' id='pfase'>");
    	out.println("<input type='submit' value='Enviar' id='enviar'>");
    	out.println("<input id='atras' type='submit' value='Atrás' onClick='form.pfase.value=22'>");
    	out.println("<input id='inicio' type='submit' value='Inicio' onClick='form.pfase.value=01'>");
    	out.println("</form>");
    	out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }


    public void mostrarFase24(String plang, String pact, String ppais) {
    	out.println("<body>");
        out.println("<h1>Servicio de consulta de películas</h1>");
        out.println("<h2>Idioma=" + plang + ", Actor/Actriz=" + pact + ", Pais=" + ppais + "</h2>");
        out.println("<h2>Estas son sus películas:</h2>");
        out.println("<form>");
        out.println("<input type='hidden' value='"+ passIntroducida +"' name='p' id='p'>");
        out.println("<fieldset>");
        List<Pelicula> listaPeliculas = getC2Peliculas(plang, pact, ppais);
        for (int indice = 0; indice < listaPeliculas.size(); indice++) {
        	out.println("&bull; " + (indice + 1) + ".- <b>Película</b>=" + listaPeliculas.get(indice).getTitulo() 
        			+ ", <b>IP</b>=" + listaPeliculas.get(indice).getIp() + "");
        	out.println("<br>");
        }
        out.println("</fieldset>");
        out.println("<input type='hidden' value='23' name='pfase' id='pfase'>");
        out.println("<input type='hidden' value='" + plang + "' name='plang' id='plang'>");
        out.println("<input type='hidden' value='" + pact + "' name='pact' id='pact'>");
        out.println("<input id='atras' type='submit' value='Atrás' onClick='form.pfase.value=23'>");
        out.println("<input id='inicio' type='submit' value='Inicio' onClick='form.pfase.value=01'>");
        out.println("</form>");
        out.println("<h5> Marcos Lago Ramilo </h5>");
        out.println("</body>");
        out.println("</html>");
    }


    //Mostra as pantallas en modo auto
    public void autoNonP(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no passwd</wrongRequest>");
    }

    public void autoMalP(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>bad passwd</wrongRequest>");
    }

    public void autoNonPlang(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no param:plang</wrongRequest>");
    }

    public void autoNonPact(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no param:pact</wrongRequest>");
    }

    public void autoNonPpais(){
        out.println("<?xml version='1.0' encoding='utf-8'?>");
        out.println("<wrongRequest>no param:ppais</wrongRequest>");
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
        for (int j = 0; j < listaFichError.size(); j++){
            out.println("<error>");
            out.println("<file>" + listaFichError.get(j) + "</file>");
            for (int i = 0; i < errors.size(); i++){
                if(listaFichError.get(j).equalsIgnoreCase(errors.get(i).getSystemId()))
                    out.println("<cause>" + errors.get(i).toString() + "</cause>");
            }
            out.println("</error>");
        }
        out.println("</errors>");
        out.println("<fatalerrors>");
        for (int j = 0; j < listaFichFatal.size(); j++){
            out.println("<fatalerror>");
            out.println("<file>" + listaFichFatal.get(j)  + "</file>");
            for (int i = 0; i < fatalerrors.size(); i++){
                if(listaFichFatal.get(j).equalsIgnoreCase(fatalerrors.get(i).getSystemId()))
                out.println("<cause>" + fatalerrors.get(i).toString() + "</cause>");
            }
            out.println("</fatalerror>");
        }
        out.println("</fatalerrors>");
        out.println("</errores>");
    }

    public void autoMostrarFase01(){
        out.println("<?xml version='1.0' encoding='utf-8' ?>");
        out.println("<service>");
        out.println("<status>OK</status>");
        out.println("</service>");
    }

    public void autoMostrarFase21() {
    	out.println("<?xml version='1.0' encoding='utf-8' ?>");
    	out.println("<langs>");
    	List<Lang> listaLangs = getC2Langs();
    	for(int indice = 0; indice < listaLangs.size(); indice++) {
    		out.println(String.format("<lang>%s</lang>", listaLangs.get(indice).getLang()));
    	}
    	out.println("</langs>");
    }

    public void autoMostrarFase22(String plang) {
    	out.println("<?xml version='1.0' encoding='utf-8' ?>");
    	out.println("<acts>");
    	List<Act> listaActs = getC2Acts(plang);
    	for (int indice = 0; indice < listaActs.size(); indice++) {
    		out.println(String.format("<ac ciudad='%s' oscar='%s'>%s</ac>", listaActs.get(indice).getCiudadPais(), listaActs.get(indice).getTieneOscar(), listaActs.get(indice).getNombre()));
    	}
    	out.println("</acts>");
    }


    public void autoMostrarFase23(String plang, String pact) {
    	out.println("<?xml version='1.0' encoding='utf-8' ?>");
    	out.println("<paises>");
    	List<Pais> listaPaises = getC2Paises(plang, pact);
    	for (int indice = 0; indice < listaPaises.size(); indice++) {
    		out.println(String.format("<pais lang='%s' num=\"%s\">%s</pais>", listaPaises.get(indice).getIdiomaDefecto(), listaPaises.get(indice).getNumeroPeliculas(), listaPaises.get(indice).getNombrePais()));
    	}
    	out.println("</paises>");
    }

    
    public void autoMostrarFase24(String plang, String pact, String ppais) {
    	out.println("<?xml version='1.0' encoding='utf-8' ?>");
    	out.println("<titulos>");
    	List<Pelicula> listaPeliculas = getC2Peliculas(plang, pact, ppais);
    	for (int indice = 0; indice < listaPeliculas.size(); indice++) {
    		out.println(String.format("<titulo ip='" + listaPeliculas.get(indice).getIp() + "'>" + listaPeliculas.get(indice).getTitulo() + "</titulo>"));
    	}
    	out.println("</titulos>");
    }


    public class Lang implements Comparable<Lang>{
    	String lang;
    	
    	public Lang() {
    	}
    	
    	public Lang(String lang) {
    		this.lang = lang;
    	}
    	
    	public void setLang(String lang) {
    		this.lang = lang;
    	}
    	
    	public String getLang() {
    		return lang;
        }
        
        @Override
        public int compareTo(Lang otro) {
            return -lang.compareTo(otro.lang);
        }
    }
    
    public class Act implements Comparable<Act>{
		String nombre;
		String personaje;
		String oscar;
		boolean tieneOscar = false;
		String ciudad_pais;
		LinkedList filmografia;
		
		public Act() {
		}
		
		public Act(String nombre, String personaje, String oscar, String ciudad_pais, LinkedList<String> filmografia) {
			this.nombre = nombre;
			this.personaje = personaje;
			this.oscar = oscar;
			this.ciudad_pais = ciudad_pais;
			this.filmografia = filmografia;
		}
		
		public boolean equals(Object otro) {
            if (otro == null) return false;
            if (otro instanceof Act) {
                return  nombre.equals(((Act) otro).nombre);
            }
            return false;
        }
		
		@Override
        public int compareTo(Act otro) {
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
        
        public void setTieneOscar(boolean tieneOscar){
            this.tieneOscar = tieneOscar;
        }

        public boolean getTieneOscar(){
            return tieneOscar;
        }
	}
    
    public class Pais implements Comparable<Pais>{
    	String nombrePais;
    	String idiomaDefecto;
    	LinkedList<Pelicula> peliculas;
    	int numeroPeliculas;
    	
    	public Pais() {
    	}
    	
    	public Pais(String nombrePais, String idiomaDefecto, LinkedList<Pelicula> peliculas) {
    		this.nombrePais = nombrePais;
    		this.idiomaDefecto = idiomaDefecto;
    		this.peliculas = peliculas;
    	}
    	
    	public boolean equals(Object otro) {
    		if(otro == null) return false;
    		if (otro instanceof Pais) {
    			return nombrePais.equals(((Pais) otro).nombrePais);
    		}
    		return false;
    	}
    	
    	@Override
    	public int compareTo(Pais otro) {
    		if (numeroPeliculas == otro.numeroPeliculas) {
    			//Ordena por orden descendente de peliculas producidas
    			//Si son iguales por orden alfabetico
    			return nombrePais.compareTo(((Pais) otro).nombrePais);
    		} else {
    			return new Integer(numeroPeliculas).compareTo(new Integer(otro.numeroPeliculas))*(-1);
    		}
    	}

		public String getNombrePais() {
			return nombrePais;
		}

		public void setNombrePais(String nombrePais) {
			this.nombrePais = nombrePais;
		}

		public String getIdiomaDefecto() {
			return idiomaDefecto;
		}

		public void setIdiomaDefecto(String idiomaDefecto) {
			this.idiomaDefecto = idiomaDefecto;
		}

		public LinkedList<Pelicula> getPeliculas() {
			return peliculas;
		}

		public void setPeliculas(LinkedList<Pelicula> peliculas) {
			this.peliculas = peliculas;
		}

		public int getNumeroPeliculas() {
			return numeroPeliculas;
		}

		public void setNumeroPeliculas(int numeroPeliculas) {
			this.numeroPeliculas = numeroPeliculas;
		}
    }
    
    public class Pelicula implements Comparable<Pelicula>{
    	String titulo;
    	String ip;
    	
    	public Pelicula() {
    	}
    	
    	public Pelicula(String titulo, String ip) {
    		this.titulo = titulo;
    		this.ip = ip;
    	}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}
		
		public boolean equals(Object otro) {
			if (otro == null) return false;
			if (otro instanceof Pelicula) {
				return ip.equals(((Pelicula) otro).ip);
			}
			return false;
		}
		
		@Override
		public int compareTo(Pelicula otra) {
			//if (ip.equals(otra.ip)) {
				return ip.compareTo(otra.ip);
			//} else {
			//	return new Integer(ip).compareTo(new Integer(otra.ip));
			//}
		}
    }

}
