package com.example.bilabonnement.damageDepartment.forms.damageReportForm.repository;

import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class DamageReportRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public DamageReport findById(Integer id) {
        return id == null ? null : entityManager.find(DamageReport.class, id);
    }



//Seneste rapport-ID for et lease, som HAR mindst ét item.

    public Integer findLatestWithItemsIdByLeaseId(Integer leaseId) {
        if (leaseId == null) return null;
        return entityManager.createQuery(
                "select max(dr.damageReportId) " +
                        "from DamageReport dr " +
                        "where dr.leasingContractId = :leaseId " +
                        "and exists (select 1 from DamageItem di where di.damageReport = dr)",
                Integer.class
        ).setParameter("leaseId", leaseId).getSingleResult();
    }



//Seneste rapport-ID for et lease (kan være tom).

    public Integer findLatestIdByLeaseId(Integer leaseId) {
        if (leaseId == null) return null;
        return entityManager.createQuery(
                "select max(dr.damageReportId) " +
                        "from DamageReport dr " +
                        "where dr.leasingContractId = :leaseId",
                Integer.class
        ).setParameter("leaseId", leaseId).getSingleResult();
    }



//Hent én rapport + dens items (fetch join).

    public DamageReport findWithItemsById(Integer id) {
        if (id == null) return null;
        List<DamageReport> list = entityManager.createQuery(
                "select distinct dr from DamageReport dr " +
                        "left join fetch dr.damageItems di " +
                        "where dr.damageReportId = :id",
                DamageReport.class
        ).setParameter("id", id).getResultList();
        return list.isEmpty() ? null : list.get(0);
    }


//Vi opdaterer rapporten

    public DamageReport save(DamageReport report) {
        if (report.getDamageReportId() == null) {
            entityManager.persist(report);     // NY → får id
            return report;
        } else {
            return entityManager.merge(report);
        }
    }

}
