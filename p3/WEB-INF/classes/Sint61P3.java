import beans.*;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.QName;
//import javax.swing.text.html.parser.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.lang.Integer;


public class Sint61P3 extends HttpServlet{
    //Variables constantes
    private final static String INIT_DOC = "http://gssi.det.uvigo.es/users/agil/public_html/SINT/17-18/mml2001.xml";
    private final static String PASS = "StJunipero";

    //Variables privadas
    private static ArrayList<SAXParseException> errors = new ArrayList<SAXParseException>();
    private static ArrayList<SAXParseException> warnings = new ArrayList<SAXParseException>();
    private static ArrayList<SAXParseException> fatalerrors = new ArrayList<SAXParseException>();
    private static Map<String, Document> validos = new TreeMap<String, Document>();
    private static String numeroAnio;

    //Beans de las respuestas
    private MostrarErrors errores;
    private static Parser parser = new Parser();
    private Fase0 fase0;
    private Fase11 fase11;
    private Fase12 fase12;
    private Fase13 fase13;
    private Fase14 fase14;
    private Fase15 fase15;
    private NonP nonP;
    private NonCoincideP nonCoincideP;
    private GetDocs getDocs;

    private String passIntroducida = null;
    //private boolean auto = false;
    
    public void init(ServletConfig config) throws ServletException {
        //System.out.println("Se inició el parser");
        super.init(config);
        ServletContext ctx = config.getServletContext();
		try {
            parser.setInicioURL(INIT_DOC);
            parser.setErrors(errors);
            parser.setWarnings(warnings);
            parser.setFatalerrors(fatalerrors);
            parser.setValidos(validos);
            //parser.parseXML(ctx.getResource("p3/mml.xsd"));
            parser.parseXML(ctx.getResource("/mml.xsd"));
            /* validos = parser.getValidos();
            warnings = parser.getWarnings();
            errors = parser.getErrors();
            fatalerrors = parser.getFatalerrors(); */
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        /* boolean auto; */
        //Inicia o parser se non o esta
        res.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        // if (parser == null){
        //     parser = new Parser();
        //     parser.setErrors(errors);
        //     parser.setWarnings(warnings);
        //     parser.setFatalerrors(fatalerrors);
        //     parser.setValidos(validos);
        //     parser.setInicioURL(INIT_DOC);
        // }

        passIntroducida = req.getParameter("p");
        /** Análisis de la Query String para las fases o modos**/
/*         if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("si"))){ //(req.getParameter("p") != null) &&
            auto = true;
            res.setContentType("application/xml");
        } else{
            auto = false;
            res.setContentType("text/html");
        } */

        if (req.getParameter("p") == null){
            nonP(req, res);
            return;
        }

        if(!passIntroducida.equals(PASS)){
            nonCoincideP(req, res);
            return;
        } else{
            if ((req.getParameter("errores") != null)&& (req.getParameter("errores").equals("si"))){
                mostrarErrors(req, res);
                return; 
            }
        }

        //String stringFase = req.getParameter("fase");
        int fase = 0;
        if (req.getParameter("fase") != null && req.getParameter("fase").matches("\\d+"))
            fase = Integer.parseInt(req.getParameter("fase"));
        if(req.getParameter("errores") != null && req.getParameter("errores").equals("si")){
            mostrarErrors(req, res);
        } else if (req.getParameter("archivo") != null && req.getParameter("archivos").equals("true")){
            getDocs(req, res);
        } else {
            switch (fase){
                case 0:
                    fase0(req, res);
                    break;
                case 11:
                    fase11(req, res);
                    break;
                case 12:
                    fase12(req, res);
                    break;
                case 13:
                    fase13(req, res);
                    break;
                case 14:
                    fase14(req, res);
                    break;
                case 15:
                    fase15(req, res);
                    break;
                default:
                    fase0(req, res);
                    break;
            }
        } 
    }
    //METODOS QUE MUESTRAN LAS FASES CON JSP
    

	/**Metodo que muestra los archivos con errores que encontro el sistema*/
	public void mostrarErrors(HttpServletRequest req, HttpServletResponse res) {
		//prepara respuesta
		errores = new MostrarErrors();
		errores.setPass(passIntroducida);
		errores.setWarnings(warnings);
		errores.setErrors(errors);
		errores.setFatalerrors(fatalerrors);
		//inicializa parametros para respuesta
		req.setAttribute("errores", errores);
		res.setCharacterEncoding("UTF-8");
		ServletContext contexto = getServletContext();
		RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/mostrarerrors.jsp");
		try {
			dispatcher.forward(req,res);
		} catch (Exception e) {
            e.printStackTrace();
			//error creando respuesta
		}
    }

    public void getDocs(HttpServletRequest req, HttpServletResponse res){
/*         if (auto){
            fase0(req, res, auto);
        } */
        //out.println("Se incia")
        //inicia el bean
        getDocs = new GetDocs();
        getDocs.setValidos(validos);
        if (!getDocs.verifyRequest(req)){
            dataNoAvailable(req, res);
        }
        getDocs.setNumeroAnio(req.getParameter(numeroAnio)); //La fase 15 tiene que pasar el numero del anio del documento a convertir
        try {
            getDocs.procesaPeticion(res, req.getRequestURL().toString());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
			dataNoAvailable(req,res);
        }
    }

    public void nonP(HttpServletRequest req, HttpServletResponse res){
        /* nonP.setModo(auto); */
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/nonP.jsp");
        try {
        dispatcher.forward(req, res);
        } catch(Exception e) {
        }
    }

    public void nonCoincideP(HttpServletRequest req, HttpServletResponse res){
        //nonCoincideP.setModo(auto);
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/nonCoincideP.jsp");
        try {
            dispatcher.forward(req, res);
            } catch(Exception e) {
        }
    }
    
    public void fase0 (HttpServletRequest req, HttpServletResponse res) {
    //inico java bean
    Fase0 fase0 = new Fase0();
    fase0.setPass(passIntroducida);
    //inicializa request/response
    req.setAttribute("fase0", fase0);
    res.setCharacterEncoding("UTF-8");
    //parametros necesarios para definir el jsp correspondiente para resuesta
    try {
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/fase0.jsp");
        dispatcher.forward(req, res);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void fase11(HttpServletRequest req, HttpServletResponse res) {
        fase11 = new Fase11();
        //fase11.setModo(auto);
        fase11.setPass(passIntroducida);
        fase11.setValidos(validos);
        //Si hay problemas con la request va a la fase anterior
        /*
        if(!fase11.verfyRequest(req)){
            fase0(req, res, auto);
            return;
        }
        */
        /* -- Prepara la respuesta en JSP ---*/
        req.setAttribute("fase11", fase11);
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/fase11.jsp");
        try{
            dispatcher.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fase12(HttpServletRequest req, HttpServletResponse res){
        fase12 = new Fase12();
        //fase12.setModo(auto);
        fase12.setValidos(validos);
        fase12.setPass(passIntroducida);
        fase12.setAnio(req.getParameter("anio"));
        //Si algo va mal va al principio
        /*
        if(!fase12.verfyRequest(req)){
            fase0(req, res, auto);
            return;
        }
        */
        /* -- Prepara la respuesta en JSP ---*/
        req.setAttribute("fase12", fase12);
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/fase12.jsp");
        try{
            dispatcher.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }


    } 

    public void fase13(HttpServletRequest req, HttpServletResponse res){
        fase13 = new Fase13();
        //fase13.setModo(auto);
        fase13.setValidos(validos);
        fase13.setPass(passIntroducida);
        fase13.setAnio(req.getParameter("anio"));
        fase13.setPelicula(req.getParameter("pelicula"));
        //Si algo va mal va al principio
        /*
        if(!fase13.verfyRequest(req)){
            fase0(req, res, auto);
            return;
        }
        */
        /* -- Prepara la respuesta en JSP ---*/
        req.setAttribute("fase13", fase13);
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/fase13.jsp");
        try{
            dispatcher.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fase14(HttpServletRequest req, HttpServletResponse res){
        fase14 = new Fase14();
        //fase14.setModo(auto);
        fase14.setValidos(validos);
        fase14.setPass(passIntroducida);
        fase14.setAnio(req.getParameter("anio"));
        fase14.setPelicula(req.getParameter("pelicula"));
        fase14.setActor(req.getParameter("act"));
        //Si algo va mal va al principio
        /*
        if(!fase14.verfyRequest(req)){
            fase0(req, res, auto);
            return;
        }
        */
        /* -- Prepara la respuesta en JSP ---*/
        req.setAttribute("fase14", fase14);
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/fase14.jsp");
        try{
            dispatcher.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fase15(HttpServletRequest req, HttpServletResponse res){
        fase15 = new Fase15();
        //fase15.setModo(auto);
        fase15.setValidos(validos);
        fase15.setPass(passIntroducida);

        req.setAttribute("fase15", fase15);
        res.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/fase15.jsp");
        try{
            dispatcher.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    	/*metodo de la fase que indica que ha sucedido algun error con la informacion*/
	public void dataNoAvailable(HttpServletRequest req, HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		ServletContext contexto = getServletContext();
		RequestDispatcher dispatcher = contexto.getRequestDispatcher("/p3/oops.jsp");
		try {
			dispatcher.forward(req,res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}