/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.pojoAuto;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maurice
 */
@Entity
@Table(name = "klant_has_adres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KlantHasAdres.findAll", query = "SELECT k FROM KlantHasAdres k"),
    @NamedQuery(name = "KlantHasAdres.findByAdresTypeId", query = "SELECT k FROM KlantHasAdres k WHERE k.klantHasAdresPK.adresTypeId = :adresTypeId"),
    @NamedQuery(name = "KlantHasAdres.findByAdreAdresId", query = "SELECT k FROM KlantHasAdres k WHERE k.klantHasAdresPK.adreAdresId = :adreAdresId")})
public class KlantHasAdres implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KlantHasAdresPK klantHasAdresPK;
    @JoinColumn(name = "klant_klant_id", referencedColumnName = "klant_id")
    @ManyToOne(optional = false)
    private Klant klantKlantId;
    @JoinColumn(name = "adre_adres_id", referencedColumnName = "adres_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Adres adres;
    @JoinColumn(name = "adres_type_id", referencedColumnName = "adres_type_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Adrestype adrestype;
    @JoinColumn(name = "adres_adres_id", referencedColumnName = "adres_id")
    @ManyToOne(optional = false)
    private Adres adresAdresId;

    public KlantHasAdres() {
    }

    public KlantHasAdres(KlantHasAdresPK klantHasAdresPK) {
        this.klantHasAdresPK = klantHasAdresPK;
    }

    public KlantHasAdres(int adresTypeId, int adreAdresId) {
        this.klantHasAdresPK = new KlantHasAdresPK(adresTypeId, adreAdresId);
    }

    public KlantHasAdresPK getKlantHasAdresPK() {
        return klantHasAdresPK;
    }

    public void setKlantHasAdresPK(KlantHasAdresPK klantHasAdresPK) {
        this.klantHasAdresPK = klantHasAdresPK;
    }

    public Klant getKlantKlantId() {
        return klantKlantId;
    }

    public void setKlantKlantId(Klant klantKlantId) {
        this.klantKlantId = klantKlantId;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Adrestype getAdrestype() {
        return adrestype;
    }

    public void setAdrestype(Adrestype adrestype) {
        this.adrestype = adrestype;
    }

    public Adres getAdresAdresId() {
        return adresAdresId;
    }

    public void setAdresAdresId(Adres adresAdresId) {
        this.adresAdresId = adresAdresId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (klantHasAdresPK != null ? klantHasAdresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KlantHasAdres)) {
            return false;
        }
        KlantHasAdres other = (KlantHasAdres) object;
        if ((this.klantHasAdresPK == null && other.klantHasAdresPK != null) || (this.klantHasAdresPK != null && !this.klantHasAdresPK.equals(other.klantHasAdresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.KlantHasAdres[ klantHasAdresPK=" + klantHasAdresPK + " ]";
    }
    
}
