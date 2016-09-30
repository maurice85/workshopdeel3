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
import com.workshopdeel3.workshopdeel3.pojoAuto.Klant;
import com.workshopdeel3.workshopdeel3.pojoAuto.Adres;
import com.workshopdeel3.workshopdeel3.pojoAuto.Adrestype;
import com.workshopdeel3.workshopdeel3.pojoAuto.KlantHasAdres;
import com.workshopdeel3.workshopdeel3.pojoAuto.KlantHasAdresPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
public class KlantHasAdresDao implements Serializable {

    public KlantHasAdresDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KlantHasAdres klantHasAdres) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (klantHasAdres.getKlantHasAdresPK() == null) {
            klantHasAdres.setKlantHasAdresPK(new KlantHasAdresPK());
        }
        klantHasAdres.getKlantHasAdresPK().setAdresTypeId(klantHasAdres.getAdrestype().getAdresTypeId());
        klantHasAdres.getKlantHasAdresPK().setAdreAdresId(klantHasAdres.getAdres().getAdresId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Klant klantKlantId = klantHasAdres.getKlantKlantId();
            if (klantKlantId != null) {
                klantKlantId = em.getReference(klantKlantId.getClass(), klantKlantId.getKlantId());
                klantHasAdres.setKlantKlantId(klantKlantId);
            }
            Adres adres = klantHasAdres.getAdres();
            if (adres != null) {
                adres = em.getReference(adres.getClass(), adres.getAdresId());
                klantHasAdres.setAdres(adres);
            }
            Adrestype adrestype = klantHasAdres.getAdrestype();
            if (adrestype != null) {
                adrestype = em.getReference(adrestype.getClass(), adrestype.getAdresTypeId());
                klantHasAdres.setAdrestype(adrestype);
            }
            Adres adresAdresId = klantHasAdres.getAdresAdresId();
            if (adresAdresId != null) {
                adresAdresId = em.getReference(adresAdresId.getClass(), adresAdresId.getAdresId());
                klantHasAdres.setAdresAdresId(adresAdresId);
            }
            em.persist(klantHasAdres);
            if (klantKlantId != null) {
                klantKlantId.getKlantHasAdresSet().add(klantHasAdres);
                klantKlantId = em.merge(klantKlantId);
            }
            if (adres != null) {
                adres.getKlantHasAdresSet().add(klantHasAdres);
                adres = em.merge(adres);
            }
            if (adrestype != null) {
                adrestype.getKlantHasAdresSet().add(klantHasAdres);
                adrestype = em.merge(adrestype);
            }
            if (adresAdresId != null) {
                adresAdresId.getKlantHasAdresSet().add(klantHasAdres);
                adresAdresId = em.merge(adresAdresId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findKlantHasAdres(klantHasAdres.getKlantHasAdresPK()) != null) {
                throw new PreexistingEntityException("KlantHasAdres " + klantHasAdres + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KlantHasAdres klantHasAdres) throws NonexistentEntityException, RollbackFailureException, Exception {
        klantHasAdres.getKlantHasAdresPK().setAdresTypeId(klantHasAdres.getAdrestype().getAdresTypeId());
        klantHasAdres.getKlantHasAdresPK().setAdreAdresId(klantHasAdres.getAdres().getAdresId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            KlantHasAdres persistentKlantHasAdres = em.find(KlantHasAdres.class, klantHasAdres.getKlantHasAdresPK());
            Klant klantKlantIdOld = persistentKlantHasAdres.getKlantKlantId();
            Klant klantKlantIdNew = klantHasAdres.getKlantKlantId();
            Adres adresOld = persistentKlantHasAdres.getAdres();
            Adres adresNew = klantHasAdres.getAdres();
            Adrestype adrestypeOld = persistentKlantHasAdres.getAdrestype();
            Adrestype adrestypeNew = klantHasAdres.getAdrestype();
            Adres adresAdresIdOld = persistentKlantHasAdres.getAdresAdresId();
            Adres adresAdresIdNew = klantHasAdres.getAdresAdresId();
            if (klantKlantIdNew != null) {
                klantKlantIdNew = em.getReference(klantKlantIdNew.getClass(), klantKlantIdNew.getKlantId());
                klantHasAdres.setKlantKlantId(klantKlantIdNew);
            }
            if (adresNew != null) {
                adresNew = em.getReference(adresNew.getClass(), adresNew.getAdresId());
                klantHasAdres.setAdres(adresNew);
            }
            if (adrestypeNew != null) {
                adrestypeNew = em.getReference(adrestypeNew.getClass(), adrestypeNew.getAdresTypeId());
                klantHasAdres.setAdrestype(adrestypeNew);
            }
            if (adresAdresIdNew != null) {
                adresAdresIdNew = em.getReference(adresAdresIdNew.getClass(), adresAdresIdNew.getAdresId());
                klantHasAdres.setAdresAdresId(adresAdresIdNew);
            }
            klantHasAdres = em.merge(klantHasAdres);
            if (klantKlantIdOld != null && !klantKlantIdOld.equals(klantKlantIdNew)) {
                klantKlantIdOld.getKlantHasAdresSet().remove(klantHasAdres);
                klantKlantIdOld = em.merge(klantKlantIdOld);
            }
            if (klantKlantIdNew != null && !klantKlantIdNew.equals(klantKlantIdOld)) {
                klantKlantIdNew.getKlantHasAdresSet().add(klantHasAdres);
                klantKlantIdNew = em.merge(klantKlantIdNew);
            }
            if (adresOld != null && !adresOld.equals(adresNew)) {
                adresOld.getKlantHasAdresSet().remove(klantHasAdres);
                adresOld = em.merge(adresOld);
            }
            if (adresNew != null && !adresNew.equals(adresOld)) {
                adresNew.getKlantHasAdresSet().add(klantHasAdres);
                adresNew = em.merge(adresNew);
            }
            if (adrestypeOld != null && !adrestypeOld.equals(adrestypeNew)) {
                adrestypeOld.getKlantHasAdresSet().remove(klantHasAdres);
                adrestypeOld = em.merge(adrestypeOld);
            }
            if (adrestypeNew != null && !adrestypeNew.equals(adrestypeOld)) {
                adrestypeNew.getKlantHasAdresSet().add(klantHasAdres);
                adrestypeNew = em.merge(adrestypeNew);
            }
            if (adresAdresIdOld != null && !adresAdresIdOld.equals(adresAdresIdNew)) {
                adresAdresIdOld.getKlantHasAdresSet().remove(klantHasAdres);
                adresAdresIdOld = em.merge(adresAdresIdOld);
            }
            if (adresAdresIdNew != null && !adresAdresIdNew.equals(adresAdresIdOld)) {
                adresAdresIdNew.getKlantHasAdresSet().add(klantHasAdres);
                adresAdresIdNew = em.merge(adresAdresIdNew);
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
                KlantHasAdresPK id = klantHasAdres.getKlantHasAdresPK();
                if (findKlantHasAdres(id) == null) {
                    throw new NonexistentEntityException("The klantHasAdres with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(KlantHasAdresPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            KlantHasAdres klantHasAdres;
            try {
                klantHasAdres = em.getReference(KlantHasAdres.class, id);
                klantHasAdres.getKlantHasAdresPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The klantHasAdres with id " + id + " no longer exists.", enfe);
            }
            Klant klantKlantId = klantHasAdres.getKlantKlantId();
            if (klantKlantId != null) {
                klantKlantId.getKlantHasAdresSet().remove(klantHasAdres);
                klantKlantId = em.merge(klantKlantId);
            }
            Adres adres = klantHasAdres.getAdres();
            if (adres != null) {
                adres.getKlantHasAdresSet().remove(klantHasAdres);
                adres = em.merge(adres);
            }
            Adrestype adrestype = klantHasAdres.getAdrestype();
            if (adrestype != null) {
                adrestype.getKlantHasAdresSet().remove(klantHasAdres);
                adrestype = em.merge(adrestype);
            }
            Adres adresAdresId = klantHasAdres.getAdresAdresId();
            if (adresAdresId != null) {
                adresAdresId.getKlantHasAdresSet().remove(klantHasAdres);
                adresAdresId = em.merge(adresAdresId);
            }
            em.remove(klantHasAdres);
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

    public List<KlantHasAdres> findKlantHasAdresEntities() {
        return findKlantHasAdresEntities(true, -1, -1);
    }

    public List<KlantHasAdres> findKlantHasAdresEntities(int maxResults, int firstResult) {
        return findKlantHasAdresEntities(false, maxResults, firstResult);
    }

    private List<KlantHasAdres> findKlantHasAdresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KlantHasAdres.class));
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

    public KlantHasAdres findKlantHasAdres(KlantHasAdresPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KlantHasAdres.class, id);
        } finally {
            em.close();
        }
    }

    public int getKlantHasAdresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KlantHasAdres> rt = cq.from(KlantHasAdres.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
