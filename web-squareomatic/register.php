<?php
require_once("function/common.php");

$imei = $_GET['imei'];
$version = $_GET['version'];
$model = $_GET['model'];

logtofile("Registered User [$imei] - [$version] - [$model]");

$usr = "squareomatic";
$pwd = "triplelandssom";
$db = "som";
$host = "localhost";

$cid = mysql_connect($host,$usr,$pwd);
mysql_select_db($db, $cid);

$query = "insert into user (imei, 	model, version) values ('$imei', '$model', '$version')";

mysql_query($query);
?>