<?php

require '../izvor/Database.class.php';
require '../entiteti/Korisnik.class.php';

$baza = new Database();
$baza->spojiDB();
if(isset($_GET["query"]) && $_GET["query"]=="getAll"){
    $sviKorisnici = array();
    $dohvatBaze = $baza->selectDB("SELECT * FROM korisnik");
    $brojKorisnika = mysqli_num_rows($dohvatBaze);
    
    while($redak = mysqli_fetch_array($dohvatBaze)){
        $noviKorisnik = new Korisnik($redak);
        array_push($sviKorisnici,$noviKorisnik->dohvatiJson());
    }
    $objekt = Korisnik::kreirajJsonObjekt("korisnik",$brojKorisnika,$sviKorisnici);
    var_dump($objekt);
}
if(isset($_GET["query"]) && $_GET["query"]=="getById" && isset($_GET["user"])){
    $korisnickiIdentifikator = $_GET["user"];
    $sviKorisnici = array();
    $dohvatBaze = $baza->selectDB("SELECT * FROM korisnik WHERE id = $korisnickiIdentifikator");
    $brojKorisnika = mysqli_num_rows($dohvatBaze);
    while($redak = mysqli_fetch_array($dohvatBaze)){
        $noviKorisnik = new Korisnik($redak);
        array_push($sviKorisnici,$noviKorisnik->dohvatiJson());
    }
    $objekt = Korisnik::kreirajJsonObjekt("korisnik",$brojKorisnika,$sviKorisnici);
    var_dump($objekt);
}
$baza->zatvoriDB();
