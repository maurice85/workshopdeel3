/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author maurice
 */
public class Factuur {
    private long id;
    private String factuurNummer;
    private Date besteldDatum;
    private Set<Betaling> betalingSet;
    private Bestelling bestelling;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public String getFactuurNummer(){
        return factuurNummer;
    }
    
    public void setFactuurNummer(String factuurNummer){
        this.factuurNummer = factuurNummer;
    }
    
    public Date getBesteldDatum(){
        return besteldDatum;
    }
    
    public void setBesteldDatum(Date besteldDatum){
        this.besteldDatum = besteldDatum;
    }
    
    public Set<Betaling> getBetalingSet(){
        return betalingSet;
    }
    
    public void setBetalingSet(Set<Betaling> betalingSet){
        this.betalingSet = betalingSet;
    }
    
    public Bestelling getBestelling(){
        return bestelling;
    }
    
    public void setBestelling(Bestelling bestelling){
        this.bestelling = bestelling;
    }
    
}
