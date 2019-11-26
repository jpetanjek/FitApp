<?php

require '../izvor/Database.class.php';
require '../entiteti/Namirnica.class.php';

$baza = new Database();
$baza->spojiDB();
/*
    Funkcija dohvaća sve namirnice iz baze podataka.
*/
if(isset($_GET["query"]) && $_GET["query"]=="getAll"){
    $sveNamirnice = array();
    $dohvatNamirnica = $baza->selectDB("SELECT * FROM namirnica");
    $brojNamirnica = mysqli_num_rows($dohvatNamirnica);
    while($redak = mysqli_fetch_array($dohvatNamirnica)){
        $novaNamirnica = new Namirnica($redak,true);
        array_push($sveNamirnice,$novaNamirnica->dohvatiJson());
    }
    header('Content-type: application/json');
    http_response_code(200); 
    echo json_encode($sveNamirnice);
    
}
/*
    Funkcija dohvaća određenu namirnicu iz baze podataka prema primarnom ključu.
*/
if(isset($_GET["query"]) && $_GET["query"]=="getById" && isset($_GET["namirnica"])){
    $identifikatorNamirnica = $_GET["namirnica"];
    $dohvacenaNamirnica;
    $dohvatNamirnica = $baza->selectDB("SELECT * FROM namirnica WHERE id = $identifikatorNamirnica");
    $brojNamirnica = mysqli_num_rows($dohvatNamirnica);
    while($redak = mysqli_fetch_array($dohvatNamirnica)){
        $novaNamirnica = new Namirnica($redak,true);
        $dohvacenaNamirnica = $novaNamirnica->dohvatiJson();
    }
    header('Content-type: application/json');
    http_response_code(200); 
    echo json_encode($dohvacenaNamirnica);
}
/*
    Funkcija ažurira određenu namirnicu.
*/
if(isset($_GET["query"]) && $_GET["query"]=="update" && isset($_POST["identifikator"]) && isset($_POST["atribut"]) && isset($_POST["vrijednost"])){

    $atribut = $_POST["atribut"];
    $vrijednost = $_POST["vrijednost"];
    $identifikator = $_POST["identifikator"];
    $upit = "UPDATE namirnica SET $atribut='{$vrijednost}' WHERE id=$identifikator";
    $baza->updateDB($upit);
}
/*
    Funkcija koja briše određenu namirnicu.
*/
if(isset($_GET["query"]) && $_GET["query"]=="delete" && isset($_POST["namirnica"])){
    $namirnicaZaBrisanje = $_POST["namirnica"];
    $upit = "DELETE FROM namirnica WHERE id=$namirnicaZaBrisanje";
    $rezultatBrisanje = $baza->updateDB($upit);
}
/*
    Funkcija koja kreira novu namirnicu.
*/
function provjeriPostojanostNaziva(){
    if(isset($_POST["naziv"])){
        return true;
    }else{
        return false;
    }
}
function provjeriPostojanostBrKalorija(){
    if(isset($_POST["broj_kalorija"])){
        return true;
    }else{
        return false;
    }
}
function provjeriPostojanostTezine(){
    if(isset($_POST["tezina"])){
        return true;
    }else{
        return false;
    }
}
function provjeriPostojanostIsbn(){
    if(isset($_POST["isbn"])){
        return true;
    }else{
        return false;
    }
}
function provjeriPostVarijable(){
    if(provjeriPostojanostNaziva() && provjeriPostojanostBrKalorija() && provjeriPostojanostTezine()){
        return true;
    }else{
        return false;
    }
}
function populateObject(){
    $namirnicaObject;
    if(provjeriPostVarijable()){
        $namirnicaObject["naziv"] = $_POST["naziv"];
        $namirnicaObject["broj_kalorija"] = $_POST["broj_kalorija"];
        $namirnicaObject["tezina"] = $_POST["tezina"];
        if(provjeriPostojanostIsbn()){
            $namirnicaObject["isbn"] = $_POST["isbn"];
        }else{
            $namirnicaObject["isbn"] = "";
        }
    }
    return $namirnicaObject;
}
if(isset($_GET["query"]) && $_GET["query"] == "insert" && provjeriPostVarijable()){
    $namirnica = populateObject();
    $novaNamirnicaInsert = new Namirnica($namirnica);
    $upit = "INSERT INTO namirnica(naziv,broj_kalorija,tezina,isbn) VALUES (TRIM(BOTH '\"' FROM '$novaNamirnicaInsert->naziv'),TRIM(BOTH '\"' FROM '$novaNamirnicaInsert->brojKalorija'),TRIM(BOTH '\"' FROM '$novaNamirnicaInsert->tezina'),TRIM(BOTH '\"' FROM '$novaNamirnicaInsert->isbn'))";
    $rezultatObrade = $baza->updateDB($upit);
}
$baza->zatvoriDB();
