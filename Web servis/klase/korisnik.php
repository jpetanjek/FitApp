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
function provjeriMasu(){
    if(isset($_POST["masa"])){
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
    if(provjeriIme() && provjeriPrezime() && provjeriEmail() && provjeriVisinu() && provjeriMasu()){
        return true;
    }else{
        return false;
    }
}
function provjeriGoogleId(){
    if(isset($_POST["google_id"])){
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
        $korisnikovRjecnik["masa"] = $_POST["masa"];
    }
    if(provjeriGoogleId()){
        $korisnikovRjecnik["google_id"] = $_POST["google_id"];
    }else{
        $korisnikovRjecnik["google_id"] = "";
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
    $noviKorisnikInsert = new Korisnik($korisnik);
    $upit = "INSERT INTO korisnik(google_id,ime,prezime,email,visina,masa,cilj_mase,cilj_tjednog_mrsavljenja,spol,datum_rodenja) VALUES(TRIM(BOTH '\"' FROM '$noviKorisnikInsert->google_id'), TRIM(BOTH '\"' FROM '$noviKorisnikInsert->ime'),TRIM(BOTH '\"' FROM '$noviKorisnikInsert->prezime'),TRIM(BOTH '\"' FROM '$noviKorisnikInsert->email'),$noviKorisnikInsert->visina,$noviKorisnikInsert->masa,$noviKorisnikInsert->ciljMase,$noviKorisnikInsert->ciljTjednogMrsavljenja,TRIM(BOTH '\"' FROM '$noviKorisnikInsert->spol'),TRIM(BOTH '\"' FROM '$noviKorisnikInsert->datumRodenja'))";
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
    header('Content-type: application/json');
    http_response_code(200); 
    echo json_encode($sviKorisnici);
}
/*
    Funkcija koja dohvaća određenog korisnika.
*/
if(isset($_GET["query"]) && $_GET["query"]=="getById" && isset($_GET["user"])){
    $korisnickiIdentifikator = $_GET["user"];
    $sviKorisnici = array();
    $dohvacenKorisnik;
    $dohvatBaze = $baza->selectDB("SELECT * FROM korisnik WHERE google_id = '$korisnickiIdentifikator'");
    $brojKorisnika = mysqli_num_rows($dohvatBaze);
    while($redak = mysqli_fetch_array($dohvatBaze)){
        $noviKorisnik = new Korisnik($redak,true);
        $dohvacenKorisnik = $noviKorisnik->dohvatiJson();
    }
    header('Content-type: application/json');
    http_response_code(200); 
    echo json_encode($dohvacenKorisnik);
}
$baza->zatvoriDB();
