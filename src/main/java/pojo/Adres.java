/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

/**
 *
 * @author maurice
 */
public class Adres {
    private long id;
    private String straatnaam;
    private String postcode;
    private String toevoeging;
    private int huisnummer;
    private String woonplaats;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public String getStraatNaam(){
        return straatnaam;
    }
    
    public void setStraatNaam(String straatnaam){
        this.straatnaam = straatnaam;
    }
    
    public String getPostCode(){
        return postcode;
    }
    
    public void setPostCode(String postcode){
        this.postcode = postcode;
    }
    
    public int getHuisNummer(){
        return huisnummer;
    }
    
    public void setHuisNummer(int huisnummer){
        this.huisnummer = huisnummer;
    }
    
    public String getWoonPlaats(){
        return woonplaats;
    }
    
    public void setWoonPlaats(String woonplaats){
        this.woonplaats = woonplaats;
    }

}
