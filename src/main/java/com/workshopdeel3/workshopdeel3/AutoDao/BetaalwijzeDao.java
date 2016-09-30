/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.AutoDao;

import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.IllegalOrphanException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.NonexistentEntityException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.PreexistingEntityException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.RollbackFailureException;
import com.workshopdeel3.workshopdeel3.pojoAuto.Betaalwijze;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.workshopdeel3.workshopdeel3.pojoAuto.Betaling;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
public class BetaalwijzeDao implements Serializable {

    public BetaalwijzeDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Betaalwijze betaalwijze) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (betaalwijze.getBetalingSet() == null) {
            betaalwijze.setBetalingSet(new HashSet<Betaling>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<Betaling> attachedBetalingSet = new HashSet<Betaling>();
            for (Betaling betalingSetBetalingToAttach : betaalwijze.getBetalingSet()) {
                betalingSetBetalingToAttach = em.getReference(betalingSetBetalingToAttach.getClass(), betalingSetBetalingToAttach.getBetalingId());
                attachedBetalingSet.add(betalingSetBetalingToAttach);
            }
            betaalwijze.setBetalingSet(attachedBetalingSet);
            em.persist(betaalwijze);
            for (Betaling betalingSetBetaling : betaalwijze.getBetalingSet()) {
                Betaalwijze oldBetaalwijzeIdOfBetalingSetBetaling = betalingSetBetaling.getBetaalwijzeId();
                betalingSetBetaling.setBetaalwijzeId(betaalwijze);
                betalingSetBetaling = em.merge(betalingSetBetaling);
                if (oldBetaalwijzeIdOfBetalingSetBetaling != null) {
                    oldBetaalwijzeIdOfBetalingSetBetaling.getBetalingSet().remove(betalingSetBetaling);
                    oldBetaalwijzeIdOfBetalingSetBetaling = em.merge(oldBetaalwijzeIdOfBetalingSetBetaling);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBetaalwijze(betaalwijze.getBetaalwijzeId()) != null) {
                throw new PreexistingEntityException("Betaalwijze " + betaalwijze + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Betaalwijze betaalwijze) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Betaalwijze persistentBetaalwijze = em.find(Betaalwijze.class, betaalwijze.getBetaalwijzeId());
            Set<Betaling> betalingSetOld = persistentBetaalwijze.getBetalingSet();
            Set<Betaling> betalingSetNew = betaalwijze.getBetalingSet();
            List<String> illegalOrphanMessages = null;
            for (Betaling betalingSetOldBetaling : betalingSetOld) {
                if (!betalingSetNew.contains(betalingSetOldBetaling)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Betaling " + betalingSetOldBetaling + " since its betaalwijzeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Betaling> attachedBetalingSetNew = new HashSet<Betaling>();
            for (Betaling betalingSetNewBetalingToAttach : betalingSetNew) {
                betalingSetNewBetalingToAttach = em.getReference(betalingSetNewBetalingToAttach.getClass(), betalingSetNewBetalingToAttach.getBetalingId());
                attachedBetalingSetNew.add(betalingSetNewBetalingToAttach);
            }
            betalingSetNew = attachedBetalingSetNew;
            betaalwijze.setBetalingSet(betalingSetNew);
            betaalwijze = em.merge(betaalwijze);
            for (Betaling betalingSetNewBetaling : betalingSetNew) {
                if (!betalingSetOld.contains(betalingSetNewBetaling)) {
                    Betaalwijze oldBetaalwijzeIdOfBetalingSetNewBetaling = betalingSetNewBetaling.getBetaalwijzeId();
                    betalingSetNewBetaling.setBetaalwijzeId(betaalwijze);
                    betalingSetNewBetaling = em.merge(betalingSetNewBetaling);
                    if (oldBetaalwijzeIdOfBetalingSetNewBetaling != null && !oldBetaalwijzeIdOfBetalingSetNewBetaling.equals(betaalwijze)) {
                        oldBetaalwijzeIdOfBetalingSetNewBetaling.getBetalingSet().remove(betalingSetNewBetaling);
                        oldBetaalwijzeIdOfBetalingSetNewBetaling = em.merge(oldBetaalwijzeIdOfBetalingSetNewBetaling);
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
                Integer id = betaalwijze.getBetaalwijzeId();
                if (findBetaalwijze(id) == null) {
                    throw new NonexistentEntityException("The betaalwijze with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Betaalwijze betaalwijze;
            try {
                betaalwijze = em.getReference(Betaalwijze.class, id);
                betaalwijze.getBetaalwijzeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The betaalwijze with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Betaling> betalingSetOrphanCheck = betaalwijze.getBetalingSet();
            for (Betaling betalingSetOrphanCheckBetaling : betalingSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Betaalwijze (" + betaalwijze + ") cannot be destroyed since the Betaling " + betalingSetOrphanCheckBetaling + " in its betalingSet field has a non-nullable betaalwijzeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(betaalwijze);
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

    public List<Betaalwijze> findBetaalwijzeEntities() {
        return findBetaalwijzeEntities(true, -1, -1);
    }

    public List<Betaalwijze> findBetaalwijzeEntities(int maxResults, int firstResult) {
        return findBetaalwijzeEntities(false, maxResults, firstResult);
    }

    private List<Betaalwijze> findBetaalwijzeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Betaalwijze.class));
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

    public Betaalwijze findBetaalwijze(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Betaalwijze.class, id);
        } finally {
            em.close();
        }
    }

    public int getBetaalwijzeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Betaalwijze> rt = cq.from(Betaalwijze.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
