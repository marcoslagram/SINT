<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<html lang="es-ES">
		<link href="p1.css" rel="stylesheet" type="text/css"/>
		<title>Variables PHP</title>
    </head>
    
    <body background="cine_carrete.jpg">

        <fontface="arial">
        <div id="wrapper">
		<div id="container">
            <h1>Datos formulario</h1>

            <table frame="border" border="4" rules="all" cellspacing="2" cellpadding="8" width="100%">

				<tr>
					<th>Variable</th>
					<th>Datos</th>
                </tr>
                
                <tr>
					<th>Nombre</th>
					<td><?php echo htmlspecialchars($_REQUEST['nombre']) ?></td>
				</tr>

				<tr>
					<th>Apellidos</th>
					<td> <?php echo htmlspecialchars($_REQUEST['apellidos']) ?></td>
				</tr>

				<tr>
					<th>DNI</th>
					<td><?php echo htmlspecialchars($_REQUEST['dni']) ?></td>
				</tr>

				<tr>
					<th>Tel&eacute;fono</th>
					<td> <?php echo htmlspecialchars($_REQUEST['telf']) ?> </td>
				</tr>

				<tr>
					<th>Fecha nacimiento</th>
					<td> <?php echo htmlspecialchars($_REQUEST['fecha']) ?> </td>
				</tr>

								<tr>
					<th>G&eacute;nero</th>
					<td><?php echo htmlspecialchars($_REQUEST['gender']) ?></td>
				</tr>

				<tr>
					<th>Correo Electrónico</th>
					<td><?php echo htmlspecialchars($_REQUEST['emailUsuario']) ?></td>
				</tr>

				<tr>
					<th>Contraseña</th>
					<td> <?php echo htmlspecialchars($_REQUEST['pass1']) ?> </td>
				</tr>

				<tr>
					<th>Archivo</th>
					<td> <?php echo htmlspecialchars($_REQUEST['archivo']) ?> </td>
				</tr>

				<tr>
					<th>Novedades</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbinovedades']) ?> </td>
				</tr>

				<tr>
					<th>Animaci&oacute;n</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbianimacion']) ?> </td>
				</tr>

				<tr>
					<th>Comedia</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbicomedia']) ?> </td>
				</tr>

				<tr>
					<th>Acci&oacute;n</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbiaccion']) ?> </td>
				</tr>

				<tr>
					<th>Terror</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbiterror']) ?> </td>
				</tr>

				<tr>
					<th>Drama</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbidrama']) ?> </td>
				</tr>

				<tr>
					<th>Cine de autor</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbicine_autor']) ?> </td>
				</tr>

				<tr>
					<th>Suspense</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbisuspense']) ?> </td>
				</tr>

				<tr>
					<th>Biograf&iacute;a</th>
					<td> <?php echo htmlspecialchars($_REQUEST['cbibiografia']) ?> </td>
				</tr>

				<tr>
					<th>Frecuencia con la que recibe bolet&iacute;n de informaci&oacute;n</th>
					<td> <?php echo htmlspecialchars($_REQUEST['frecuencia']) ?> </td>
				</tr>

				<tr>
					<th>Comentario</th>
					<td> <?php echo htmlspecialchars($_REQUEST['comentario']) ?> </td>
				</tr>

				<tr>
					<th>Acepta los t&eacute;rminos y condiciones</th>
					<td> <?php echo htmlspecialchars($_REQUEST['eula']) ?> </td>
				</tr>

			</table>

		</div>
		</div>

		<div id="wrapper">
		<div id="container">
			<h1>Variables de entorno</h1>

			<table frame="border" border="4" rules="all" cellspacing="2" cellpadding="8" width="100%">
				<tr>
					<th>IP del Servidor</th>
					<td><?php echo htmlspecialchars($_SERVER['SERVER_ADDR']) ?></td>
				</tr>

				<tr>
					<th>Nombre del Servidor</th>
					<td><?php echo htmlspecialchars($_SERVER['SERVER_NAME']) ?></td>
				</tr>

				<tr>
					<th>Puerto</th>
					<td><?php echo htmlspecialchars($_SERVER['SERVER_PORT']) ?></td>
				</tr>

				<tr>
					<th>IP del Cliente</th>
					<td><?php echo htmlspecialchars($_SERVER['REMOTE_ADDR']) ?></td>
				</tr>

				<tr>
					<th>Software del Servidor</th>
					<td><?php echo htmlspecialchars($_SERVER['SERVER_SOFTWARE']) ?></td>
				</tr>

				<tr>
					<th>Codificación</th>
					<td><?php echo htmlspecialchars($_REQUEST['codif']) ?></td>
				</tr>

				<tr>
					<th>Método de envío</th>
					<td><?php echo htmlspecialchars($_REQUEST['metodo']) ?></td>
				</tr>

				<tr>
					<th>Hora de envío</th>
					<td><?php echo htmlspecialchars($_REQUEST['horaEnvio']) ?></td>
				</tr>

				<tr>
					<th>Navegador del usuario</th>
					<td><?php echo htmlspecialchars($_REQUEST['navegador']) ?></td>
				</tr>
				</table>
		</div>
		</div>
		</font>

    </body>


</html>