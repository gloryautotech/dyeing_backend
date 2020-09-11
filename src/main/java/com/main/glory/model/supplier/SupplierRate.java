package com.main.glory.model.supplier;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "supplier_rate")
@Getter
@Setter
@AllArgsConstructor
public class SupplierRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    Long id;
    Long supplier_id;
    String item_name;
    Double rate;
    @ApiModelProperty(hidden = true)
    Double discounted_rate;

    @ApiModelProperty(hidden = true)
    Double gst_rate;

    Long user_id;

    @ApiModelProperty(hidden = true)
    Date created_date;
    String created_by;

    @ApiModelProperty(hidden = true)
    Boolean is_active;
    String updated_by;

    @ApiModelProperty(hidden = true)
    Date updated_date;

    public SupplierRate() {
        this.is_active = true;
    }


}

