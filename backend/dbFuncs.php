<?php
	function connectDB()
	{
		$hn = "localhost";
		$un = "phpUser";
		$pwd = "DbUser!123?";
		$db = "membership";

		// $hn = "localhost";
		// $un = "u386797014_phpUser1";
		// $pwd = "DbUser!123?";
		// $db = "u386797014_membership";

		$connect = new mysqli($hn, $un, $pwd, $db);
		if($connect->connect_error) die("Fatal Error");
		return $connect;
	}

	function selectQueryResults($conn, $query)
	{
		$arr = array();

        $result = $conn->query($query);
        if(!$result) die("Fatal Error on query");

        $rows = $result->num_rows;

        for ($i=0; $i <$rows ; $i++) { 
          $record = $result->fetch_array(MYSQLI_ASSOC);
          array_push($arr, $record);
      }
      $result ->close();
      return $arr;
	}

	function insertQueryResults($conn, $query){
		$arr=array();
		$result=$conn->$query($query);
		if(!$result) die("Fatal Error on Query");

		//$result->close();
	}


?>