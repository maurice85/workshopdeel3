/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed.beans;

import com.workshopdeel3.workshopdeel3.AutoDao.AdresDao;
import com.workshopdeel3.workshopdeel3.AutoDao.KlantDao;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.RollbackFailureException;
import com.workshopdeel3.workshopdeel3.pojoAuto.Adres;
import com.workshopdeel3.workshopdeel3.pojoAuto.Klant;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author maurice
 */
@Named("klantAdresMb")
@Dependent
@Stateless
public class KlantAdresMb {
    //v
    @EJB private Adres newAdres;
    @EJB private Klant newKlant;
    @EJB private KlantDao klantDao;
    @EJB private AdresDao adresDao;
    
    
    public Adres getNewAdres() {
        return newAdres;
    }

    public void setNewAdres(Adres newAdres) {
        this.newAdres = newAdres;
    }

    public Klant getNewKlant() {
        return newKlant;
    }

    public void setNewKlant(Klant newKlant) {
        this.newKlant = newKlant;
    }
    
    public String createNewKlantAdres() throws Exception{
        setKlant(newKlant);
        setAdres(newAdres);
        return "bevestigingspagina";
    }
    public void setKlant(Klant klant) throws Exception{
        klantDao.create(klant);
        
    }
    
    public Klant getKlant(Klant klant){
        return klantDao.findKlant(klant.getKlantId());
    }
    
    public void setAdres(Adres adres) throws Exception{
        adresDao.create(adres);
    }
    
    public Adres getAdres(Klant klant){
      return adresDao.findAdres(klant.getKlantId());
        
    }
    
}
