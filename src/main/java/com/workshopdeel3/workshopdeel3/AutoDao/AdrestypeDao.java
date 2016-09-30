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
import com.workshopdeel3.workshopdeel3.pojoAuto.Adrestype;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.workshopdeel3.workshopdeel3.pojoAuto.KlantHasAdres;
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
public class AdrestypeDao implements Serializable {

    public AdrestypeDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adrestype adrestype) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (adrestype.getKlantHasAdresSet() == null) {
            adrestype.setKlantHasAdresSet(new HashSet<KlantHasAdres>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<KlantHasAdres> attachedKlantHasAdresSet = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSetKlantHasAdresToAttach : adrestype.getKlantHasAdresSet()) {
                klantHasAdresSetKlantHasAdresToAttach = em.getReference(klantHasAdresSetKlantHasAdresToAttach.getClass(), klantHasAdresSetKlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSet.add(klantHasAdresSetKlantHasAdresToAttach);
            }
            adrestype.setKlantHasAdresSet(attachedKlantHasAdresSet);
            em.persist(adrestype);
            for (KlantHasAdres klantHasAdresSetKlantHasAdres : adrestype.getKlantHasAdresSet()) {
                Adrestype oldAdrestypeOfKlantHasAdresSetKlantHasAdres = klantHasAdresSetKlantHasAdres.getAdrestype();
                klantHasAdresSetKlantHasAdres.setAdrestype(adrestype);
                klantHasAdresSetKlantHasAdres = em.merge(klantHasAdresSetKlantHasAdres);
                if (oldAdrestypeOfKlantHasAdresSetKlantHasAdres != null) {
                    oldAdrestypeOfKlantHasAdresSetKlantHasAdres.getKlantHasAdresSet().remove(klantHasAdresSetKlantHasAdres);
                    oldAdrestypeOfKlantHasAdresSetKlantHasAdres = em.merge(oldAdrestypeOfKlantHasAdresSetKlantHasAdres);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAdrestype(adrestype.getAdresTypeId()) != null) {
                throw new PreexistingEntityException("Adrestype " + adrestype + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adrestype adrestype) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Adrestype persistentAdrestype = em.find(Adrestype.class, adrestype.getAdresTypeId());
            Set<KlantHasAdres> klantHasAdresSetOld = persistentAdrestype.getKlantHasAdresSet();
            Set<KlantHasAdres> klantHasAdresSetNew = adrestype.getKlantHasAdresSet();
            List<String> illegalOrphanMessages = null;
            for (KlantHasAdres klantHasAdresSetOldKlantHasAdres : klantHasAdresSetOld) {
                if (!klantHasAdresSetNew.contains(klantHasAdresSetOldKlantHasAdres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KlantHasAdres " + klantHasAdresSetOldKlantHasAdres + " since its adrestype field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<KlantHasAdres> attachedKlantHasAdresSetNew = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSetNewKlantHasAdresToAttach : klantHasAdresSetNew) {
                klantHasAdresSetNewKlantHasAdresToAttach = em.getReference(klantHasAdresSetNewKlantHasAdresToAttach.getClass(), klantHasAdresSetNewKlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSetNew.add(klantHasAdresSetNewKlantHasAdresToAttach);
            }
            klantHasAdresSetNew = attachedKlantHasAdresSetNew;
            adrestype.setKlantHasAdresSet(klantHasAdresSetNew);
            adrestype = em.merge(adrestype);
            for (KlantHasAdres klantHasAdresSetNewKlantHasAdres : klantHasAdresSetNew) {
                if (!klantHasAdresSetOld.contains(klantHasAdresSetNewKlantHasAdres)) {
                    Adrestype oldAdrestypeOfKlantHasAdresSetNewKlantHasAdres = klantHasAdresSetNewKlantHasAdres.getAdrestype();
                    klantHasAdresSetNewKlantHasAdres.setAdrestype(adrestype);
                    klantHasAdresSetNewKlantHasAdres = em.merge(klantHasAdresSetNewKlantHasAdres);
                    if (oldAdrestypeOfKlantHasAdresSetNewKlantHasAdres != null && !oldAdrestypeOfKlantHasAdresSetNewKlantHasAdres.equals(adrestype)) {
                        oldAdrestypeOfKlantHasAdresSetNewKlantHasAdres.getKlantHasAdresSet().remove(klantHasAdresSetNewKlantHasAdres);
                        oldAdrestypeOfKlantHasAdresSetNewKlantHasAdres = em.merge(oldAdrestypeOfKlantHasAdresSetNewKlantHasAdres);
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
                Integer id = adrestype.getAdresTypeId();
                if (findAdrestype(id) == null) {
                    throw new NonexistentEntityException("The adrestype with id " + id + " no longer exists.");
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
            Adrestype adrestype;
            try {
                adrestype = em.getReference(Adrestype.class, id);
                adrestype.getAdresTypeId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adrestype with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<KlantHasAdres> klantHasAdresSetOrphanCheck = adrestype.getKlantHasAdresSet();
            for (KlantHasAdres klantHasAdresSetOrphanCheckKlantHasAdres : klantHasAdresSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Adrestype (" + adrestype + ") cannot be destroyed since the KlantHasAdres " + klantHasAdresSetOrphanCheckKlantHasAdres + " in its klantHasAdresSet field has a non-nullable adrestype field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(adrestype);
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

    public List<Adrestype> findAdrestypeEntities() {
        return findAdrestypeEntities(true, -1, -1);
    }

    public List<Adrestype> findAdrestypeEntities(int maxResults, int firstResult) {
        return findAdrestypeEntities(false, maxResults, firstResult);
    }

    private List<Adrestype> findAdrestypeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adrestype.class));
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

    public Adrestype findAdrestype(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adrestype.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdrestypeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adrestype> rt = cq.from(Adrestype.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
