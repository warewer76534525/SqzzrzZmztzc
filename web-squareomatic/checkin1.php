<?php
require_once("function/common.php");

$place = $_POST['placeid'];
$actk = $_POST['actk'];

$url = "https://api.foursquare.com/v2/checkins/add";

$fields = array(
	'oauth_token'=>urlencode($actk),
	'broadcast'=>urlencode("public,twitter,facebook"),
	'venueId'=>urlencode($place)
);

foreach($fields as $key=>$value) { $fields_string .= $key.'='.$value.'&'; }
rtrim($fields_string,'&');

// OK cool - then let's create a new cURL resource handle
$ch = curl_init();

// Set URL to download
curl_setopt($ch, CURLOPT_URL, $url);

curl_setopt($ch,CURLOPT_POST,count($fields));

curl_setopt($ch,CURLOPT_POSTFIELDS,$fields_string);
 
// Include header in result? (0 = yes, 1 = no)
curl_setopt($ch, CURLOPT_HEADER, 0);
 
// Should cURL return or print out the data? (true = return, false = print)
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
// Timeout in seconds
curl_setopt($ch, CURLOPT_TIMEOUT, 100); 

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

//excecute
curl_exec($ch);

//close connection
curl_close($ch);

logtofile("checkin [$actk] to [$place]");

echo "success to checkin";
?>