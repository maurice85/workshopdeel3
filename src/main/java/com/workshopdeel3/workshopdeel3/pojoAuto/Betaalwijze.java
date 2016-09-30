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
@Table(name = "betaalwijze")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Betaalwijze.findAll", query = "SELECT b FROM Betaalwijze b"),
    @NamedQuery(name = "Betaalwijze.findByBetaalwijzeId", query = "SELECT b FROM Betaalwijze b WHERE b.betaalwijzeId = :betaalwijzeId"),
    @NamedQuery(name = "Betaalwijze.findByBetaalDatum", query = "SELECT b FROM Betaalwijze b WHERE b.betaalDatum = :betaalDatum"),
    @NamedQuery(name = "Betaalwijze.findByBetaalwijze", query = "SELECT b FROM Betaalwijze b WHERE b.betaalwijze = :betaalwijze")})
public class Betaalwijze implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "betaalwijze_id")
    private Integer betaalwijzeId;
    @Column(name = "betaal_datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date betaalDatum;
    @Size(max = 255)
    @Column(name = "betaalwijze")
    private String betaalwijze;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "betaalwijzeId")
    private Set<Betaling> betalingSet;

    public Betaalwijze() {
    }

    public Betaalwijze(Integer betaalwijzeId) {
        this.betaalwijzeId = betaalwijzeId;
    }

    public Integer getBetaalwijzeId() {
        return betaalwijzeId;
    }

    public void setBetaalwijzeId(Integer betaalwijzeId) {
        this.betaalwijzeId = betaalwijzeId;
    }

    public Date getBetaalDatum() {
        return betaalDatum;
    }

    public void setBetaalDatum(Date betaalDatum) {
        this.betaalDatum = betaalDatum;
    }

    public String getBetaalwijze() {
        return betaalwijze;
    }

    public void setBetaalwijze(String betaalwijze) {
        this.betaalwijze = betaalwijze;
    }

    @XmlTransient
    public Set<Betaling> getBetalingSet() {
        return betalingSet;
    }

    public void setBetalingSet(Set<Betaling> betalingSet) {
        this.betalingSet = betalingSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (betaalwijzeId != null ? betaalwijzeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Betaalwijze)) {
            return false;
        }
        Betaalwijze other = (Betaalwijze) object;
        if ((this.betaalwijzeId == null && other.betaalwijzeId != null) || (this.betaalwijzeId != null && !this.betaalwijzeId.equals(other.betaalwijzeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.workshopdeel3.workshopdeel3.pojoAuto.Betaalwijze[ betaalwijzeId=" + betaalwijzeId + " ]";
    }
    
}
