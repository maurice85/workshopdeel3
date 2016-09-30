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
public class Artikel {
    private long id;
    private String artikelNummer;
    private double prijs;
    private String naam;
    private String omschrijving;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
        
    }
    
    public String getArtikelNummer(){
        return artikelNummer;
    }
    
    public void setArtikelNummer(String artikelNummer){
        this.artikelNummer = artikelNummer;
    }
    
    public String getNaam(){
        return naam;
    }
    
    public void setNaam(String naam){
        this.naam = naam;
    }
    
    public String getOmschrijving(){
        return omschrijving;
    }
    
    public void setOmschrijving(String omschrijving){
        this.omschrijving = omschrijving;
    }
    
    public double getPrijs(){
        return prijs;
    }
    
    public void setPrijs(double prijs){
        this.prijs = prijs;
    }
    
    
}
