<?php

//get value from mobile
$actk = $_GET['actk'];
$lang = $_GET['lang'];
$lat = $_GET['lat'];


//then let's create a new cURL resource handle
$ch = curl_init();

// Set URL to download
curl_setopt($ch, CURLOPT_URL, "localhost:8080/squeromatic/history?token=$actk&langlat=$lang,$lat");
 
// Include header in result? (0 = yes, 1 = no)
curl_setopt($ch, CURLOPT_HEADER, 0);
 
// Should cURL return or print out the data? (true = return, false = print)
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
// Timeout in seconds
curl_setopt($ch, CURLOPT_TIMEOUT, 100);

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    
// Download the given URL, and return output
echo curl_exec($ch), true);

// Close the cURL resource, and free system resources
curl_close($ch);
?>