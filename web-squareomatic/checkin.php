<?php

require_once("function/common.php");

header("Cache-Control: no-cache, must-revalidate"); // HTTP/1.1
header("Expires: Sat, 26 Jul 1997 05:00:00 GMT"); // Date in the past

$place = $_GET['placeid'];
$actk = $_GET['actk'];

logtofile("checkin [$actk] to [$place]");

$url = "http://localhost:8080/squeromatic/checkin/";
$data = array('token' => $actk, 'venueId' => $place);

JsonPost($url, $data);

echo "Success to Checkin.";

function JsonPost($url, $input) {

	// encode and prepare headers
      $json = json_encode($input);
      $contentLength = strlen($json);
      $headers = "Content-type: application/json\r\nContent-Length: {$contentLength}\r\n";
                			
      // populate the options,
      $options = array(
         'http' => array(
         'method' => 'POST',
            'header' => $headers,		
            'ignore_errors' => true,
            'content' => $json
         )
      );

      // create context
      $context = stream_context_create($options);
      $fp = @fopen($url, 'rb', false, $context);
      if (!$fp) {
         // failed here
         $resp = null;
      } else {
         // get the response and decode
         $resp = stream_get_contents($fp);
         $resp = json_decode($resp, true);

         // close the response
         fclose($fp);
      }
      // decode JSON response
      return $resp;

   }
?>