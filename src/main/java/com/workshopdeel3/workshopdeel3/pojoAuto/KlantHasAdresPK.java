/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.pojoAuto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author maurice
 */
@Embeddable
public class KlantHasAdresPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "adres_type_id")
    private int adresTypeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "adre_adres_id")
    private int adreAdresId;

    public KlantHasAdresPK() {
    }

    public KlantHasAdresPK(int adresTypeId, int adreAdresId) {
        this.adresTypeId = adresTypeId;
        this.adreAdresId = adreAdresId;
    }

    public int getAdresTypeId() {
        return adresTypeId;
    }

    public void setAdresTypeId(int adresTypeId) {
        this.adresTypeId = adresTypeId;
    }

    public int getAdreAdresId() {
        return adreAdresId;
    }

    public void setAdreAdresId(int adreAdresId) {
        this.adreAdresId = adreAdresId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) adresTypeId;
        hash += (int) adreAdresId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KlantHasAdresPK)) {
            return false;
        }
        KlantHasAdresPK other = (KlantHasAdresPK) object;
        if (this.adresTypeId != other.adresTypeId) {
            return false;
        }
        if (this.adreAdresId != other.adreAdresId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.KlantHasAdresPK[ adresTypeId=" + adresTypeId + ", adreAdresId=" + adreAdresId + " ]";
    }
    
}
