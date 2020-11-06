package com.main.glory.Dao;

import com.main.glory.model.supplier.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface SupplierDao extends JpaRepository<Supplier, Long> {

    @Query("select new com.main.glory.model.supplier.Supplier(s.id, s.supplierName, s.discountPercentage, s.gstPercentage, s.userId, s.remark, s.createdBy, s.createdDate, s.updatedDate, s.paymentTerms, s.updatedBy) from Supplier s")
    List<Supplier> findAllWithoutRates();

    Optional<Supplier> findById(Long id);

}
