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
import com.workshopdeel3.workshopdeel3.pojoAuto.Factuur;
import com.workshopdeel3.workshopdeel3.pojoAuto.Klant;
import com.workshopdeel3.workshopdeel3.pojoAuto.Betaalwijze;
import com.workshopdeel3.workshopdeel3.pojoAuto.Betaling;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
public class BetalingDao implements Serializable {

    public BetalingDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Betaling betaling) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Factuur factuurId = betaling.getFactuurId();
            if (factuurId != null) {
                factuurId = em.getReference(factuurId.getClass(), factuurId.getFactuurId());
                betaling.setFactuurId(factuurId);
            }
            Klant klantId = betaling.getKlantId();
            if (klantId != null) {
                klantId = em.getReference(klantId.getClass(), klantId.getKlantId());
                betaling.setKlantId(klantId);
            }
            Betaalwijze betaalwijzeId = betaling.getBetaalwijzeId();
            if (betaalwijzeId != null) {
                betaalwijzeId = em.getReference(betaalwijzeId.getClass(), betaalwijzeId.getBetaalwijzeId());
                betaling.setBetaalwijzeId(betaalwijzeId);
            }
            em.persist(betaling);
            if (factuurId != null) {
                factuurId.getBetalingSet().add(betaling);
                factuurId = em.merge(factuurId);
            }
            if (klantId != null) {
                klantId.getBetalingSet().add(betaling);
                klantId = em.merge(klantId);
            }
            if (betaalwijzeId != null) {
                betaalwijzeId.getBetalingSet().add(betaling);
                betaalwijzeId = em.merge(betaalwijzeId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBetaling(betaling.getBetalingId()) != null) {
                throw new PreexistingEntityException("Betaling " + betaling + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Betaling betaling) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Betaling persistentBetaling = em.find(Betaling.class, betaling.getBetalingId());
            Factuur factuurIdOld = persistentBetaling.getFactuurId();
            Factuur factuurIdNew = betaling.getFactuurId();
            Klant klantIdOld = persistentBetaling.getKlantId();
            Klant klantIdNew = betaling.getKlantId();
            Betaalwijze betaalwijzeIdOld = persistentBetaling.getBetaalwijzeId();
            Betaalwijze betaalwijzeIdNew = betaling.getBetaalwijzeId();
            if (factuurIdNew != null) {
                factuurIdNew = em.getReference(factuurIdNew.getClass(), factuurIdNew.getFactuurId());
                betaling.setFactuurId(factuurIdNew);
            }
            if (klantIdNew != null) {
                klantIdNew = em.getReference(klantIdNew.getClass(), klantIdNew.getKlantId());
                betaling.setKlantId(klantIdNew);
            }
            if (betaalwijzeIdNew != null) {
                betaalwijzeIdNew = em.getReference(betaalwijzeIdNew.getClass(), betaalwijzeIdNew.getBetaalwijzeId());
                betaling.setBetaalwijzeId(betaalwijzeIdNew);
            }
            betaling = em.merge(betaling);
            if (factuurIdOld != null && !factuurIdOld.equals(factuurIdNew)) {
                factuurIdOld.getBetalingSet().remove(betaling);
                factuurIdOld = em.merge(factuurIdOld);
            }
            if (factuurIdNew != null && !factuurIdNew.equals(factuurIdOld)) {
                factuurIdNew.getBetalingSet().add(betaling);
                factuurIdNew = em.merge(factuurIdNew);
            }
            if (klantIdOld != null && !klantIdOld.equals(klantIdNew)) {
                klantIdOld.getBetalingSet().remove(betaling);
                klantIdOld = em.merge(klantIdOld);
            }
            if (klantIdNew != null && !klantIdNew.equals(klantIdOld)) {
                klantIdNew.getBetalingSet().add(betaling);
                klantIdNew = em.merge(klantIdNew);
            }
            if (betaalwijzeIdOld != null && !betaalwijzeIdOld.equals(betaalwijzeIdNew)) {
                betaalwijzeIdOld.getBetalingSet().remove(betaling);
                betaalwijzeIdOld = em.merge(betaalwijzeIdOld);
            }
            if (betaalwijzeIdNew != null && !betaalwijzeIdNew.equals(betaalwijzeIdOld)) {
                betaalwijzeIdNew.getBetalingSet().add(betaling);
                betaalwijzeIdNew = em.merge(betaalwijzeIdNew);
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
                Integer id = betaling.getBetalingId();
                if (findBetaling(id) == null) {
                    throw new NonexistentEntityException("The betaling with id " + id + " no longer exists.");
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
            Betaling betaling;
            try {
                betaling = em.getReference(Betaling.class, id);
                betaling.getBetalingId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The betaling with id " + id + " no longer exists.", enfe);
            }
            Factuur factuurId = betaling.getFactuurId();
            if (factuurId != null) {
                factuurId.getBetalingSet().remove(betaling);
                factuurId = em.merge(factuurId);
            }
            Klant klantId = betaling.getKlantId();
            if (klantId != null) {
                klantId.getBetalingSet().remove(betaling);
                klantId = em.merge(klantId);
            }
            Betaalwijze betaalwijzeId = betaling.getBetaalwijzeId();
            if (betaalwijzeId != null) {
                betaalwijzeId.getBetalingSet().remove(betaling);
                betaalwijzeId = em.merge(betaalwijzeId);
            }
            em.remove(betaling);
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

    public List<Betaling> findBetalingEntities() {
        return findBetalingEntities(true, -1, -1);
    }

    public List<Betaling> findBetalingEntities(int maxResults, int firstResult) {
        return findBetalingEntities(false, maxResults, firstResult);
    }

    private List<Betaling> findBetalingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Betaling.class));
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

    public Betaling findBetaling(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Betaling.class, id);
        } finally {
            em.close();
        }
    }

    public int getBetalingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Betaling> rt = cq.from(Betaling.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
