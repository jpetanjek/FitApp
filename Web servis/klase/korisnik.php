<?php

require '../izvor/Database.class.php';
require '../entiteti/Korisnik.class.php';

$baza = new Database();
$baza->spojiDB();
/*
    Funkcije za provjeru postojanja $_POST parametara
*/
function provjeriIme(){
    if(isset($_POST["ime"])){
        return true;
    }else{
        return false;
    }
}
function provjeriPrezime(){
    if(isset($_POST["prezime"])){
        return true;
    }else{
        return false;
    }
}
function provjeriEmail(){
    if(isset($_POST["email"])){
        return true;
    }else{
        return false;
    }
}
function provjeriRazinuAktivnosti(){
    if(isset($_POST["razina_aktivnosti"])){
        return true;
    }else{
        return false;
    }
}
function provjeriDatumRodenja(){
    if(isset($_POST["datum_rodenja"])){
        return true;
    }else{
        return false;
    }
}
function provjeriCiljMase(){
    if(isset($_POST["cilj_mase"])){
        return true;
    }else{
        return false;
    }
}
function provjeriCiljTjednogMrsavljenja(){
    if(isset($_POST["cilj_tjednog_mrsavljenja"])){
        return true;
    }else{
        return false;
    }
}
function provjeriSpol(){
    if(isset($_POST["spol"])){
        return true;
    }else{
        return false;
    }
}
function provjeriVisinu(){
    if(isset($_POST["visina"])){
        return true;
    }else{
        return false;
    }
}
function postojanostNuznihElemenataUnosa(){
    if(provjeriIme() && provjeriPrezime() && provjeriEmail() && provjeriVisinu() && provjeriRazinuAktivnosti()){
        return true;
    }else{
        return false;
    }
}
function kreirajRjecnik(){
    $korisnikovRjecnik;
    if(postojanostNuznihElemenataUnosa()){
        $korisnikovRjecnik["ime"] = $_POST["ime"];
        $korisnikovRjecnik["prezime"] = $_POST["prezime"];
        $korisnikovRjecnik["email"] = $_POST["email"];
        $korisnikovRjecnik["visina"] = $_POST["visina"];
        $korisnikovRjecnik["razina_aktivnosti"] = $_POST["razina_aktivnosti"];
    }
    if(provjeriCiljMase()){
        $korisnikovRjecnik["cilj_mase"] = $_POST["cilj_mase"];
    }else{
        $korisnikovRjecnik["cilj_mase"] = NULL;
    }
    if(provjeriCiljTjednogMrsavljenja()){
        $korisnikovRjecnik["cilj_tjednog_mrsavljenja"] = $_POST["cilj_tjednog_mrsavljenja"];
    }else{
        $korisnikovRjecnik["cilj_tjednog_mrsavljenja"] = NULL;
    }
    if(provjeriDatumRodenja()){
        $korisnikovRjecnik["datum_rodenja"] = $_POST["datum_rodenja"];
    }else{
        $korisnikovRjecnik["datum_rodenja"] = NULL;
    }
    if(provjeriSpol()){
        $korisnikovRjecnik["spol"] = $_POST["spol"];
    }else{
        $korisnikovRjecnik["spol"] = "N";
    }
    return $korisnikovRjecnik;
}
/*
    Funkcija koja ažurira atribut određenog korisnika
*/
if(isset($_GET["query"]) && $_GET["query"]=="update" && isset($_POST["identifikator"]) && isset($_POST["atribut"]) && isset($_POST["vrijednost"])){

    $atribut = $_POST["atribut"];
    $vrijednost = $_POST["vrijednost"];
    $identifikator = $_POST["identifikator"];
    $upit = "UPDATE korisnik SET $atribut='{$vrijednost}' WHERE id=$identifikator";
    $baza->updateDB($upit);
    var_dump($upit);
}
/*
    Funkcija koja briše određenog korisnika
*/
if(isset($_GET["query"]) && $_GET["query"]=="delete" && isset($_POST["user"])){
    $korisnikZaBrisanje = $_POST["user"];
    $upit = "DELETE FROM korisnik WHERE id=$korisnikZaBrisanje";
    $rezultatBrisanje = $baza->updateDB($upit);
}
/*
    Funkcija koja kreira novog korisnika.
*/
if(isset($_GET["query"]) && $_GET["query"] == "insert" && postojanostNuznihElemenataUnosa()){
    $korisnik = kreirajRjecnik();
    $majk = new Korisnik($korisnik);
    $upit = "INSERT INTO korisnik (ime,prezime,email,visina,razina_aktivnosti,cilj_mase,cilj_tjednog_mrsavljenja,spol,datum_rodenja) 
    VALUES ('$majk->ime','$majk->prezime','$majk->email',$majk->visina,$majk->razinaAktivnosti,$majk->ciljMase,$majk->ciljTjednogMrsavljenja,'$majk->spol','$majk->datumRodenja')";
    $rezultatObrade = $baza->updateDB($upit);
}

/*
    Funkcija koja dohvaća sve korisnike iz baze podataka.
*/
if(isset($_GET["query"]) && $_GET["query"]=="getAll"){
    $sviKorisnici = array();
    $dohvatBaze = $baza->selectDB("SELECT * FROM korisnik");
    $brojKorisnika = mysqli_num_rows($dohvatBaze);
    
    while($redak = mysqli_fetch_array($dohvatBaze)){
        $noviKorisnik = new Korisnik($redak,true);
        array_push($sviKorisnici,$noviKorisnik->dohvatiJson());
    }
    $objekt = Korisnik::kreirajJsonObjekt("korisnik",$brojKorisnika,$sviKorisnici);
    var_dump($objekt);
}
/*
    Funkcija koja dohvaća određenog korisnika.
*/
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
