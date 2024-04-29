<?php
  session_start();
  if(isset($_SESSION["name"]))
    $logged=true;
  else
    $logged=false;
?>

<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Log In</title>
  </head>

  <body>
    <?php
        require_once "dbFuncs.php";
        require_once "pageFuncs.php";

        $logo1 = "logo2.png";
        $title = "Login";
        pageHead1($logo1, $title, $logged);
      ?>
      <div class="container">
        <br>
      <?php
        if(isset($_GET['msg']))
        {
          echo "<h4 class=\"text-danger\">" .$_GET['msg']. "</h4>";
        }
        ?>

        <form action = "./adminLoginHandler.php" method = "post">
          <div class="mb-3">
            <label for="email" class="form-label">Username:</label>
            <input type="email" class="form-control" id="email" placehodler = "Enter email" name = "email">
          </div>

          <div class="mb-3">
            <label for="pwd" class="form-label">Password:</label>
            <input type="password" class="form-control" id="pwd" placehodler= "Password" name = "pwd">
          </div>

          <button type="submit" class="btn btn-primary">Submit</button>
        </form>

  </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  </body>
</html>