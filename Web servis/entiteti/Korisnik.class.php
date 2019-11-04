<?php

class Korisnik{

    public $id;
    public $ime;
    public $prezime;
    public $email;
    public $visina;
    public $datumRodenja;
    public $razinaAktivnosti;
    public $ciljMase;
    public $ciljTjednogMrsavljenja;
    public $spol;


    function __construct($json,$identifikator = false){
        if($identifikator){
            $this->id = $json["id"];    
        }
        $this->ime = $json["ime"];
        $this->prezime = $json["prezime"];
        $this->email = $json["email"];
        $this->visina = (double)$json["visina"];
        $vrijemeUSekundama = strtotime($json["datum_rodenja"]);
        $this->datumRodenja = date('d/M/Y', $vrijemeUSekundama);
        $this->razinaAktivnosti = (int)$json["razina_aktivnosti"];
        $this->ciljMase = (int)$json["cilj_mase"];
        $this->ciljTjednogMrsavljenja = (double)$json["cilj_tjednog_mrsavljenja"];
        $this->spol = $json["spol"];
    }
    function dohvatiJson(){
        if($this->id != ""){
            $jsonPodatak = array("id"=>$this->id,"ime" => $this->ime, "prezime" => $this->prezime,"email" => $this->email, "visina" => $this->visina,"datum_rodenja" => $this->datumRodenja,"razina_aktivnosti" => $this->razinaAktivnosti,"cilj_mase" => $this->ciljMase,"cilj_tjednog_mrsavljenja" => $this->ciljTjednogMrsavljenja,"spol" => $this->spol );    
        }else{
            $jsonPodatak = array("ime" => $this->ime, "prezime" => $this->prezime,"email" => $this->email, "visina" => $this->visina,"datum_rodenja" => $this->datumRodenja,"razina_aktivnosti" => $this->razinaAktivnosti,"cilj_mase" => $this->ciljMase,"cilj_tjednog_mrsavljenja" => $this->ciljTjednogMrsavljenja,"spol" => $this->spol );
        }
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