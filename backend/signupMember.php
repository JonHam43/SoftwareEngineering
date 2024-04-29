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
    <title>Add Members</title>
  </head>

  <body>
  	<div class = "container">
	<?php
		require_once "dbFuncs.php";
    require_once "pageFuncs.php";

		$logo1 = "logo2.png";
    $title = "Add Members";

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
	<form action = "./memberHandler.php" method = "post">

		  <div class="mb-3">
            <label for="Mem_LName" class="form-label">Member Last Name:</label>
            <input class="form-control" id="Mem_LName" name = "Mem_LName">
          </div>

          <div class="mb-3">
            <label for="Mem_FName" class="form-label">Member First Name:</label>
            <input class="form-control" id="Mem_FName"  name = "Mem_FName">
          </div>

          <div class="mb-3">
            <label for="AGF_Type" class="form-label">Annual Green Fee Type:</label>
            <input class="form-control" id="AGF_Type" placehodler="AGF_Type" name = "AGF_Type">
          </div>

          <div class="mb-3">
            <label for="Mem_Amt" class="form-label">Membership Cost:</label>
            <input class="form-control" id="Mem_Amt" placehodler= "Mem_Amt" name = "Mem_Amt">
          </div>

          <div class="mb-3">
            <label for="Amt_Paid" class="form-label">Amount Paid:</label>
            <input class="form-control" id="Amt_Paid" placehodler= "Amt_Paif" name = "Amt_Paid">
          </div>

          <div class="mb-3">
            <label for="Amt_Owed" class="form-label">Amount Owed:</label>
            <input class="form-control" id="Amt_Owed" placehodler= "Amt_Owed" name = "Amt_Owed">
          </div>

          <div class="mb-3">
            <label for="Date_Paid" class="form-label">Date Paid:</label>
            <input type="date" class="form-control" id="Date_Paid" placehodler= "Date_Paid" name = "Date_Paid">
          </div>

          <div class="mb-3">
            <label for="Expires" class="form-label">Date Expired:</label>
            <input type="date" class="form-control" id="Expires" placehodler= "Expires" name = "Expires">
          </div>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>

  		</div>
	</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>