<?php
	session_start();
	require_once 'dbFuncs.php';
	$email = $_POST["email"];
	$pwd = $_POST["pwd"];

	$conn = connectDB();
	$sql = "SELECT * FROM `admin` WHERE `adminEmail`=\"$email\" AND `password`=SHA1(\"$pwd\");";

	$arr = selectQueryResults($conn, $sql);
	
	if(count($arr) == 1)
	{
		$_SESSION['name'] = $arr[0]["adminName"];
		$_SESSION['name'] = $arr[0]["adminID"];
		$_SESSION['name'] = $arr[0]["adminEmail"];

		header('Location: ./adminLogin.php?msg=Logged in Successfully!');
	}
	else
		header('Location: ./adminLogin.php?msg=Email or Password is incorrect');


	$conn -> close();
?>