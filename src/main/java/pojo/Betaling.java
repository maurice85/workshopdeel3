/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;

/**
 *
 * @author maurice
 */
public class Betaling {
    private long id;
    private Date betaalDatum;
    private Klant klant;
    private Factuur factuur;
    private String betalingsgegevens;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public Date getBetaalDatum(){
        return betaalDatum;
    }
    
    public void setBetaalDatum(Date betaalDatum){
        this.betaalDatum = betaalDatum;
    }
    
    public Klant getKlant(){
        return klant;
    }
    
    public void setKlant(Klant klant){
        this.klant = klant;
    }
    
    public Factuur getFactuur(){
        return factuur;
    }
    
    public void setFactuur(Factuur factuur){
        this.factuur = factuur;
    }
    
    public String getBetalingsGegevens(){
        return betalingsgegevens;
    }
    
    public void setBetalingsGegevens(String betalingsgegevens){
        this.betalingsgegevens = betalingsgegevens; 
    }
}
