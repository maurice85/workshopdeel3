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
@Table(name = "klant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Klant.findAll", query = "SELECT k FROM Klant k"),
    @NamedQuery(name = "Klant.findByKlantId", query = "SELECT k FROM Klant k WHERE k.klantId = :klantId"),
    @NamedQuery(name = "Klant.findByAchternaam", query = "SELECT k FROM Klant k WHERE k.achternaam = :achternaam"),
    @NamedQuery(name = "Klant.findByEmail", query = "SELECT k FROM Klant k WHERE k.email = :email"),
    @NamedQuery(name = "Klant.findByTussenvoegsel", query = "SELECT k FROM Klant k WHERE k.tussenvoegsel = :tussenvoegsel"),
    @NamedQuery(name = "Klant.findByVoornaam", query = "SELECT k FROM Klant k WHERE k.voornaam = :voornaam")})
public class Klant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "klant_id")
    private Integer klantId;
    @Size(max = 255)
    @Column(name = "achternaam")
    private String achternaam;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "tussenvoegsel")
    private String tussenvoegsel;
    @Size(max = 255)
    @Column(name = "voornaam")
    private String voornaam;
    @OneToMany(mappedBy = "klantId")
    private Set<Betaling> betalingSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantid")
    private Set<Bestelling> bestellingSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantid")
    private Set<Factuur> factuurSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantid")
    private Set<Account> accountSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "klantKlantId")
    private Set<KlantHasAdres> klantHasAdresSet;

    public Klant() {
    }

    public Klant(Integer klantId) {
        this.klantId = klantId;
    }

    public Integer getKlantId() {
        return klantId;
    }

    public void setKlantId(Integer klantId) {
        this.klantId = klantId;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    @XmlTransient
    public Set<Betaling> getBetalingSet() {
        return betalingSet;
    }

    public void setBetalingSet(Set<Betaling> betalingSet) {
        this.betalingSet = betalingSet;
    }

    @XmlTransient
    public Set<Bestelling> getBestellingSet() {
        return bestellingSet;
    }

    public void setBestellingSet(Set<Bestelling> bestellingSet) {
        this.bestellingSet = bestellingSet;
    }

    @XmlTransient
    public Set<Factuur> getFactuurSet() {
        return factuurSet;
    }

    public void setFactuurSet(Set<Factuur> factuurSet) {
        this.factuurSet = factuurSet;
    }

    @XmlTransient
    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
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
        hash += (klantId != null ? klantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Klant)) {
            return false;
        }
        Klant other = (Klant) object;
        if ((this.klantId == null && other.klantId != null) || (this.klantId != null && !this.klantId.equals(other.klantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Klant[ klantId=" + klantId + " ]";
    }
    
}
