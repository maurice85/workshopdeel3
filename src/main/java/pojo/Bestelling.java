/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author maurice
 */
@Named
@Dependent
public class Bestelling {
    private long id;
    private Date bestelDatum;
    private String bestelNummer;
    private int factuur_id;
    private int klant_id;
    private Set<BestelArtikel> bestelArtikelSet;
    private Set<Factuur> factuurSet;
    private Klant klant;
    
    public Klant getKlant(){
        return klant;
    }
    
    public void setKlant(Klant klant){
        this.klant = klant;
    }
    
    public Set<Factuur> getFactuurSet(){
        return factuurSet;
    }
    
    public void setFactuurSet(Set<Factuur> factuurSet){
        this.factuurSet = factuurSet;
    }    
    
    public Set<BestelArtikel> getBestelArtikelSet(){
        return bestelArtikelSet;
    }
    
    public void setBestelArtikelSet(Set<BestelArtikel> bestelArtikelSet){
        this.bestelArtikelSet = bestelArtikelSet;
    }
    
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public Date getBestelDatum(){
        return bestelDatum;
    }
    
    public void setBestelDatum(Date bestelDatum){
        this.bestelDatum = bestelDatum;
    }
    
    public String getBestelNummer(){
        return bestelNummer;
    }
    
    public void setBestelNummer(String bestelNummer){
        this.bestelNummer = bestelNummer;
    }
    
    public int getFactuur_id(){
        return factuur_id;
    }
    
    public void setFactuur_id(int factuur_id){
        this.factuur_id = factuur_id;
    }
    
    public int getKlant_id(){
        return klant_id;
    }
    
    public void setKlant_id(int klant_id){
        this.klant_id = klant_id;
    }
    
}
