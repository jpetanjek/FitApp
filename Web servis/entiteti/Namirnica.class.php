<?php

class Namirnica{

    public $id;
    public $naziv;
    public $brojKalorija;
    public $tezina;
    public $isbn;

    function __construct($json,$identifikator=false){
        if($identifikator){
            $this->id = (int)$json["id"];
        }
        $this->naziv = $json["naziv"];
        $this->brojKalorija = (int)$json["broj_kalorija"];
        $this->tezina = (int)$json["tezina"];
        $this->isbn = $json["isbn"];
        
    }
    function dohvatiJson(){
        if($this->id!=""){
            $jsonPodatak = array("id" => $this->id, "naziv" => $this->naziv,"brojKalorija" => $this->brojKalorija, "tezina" => $this->tezina,"isbn" => $this->isbn);
        }else{
            $jsonPodatak = array("naziv" => $this->naziv,"brojKalorija" => $this->brojKalorija, "tezina" => $this->tezina,"isbn" => $this->isbn);
        }
        
        return $jsonPodatak;
    }
    static function kreirajJsonObjekt($tablica = "namirnica", $brZapisa, $lista){
        $json = array();
        array_push($json,$lista);
        json_encode($json);
        return $json;
    }
}