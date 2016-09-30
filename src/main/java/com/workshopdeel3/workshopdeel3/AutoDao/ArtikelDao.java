/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.workshopdeel3.workshopdeel3.AutoDao;

import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.NonexistentEntityException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.PreexistingEntityException;
import com.workshopdeel3.workshopdeel3.AutoDao.exceptions.RollbackFailureException;
import com.workshopdeel3.workshopdeel3.pojoAuto.Artikel;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.workshopdeel3.workshopdeel3.pojoAuto.Bestelling;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
public class ArtikelDao implements Serializable {

    public ArtikelDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Artikel artikel) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bestelling bestellingId = artikel.getBestellingId();
            if (bestellingId != null) {
                bestellingId = em.getReference(bestellingId.getClass(), bestellingId.getBestellingId());
                artikel.setBestellingId(bestellingId);
            }
            em.persist(artikel);
            if (bestellingId != null) {
                bestellingId.getArtikelSet().add(artikel);
                bestellingId = em.merge(bestellingId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArtikel(artikel.getArtikelID()) != null) {
                throw new PreexistingEntityException("Artikel " + artikel + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Artikel artikel) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Artikel persistentArtikel = em.find(Artikel.class, artikel.getArtikelID());
            Bestelling bestellingIdOld = persistentArtikel.getBestellingId();
            Bestelling bestellingIdNew = artikel.getBestellingId();
            if (bestellingIdNew != null) {
                bestellingIdNew = em.getReference(bestellingIdNew.getClass(), bestellingIdNew.getBestellingId());
                artikel.setBestellingId(bestellingIdNew);
            }
            artikel = em.merge(artikel);
            if (bestellingIdOld != null && !bestellingIdOld.equals(bestellingIdNew)) {
                bestellingIdOld.getArtikelSet().remove(artikel);
                bestellingIdOld = em.merge(bestellingIdOld);
            }
            if (bestellingIdNew != null && !bestellingIdNew.equals(bestellingIdOld)) {
                bestellingIdNew.getArtikelSet().add(artikel);
                bestellingIdNew = em.merge(bestellingIdNew);
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
                Integer id = artikel.getArtikelID();
                if (findArtikel(id) == null) {
                    throw new NonexistentEntityException("The artikel with id " + id + " no longer exists.");
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
            Artikel artikel;
            try {
                artikel = em.getReference(Artikel.class, id);
                artikel.getArtikelID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The artikel with id " + id + " no longer exists.", enfe);
            }
            Bestelling bestellingId = artikel.getBestellingId();
            if (bestellingId != null) {
                bestellingId.getArtikelSet().remove(artikel);
                bestellingId = em.merge(bestellingId);
            }
            em.remove(artikel);
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

    public List<Artikel> findArtikelEntities() {
        return findArtikelEntities(true, -1, -1);
    }

    public List<Artikel> findArtikelEntities(int maxResults, int firstResult) {
        return findArtikelEntities(false, maxResults, firstResult);
    }

    private List<Artikel> findArtikelEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Artikel.class));
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

    public Artikel findArtikel(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Artikel.class, id);
        } finally {
            em.close();
        }
    }

    public int getArtikelCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Artikel> rt = cq.from(Artikel.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
