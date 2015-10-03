<?php

include 'db_helper.php';

class Client {

  static function getClients() {
    $response = array();
    DBHelper::createDatabaseConnection();
    DBHelper::$db->select("clients");

    $contents = DBHelper::$db->result();

    $response["clientCount"] = DBHelper::$db->count();
    $response["clients"] = $contents;

    return $response;
  }

  static function getClientDetails($clientId) {
    $response = array();
    DBHelper::createDatabaseConnection();

    $contents = DBHelper::$db->select("clients", array("objectId" => $clientId));

    $contents = DBHelper::$db->result();

    $response["clientDetails"] = $contents;

    return $response;
  }

  static function getClientLocationHistory($clientId, $count) {
    $response = array();
    DBHelper::createDatabaseConnection();

    $contents = DBHelper::$db->select("location_history", array("objectId" => $clientId), $count, "time DESC");

    $contents = DBHelper::$db->result();

    $response["locations"] = $contents;

    return $response;
  }

}

?>
