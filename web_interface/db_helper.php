<?php

require('db.php');

define("DATABASE_NAME", "clients");
define("DATABASE_USER", "root");
define("DATABASE_PASSWORD", "");

class DBHelper {

  public static $db;

  static function createDatabaseConnection() {
    self::$db = new db(DATABASE_NAME, DATABASE_USER, DATABASE_PASSWORD);
  }
}

?>
