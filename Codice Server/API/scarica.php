<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
$DBhost = "localhost";
$DBName = "my_easybuy20";
$table=$_POST['tabella'];

$con= new mysqli($DBhost, 'easybuy20', '', $DBName);


$sqlquery = "SELECT * FROM $table";
$stmt=$con->prepare($sqlquery);
$stmt->bind_param();
if($stmt->execute()){
$result=$stmt->get_result();
$vett = [];
    while($row = mysqli_fetch_assoc($result)){
        $vett[] = $row;
    }
    echo json_encode($vett);


}



}
?>