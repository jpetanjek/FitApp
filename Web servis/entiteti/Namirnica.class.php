<?php

class Namirnica{

    private $id;
    private $naziv;
    private $brojKalorija;
    private $tezina;
    private $isbn;

    function __construct($json){
        $this->id = $json["id"];
        $this->naziv = $json["naziv"];
        $this->brojKalorija = $json["broj_kalorija"];
        $this->tezina = $json["tezina"];
        $this->isbn = $json["isbn"];
        
    }
    function dohvatiJson(){
        $jsonPodatak = array("id" => $this->id, "naziv" => $this->naziv,"brojKalorija" => $this->brojKalorija, "tezina" => $this->tezina,"isbn" => $this->isbn);
        return $jsonPodatak;
    }
    static function kreirajJsonObjekt($tablica = "namirnica", $brZapisa, $lista){
        $json = array();
        array_push($json,$tablica);
        array_push($json,$brZapisa);
        array_push($json,$lista);
        json_encode($json);
        return $json;
    }
}