package com.main.glory.model.supplier.responce;

import com.main.glory.model.supplier.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetItemWithSupplier {
    Long supplierId;
    String supplierName;
    Long itemId;
    String itemName;


    public GetItemWithSupplier(Supplier supplier, ItemWithSupplier item) {
        this.supplierId=supplier.getId();
        this.supplierName=supplier.getSupplierName();
        this.itemId=item.id;
        this.itemName=item.itemName;
    }
}
