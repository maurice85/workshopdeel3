/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.pojoAuto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maurice
 */
@Entity
@Table(name = "artikel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikel.findAll", query = "SELECT a FROM Artikel a"),
    @NamedQuery(name = "Artikel.findByArtikelID", query = "SELECT a FROM Artikel a WHERE a.artikelID = :artikelID"),
    @NamedQuery(name = "Artikel.findByArtikelBeschrijving", query = "SELECT a FROM Artikel a WHERE a.artikelBeschrijving = :artikelBeschrijving"),
    @NamedQuery(name = "Artikel.findByArtikelNaam", query = "SELECT a FROM Artikel a WHERE a.artikelNaam = :artikelNaam"),
    @NamedQuery(name = "Artikel.findByArtikelNummer", query = "SELECT a FROM Artikel a WHERE a.artikelNummer = :artikelNummer"),
    @NamedQuery(name = "Artikel.findByArtikelPrijs", query = "SELECT a FROM Artikel a WHERE a.artikelPrijs = :artikelPrijs")})
public class Artikel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "artikelID")
    private Integer artikelID;
    @Size(max = 255)
    @Column(name = "artikelBeschrijving")
    private String artikelBeschrijving;
    @Size(max = 255)
    @Column(name = "artikelNaam")
    private String artikelNaam;
    @Size(max = 255)
    @Column(name = "artikelNummer")
    private String artikelNummer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "artikelPrijs")
    private Double artikelPrijs;
    @JoinColumn(name = "bestelling_id", referencedColumnName = "bestelling_id")
    @ManyToOne(optional = false)
    private Bestelling bestellingId;

    public Artikel() {
    }

    public Artikel(Integer artikelID) {
        this.artikelID = artikelID;
    }

    public Integer getArtikelID() {
        return artikelID;
    }

    public void setArtikelID(Integer artikelID) {
        this.artikelID = artikelID;
    }

    public String getArtikelBeschrijving() {
        return artikelBeschrijving;
    }

    public void setArtikelBeschrijving(String artikelBeschrijving) {
        this.artikelBeschrijving = artikelBeschrijving;
    }

    public String getArtikelNaam() {
        return artikelNaam;
    }

    public void setArtikelNaam(String artikelNaam) {
        this.artikelNaam = artikelNaam;
    }

    public String getArtikelNummer() {
        return artikelNummer;
    }

    public void setArtikelNummer(String artikelNummer) {
        this.artikelNummer = artikelNummer;
    }

    public Double getArtikelPrijs() {
        return artikelPrijs;
    }

    public void setArtikelPrijs(Double artikelPrijs) {
        this.artikelPrijs = artikelPrijs;
    }

    public Bestelling getBestellingId() {
        return bestellingId;
    }

    public void setBestellingId(Bestelling bestellingId) {
        this.bestellingId = bestellingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (artikelID != null ? artikelID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikel)) {
            return false;
        }
        Artikel other = (Artikel) object;
        if ((this.artikelID == null && other.artikelID != null) || (this.artikelID != null && !this.artikelID.equals(other.artikelID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Artikel[ artikelID=" + artikelID + " ]";
    }
    
}
