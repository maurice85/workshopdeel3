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
import javax.persistence.CascadeType;
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
@Table(name = "bestelling")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bestelling.findAll", query = "SELECT b FROM Bestelling b"),
    @NamedQuery(name = "Bestelling.findByBestellingId", query = "SELECT b FROM Bestelling b WHERE b.bestellingId = :bestellingId"),
    @NamedQuery(name = "Bestelling.findByBestelNummer", query = "SELECT b FROM Bestelling b WHERE b.bestelNummer = :bestelNummer"),
    @NamedQuery(name = "Bestelling.findByBestelDatum", query = "SELECT b FROM Bestelling b WHERE b.bestelDatum = :bestelDatum")})
public class Bestelling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "bestelling_id")
    private Integer bestellingId;
    @Size(max = 255)
    @Column(name = "bestelNummer")
    private String bestelNummer;
    @Column(name = "bestel_datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bestelDatum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bestellingId")
    private Set<Artikel> artikelSet;
    @JoinColumn(name = "Klant_id", referencedColumnName = "klant_id")
    @ManyToOne(optional = false)
    private Klant klantid;
    @JoinColumn(name = "factuur_id", referencedColumnName = "factuur_id")
    @ManyToOne
    private Factuur factuurId;
    @OneToMany(mappedBy = "bestellingId")
    private Set<Factuur> factuurSet;

    public Bestelling() {
    }

    public Bestelling(Integer bestellingId) {
        this.bestellingId = bestellingId;
    }

    public Integer getBestellingId() {
        return bestellingId;
    }

    public void setBestellingId(Integer bestellingId) {
        this.bestellingId = bestellingId;
    }

    public String getBestelNummer() {
        return bestelNummer;
    }

    public void setBestelNummer(String bestelNummer) {
        this.bestelNummer = bestelNummer;
    }

    public Date getBestelDatum() {
        return bestelDatum;
    }

    public void setBestelDatum(Date bestelDatum) {
        this.bestelDatum = bestelDatum;
    }

    @XmlTransient
    public Set<Artikel> getArtikelSet() {
        return artikelSet;
    }

    public void setArtikelSet(Set<Artikel> artikelSet) {
        this.artikelSet = artikelSet;
    }

    public Klant getKlantid() {
        return klantid;
    }

    public void setKlantid(Klant klantid) {
        this.klantid = klantid;
    }

    public Factuur getFactuurId() {
        return factuurId;
    }

    public void setFactuurId(Factuur factuurId) {
        this.factuurId = factuurId;
    }

    @XmlTransient
    public Set<Factuur> getFactuurSet() {
        return factuurSet;
    }

    public void setFactuurSet(Set<Factuur> factuurSet) {
        this.factuurSet = factuurSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bestellingId != null ? bestellingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bestelling)) {
            return false;
        }
        Bestelling other = (Bestelling) object;
        if ((this.bestellingId == null && other.bestellingId != null) || (this.bestellingId != null && !this.bestellingId.equals(other.bestellingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Bestelling[ bestellingId=" + bestellingId + " ]";
    }
    
}
