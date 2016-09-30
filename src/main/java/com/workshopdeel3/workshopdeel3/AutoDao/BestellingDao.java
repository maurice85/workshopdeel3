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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.workshopdeel3.workshopdeel3.pojoAuto.Klant;
import com.workshopdeel3.workshopdeel3.pojoAuto.Factuur;
import com.workshopdeel3.workshopdeel3.pojoAuto.Artikel;
import com.workshopdeel3.workshopdeel3.pojoAuto.Bestelling;
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
public class BestellingDao implements Serializable {

    public BestellingDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bestelling bestelling) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (bestelling.getArtikelSet() == null) {
            bestelling.setArtikelSet(new HashSet<Artikel>());
        }
        if (bestelling.getFactuurSet() == null) {
            bestelling.setFactuurSet(new HashSet<Factuur>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Klant klantid = bestelling.getKlantid();
            if (klantid != null) {
                klantid = em.getReference(klantid.getClass(), klantid.getKlantId());
                bestelling.setKlantid(klantid);
            }
            Factuur factuurId = bestelling.getFactuurId();
            if (factuurId != null) {
                factuurId = em.getReference(factuurId.getClass(), factuurId.getFactuurId());
                bestelling.setFactuurId(factuurId);
            }
            Set<Artikel> attachedArtikelSet = new HashSet<Artikel>();
            for (Artikel artikelSetArtikelToAttach : bestelling.getArtikelSet()) {
                artikelSetArtikelToAttach = em.getReference(artikelSetArtikelToAttach.getClass(), artikelSetArtikelToAttach.getArtikelID());
                attachedArtikelSet.add(artikelSetArtikelToAttach);
            }
            bestelling.setArtikelSet(attachedArtikelSet);
            Set<Factuur> attachedFactuurSet = new HashSet<Factuur>();
            for (Factuur factuurSetFactuurToAttach : bestelling.getFactuurSet()) {
                factuurSetFactuurToAttach = em.getReference(factuurSetFactuurToAttach.getClass(), factuurSetFactuurToAttach.getFactuurId());
                attachedFactuurSet.add(factuurSetFactuurToAttach);
            }
            bestelling.setFactuurSet(attachedFactuurSet);
            em.persist(bestelling);
            if (klantid != null) {
                klantid.getBestellingSet().add(bestelling);
                klantid = em.merge(klantid);
            }
            if (factuurId != null) {
                factuurId.getBestellingSet().add(bestelling);
                factuurId = em.merge(factuurId);
            }
            for (Artikel artikelSetArtikel : bestelling.getArtikelSet()) {
                Bestelling oldBestellingIdOfArtikelSetArtikel = artikelSetArtikel.getBestellingId();
                artikelSetArtikel.setBestellingId(bestelling);
                artikelSetArtikel = em.merge(artikelSetArtikel);
                if (oldBestellingIdOfArtikelSetArtikel != null) {
                    oldBestellingIdOfArtikelSetArtikel.getArtikelSet().remove(artikelSetArtikel);
                    oldBestellingIdOfArtikelSetArtikel = em.merge(oldBestellingIdOfArtikelSetArtikel);
                }
            }
            for (Factuur factuurSetFactuur : bestelling.getFactuurSet()) {
                Bestelling oldBestellingIdOfFactuurSetFactuur = factuurSetFactuur.getBestellingId();
                factuurSetFactuur.setBestellingId(bestelling);
                factuurSetFactuur = em.merge(factuurSetFactuur);
                if (oldBestellingIdOfFactuurSetFactuur != null) {
                    oldBestellingIdOfFactuurSetFactuur.getFactuurSet().remove(factuurSetFactuur);
                    oldBestellingIdOfFactuurSetFactuur = em.merge(oldBestellingIdOfFactuurSetFactuur);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findBestelling(bestelling.getBestellingId()) != null) {
                throw new PreexistingEntityException("Bestelling " + bestelling + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bestelling bestelling) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Bestelling persistentBestelling = em.find(Bestelling.class, bestelling.getBestellingId());
            Klant klantidOld = persistentBestelling.getKlantid();
            Klant klantidNew = bestelling.getKlantid();
            Factuur factuurIdOld = persistentBestelling.getFactuurId();
            Factuur factuurIdNew = bestelling.getFactuurId();
            Set<Artikel> artikelSetOld = persistentBestelling.getArtikelSet();
            Set<Artikel> artikelSetNew = bestelling.getArtikelSet();
            Set<Factuur> factuurSetOld = persistentBestelling.getFactuurSet();
            Set<Factuur> factuurSetNew = bestelling.getFactuurSet();
            List<String> illegalOrphanMessages = null;
            for (Artikel artikelSetOldArtikel : artikelSetOld) {
                if (!artikelSetNew.contains(artikelSetOldArtikel)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Artikel " + artikelSetOldArtikel + " since its bestellingId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (klantidNew != null) {
                klantidNew = em.getReference(klantidNew.getClass(), klantidNew.getKlantId());
                bestelling.setKlantid(klantidNew);
            }
            if (factuurIdNew != null) {
                factuurIdNew = em.getReference(factuurIdNew.getClass(), factuurIdNew.getFactuurId());
                bestelling.setFactuurId(factuurIdNew);
            }
            Set<Artikel> attachedArtikelSetNew = new HashSet<Artikel>();
            for (Artikel artikelSetNewArtikelToAttach : artikelSetNew) {
                artikelSetNewArtikelToAttach = em.getReference(artikelSetNewArtikelToAttach.getClass(), artikelSetNewArtikelToAttach.getArtikelID());
                attachedArtikelSetNew.add(artikelSetNewArtikelToAttach);
            }
            artikelSetNew = attachedArtikelSetNew;
            bestelling.setArtikelSet(artikelSetNew);
            Set<Factuur> attachedFactuurSetNew = new HashSet<Factuur>();
            for (Factuur factuurSetNewFactuurToAttach : factuurSetNew) {
                factuurSetNewFactuurToAttach = em.getReference(factuurSetNewFactuurToAttach.getClass(), factuurSetNewFactuurToAttach.getFactuurId());
                attachedFactuurSetNew.add(factuurSetNewFactuurToAttach);
            }
            factuurSetNew = attachedFactuurSetNew;
            bestelling.setFactuurSet(factuurSetNew);
            bestelling = em.merge(bestelling);
            if (klantidOld != null && !klantidOld.equals(klantidNew)) {
                klantidOld.getBestellingSet().remove(bestelling);
                klantidOld = em.merge(klantidOld);
            }
            if (klantidNew != null && !klantidNew.equals(klantidOld)) {
                klantidNew.getBestellingSet().add(bestelling);
                klantidNew = em.merge(klantidNew);
            }
            if (factuurIdOld != null && !factuurIdOld.equals(factuurIdNew)) {
                factuurIdOld.getBestellingSet().remove(bestelling);
                factuurIdOld = em.merge(factuurIdOld);
            }
            if (factuurIdNew != null && !factuurIdNew.equals(factuurIdOld)) {
                factuurIdNew.getBestellingSet().add(bestelling);
                factuurIdNew = em.merge(factuurIdNew);
            }
            for (Artikel artikelSetNewArtikel : artikelSetNew) {
                if (!artikelSetOld.contains(artikelSetNewArtikel)) {
                    Bestelling oldBestellingIdOfArtikelSetNewArtikel = artikelSetNewArtikel.getBestellingId();
                    artikelSetNewArtikel.setBestellingId(bestelling);
                    artikelSetNewArtikel = em.merge(artikelSetNewArtikel);
                    if (oldBestellingIdOfArtikelSetNewArtikel != null && !oldBestellingIdOfArtikelSetNewArtikel.equals(bestelling)) {
                        oldBestellingIdOfArtikelSetNewArtikel.getArtikelSet().remove(artikelSetNewArtikel);
                        oldBestellingIdOfArtikelSetNewArtikel = em.merge(oldBestellingIdOfArtikelSetNewArtikel);
                    }
                }
            }
            for (Factuur factuurSetOldFactuur : factuurSetOld) {
                if (!factuurSetNew.contains(factuurSetOldFactuur)) {
                    factuurSetOldFactuur.setBestellingId(null);
                    factuurSetOldFactuur = em.merge(factuurSetOldFactuur);
                }
            }
            for (Factuur factuurSetNewFactuur : factuurSetNew) {
                if (!factuurSetOld.contains(factuurSetNewFactuur)) {
                    Bestelling oldBestellingIdOfFactuurSetNewFactuur = factuurSetNewFactuur.getBestellingId();
                    factuurSetNewFactuur.setBestellingId(bestelling);
                    factuurSetNewFactuur = em.merge(factuurSetNewFactuur);
                    if (oldBestellingIdOfFactuurSetNewFactuur != null && !oldBestellingIdOfFactuurSetNewFactuur.equals(bestelling)) {
                        oldBestellingIdOfFactuurSetNewFactuur.getFactuurSet().remove(factuurSetNewFactuur);
                        oldBestellingIdOfFactuurSetNewFactuur = em.merge(oldBestellingIdOfFactuurSetNewFactuur);
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
                Integer id = bestelling.getBestellingId();
                if (findBestelling(id) == null) {
                    throw new NonexistentEntityException("The bestelling with id " + id + " no longer exists.");
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
            Bestelling bestelling;
            try {
                bestelling = em.getReference(Bestelling.class, id);
                bestelling.getBestellingId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bestelling with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Artikel> artikelSetOrphanCheck = bestelling.getArtikelSet();
            for (Artikel artikelSetOrphanCheckArtikel : artikelSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bestelling (" + bestelling + ") cannot be destroyed since the Artikel " + artikelSetOrphanCheckArtikel + " in its artikelSet field has a non-nullable bestellingId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Klant klantid = bestelling.getKlantid();
            if (klantid != null) {
                klantid.getBestellingSet().remove(bestelling);
                klantid = em.merge(klantid);
            }
            Factuur factuurId = bestelling.getFactuurId();
            if (factuurId != null) {
                factuurId.getBestellingSet().remove(bestelling);
                factuurId = em.merge(factuurId);
            }
            Set<Factuur> factuurSet = bestelling.getFactuurSet();
            for (Factuur factuurSetFactuur : factuurSet) {
                factuurSetFactuur.setBestellingId(null);
                factuurSetFactuur = em.merge(factuurSetFactuur);
            }
            em.remove(bestelling);
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

    public List<Bestelling> findBestellingEntities() {
        return findBestellingEntities(true, -1, -1);
    }

    public List<Bestelling> findBestellingEntities(int maxResults, int firstResult) {
        return findBestellingEntities(false, maxResults, firstResult);
    }

    private List<Bestelling> findBestellingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bestelling.class));
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

    public Bestelling findBestelling(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bestelling.class, id);
        } finally {
            em.close();
        }
    }

    public int getBestellingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bestelling> rt = cq.from(Bestelling.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
