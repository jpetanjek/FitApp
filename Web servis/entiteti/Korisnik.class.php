<?php

class Korisnik{

    private $id;
    private $ime;
    private $prezime;
    private $email;
    private $visina;
    private $datumRodenja;
    private $razinaAktivnosti;
    private $ciljMase;
    private $ciljTjednogMrsavljenja;
    private $spol;


    function __construct($json){
        $this->id = $json["id"];
        $this->ime = $json["ime"];
        $this->prezime = $json["prezime"];
        $this->email = $json["email"];
        $this->visina = $json["visina"];
        $this->datumRodenja = $json["datum_rodenja"];
        $this->razinaAktivnosti = $json["razina_aktivnosti"];
        $this->ciljMase = $json["cilj_mase"];
        $this->ciljTjednogMrsavljenja = $json["cilj_tjednog_mrsavljenja"];
        $this->spol = $json["spol"];
    }
    function dohvatiJson(){
        $jsonPodatak = array("ime" => $this->ime, "prezime" => $this->prezime,"email" => $this->email, "visina" => $this->visina,"datum_rodenja" => $this->datumRodenja,"razina_aktivnosti" => $this->razinaAktivnosti,"cilj_mase" => $this->ciljMase,"cilj_tjednog_mrsavljenja" => $this->ciljTjednogMrsavljenja,"spol" => $this->spol );
        return $jsonPodatak;
    }
    static function kreirajJsonObjekt($tablica = "korisnik", $brZapisa, $lista){
        $json = array();
        array_push($json,$tablica);
        array_push($json,$brZapisa);
        array_push($json,$lista);
        json_encode($json);
        return $json;
    }
}