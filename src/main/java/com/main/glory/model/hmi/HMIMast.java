package com.main.glory.model.hmi;

import com.main.glory.model.jet.JetData;
import com.main.glory.model.jet.request.JetStart;
import com.main.glory.model.productionPlan.ProductionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class HMIMast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String jetNo;
    String batchId;
    Double wt;
    String shadeNo;
    Boolean status;
    Date createdDate;
    Date updatedDate;
    Long createdBy;
    Long updatedBy;

    public HMIMast(String lotNo, String shadeno, Double wt, String jetNo) {
        this.batchId = lotNo;
        this.wt = wt;
        this.shadeNo = shadeno;
        this.jetNo=jetNo;

    }

    public HMIMast(ProductionPlan productionPlan, JetData jetDataExist, Double totalWt, JetStart records) {
        this.jetNo = String.valueOf(jetDataExist.getControlId());
        this.batchId = productionPlan.getBatchId();
        this.wt = totalWt;
        this.shadeNo = productionPlan.getShadeId()==null?"No Shade mention":String.valueOf(productionPlan.getShadeId());
        this.createdBy = records.getCreatedBy()==null?null:records.getCreatedBy();
        this.updatedBy = records.getUpdatedBy()==null?null: records.getUpdatedBy();
    }

    @PrePersist
    public void create()
    {
        this.createdDate = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    public void update()
    {
        this.updatedDate = new Date(System.currentTimeMillis());
    }

}
