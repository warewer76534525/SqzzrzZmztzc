<?php
require_once("function/common.php");

//get all parameter
$actk = $_GET['actk'];
$long = str_replace(".", ",", $_GET['long']);
$lat = str_replace(".", ",", $_GET['lat']);

logtofile("retrieve history from [$actk] ll [$lat] - [$long]");

//then let's create a new cURL resource handle
$ch = curl_init();

// Set URL to download
curl_setopt($ch, CURLOPT_URL, "http://localhost:8080/squeromatic/nearestHistory/$actk/$lat/$long");
 
// Include header in result? (0 = yes, 1 = no)
curl_setopt($ch, CURLOPT_HEADER, 0);
 
// Should cURL return or print out the data? (true = return, false = print)
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
// Timeout in seconds
curl_setopt($ch, CURLOPT_TIMEOUT, 100);

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    
// Download the given URL, and return output
echo curl_exec($ch);

// Close the cURL resource, and free system resources
curl_close($ch);
?>