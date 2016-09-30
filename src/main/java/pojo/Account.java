/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.util.Date;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 *
 * @author maurice
 */
@Named
@Dependent
public class Account {
    private int id;
    private Date date;
    private int klant_id;
    private String naam;
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }
    
    public int getKlant_id(){
        return klant_id;
    }
    
    public void setKlant_id(int klant_id){
        this.klant_id = klant_id;
    }
    
    public String getNaam(){
        return naam;
    }
    
    public void setNaam(String naam){
        this.naam = naam;
    }

    
    
}
