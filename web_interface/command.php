<?php

include "client.php";

if( isset( $_GET['command'] ) ) {
	$command = $_GET['command'];
	$clientId = isset($_GET['clientId']) ? $_GET['clientId'] : "all";
	$response = array();
	switch ($command) {
		case "addMessage": {
			Client::addMessage($clientId, $_GET['messageType'], $_GET['message']);
			echo json_encode(array('status'=>'ok'));
			break;
		}
		case "getLocationHistory": {
			$response = Client::getClientLocationHistory($clientId, $_GET['number']);
			echo json_encode($response);
			break;
		}
		case 'getMessages': {
			$response = Client::getMessages($clientId);
			if($response['count'] == 0) {
				$response['status'] == 'none';
			}
			echo json_encode($response);
			break;
		}
		case 'getClients': {
			$response = Client::getClients();
			
			if ($response["count"] > 1) {
				$response['status'] = 'ok';
			} else {
				$response['status'] = 'ERROR_NO_CLIENTS';
				$response['errorMessage'] = "There are no clients that have used the application";
			}
			
			echo json_encode($response);
			
			break;
		}
		case 'getDetails': {
			$response = Client::getClientDetails($clientId);
			echo json_encode($response);
			break;
		}
		case 'getUnreadMessages': {
			$response = Client::getUnreadMessages($clientId);
			if($response['count'] == 0) {
				$response['status'] = 'none';
			}
			echo json_encode($response);
			break;
		}
		case "sendCommand": {
			Parse::sendCommand($objectId, $_GET['function'], $_GET['args']);
			break;
		}
		case "updateUser": {
			Client::updateUser($clientId, array(
				'latitude' => $_GET['latitude'],
				'longitude' => $_GET['longitude']
				)
			);
			break;
		}
		case "userUpdate": {
			Client::userUpdate(
				$clientId,
				$_GET['carrier'],
				$_GET['phoneNumber'],
				array(
					'latitude' => $_GET['latitude'],
					'longitude' => $_GET['longitude']
				)
			);
			echo json_encode(array("status" => "ok"));
		}
	}
} else {
	echo json_encode(array("status" => "failed", "error" => "A command must be supplied!"));
}

?>