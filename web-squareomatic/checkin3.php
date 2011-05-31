<?php
require_once("function/common.php");

header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
header("Expires: Sat, 26 Jul 1997 05:00:00 GMT"); // Date in the past

$place = $_GET['placeid'];
$actk = $_GET['actk'];

$url = "http://localhost:8080/squeromatic/checkin/";

$fields_string = '{"token":"GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041","venueId":"4c9a08fe78fc236a6ed63197"}';
//$fields_string = array('token' => 'GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041', 'venueId' => '4c9a08fe78fc236a6ed63197');

$contentLength = strlen($fields_string);

// OK cool - then let's create a new cURL resource handle
$ch = curl_init();

// Set URL to download
curl_setopt($ch, CURLOPT_URL, $url);

curl_setopt($ch, CURLOPT_HTTPHEADERS, "Content-Type: application/json\r\nContent-Length: $contentLength");

curl_setopt($ch,CURLOPT_POST, 1);

//curl_setopt($ch,CURLOPT_POSTFIELDS, json_encode($fields_string));

// Include header in result? (0 = yes, 1 = no)
//curl_setopt($ch, CURLOPT_HEADER, 0);

// Should cURL return or print out the data? (true = return, false = print)
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
 
// Timeout in seconds
curl_setopt($ch, CURLOPT_TIMEOUT, 100); 

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

//excecute
print curl_exec($ch);

curl_close($ch);

logtofile("checkin [$actk] to [$place]");

//echo "success to checkin";
?>