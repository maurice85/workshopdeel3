/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.AutoDao;

import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.NonexistentEntityException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.PreexistingEntityException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.workshopdeel3.workshopdeel3.pojoAuto.Bestelling;
import com.workshopdeel3.workshopdeel3.pojoAuto.Klant;
import com.workshopdeel3.workshopdeel3.pojoAuto.Betaling;
import com.workshopdeel3.workshopdeel3.pojoAuto.Factuur;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
public class FactuurDao implements Serializable {

    public FactuurDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factuur factuur) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (factuur.getBetalingSet() == null) {
            factuur.setBetalingSet(new HashSet<Betaling>());
        }
        if (factuur.getBestellingSet() == null) {
            factuur.setBestellingSet(new HashSet<Bestelling>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bestelling bestellingId = factuur.getBestellingId();
            if (bestellingId != null) {
                bestellingId = em.getReference(bestellingId.getClass(), bestellingId.getBestellingId());
                factuur.setBestellingId(bestellingId);
            }
            Klant klantid = factuur.getKlantid();
            if (klantid != null) {
                klantid = em.getReference(klantid.getClass(), klantid.getKlantId());
                factuur.setKlantid(klantid);
            }
            Set<Betaling> attachedBetalingSet = new HashSet<Betaling>();
            for (Betaling betalingSetBetalingToAttach : factuur.getBetalingSet()) {
                betalingSetBetalingToAttach = em.getReference(betalingSetBetalingToAttach.getClass(), betalingSetBetalingToAttach.getBetalingId());
                attachedBetalingSet.add(betalingSetBetalingToAttach);
            }
            factuur.setBetalingSet(attachedBetalingSet);
            Set<Bestelling> attachedBestellingSet = new HashSet<Bestelling>();
            for (Bestelling bestellingSetBestellingToAttach : factuur.getBestellingSet()) {
                bestellingSetBestellingToAttach = em.getReference(bestellingSetBestellingToAttach.getClass(), bestellingSetBestellingToAttach.getBestellingId());
                attachedBestellingSet.add(bestellingSetBestellingToAttach);
            }
            factuur.setBestellingSet(attachedBestellingSet);
            em.persist(factuur);
            if (bestellingId != null) {
                Factuur oldFactuurIdOfBestellingId = bestellingId.getFactuurId();
                if (oldFactuurIdOfBestellingId != null) {
                    oldFactuurIdOfBestellingId.setBestellingId(null);
                    oldFactuurIdOfBestellingId = em.merge(oldFactuurIdOfBestellingId);
                }
                bestellingId.setFactuurId(factuur);
                bestellingId = em.merge(bestellingId);
            }
            if (klantid != null) {
                klantid.getFactuurSet().add(factuur);
                klantid = em.merge(klantid);
            }
            for (Betaling betalingSetBetaling : factuur.getBetalingSet()) {
                Factuur oldFactuurIdOfBetalingSetBetaling = betalingSetBetaling.getFactuurId();
                betalingSetBetaling.setFactuurId(factuur);
                betalingSetBetaling = em.merge(betalingSetBetaling);
                if (oldFactuurIdOfBetalingSetBetaling != null) {
                    oldFactuurIdOfBetalingSetBetaling.getBetalingSet().remove(betalingSetBetaling);
                    oldFactuurIdOfBetalingSetBetaling = em.merge(oldFactuurIdOfBetalingSetBetaling);
                }
            }
            for (Bestelling bestellingSetBestelling : factuur.getBestellingSet()) {
                Factuur oldFactuurIdOfBestellingSetBestelling = bestellingSetBestelling.getFactuurId();
                bestellingSetBestelling.setFactuurId(factuur);
                bestellingSetBestelling = em.merge(bestellingSetBestelling);
                if (oldFactuurIdOfBestellingSetBestelling != null) {
                    oldFactuurIdOfBestellingSetBestelling.getBestellingSet().remove(bestellingSetBestelling);
                    oldFactuurIdOfBestellingSetBestelling = em.merge(oldFactuurIdOfBestellingSetBestelling);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findFactuur(factuur.getFactuurId()) != null) {
                throw new PreexistingEntityException("Factuur " + factuur + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factuur factuur) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Factuur persistentFactuur = em.find(Factuur.class, factuur.getFactuurId());
            Bestelling bestellingIdOld = persistentFactuur.getBestellingId();
            Bestelling bestellingIdNew = factuur.getBestellingId();
            Klant klantidOld = persistentFactuur.getKlantid();
            Klant klantidNew = factuur.getKlantid();
            Set<Betaling> betalingSetOld = persistentFactuur.getBetalingSet();
            Set<Betaling> betalingSetNew = factuur.getBetalingSet();
            Set<Bestelling> bestellingSetOld = persistentFactuur.getBestellingSet();
            Set<Bestelling> bestellingSetNew = factuur.getBestellingSet();
            if (bestellingIdNew != null) {
                bestellingIdNew = em.getReference(bestellingIdNew.getClass(), bestellingIdNew.getBestellingId());
                factuur.setBestellingId(bestellingIdNew);
            }
            if (klantidNew != null) {
                klantidNew = em.getReference(klantidNew.getClass(), klantidNew.getKlantId());
                factuur.setKlantid(klantidNew);
            }
            Set<Betaling> attachedBetalingSetNew = new HashSet<Betaling>();
            for (Betaling betalingSetNewBetalingToAttach : betalingSetNew) {
                betalingSetNewBetalingToAttach = em.getReference(betalingSetNewBetalingToAttach.getClass(), betalingSetNewBetalingToAttach.getBetalingId());
                attachedBetalingSetNew.add(betalingSetNewBetalingToAttach);
            }
            betalingSetNew = attachedBetalingSetNew;
            factuur.setBetalingSet(betalingSetNew);
            Set<Bestelling> attachedBestellingSetNew = new HashSet<Bestelling>();
            for (Bestelling bestellingSetNewBestellingToAttach : bestellingSetNew) {
                bestellingSetNewBestellingToAttach = em.getReference(bestellingSetNewBestellingToAttach.getClass(), bestellingSetNewBestellingToAttach.getBestellingId());
                attachedBestellingSetNew.add(bestellingSetNewBestellingToAttach);
            }
            bestellingSetNew = attachedBestellingSetNew;
            factuur.setBestellingSet(bestellingSetNew);
            factuur = em.merge(factuur);
            if (bestellingIdOld != null && !bestellingIdOld.equals(bestellingIdNew)) {
                bestellingIdOld.setFactuurId(null);
                bestellingIdOld = em.merge(bestellingIdOld);
            }
            if (bestellingIdNew != null && !bestellingIdNew.equals(bestellingIdOld)) {
                Factuur oldFactuurIdOfBestellingId = bestellingIdNew.getFactuurId();
                if (oldFactuurIdOfBestellingId != null) {
                    oldFactuurIdOfBestellingId.setBestellingId(null);
                    oldFactuurIdOfBestellingId = em.merge(oldFactuurIdOfBestellingId);
                }
                bestellingIdNew.setFactuurId(factuur);
                bestellingIdNew = em.merge(bestellingIdNew);
            }
            if (klantidOld != null && !klantidOld.equals(klantidNew)) {
                klantidOld.getFactuurSet().remove(factuur);
                klantidOld = em.merge(klantidOld);
            }
            if (klantidNew != null && !klantidNew.equals(klantidOld)) {
                klantidNew.getFactuurSet().add(factuur);
                klantidNew = em.merge(klantidNew);
            }
            for (Betaling betalingSetOldBetaling : betalingSetOld) {
                if (!betalingSetNew.contains(betalingSetOldBetaling)) {
                    betalingSetOldBetaling.setFactuurId(null);
                    betalingSetOldBetaling = em.merge(betalingSetOldBetaling);
                }
            }
            for (Betaling betalingSetNewBetaling : betalingSetNew) {
                if (!betalingSetOld.contains(betalingSetNewBetaling)) {
                    Factuur oldFactuurIdOfBetalingSetNewBetaling = betalingSetNewBetaling.getFactuurId();
                    betalingSetNewBetaling.setFactuurId(factuur);
                    betalingSetNewBetaling = em.merge(betalingSetNewBetaling);
                    if (oldFactuurIdOfBetalingSetNewBetaling != null && !oldFactuurIdOfBetalingSetNewBetaling.equals(factuur)) {
                        oldFactuurIdOfBetalingSetNewBetaling.getBetalingSet().remove(betalingSetNewBetaling);
                        oldFactuurIdOfBetalingSetNewBetaling = em.merge(oldFactuurIdOfBetalingSetNewBetaling);
                    }
                }
            }
            for (Bestelling bestellingSetOldBestelling : bestellingSetOld) {
                if (!bestellingSetNew.contains(bestellingSetOldBestelling)) {
                    bestellingSetOldBestelling.setFactuurId(null);
                    bestellingSetOldBestelling = em.merge(bestellingSetOldBestelling);
                }
            }
            for (Bestelling bestellingSetNewBestelling : bestellingSetNew) {
                if (!bestellingSetOld.contains(bestellingSetNewBestelling)) {
                    Factuur oldFactuurIdOfBestellingSetNewBestelling = bestellingSetNewBestelling.getFactuurId();
                    bestellingSetNewBestelling.setFactuurId(factuur);
                    bestellingSetNewBestelling = em.merge(bestellingSetNewBestelling);
                    if (oldFactuurIdOfBestellingSetNewBestelling != null && !oldFactuurIdOfBestellingSetNewBestelling.equals(factuur)) {
                        oldFactuurIdOfBestellingSetNewBestelling.getBestellingSet().remove(bestellingSetNewBestelling);
                        oldFactuurIdOfBestellingSetNewBestelling = em.merge(oldFactuurIdOfBestellingSetNewBestelling);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factuur.getFactuurId();
                if (findFactuur(id) == null) {
                    throw new NonexistentEntityException("The factuur with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Factuur factuur;
            try {
                factuur = em.getReference(Factuur.class, id);
                factuur.getFactuurId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factuur with id " + id + " no longer exists.", enfe);
            }
            Bestelling bestellingId = factuur.getBestellingId();
            if (bestellingId != null) {
                bestellingId.setFactuurId(null);
                bestellingId = em.merge(bestellingId);
            }
            Klant klantid = factuur.getKlantid();
            if (klantid != null) {
                klantid.getFactuurSet().remove(factuur);
                klantid = em.merge(klantid);
            }
            Set<Betaling> betalingSet = factuur.getBetalingSet();
            for (Betaling betalingSetBetaling : betalingSet) {
                betalingSetBetaling.setFactuurId(null);
                betalingSetBetaling = em.merge(betalingSetBetaling);
            }
            Set<Bestelling> bestellingSet = factuur.getBestellingSet();
            for (Bestelling bestellingSetBestelling : bestellingSet) {
                bestellingSetBestelling.setFactuurId(null);
                bestellingSetBestelling = em.merge(bestellingSetBestelling);
            }
            em.remove(factuur);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factuur> findFactuurEntities() {
        return findFactuurEntities(true, -1, -1);
    }

    public List<Factuur> findFactuurEntities(int maxResults, int firstResult) {
        return findFactuurEntities(false, maxResults, firstResult);
    }

    private List<Factuur> findFactuurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factuur.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Factuur findFactuur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factuur.class, id);
        } finally {
            em.close();
        }
    }

    public int getFactuurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factuur> rt = cq.from(Factuur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
