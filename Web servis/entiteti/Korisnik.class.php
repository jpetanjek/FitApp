<?php

class Korisnik{

    public $id;
    public $google_id;
    public $ime;
    public $prezime;
    public $email;
    public $visina;
    public $datumRodenja;
    public $masa;
    public $ciljMase;
    public $ciljTjednogMrsavljenja;
    public $spol;


    function __construct($json,$identifikator = false){
        if($identifikator){
            $this->id = (int)$json["id"];    
        }
        $this->ime = $json["ime"];
        $this->google_id = $json["google_id"];
        $this->prezime = $json["prezime"];
        $this->email = $json["email"];
        $this->visina = (double)$json["visina"];
        $this->datumRodenja = $json["datum_rodenja"];
        $this->masa = (double)$json["masa"];
        $this->ciljMase = (int)$json["cilj_mase"];
        $this->ciljTjednogMrsavljenja = (double)$json["cilj_tjednog_mrsavljenja"];
        $this->spol = $json["spol"];
    }
    function dohvatiJson(){
        if($this->id != ""){
            $jsonPodatak = array("id"=>$this->id,"google_id"=>$this->google_id,"ime" => $this->ime, "prezime" => $this->prezime,"email" => $this->email, "visina" => $this->visina,"datum_rodenja" => $this->datumRodenja,"masa" => $this->masa,"cilj_mase" => $this->ciljMase,"cilj_tjednog_mrsavljenja" => $this->ciljTjednogMrsavljenja,"spol" => $this->spol );    
        }else{
            $jsonPodatak = array("google_id"=>$this->google_id,"ime" => $this->ime, "prezime" => $this->prezime,"email" => $this->email, "visina" => $this->visina,"datum_rodenja" => $this->datumRodenja,"masa" => $this->masa,"cilj_mase" => $this->ciljMase,"cilj_tjednog_mrsavljenja" => $this->ciljTjednogMrsavljenja,"spol" => $this->spol );
        }
        return $jsonPodatak;
    }
    static function kreirajJsonObjekt($tablica = "korisnik", $brZapisa, $lista){
        $json = array();
        array_push($json,$lista);
        json_encode($json);
        return $json;
    }
}