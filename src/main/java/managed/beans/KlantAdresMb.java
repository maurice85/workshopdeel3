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
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author maurice
 */
@Named
@Dependent
public class KlantAdresMb {
    @Inject private KlantDao klantDao;
    @Inject private AdresDao adresDao;
    
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
