package com.main.glory.model.purchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
@Getter
@Setter
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long supplierId;
    String supplierName;
    Long itemId;
    String itemName;
    Long userHeadId;
    Long createdBy;
    Long status;


}
