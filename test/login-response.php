<?php
// attempt at a local server that doesn't work due to this code not being able to accept POST requests

$jsonData = file_get_contents('php://input');
$data = json_decode($jsonData, true);

if ($_SERVER["REQUEST_METHOD"] == "POST")
{
    $user = $data['USER_NAME'];
    $pwd = $data['PASSWORD'];

    if ($user == "Brady" || $user == "Hannah" || $user == "Jamarcus" || $user == "Jon" || $user == "Sean") 
     {
        if ($pwd == "12345")
        {
            $response = 
            [
                'status' => 'success',
                'message' => 'Login successful!',
            ];
        }
    }
    else
    {
        $response = 
        [
            'status' => 'fail',
            'message' => 'Login failed!',
        ];
    }

    // Output JSON response
    header('Content-Type: application/json');
    echo json_encode($response);
}