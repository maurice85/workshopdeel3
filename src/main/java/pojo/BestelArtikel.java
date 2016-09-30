/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author maurice
 */
@Named
@Dependent
public class BestelArtikel {
    private long id;
    private Artikel artikel;
    private Bestelling bestelling;
    private int aantal;

public long getId(){
    return id;
}    

public void setId(long id){
    this.id = id;
}

public int getAantal(){
    return aantal;
}

public void setAantal(int aantal){
    this.aantal = aantal;
}

public Artikel getArtikel(){
    return artikel;
}

public void setArtikel(Artikel artikel){
    this.artikel = artikel;
}

public Bestelling getBestelling(){
    return bestelling;
}

public void setBestelling(Bestelling bestelling){
    this.bestelling = bestelling;
}



}
