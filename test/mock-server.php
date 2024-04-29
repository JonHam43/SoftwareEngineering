<?php
// attempt at a local server that doesn't work due to this code not being able to accept POST requests

echo "mock-server.php";
$jsonData = file_get_contents('php://input');
$data = json_decode($jsonData, true);

// Check if the form is submitted for login
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($data['login'])) 
{
    $user = $data['USER_NAME'];
    $pwd = $data['PASSWORD'];

    $response = 
    [
        'status' => 'success',
        'message' => 'Login successful!',
    ];
} 

// Check if the form is submitted for signup
elseif ($_SERVER["REQUEST_METHOD"] == "POST" && isset($data['signup'])) 
{
    $fname = $data['FIRST_NAME'];
    $mname = $data['MIDDLE_INIT'];
    $lname = $data['LAST_NAME'];
    $suf = $data['SUFFIX'];
    $dob = $data['DOB'];
    $phone = $data['PHONE_NUMBER'];
    $addr = $data['STREET'];
    $city = $data['CITY'];
    $state = $data['STATE'];
    $zip = $data['ZIP'];
    $email = $data['EMAIL'];
    $userID = $data['USER_NAME'];
    $pwd = $data['PASSWORD'];

    $response = 
    [
        'status' => 'success',
        'message' => 'Signup successful!',
    ];
}

// Output JSON response
header('Content-Type: application/json');
echo json_encode($response);