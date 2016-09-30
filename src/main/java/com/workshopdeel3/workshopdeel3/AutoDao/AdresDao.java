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
import com.workshopdeel3.workshopdeel3.pojoAuto.Adres;
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
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
@Named
@Dependent
public class AdresDao implements Serializable {

    public AdresDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adres adres) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (adres.getKlantHasAdresSet() == null) {
            adres.setKlantHasAdresSet(new HashSet<KlantHasAdres>());
        }
        if (adres.getKlantHasAdresSet1() == null) {
            adres.setKlantHasAdresSet1(new HashSet<KlantHasAdres>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<KlantHasAdres> attachedKlantHasAdresSet = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSetKlantHasAdresToAttach : adres.getKlantHasAdresSet()) {
                klantHasAdresSetKlantHasAdresToAttach = em.getReference(klantHasAdresSetKlantHasAdresToAttach.getClass(), klantHasAdresSetKlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSet.add(klantHasAdresSetKlantHasAdresToAttach);
            }
            adres.setKlantHasAdresSet(attachedKlantHasAdresSet);
            Set<KlantHasAdres> attachedKlantHasAdresSet1 = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSet1KlantHasAdresToAttach : adres.getKlantHasAdresSet1()) {
                klantHasAdresSet1KlantHasAdresToAttach = em.getReference(klantHasAdresSet1KlantHasAdresToAttach.getClass(), klantHasAdresSet1KlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSet1.add(klantHasAdresSet1KlantHasAdresToAttach);
            }
            adres.setKlantHasAdresSet1(attachedKlantHasAdresSet1);
            em.persist(adres);
            for (KlantHasAdres klantHasAdresSetKlantHasAdres : adres.getKlantHasAdresSet()) {
                Adres oldAdresOfKlantHasAdresSetKlantHasAdres = klantHasAdresSetKlantHasAdres.getAdres();
                klantHasAdresSetKlantHasAdres.setAdres(adres);
                klantHasAdresSetKlantHasAdres = em.merge(klantHasAdresSetKlantHasAdres);
                if (oldAdresOfKlantHasAdresSetKlantHasAdres != null) {
                    oldAdresOfKlantHasAdresSetKlantHasAdres.getKlantHasAdresSet().remove(klantHasAdresSetKlantHasAdres);
                    oldAdresOfKlantHasAdresSetKlantHasAdres = em.merge(oldAdresOfKlantHasAdresSetKlantHasAdres);
                }
            }
            for (KlantHasAdres klantHasAdresSet1KlantHasAdres : adres.getKlantHasAdresSet1()) {
                Adres oldAdresAdresIdOfKlantHasAdresSet1KlantHasAdres = klantHasAdresSet1KlantHasAdres.getAdresAdresId();
                klantHasAdresSet1KlantHasAdres.setAdresAdresId(adres);
                klantHasAdresSet1KlantHasAdres = em.merge(klantHasAdresSet1KlantHasAdres);
                if (oldAdresAdresIdOfKlantHasAdresSet1KlantHasAdres != null) {
                    oldAdresAdresIdOfKlantHasAdresSet1KlantHasAdres.getKlantHasAdresSet1().remove(klantHasAdresSet1KlantHasAdres);
                    oldAdresAdresIdOfKlantHasAdresSet1KlantHasAdres = em.merge(oldAdresAdresIdOfKlantHasAdresSet1KlantHasAdres);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAdres(adres.getAdresId()) != null) {
                throw new PreexistingEntityException("Adres " + adres + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adres adres) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Adres persistentAdres = em.find(Adres.class, adres.getAdresId());
            Set<KlantHasAdres> klantHasAdresSetOld = persistentAdres.getKlantHasAdresSet();
            Set<KlantHasAdres> klantHasAdresSetNew = adres.getKlantHasAdresSet();
            Set<KlantHasAdres> klantHasAdresSet1Old = persistentAdres.getKlantHasAdresSet1();
            Set<KlantHasAdres> klantHasAdresSet1New = adres.getKlantHasAdresSet1();
            List<String> illegalOrphanMessages = null;
            for (KlantHasAdres klantHasAdresSetOldKlantHasAdres : klantHasAdresSetOld) {
                if (!klantHasAdresSetNew.contains(klantHasAdresSetOldKlantHasAdres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KlantHasAdres " + klantHasAdresSetOldKlantHasAdres + " since its adres field is not nullable.");
                }
            }
            for (KlantHasAdres klantHasAdresSet1OldKlantHasAdres : klantHasAdresSet1Old) {
                if (!klantHasAdresSet1New.contains(klantHasAdresSet1OldKlantHasAdres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KlantHasAdres " + klantHasAdresSet1OldKlantHasAdres + " since its adresAdresId field is not nullable.");
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
            adres.setKlantHasAdresSet(klantHasAdresSetNew);
            Set<KlantHasAdres> attachedKlantHasAdresSet1New = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSet1NewKlantHasAdresToAttach : klantHasAdresSet1New) {
                klantHasAdresSet1NewKlantHasAdresToAttach = em.getReference(klantHasAdresSet1NewKlantHasAdresToAttach.getClass(), klantHasAdresSet1NewKlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSet1New.add(klantHasAdresSet1NewKlantHasAdresToAttach);
            }
            klantHasAdresSet1New = attachedKlantHasAdresSet1New;
            adres.setKlantHasAdresSet1(klantHasAdresSet1New);
            adres = em.merge(adres);
            for (KlantHasAdres klantHasAdresSetNewKlantHasAdres : klantHasAdresSetNew) {
                if (!klantHasAdresSetOld.contains(klantHasAdresSetNewKlantHasAdres)) {
                    Adres oldAdresOfKlantHasAdresSetNewKlantHasAdres = klantHasAdresSetNewKlantHasAdres.getAdres();
                    klantHasAdresSetNewKlantHasAdres.setAdres(adres);
                    klantHasAdresSetNewKlantHasAdres = em.merge(klantHasAdresSetNewKlantHasAdres);
                    if (oldAdresOfKlantHasAdresSetNewKlantHasAdres != null && !oldAdresOfKlantHasAdresSetNewKlantHasAdres.equals(adres)) {
                        oldAdresOfKlantHasAdresSetNewKlantHasAdres.getKlantHasAdresSet().remove(klantHasAdresSetNewKlantHasAdres);
                        oldAdresOfKlantHasAdresSetNewKlantHasAdres = em.merge(oldAdresOfKlantHasAdresSetNewKlantHasAdres);
                    }
                }
            }
            for (KlantHasAdres klantHasAdresSet1NewKlantHasAdres : klantHasAdresSet1New) {
                if (!klantHasAdresSet1Old.contains(klantHasAdresSet1NewKlantHasAdres)) {
                    Adres oldAdresAdresIdOfKlantHasAdresSet1NewKlantHasAdres = klantHasAdresSet1NewKlantHasAdres.getAdresAdresId();
                    klantHasAdresSet1NewKlantHasAdres.setAdresAdresId(adres);
                    klantHasAdresSet1NewKlantHasAdres = em.merge(klantHasAdresSet1NewKlantHasAdres);
                    if (oldAdresAdresIdOfKlantHasAdresSet1NewKlantHasAdres != null && !oldAdresAdresIdOfKlantHasAdresSet1NewKlantHasAdres.equals(adres)) {
                        oldAdresAdresIdOfKlantHasAdresSet1NewKlantHasAdres.getKlantHasAdresSet1().remove(klantHasAdresSet1NewKlantHasAdres);
                        oldAdresAdresIdOfKlantHasAdresSet1NewKlantHasAdres = em.merge(oldAdresAdresIdOfKlantHasAdresSet1NewKlantHasAdres);
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
                Integer id = adres.getAdresId();
                if (findAdres(id) == null) {
                    throw new NonexistentEntityException("The adres with id " + id + " no longer exists.");
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
            Adres adres;
            try {
                adres = em.getReference(Adres.class, id);
                adres.getAdresId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adres with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<KlantHasAdres> klantHasAdresSetOrphanCheck = adres.getKlantHasAdresSet();
            for (KlantHasAdres klantHasAdresSetOrphanCheckKlantHasAdres : klantHasAdresSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Adres (" + adres + ") cannot be destroyed since the KlantHasAdres " + klantHasAdresSetOrphanCheckKlantHasAdres + " in its klantHasAdresSet field has a non-nullable adres field.");
            }
            Set<KlantHasAdres> klantHasAdresSet1OrphanCheck = adres.getKlantHasAdresSet1();
            for (KlantHasAdres klantHasAdresSet1OrphanCheckKlantHasAdres : klantHasAdresSet1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Adres (" + adres + ") cannot be destroyed since the KlantHasAdres " + klantHasAdresSet1OrphanCheckKlantHasAdres + " in its klantHasAdresSet1 field has a non-nullable adresAdresId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(adres);
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

    public List<Adres> findAdresEntities() {
        return findAdresEntities(true, -1, -1);
    }

    public List<Adres> findAdresEntities(int maxResults, int firstResult) {
        return findAdresEntities(false, maxResults, firstResult);
    }

    private List<Adres> findAdresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adres.class));
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

    public Adres findAdres(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adres.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adres> rt = cq.from(Adres.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
