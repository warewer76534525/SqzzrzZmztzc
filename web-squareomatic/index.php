<?php
require_once("function/common.php");
logtofile("someone access index.php");

if(!isset($_GET['code'])){
	redirect("home.php");
}

//get the client code
$code = $_GET['code'];

//then let's create a new cURL resource handle
$ch = curl_init();

// Set URL to download
curl_setopt($ch, CURLOPT_URL, "https://foursquare.com/oauth2/access_token?client_id=OFQLSHGQYEBW345A12XRP1XZXD4NUVIMEYTYHEM4AH0BGIKB&client_secret=0DXEVVFIWDMLJPYHTUH3Z114KVRNXHJO42HCUEMR2EDLMKHD&grant_type=authorization_code&redirect_uri=http://202.51.96.41/som/&code=$code");
 
// Include header in result? (0 = yes, 1 = no)
curl_setopt($ch, CURLOPT_HEADER, 0);
 
// Should cURL return or print out the data? (true = return, false = print)
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
// Timeout in seconds
curl_setopt($ch, CURLOPT_TIMEOUT, 100);

curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    
// Download the given URL, and return output
$acc_token = json_decode(curl_exec($ch), true);

// Close the cURL resource, and free system resources
curl_close($ch);

//welcome to our application
redirect("welcome.php?actk=".$acc_token['access_token']);
?>