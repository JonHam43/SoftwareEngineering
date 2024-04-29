<style>
  nav{
    opacity: 0.9;
  }
  </style>

<?php

	function pageHead1($img, $title, $logged)
	{
		echo "<nav class=\"navbar navbar-expand-md navbar sticky-top navbar-light bg-light\" style=\"color: gold;\">";
		echo "<img class=\"img-thumbnail\" src=\"./images/$img\" alt=\"$title\" width=\"100\" height=\"100\">";
		echo "<h3 style = \"padding: 10px;\">$title</h3>";

  if(!$logged) {
  echo <<<_END
    <div class="container-fluid">
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="index.php" style="font-weight: bold;">Home</a>
          </li>
          <li class="nav-item">
          <a class="nav-link" href="rates.php" style="font-weight: bold;">Rates</a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="font-weight: bold;">Account</a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="adminLogin.php" style="font-weight: bold;">Admin Log In</a></li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  _END;
  }

  else {
  echo <<<_END

    <div class="container-fluid">
      <div class="collapse navbar-collapse" id="navbarNavDropdown">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="index.php" style="font-weight: bold;">Home</a>
          </li>
          <li class="nav-item">
          <a class="nav-link" href="rates.php" style="font-weight: bold;">Rates</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="membership.php" style="font-weight: bold;">Memberships</a>
          </li>
          <li class="nav-item">
          <a class="nav-link" href="searchMember.php" style="font-weight: bold;">Search Members</a>
        </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="font-weight: bold;">Account</a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="adminLogout.php" style="font-weight: bold;">Log Out</a></li>
              <li><a class="dropdown-item" href="signupMember.php" style="font-weight: bold;">Add Members</a></li>
              <li><a class="dropdown-item" href="signupAdmin.php" style="font-weight: bold;">Add Admins</a></li>

            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  _END;
  }
}

?>