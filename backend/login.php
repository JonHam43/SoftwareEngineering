<?php
// Read the JSON file containing user data
$users_data = file_get_contents('users.json');
$users = json_decode($users_data, true);

// Get user input from the login form
$username = $_POST['username'];
$password = $_POST['password'];

// Check if the user exists in the JSON data
foreach ($users['users'] as $user) {
    if ($user['username'] === $username && $user['password'] === $password) {
        echo "Login successful!";

        // Check if the user is in the employee table and grant admin privileges
        if ($user['isAdmin']) {
            echo " User has admin privileges.";
            // Perform actions for users with admin privileges
        } else {
            echo " User does not have admin privileges.";
            // Perform actions for regular users
        }

        // Exit the loop since the user has been found
        break;
    }
}

// If the loop finishes without finding the user, display an error message
echo "Login failed. Please check your username and password.";
?>