/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author maurice
 */
@Dependent
@Named
public class Klant {
    private long id;
    private String adres;
    private String mail;
    private String tussenvoegsel;
    private String voornaam;
    private String achternaam;
    private Set<Bestelling> BestellingSet;
    private Set<Factuur> FactuurSet;
    private Set<Account> AccountSet;
    private Set<KlantHasAdres> AdresSet;
    
    public Set<KlantHasAdres> getAdresSet(){
        return AdresSet;
    }
    
    public void setAdresSet(Set<KlantHasAdres> AdresSet){
        this.AdresSet = AdresSet;
    }
    
    public Set<Bestelling> getBestellingSet(){
        return BestellingSet;
    }
    
    public void setBestellingSet(Set<Bestelling> BestellingSet){
        this.BestellingSet = BestellingSet;
        
    }
    
    public Set<Factuur> getFactuurSet(){
        return FactuurSet;
    }
    
    public void setFactuurSet(Set<Factuur> FactuurSet){
        this.FactuurSet = FactuurSet;
    }
    
    public Set<Account> getAccountSet(){
        return AccountSet;
    }
    
    public void setAccountSet(Set<Account> AccountSet){
        this.AccountSet = AccountSet;
    }
    
    public long getID(){
        return id;
    }
    
    public void setID(long id){
        this.id = id;
    }
    
    public String getAdres(){
        return adres;
    }
    
    public void setAdres(String adres){
        this.adres = adres;
    }
    
    public String getTussenvoegsel(){
        return tussenvoegsel;
    }
        
    public void setTussenvoegsel(String tussenvoegsel){
        this.tussenvoegsel = tussenvoegsel;
    }
    
    public String getVoornaam(){
        return voornaam;
    }
    
    public void setVoornaam(String voornaam){
        this.voornaam = voornaam;
    }
    
    public String getAchternaam(){
        return achternaam;
    }
    
    public void setAchternaam(String achternaam){
        this.achternaam = achternaam;
    }
    
    
}
