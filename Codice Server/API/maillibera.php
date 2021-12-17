<?php
$DBhost = "localhost";
$DBName = "my_easybuy20";

mysql_connect($DBhost) or die("Impossibile collegarsi al server");
mysql_select_db($DBName) or die("Impossibile connettersi al database $DBName");
$mail=$_POST["mail"];
$sqlquery = "SELECT * FROM UTENTE WHERE Email='$mail'";
$result = mysql_query($sqlquery);
if(mysql_num_rows($result)<1){
echo "1";
}else{
echo "0";
}
?>