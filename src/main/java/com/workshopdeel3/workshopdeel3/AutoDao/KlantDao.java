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
import com.workshopdeel3.workshopdeel3.pojoAuto.Betaling;
import java.util.HashSet;
import java.util.Set;
import com.workshopdeel3.workshopdeel3.pojoAuto.Bestelling;
import com.workshopdeel3.workshopdeel3.pojoAuto.Factuur;
import com.workshopdeel3.workshopdeel3.pojoAuto.Account;
import com.workshopdeel3.workshopdeel3.pojoAuto.Klant;
import com.workshopdeel3.workshopdeel3.pojoAuto.KlantHasAdres;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author maurice
 */
@Named(value = "KlantDao")
@SessionScoped
public class KlantDao implements Serializable {

    public KlantDao(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Klant klant) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (klant.getBetalingSet() == null) {
            klant.setBetalingSet(new HashSet<Betaling>());
        }
        if (klant.getBestellingSet() == null) {
            klant.setBestellingSet(new HashSet<Bestelling>());
        }
        if (klant.getFactuurSet() == null) {
            klant.setFactuurSet(new HashSet<Factuur>());
        }
        if (klant.getAccountSet() == null) {
            klant.setAccountSet(new HashSet<Account>());
        }
        if (klant.getKlantHasAdresSet() == null) {
            klant.setKlantHasAdresSet(new HashSet<KlantHasAdres>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Set<Betaling> attachedBetalingSet = new HashSet<Betaling>();
            for (Betaling betalingSetBetalingToAttach : klant.getBetalingSet()) {
                betalingSetBetalingToAttach = em.getReference(betalingSetBetalingToAttach.getClass(), betalingSetBetalingToAttach.getBetalingId());
                attachedBetalingSet.add(betalingSetBetalingToAttach);
            }
            klant.setBetalingSet(attachedBetalingSet);
            Set<Bestelling> attachedBestellingSet = new HashSet<Bestelling>();
            for (Bestelling bestellingSetBestellingToAttach : klant.getBestellingSet()) {
                bestellingSetBestellingToAttach = em.getReference(bestellingSetBestellingToAttach.getClass(), bestellingSetBestellingToAttach.getBestellingId());
                attachedBestellingSet.add(bestellingSetBestellingToAttach);
            }
            klant.setBestellingSet(attachedBestellingSet);
            Set<Factuur> attachedFactuurSet = new HashSet<Factuur>();
            for (Factuur factuurSetFactuurToAttach : klant.getFactuurSet()) {
                factuurSetFactuurToAttach = em.getReference(factuurSetFactuurToAttach.getClass(), factuurSetFactuurToAttach.getFactuurId());
                attachedFactuurSet.add(factuurSetFactuurToAttach);
            }
            klant.setFactuurSet(attachedFactuurSet);
            Set<Account> attachedAccountSet = new HashSet<Account>();
            for (Account accountSetAccountToAttach : klant.getAccountSet()) {
                accountSetAccountToAttach = em.getReference(accountSetAccountToAttach.getClass(), accountSetAccountToAttach.getAccountId());
                attachedAccountSet.add(accountSetAccountToAttach);
            }
            klant.setAccountSet(attachedAccountSet);
            Set<KlantHasAdres> attachedKlantHasAdresSet = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSetKlantHasAdresToAttach : klant.getKlantHasAdresSet()) {
                klantHasAdresSetKlantHasAdresToAttach = em.getReference(klantHasAdresSetKlantHasAdresToAttach.getClass(), klantHasAdresSetKlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSet.add(klantHasAdresSetKlantHasAdresToAttach);
            }
            klant.setKlantHasAdresSet(attachedKlantHasAdresSet);
            em.persist(klant);
            for (Betaling betalingSetBetaling : klant.getBetalingSet()) {
                Klant oldKlantIdOfBetalingSetBetaling = betalingSetBetaling.getKlantId();
                betalingSetBetaling.setKlantId(klant);
                betalingSetBetaling = em.merge(betalingSetBetaling);
                if (oldKlantIdOfBetalingSetBetaling != null) {
                    oldKlantIdOfBetalingSetBetaling.getBetalingSet().remove(betalingSetBetaling);
                    oldKlantIdOfBetalingSetBetaling = em.merge(oldKlantIdOfBetalingSetBetaling);
                }
            }
            for (Bestelling bestellingSetBestelling : klant.getBestellingSet()) {
                Klant oldKlantidOfBestellingSetBestelling = bestellingSetBestelling.getKlantid();
                bestellingSetBestelling.setKlantid(klant);
                bestellingSetBestelling = em.merge(bestellingSetBestelling);
                if (oldKlantidOfBestellingSetBestelling != null) {
                    oldKlantidOfBestellingSetBestelling.getBestellingSet().remove(bestellingSetBestelling);
                    oldKlantidOfBestellingSetBestelling = em.merge(oldKlantidOfBestellingSetBestelling);
                }
            }
            for (Factuur factuurSetFactuur : klant.getFactuurSet()) {
                Klant oldKlantidOfFactuurSetFactuur = factuurSetFactuur.getKlantid();
                factuurSetFactuur.setKlantid(klant);
                factuurSetFactuur = em.merge(factuurSetFactuur);
                if (oldKlantidOfFactuurSetFactuur != null) {
                    oldKlantidOfFactuurSetFactuur.getFactuurSet().remove(factuurSetFactuur);
                    oldKlantidOfFactuurSetFactuur = em.merge(oldKlantidOfFactuurSetFactuur);
                }
            }
            for (Account accountSetAccount : klant.getAccountSet()) {
                Klant oldKlantidOfAccountSetAccount = accountSetAccount.getKlantid();
                accountSetAccount.setKlantid(klant);
                accountSetAccount = em.merge(accountSetAccount);
                if (oldKlantidOfAccountSetAccount != null) {
                    oldKlantidOfAccountSetAccount.getAccountSet().remove(accountSetAccount);
                    oldKlantidOfAccountSetAccount = em.merge(oldKlantidOfAccountSetAccount);
                }
            }
            for (KlantHasAdres klantHasAdresSetKlantHasAdres : klant.getKlantHasAdresSet()) {
                Klant oldKlantKlantIdOfKlantHasAdresSetKlantHasAdres = klantHasAdresSetKlantHasAdres.getKlantKlantId();
                klantHasAdresSetKlantHasAdres.setKlantKlantId(klant);
                klantHasAdresSetKlantHasAdres = em.merge(klantHasAdresSetKlantHasAdres);
                if (oldKlantKlantIdOfKlantHasAdresSetKlantHasAdres != null) {
                    oldKlantKlantIdOfKlantHasAdresSetKlantHasAdres.getKlantHasAdresSet().remove(klantHasAdresSetKlantHasAdres);
                    oldKlantKlantIdOfKlantHasAdresSetKlantHasAdres = em.merge(oldKlantKlantIdOfKlantHasAdresSetKlantHasAdres);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findKlant(klant.getKlantId()) != null) {
                throw new PreexistingEntityException("Klant " + klant + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Klant klant) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Klant persistentKlant = em.find(Klant.class, klant.getKlantId());
            Set<Betaling> betalingSetOld = persistentKlant.getBetalingSet();
            Set<Betaling> betalingSetNew = klant.getBetalingSet();
            Set<Bestelling> bestellingSetOld = persistentKlant.getBestellingSet();
            Set<Bestelling> bestellingSetNew = klant.getBestellingSet();
            Set<Factuur> factuurSetOld = persistentKlant.getFactuurSet();
            Set<Factuur> factuurSetNew = klant.getFactuurSet();
            Set<Account> accountSetOld = persistentKlant.getAccountSet();
            Set<Account> accountSetNew = klant.getAccountSet();
            Set<KlantHasAdres> klantHasAdresSetOld = persistentKlant.getKlantHasAdresSet();
            Set<KlantHasAdres> klantHasAdresSetNew = klant.getKlantHasAdresSet();
            List<String> illegalOrphanMessages = null;
            for (Bestelling bestellingSetOldBestelling : bestellingSetOld) {
                if (!bestellingSetNew.contains(bestellingSetOldBestelling)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bestelling " + bestellingSetOldBestelling + " since its klantid field is not nullable.");
                }
            }
            for (Factuur factuurSetOldFactuur : factuurSetOld) {
                if (!factuurSetNew.contains(factuurSetOldFactuur)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factuur " + factuurSetOldFactuur + " since its klantid field is not nullable.");
                }
            }
            for (Account accountSetOldAccount : accountSetOld) {
                if (!accountSetNew.contains(accountSetOldAccount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Account " + accountSetOldAccount + " since its klantid field is not nullable.");
                }
            }
            for (KlantHasAdres klantHasAdresSetOldKlantHasAdres : klantHasAdresSetOld) {
                if (!klantHasAdresSetNew.contains(klantHasAdresSetOldKlantHasAdres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KlantHasAdres " + klantHasAdresSetOldKlantHasAdres + " since its klantKlantId field is not nullable.");
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
            klant.setBetalingSet(betalingSetNew);
            Set<Bestelling> attachedBestellingSetNew = new HashSet<Bestelling>();
            for (Bestelling bestellingSetNewBestellingToAttach : bestellingSetNew) {
                bestellingSetNewBestellingToAttach = em.getReference(bestellingSetNewBestellingToAttach.getClass(), bestellingSetNewBestellingToAttach.getBestellingId());
                attachedBestellingSetNew.add(bestellingSetNewBestellingToAttach);
            }
            bestellingSetNew = attachedBestellingSetNew;
            klant.setBestellingSet(bestellingSetNew);
            Set<Factuur> attachedFactuurSetNew = new HashSet<Factuur>();
            for (Factuur factuurSetNewFactuurToAttach : factuurSetNew) {
                factuurSetNewFactuurToAttach = em.getReference(factuurSetNewFactuurToAttach.getClass(), factuurSetNewFactuurToAttach.getFactuurId());
                attachedFactuurSetNew.add(factuurSetNewFactuurToAttach);
            }
            factuurSetNew = attachedFactuurSetNew;
            klant.setFactuurSet(factuurSetNew);
            Set<Account> attachedAccountSetNew = new HashSet<Account>();
            for (Account accountSetNewAccountToAttach : accountSetNew) {
                accountSetNewAccountToAttach = em.getReference(accountSetNewAccountToAttach.getClass(), accountSetNewAccountToAttach.getAccountId());
                attachedAccountSetNew.add(accountSetNewAccountToAttach);
            }
            accountSetNew = attachedAccountSetNew;
            klant.setAccountSet(accountSetNew);
            Set<KlantHasAdres> attachedKlantHasAdresSetNew = new HashSet<KlantHasAdres>();
            for (KlantHasAdres klantHasAdresSetNewKlantHasAdresToAttach : klantHasAdresSetNew) {
                klantHasAdresSetNewKlantHasAdresToAttach = em.getReference(klantHasAdresSetNewKlantHasAdresToAttach.getClass(), klantHasAdresSetNewKlantHasAdresToAttach.getKlantHasAdresPK());
                attachedKlantHasAdresSetNew.add(klantHasAdresSetNewKlantHasAdresToAttach);
            }
            klantHasAdresSetNew = attachedKlantHasAdresSetNew;
            klant.setKlantHasAdresSet(klantHasAdresSetNew);
            klant = em.merge(klant);
            for (Betaling betalingSetOldBetaling : betalingSetOld) {
                if (!betalingSetNew.contains(betalingSetOldBetaling)) {
                    betalingSetOldBetaling.setKlantId(null);
                    betalingSetOldBetaling = em.merge(betalingSetOldBetaling);
                }
            }
            for (Betaling betalingSetNewBetaling : betalingSetNew) {
                if (!betalingSetOld.contains(betalingSetNewBetaling)) {
                    Klant oldKlantIdOfBetalingSetNewBetaling = betalingSetNewBetaling.getKlantId();
                    betalingSetNewBetaling.setKlantId(klant);
                    betalingSetNewBetaling = em.merge(betalingSetNewBetaling);
                    if (oldKlantIdOfBetalingSetNewBetaling != null && !oldKlantIdOfBetalingSetNewBetaling.equals(klant)) {
                        oldKlantIdOfBetalingSetNewBetaling.getBetalingSet().remove(betalingSetNewBetaling);
                        oldKlantIdOfBetalingSetNewBetaling = em.merge(oldKlantIdOfBetalingSetNewBetaling);
                    }
                }
            }
            for (Bestelling bestellingSetNewBestelling : bestellingSetNew) {
                if (!bestellingSetOld.contains(bestellingSetNewBestelling)) {
                    Klant oldKlantidOfBestellingSetNewBestelling = bestellingSetNewBestelling.getKlantid();
                    bestellingSetNewBestelling.setKlantid(klant);
                    bestellingSetNewBestelling = em.merge(bestellingSetNewBestelling);
                    if (oldKlantidOfBestellingSetNewBestelling != null && !oldKlantidOfBestellingSetNewBestelling.equals(klant)) {
                        oldKlantidOfBestellingSetNewBestelling.getBestellingSet().remove(bestellingSetNewBestelling);
                        oldKlantidOfBestellingSetNewBestelling = em.merge(oldKlantidOfBestellingSetNewBestelling);
                    }
                }
            }
            for (Factuur factuurSetNewFactuur : factuurSetNew) {
                if (!factuurSetOld.contains(factuurSetNewFactuur)) {
                    Klant oldKlantidOfFactuurSetNewFactuur = factuurSetNewFactuur.getKlantid();
                    factuurSetNewFactuur.setKlantid(klant);
                    factuurSetNewFactuur = em.merge(factuurSetNewFactuur);
                    if (oldKlantidOfFactuurSetNewFactuur != null && !oldKlantidOfFactuurSetNewFactuur.equals(klant)) {
                        oldKlantidOfFactuurSetNewFactuur.getFactuurSet().remove(factuurSetNewFactuur);
                        oldKlantidOfFactuurSetNewFactuur = em.merge(oldKlantidOfFactuurSetNewFactuur);
                    }
                }
            }
            for (Account accountSetNewAccount : accountSetNew) {
                if (!accountSetOld.contains(accountSetNewAccount)) {
                    Klant oldKlantidOfAccountSetNewAccount = accountSetNewAccount.getKlantid();
                    accountSetNewAccount.setKlantid(klant);
                    accountSetNewAccount = em.merge(accountSetNewAccount);
                    if (oldKlantidOfAccountSetNewAccount != null && !oldKlantidOfAccountSetNewAccount.equals(klant)) {
                        oldKlantidOfAccountSetNewAccount.getAccountSet().remove(accountSetNewAccount);
                        oldKlantidOfAccountSetNewAccount = em.merge(oldKlantidOfAccountSetNewAccount);
                    }
                }
            }
            for (KlantHasAdres klantHasAdresSetNewKlantHasAdres : klantHasAdresSetNew) {
                if (!klantHasAdresSetOld.contains(klantHasAdresSetNewKlantHasAdres)) {
                    Klant oldKlantKlantIdOfKlantHasAdresSetNewKlantHasAdres = klantHasAdresSetNewKlantHasAdres.getKlantKlantId();
                    klantHasAdresSetNewKlantHasAdres.setKlantKlantId(klant);
                    klantHasAdresSetNewKlantHasAdres = em.merge(klantHasAdresSetNewKlantHasAdres);
                    if (oldKlantKlantIdOfKlantHasAdresSetNewKlantHasAdres != null && !oldKlantKlantIdOfKlantHasAdresSetNewKlantHasAdres.equals(klant)) {
                        oldKlantKlantIdOfKlantHasAdresSetNewKlantHasAdres.getKlantHasAdresSet().remove(klantHasAdresSetNewKlantHasAdres);
                        oldKlantKlantIdOfKlantHasAdresSetNewKlantHasAdres = em.merge(oldKlantKlantIdOfKlantHasAdresSetNewKlantHasAdres);
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
                Integer id = klant.getKlantId();
                if (findKlant(id) == null) {
                    throw new NonexistentEntityException("The klant with id " + id + " no longer exists.");
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
            Klant klant;
            try {
                klant = em.getReference(Klant.class, id);
                klant.getKlantId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The klant with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Bestelling> bestellingSetOrphanCheck = klant.getBestellingSet();
            for (Bestelling bestellingSetOrphanCheckBestelling : bestellingSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Klant (" + klant + ") cannot be destroyed since the Bestelling " + bestellingSetOrphanCheckBestelling + " in its bestellingSet field has a non-nullable klantid field.");
            }
            Set<Factuur> factuurSetOrphanCheck = klant.getFactuurSet();
            for (Factuur factuurSetOrphanCheckFactuur : factuurSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Klant (" + klant + ") cannot be destroyed since the Factuur " + factuurSetOrphanCheckFactuur + " in its factuurSet field has a non-nullable klantid field.");
            }
            Set<Account> accountSetOrphanCheck = klant.getAccountSet();
            for (Account accountSetOrphanCheckAccount : accountSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Klant (" + klant + ") cannot be destroyed since the Account " + accountSetOrphanCheckAccount + " in its accountSet field has a non-nullable klantid field.");
            }
            Set<KlantHasAdres> klantHasAdresSetOrphanCheck = klant.getKlantHasAdresSet();
            for (KlantHasAdres klantHasAdresSetOrphanCheckKlantHasAdres : klantHasAdresSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Klant (" + klant + ") cannot be destroyed since the KlantHasAdres " + klantHasAdresSetOrphanCheckKlantHasAdres + " in its klantHasAdresSet field has a non-nullable klantKlantId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Betaling> betalingSet = klant.getBetalingSet();
            for (Betaling betalingSetBetaling : betalingSet) {
                betalingSetBetaling.setKlantId(null);
                betalingSetBetaling = em.merge(betalingSetBetaling);
            }
            em.remove(klant);
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

    public List<Klant> findKlantEntities() {
        return findKlantEntities(true, -1, -1);
    }

    public List<Klant> findKlantEntities(int maxResults, int firstResult) {
        return findKlantEntities(false, maxResults, firstResult);
    }

    private List<Klant> findKlantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Klant.class));
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

    public Klant findKlant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Klant.class, id);
        } finally {
            em.close();
        }
    }

    public int getKlantCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Klant> rt = cq.from(Klant.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
