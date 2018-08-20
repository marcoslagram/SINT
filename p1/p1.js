//Si se va a enviar por el método GET, se elimina la posibilidad de usar multipart
function metodoGET(){
	document.getElementById("urlencoded").checked=true;
	document.getElementById("multipart").checked=false;
	document.getElementById("multipart").disabled=true;
}

function log(nombre) {
   checktexto(nombre.value)
}
//Si se selecciona el método POST, no hay problemas en escoger cualquier tipo de codificación.
function metodoPOST(){

	document.getElementById("multipart").disabled=false;
}

// function checkall(source){
// 	checkboxes = document.getElementsByName('genero[]');
// 	for(var i=0, n=checkboxes.length;i<n;i++) {
//     checkboxes[i].checked = source.checked;
//   }
// }

function checkall(source) {
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i] != source)
            checkboxes[i].checked = source.checked;
    }
}

function checktexto(texto){
    if (texto.value.trim().length != 0 && texto.value.match(/[0-9]/)) {
        alert("En nombre no es válido"); //Tiene numeros
				return false;
    }
    return true;
}


function checktelf(telf){
    if(telf.value.trim().length == 0 || telf.value.match(/^[\d]{3}[-]*([\d]{2}[-]*){2}[\d]{2}$/)) {
        return true;
    }
    alert("El teléfono no es válido");
		return false;
}

function checkdni(dni){

				if (dni.value.trim().length == 0) {
					return true;
				}
        else if(dni.value.match(/^[0-9]{8}[A-Z]{1}/) && (dni.length = 9)){
            return true;
        }
        // else if (dni.length < 9){
        //     return true;
        // }
        else{
            alert("El DNI introducido no tiene el formato 12345678X");
						return false;
        }

    return true;
}

function checkmail(email){
    //tuEmail = document.formu1.email.value
		if (email.value.trim().length == 0) {
			return true;
		}
    tuEmail = email.value.toString();
    patron = /^[\w]+@{1}[\w]+\.+[a-z]{2,3}$/;
    respuesta = patron.test(tuEmail);
    if(!respuesta){
        alert("email no valido");
        return false;
    }
    return true;
    }


function checkpass(){
    //Comprobación de coincidencia de contraseñas
	if(document.getElementById("pass1").value != document.getElementById("pass2").value){
		alert("Las contraseñas no coinciden. Por favor, vuelva a escribirlas");
		document.getElementById("pass1").value="";
		document.getElementById("pass2").value="";
		return false;
	}
	return true;
}

//Funciones para validar fechas
function validarFormatoFecha(campo) {
    var RegExPattern = /^\d{1,2}\/\d{1,2}\/\d{2,4}$/;
    if ((campo.match(RegExPattern)) && (campo!='')) {
        return true;
    } else {
        return false;
    }
}

function existeFecha(fecha){
    var fechaf = fecha.split("/");
    var day = fechaf[0];
    var month = fechaf[1];
    var year = fechaf[2];
    var date = new Date(year,month,'0');
    if((day-0)>(date.getDate()-0)){
        return false;
    }
    return true;
}
    
//function existeFecha2 (fecha) {
//    var fechaf = fecha.split("/");
//    var d = fechaf[0];
//    var m = fechaf[1];
//    var y = fechaf[2];
//    return m > 0 && m < 13 && y > 0 && y < 32768 && d > 0 && d <= (new Date(y, m, 0)).getDate();
//}

//var fecha = "13/09/1985";
function validarFecha(fechaIntroducida){
		if (fechaIntroducida.value.trim().length == 0) {
			return true;
		}
    var fecha = fechaIntroducida.value.toString();
    if(validarFormatoFecha(fecha)){
        if(existeFecha(fecha)){
            return true;
        }else{
            alert("La fecha introducida no existe.");
						return false;
      }
    }else{
        alert("El formato de la fecha es incorrecto.");
				return false;
    }
}
//Funciones para obener datos del entorno para el formulario
function fechayhora(){
    var date = new Date();

    var horaSistema = "";

    if(date.getMinutes() < 10){
        horaSistema = "La hora del sistema es: " + date.getHours() + ":0" + date.getMinutes();
    } else {
        horaSistema = "La hora del sistema es: " + date.getHours() + ":" + date.getMinutes();
    }

    document.getElementById("horaEnvio").value=horaSistema;
}

function navegador(){
    var navegador = navigator.userAgent;
	document.getElementById("navegador").value=navegador;
}

// function cluf(){
// 	if(document.getElementById("eula").checked && !checkChanged){
// 		document.getElementById("eula").value="Si";
// 	} else {
// 		document.getElementById("eula").checked = 1 ;
// 		document.getElementById("eula").value = "No";
// 		checkChanged = true;
// 	}
// }
function metodoEnvio(){
    //Flags control
	//var checkedGET = false;
    //var checkChanged = false;

    //Comprobación del método de envío
    if (document.getElementById("sendget").checked){
		document.getElementById("formulario").method="GET";
		checkedGET = true;
	}

	if (document.getElementById("sendpost").checked){
		document.getElementById("formulario").method="POST";
	}


	//Comprobación de la codificación de envío
	if (document.getElementById("urlencoded").checked){
		document.getElementById("formulario").enctype="application/x-www-form-urlencoded";
	}

	if (document.getElementById("multipart").checked){
		document.getElementById("formulario").enctype="multipart/form-data";
	}

	//Comprobación del método PHP elegido
	if (document.getElementById("sintprof").checked){
		document.getElementById("formulario").action="/ip/p1.php";

	}

	if (document.getElementById("myphp").checked){
		document.getElementById("formulario").action="p1.php";

	}
}

function enviarFormulario(){
		let flag = true;
    //Comprobamos por ultima vez antes de enviar el formulario
    if(!document.getElementById("nombre").firstChild.value.trim().length == 0){
    flag = flag && checktexto(document.getElementById("nombre").firstChild);
    }

    if(!document.getElementById("apellidos").firstChild.value.trim().length == 0){
    flag = flagf && checktexto(document.getElementById("apellidos").firstChild.value);
    }


		flag = flag && checkdni(document.getElementById("dni").firstChild);
		// console.log("works");
		// console.log(document.getElementById("telf").firstChild);
    flag = flag && checktelf(document.getElementById("telf").firstChild);

    flag = flag && validarFecha(document.getElementById("fecha").firstChild);

    flag = flag && checkmail(document.getElementById("email").firstChild);

    flag = flag && checkpass(document.getElementById("pass1").firstChild);

    // cluf();

    fechayhora();

    navegador();

    metodoEnvio();


    return flag;
}

// console.log("Fin de script");
