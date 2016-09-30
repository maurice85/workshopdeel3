/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.pojoAuto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author maurice
 */
@Entity
@Table(name = "factuur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factuur.findAll", query = "SELECT f FROM Factuur f"),
    @NamedQuery(name = "Factuur.findByFactuurId", query = "SELECT f FROM Factuur f WHERE f.factuurId = :factuurId"),
    @NamedQuery(name = "Factuur.findByFactuurNummer", query = "SELECT f FROM Factuur f WHERE f.factuurNummer = :factuurNummer"),
    @NamedQuery(name = "Factuur.findByFactuurDatum", query = "SELECT f FROM Factuur f WHERE f.factuurDatum = :factuurDatum")})
public class Factuur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "factuur_id")
    private Integer factuurId;
    @Size(max = 255)
    @Column(name = "factuurNummer")
    private String factuurNummer;
    @Column(name = "factuur_datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date factuurDatum;
    @OneToMany(mappedBy = "factuurId")
    private Set<Betaling> betalingSet;
    @OneToMany(mappedBy = "factuurId")
    private Set<Bestelling> bestellingSet;
    @JoinColumn(name = "bestelling_id", referencedColumnName = "bestelling_id")
    @ManyToOne
    private Bestelling bestellingId;
    @JoinColumn(name = "Klant_id", referencedColumnName = "klant_id")
    @ManyToOne(optional = false)
    private Klant klantid;

    public Factuur() {
    }

    public Factuur(Integer factuurId) {
        this.factuurId = factuurId;
    }

    public Integer getFactuurId() {
        return factuurId;
    }

    public void setFactuurId(Integer factuurId) {
        this.factuurId = factuurId;
    }

    public String getFactuurNummer() {
        return factuurNummer;
    }

    public void setFactuurNummer(String factuurNummer) {
        this.factuurNummer = factuurNummer;
    }

    public Date getFactuurDatum() {
        return factuurDatum;
    }

    public void setFactuurDatum(Date factuurDatum) {
        this.factuurDatum = factuurDatum;
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

    public Bestelling getBestellingId() {
        return bestellingId;
    }

    public void setBestellingId(Bestelling bestellingId) {
        this.bestellingId = bestellingId;
    }

    public Klant getKlantid() {
        return klantid;
    }

    public void setKlantid(Klant klantid) {
        this.klantid = klantid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (factuurId != null ? factuurId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factuur)) {
            return false;
        }
        Factuur other = (Factuur) object;
        if ((this.factuurId == null && other.factuurId != null) || (this.factuurId != null && !this.factuurId.equals(other.factuurId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Factuur[ factuurId=" + factuurId + " ]";
    }
    
}
