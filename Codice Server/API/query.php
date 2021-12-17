<?php
$DBhost = "localhost";
$DBName = "my_easybuy20";

mysql_connect($DBhost) or die("Impossibile collegarsi al server");
mysql_select_db($DBName) or die("Impossibile connettersi al database $DBName");

$sqlquery = $_POST["query"];
mysql_query($sqlquery);
?>