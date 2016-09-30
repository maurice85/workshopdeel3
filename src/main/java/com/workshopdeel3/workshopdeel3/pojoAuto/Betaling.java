/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.pojoAuto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author maurice
 */
@Entity
@Table(name = "betaling")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Betaling.findAll", query = "SELECT b FROM Betaling b"),
    @NamedQuery(name = "Betaling.findByBetalingId", query = "SELECT b FROM Betaling b WHERE b.betalingId = :betalingId"),
    @NamedQuery(name = "Betaling.findByBetaalDatum", query = "SELECT b FROM Betaling b WHERE b.betaalDatum = :betaalDatum"),
    @NamedQuery(name = "Betaling.findByBetalingsGegevens", query = "SELECT b FROM Betaling b WHERE b.betalingsGegevens = :betalingsGegevens")})
public class Betaling implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "betaling_id")
    private Integer betalingId;
    @Column(name = "betaal_datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date betaalDatum;
    @Size(max = 255)
    @Column(name = "betalingsGegevens")
    private String betalingsGegevens;
    @JoinColumn(name = "factuur_id", referencedColumnName = "factuur_id")
    @ManyToOne
    private Factuur factuurId;
    @JoinColumn(name = "klant_id", referencedColumnName = "klant_id")
    @ManyToOne
    private Klant klantId;
    @JoinColumn(name = "betaalwijze_id", referencedColumnName = "betaalwijze_id")
    @ManyToOne(optional = false)
    private Betaalwijze betaalwijzeId;

    public Betaling() {
    }

    public Betaling(Integer betalingId) {
        this.betalingId = betalingId;
    }

    public Integer getBetalingId() {
        return betalingId;
    }

    public void setBetalingId(Integer betalingId) {
        this.betalingId = betalingId;
    }

    public Date getBetaalDatum() {
        return betaalDatum;
    }

    public void setBetaalDatum(Date betaalDatum) {
        this.betaalDatum = betaalDatum;
    }

    public String getBetalingsGegevens() {
        return betalingsGegevens;
    }

    public void setBetalingsGegevens(String betalingsGegevens) {
        this.betalingsGegevens = betalingsGegevens;
    }

    public Factuur getFactuurId() {
        return factuurId;
    }

    public void setFactuurId(Factuur factuurId) {
        this.factuurId = factuurId;
    }

    public Klant getKlantId() {
        return klantId;
    }

    public void setKlantId(Klant klantId) {
        this.klantId = klantId;
    }

    public Betaalwijze getBetaalwijzeId() {
        return betaalwijzeId;
    }

    public void setBetaalwijzeId(Betaalwijze betaalwijzeId) {
        this.betaalwijzeId = betaalwijzeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (betalingId != null ? betalingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Betaling)) {
            return false;
        }
        Betaling other = (Betaling) object;
        if ((this.betalingId == null && other.betalingId != null) || (this.betalingId != null && !this.betalingId.equals(other.betalingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Betaling[ betalingId=" + betalingId + " ]";
    }
    
}
