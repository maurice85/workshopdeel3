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
@Table(name = "adres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adres.findAll", query = "SELECT a FROM Adres a"),
    @NamedQuery(name = "Adres.findByAdresId", query = "SELECT a FROM Adres a WHERE a.adresId = :adresId"),
    @NamedQuery(name = "Adres.findByHuisnummer", query = "SELECT a FROM Adres a WHERE a.huisnummer = :huisnummer"),
    @NamedQuery(name = "Adres.findByPostcode", query = "SELECT a FROM Adres a WHERE a.postcode = :postcode"),
    @NamedQuery(name = "Adres.findByStraatnaam", query = "SELECT a FROM Adres a WHERE a.straatnaam = :straatnaam"),
    @NamedQuery(name = "Adres.findByToevoeging", query = "SELECT a FROM Adres a WHERE a.toevoeging = :toevoeging"),
    @NamedQuery(name = "Adres.findByWoonplaats", query = "SELECT a FROM Adres a WHERE a.woonplaats = :woonplaats")})
public class Adres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "adres_id")
    private Integer adresId;
    @Column(name = "huisnummer")
    private Integer huisnummer;
    @Size(max = 255)
    @Column(name = "postcode")
    private String postcode;
    @Size(max = 255)
    @Column(name = "straatnaam")
    private String straatnaam;
    @Size(max = 255)
    @Column(name = "toevoeging")
    private String toevoeging;
    @Size(max = 255)
    @Column(name = "woonplaats")
    private String woonplaats;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adres")
    private Set<KlantHasAdres> klantHasAdresSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adresAdresId")
    private Set<KlantHasAdres> klantHasAdresSet1;

    public Adres() {
    }

    public Adres(Integer adresId) {
        this.adresId = adresId;
    }

    public Integer getAdresId() {
        return adresId;
    }

    public void setAdresId(Integer adresId) {
        this.adresId = adresId;
    }

    public Integer getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    @XmlTransient
    public Set<KlantHasAdres> getKlantHasAdresSet() {
        return klantHasAdresSet;
    }

    public void setKlantHasAdresSet(Set<KlantHasAdres> klantHasAdresSet) {
        this.klantHasAdresSet = klantHasAdresSet;
    }

    @XmlTransient
    public Set<KlantHasAdres> getKlantHasAdresSet1() {
        return klantHasAdresSet1;
    }

    public void setKlantHasAdresSet1(Set<KlantHasAdres> klantHasAdresSet1) {
        this.klantHasAdresSet1 = klantHasAdresSet1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (adresId != null ? adresId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adres)) {
            return false;
        }
        Adres other = (Adres) object;
        if ((this.adresId == null && other.adresId != null) || (this.adresId != null && !this.adresId.equals(other.adresId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Adres[ adresId=" + adresId + " ]";
    }
    
}
