<?php
  session_start();
  if(isset($_SESSION["name"]))
    $logged=true;
  else
    $logged=false;
?>
<!DOCTYPE html>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Add Admin</title>
  </head>

  <body>
  	<div class = "container">
	<?php
		require_once "dbFuncs.php";
    require_once "pageFuncs.php";

		$logo1 = "logo2.png";
        $title = "Add Admin";

		pageHead1($logo1, $title, $logged);

    if(isset($_GET['msg']))
      {
        echo "<h4 class=\"text-info\">" .$_GET['msg']. "</h4>";
      }

      if(isset($_GET['message'])){
       echo "<h4 class=\"text-danger\">" .$_GET['message']. "</h4>"; 
      }
	?>

  <br>
	<form action = "./adminHandler.php" method = "post">

		  <div class="mb-3">
            <label for="adminName" class="form-label">Admin's Name:</label>
            <input class="form-control" id="adminName" name = "adminName">
          </div>

          <div class="mb-3">
            <label for="adminEmail" class="form-label">Admin's Email:</label>
            <input type = "email" class="form-control" id="adminEmail"  name = "adminEmail">
          </div>

          <div class="mb-3">
            <label for="adminPhone" class="form-label">Admin's Phone Number:</label>
            <input type ="text" class="form-control" id="adminPhone" placehodler="adminPhone" name = "adminPhone">
          </div>

          <div class="mb-3">
            <label for="pwd" class="form-label">Password</label>
            <input type ="password" class="form-control" id="pwd" placehodler= "pwd" name = "pwd">
          </div>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>

  		</div>
	</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>