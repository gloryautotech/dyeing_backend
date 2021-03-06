package com.main.glory.model.paymentTerm;

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
public class PaymentData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    Long id;
    Long controlId;
    Double payAmt;
    Date createdDate;
    Long no;
    String paymentDate;
    Boolean status;
    Long payTypeId;
    String bank;
    String remark;


    @PrePersist
    protected void onCreate(){  this.createdDate=new Date(System.currentTimeMillis());  }


}
