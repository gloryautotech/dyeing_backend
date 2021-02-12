package com.main.glory.Dao;

import com.main.glory.model.supplier.GetAllSupplierRate;
import com.main.glory.model.supplier.Supplier;
import com.main.glory.model.supplier.SupplierRate;
import com.main.glory.model.supplier.responce.GetItemWithSupplier;
import com.main.glory.model.supplier.responce.ItemWithSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface SupplierRateDao extends JpaRepository<SupplierRate, Long> {
    List<SupplierRate> findBySupplierId(Long aLong);
    @Query("Select new com.main.glory.model.supplier.GetAllSupplierRate(q, (Select p.supplierName from Supplier p where p.id = q.supplierId)) from SupplierRate q where supplierId IS NOT NULL")
    List<GetAllSupplierRate> findWithSupplierName();


    @Query("Select new com.main.glory.model.supplier.responce.ItemWithSupplier(q.id as id,q.itemName as itemName,q.supplierId as supplierId,SUM(q.rate) as RATE) from SupplierRate q where supplierId IS NOT NULL GROUP BY  id,supplierId")
    List<ItemWithSupplier> findAllSupplierItem();


    Optional<SupplierRate> findByIdAndSupplierId(Long id,Long supplierId);

    @Query("select s.supplierName from Supplier s where s.id = (select r.supplierId from SupplierRate r where r.id=:itemId)")
    String getSupplierNameByItemId(Long itemId);

    @Query("select s from Supplier s where s.id = (select r.supplierId from SupplierRate r where r.id=:itemId )")
    Supplier getSupplierByItemId(Long itemId);



    @Query("select s from SupplierRate s where s.supplierId=:supplierId AND s.itemType=:itemType")
    List<SupplierRate> findItemBySupplierId(Long supplierId, String itemType);
}
