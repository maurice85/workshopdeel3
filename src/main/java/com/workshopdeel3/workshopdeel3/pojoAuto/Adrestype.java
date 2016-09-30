/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.pojoAuto;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author maurice
 */
@Entity
@Table(name = "adrestype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adrestype.findAll", query = "SELECT a FROM Adrestype a"),
    @NamedQuery(name = "Adrestype.findByAdresTypeId", query = "SELECT a FROM Adrestype a WHERE a.adresTypeId = :adresTypeId"),
    @NamedQuery(name = "Adrestype.findByAdresType", query = "SELECT a FROM Adrestype a WHERE a.adresType = :adresType")})
public class Adrestype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "adres_type_id")
    private Integer adresTypeId;
    @Size(max = 255)
    @Column(name = "adres_type")
    private String adresType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adrestype")
    private Set<KlantHasAdres> klantHasAdresSet;

    public Adrestype() {
    }

    public Adrestype(Integer adresTypeId) {
        this.adresTypeId = adresTypeId;
    }

    public Integer getAdresTypeId() {
        return adresTypeId;
    }

    public void setAdresTypeId(Integer adresTypeId) {
        this.adresTypeId = adresTypeId;
    }

    public String getAdresType() {
        return adresType;
    }

    public void setAdresType(String adresType) {
        this.adresType = adresType;
    }

    @XmlTransient
    public Set<KlantHasAdres> getKlantHasAdresSet() {
        return klantHasAdresSet;
    }

    public void setKlantHasAdresSet(Set<KlantHasAdres> klantHasAdresSet) {
        this.klantHasAdresSet = klantHasAdresSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adresTypeId != null ? adresTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adrestype)) {
            return false;
        }
        Adrestype other = (Adrestype) object;
        if ((this.adresTypeId == null && other.adresTypeId != null) || (this.adresTypeId != null && !this.adresTypeId.equals(other.adresTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Adrestype[ adresTypeId=" + adresTypeId + " ]";
    }
    
}
