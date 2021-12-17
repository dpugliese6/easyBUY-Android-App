<?php


	
	$DBhost = "localhost";
//$DBuser = "il tuo username";
//$DBpass = "la tua password";
$DBName = "my_easybuy20";
//$table = "UTENTE";
/* Connettiamoci al database */
mysql_connect($DBhost/*,$DBuser,$DBpass*/) or die("Impossibile collegarsi al server");
mysql_select_db($DBName) or die("Impossibile connettersi al database $DBName");

$sqlquery = $_POST["query"];
/*$result = */mysql_query($sqlquery);
echo $sqlquery;
//$number = mysql_num_rows($result);
?>