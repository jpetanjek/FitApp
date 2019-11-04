<?php

require '../izvor/Database.class.php';
require '../entiteti/Namirnica.class.php';

$baza = new Database();
$baza->spojiDB();
if(isset($_GET["query"]) && $_GET["query"]=="getAll"){
    $sveNamirnice = array();
    $dohvatNamirnica = $baza->selectDB("SELECT * FROM namirnica");
    $brojNamirnica = mysqli_num_rows($dohvatNamirnica);
    while($redak = mysqli_fetch_array($dohvatNamirnica)){
        $novaNamirnica = new Namirnica($redak);
        array_push($sveNamirnice,$novaNamirnica->dohvatiJson());
    }
    $objekt = Namirnica::kreirajJsonObjekt("namirnica",$brojNamirnica,$sveNamirnice);
    var_dump($objekt);
    
}
if(isset($_GET["query"]) && $_GET["query"]=="getById" && isset($_GET["namirnica"])){
    $identifikatorNamirnica = $_GET["namirnica"];
    $sveNamirnice = array();
    $dohvatNamirnica = $baza->selectDB("SELECT * FROM namirnica WHERE id = $identifikatorNamirnica");
    $brojNamirnica = mysqli_num_rows($dohvatNamirnica);
    while($redak = mysqli_fetch_array($dohvatNamirnica)){
        $novaNamirnica = new Namirnica($redak);
        array_push($sveNamirnice,$novaNamirnica->dohvatiJson());
    }
    $objekt = Namirnica::kreirajJsonObjekt("namirnica",$brojNamirnica,$sveNamirnice);
    var_dump($objekt);
}
if(isset($_GET["query"]) && $_GET["query"] == "getSastojke" && isset($_GET["namirnica"])){
    $identifikatorNamirnice = $_GET["namirnica"];
    $sviSastojci = array();
    $dohvatSastojaka = $baza->selectDB("SELECT nam.id,nam.naziv,nam.broj_kalorija,nam.tezina,nam.isbn FROM namirnica n
    JOIN sastoji_se s
        ON n.id = s.namirnica
            JOIN namirnica nam
                ON nam.id = s.sastojak
                    WHERE n.id = $identifikatorNamirnice");
    $brojSastojaka = mysqli_num_rows($dohvatSastojaka);
    while($redak = mysqli_fetch_array($dohvatSastojaka)){
        $novaNamirnica = new Namirnica($redak);
        array_push($sviSastojci,$novaNamirnica);
    }
    $objekt = Namirnica::kreirajJsonObjekt("sastoji_se",$brojSastojaka,$sviSastojci);
    var_dump($objekt); 
}
$baza->zatvoriDB();
