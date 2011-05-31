<?php
if ( ! function_exists('redirect'))
{
	function redirect($uri = '', $method = 'location', $http_response_code = 302)
	{
		/*if ( ! preg_match('#^https?://#i', $uri))
		{
			$uri = site_url($uri);
		}*/

		switch($method)
		{
			case 'refresh'	: header("Refresh:0;url=".$uri);
				break;
			default			: header("Location: ".$uri, TRUE, $http_response_code);
				break;
		}
		exit;
	}
}

if ( ! function_exists('logtofile'))
{
       function logtofile($string)
	{
		$fh = fopen("./log.html", 'a') or die("can't open file");
		fwrite($fh, "<br>[".date('l jS \of F Y h:i:s A')."]".$string);
		fclose($fh);
	}
}
?>