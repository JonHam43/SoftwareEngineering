<?php
// attempt at a local server that doesn't work due to this code not being able to accept POST requests

$jsonData = file_get_contents('php://input');
$data = json_decode($jsonData, true);

if ($_SERVER["REQUEST_METHOD"] == "POST")
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

    // Output JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
}