<?php
	session_start();
	require_once "dbFuncs.php";

	$Mem_LName = $_REQUEST['Mem_LName'];
	$Mem_FName = $_REQUEST['Mem_FName'];
	$AGF_Type =  $_REQUEST['AGF_Type'];
	$Mem_Amt = $_REQUEST['Mem_Amt'];
    $Amt_Paid = $_REQUEST['Amt_Paid'];
	$Amt_Owed =  $_REQUEST['Amt_Owed'];
	$Date_Paid =  $_REQUEST['Date_Paid'];
	$Expires =  $_REQUEST['Expires'];
         
	$conn = connectDB();

	$sql = "INSERT INTO `memberships` (`Mem_LName`, `Mem_FName`, `AGF_Type`, `Mem_Amt`, `Amt_Paid`,`Amt_Owed`,`Date_Paid`,`Expires`) VALUES ('$Mem_LName', '$Mem_FName','$AGF_Type','$Mem_Amt','$Amt_Paid','$Amt_Owed','$Date_Paid','$Expires')";
	
	if (mysqli_query($conn, $sql))
	{
  		header('Location: ./membership.php?msg=Member successfully entered!');
	} 
	else 
	{
  		header('Location: ./signupMember.php?message=Member not added, try again.');
	}

	mysqli_close($conn);
	//$conn->close();
?>