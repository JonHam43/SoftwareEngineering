<?php
	session_start();
	require_once "dbFuncs.php";

	$adminName = $_REQUEST['adminName'];
	$adminEmail = $_REQUEST['adminEmail'];
	$adminPhone =  $_REQUEST['adminPhone'];
	$pwd = $_REQUEST['pwd'];
         
	$conn = connectDB();

	$sql = "INSERT INTO `admin` (`adminName`, `adminEmail`, `adminPhone`, `password`) VALUES ('$adminName', '$adminEmail','$adminPhone','$pwd')";
	
	if (mysqli_query($conn, $sql))
	{
  		header('Location: ./adminLogin.php?msg=Admin successfully entered!');
	} 
	else 
	{
  		header('Location: ./signupAdmin.php?message=admin not added, try again.');
	}

	mysqli_close($conn);
	//$conn->close();
?>