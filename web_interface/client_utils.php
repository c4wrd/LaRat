<?php

include 'client.php';

$response = array();

if ( isset ($_GET['clientId']) ) {
  $clientId = $_GET['clientId'];
  if(isset($_GET['getLocation'])) {
    $response = Client::getClientLocationHistory($clientId, $_GET['getLocation']);
  }
  else if (isset($_GET['getDetails'])) {
    $response = Client::getClientDetails($clientId);
  }

  print json_encode($response);

} else if (isset($_GET['getClients'])) {
  $response = Client::getClients();

  if ($response["count"] > 1) {
    $response['status'] = 'ok';
  } else {
    $response['status'] = 'ERROR_NO_CLIENTS';
    $response['errorMessage'] = "There are no clients that have used the application";
  }
  echo $response;

} else {
  $response['status'] = 'failed';
  $response['message'] = 'Client ID must be supplied';
  print json_encode($response);
}

?>
