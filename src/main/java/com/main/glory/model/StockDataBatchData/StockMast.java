package com.main.glory.model.StockDataBatchData;

import com.main.glory.model.CommonModel.CommonField;
import com.main.glory.model.quality.Quality;
import com.main.glory.model.user.UserPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StockMast {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String stockInType;
    Long partyId;
    String billNo;
    Date billDate;
    Long chalNo;
    Date chalDate;
    String unit;
    String updatedBy;
    String createdBy;
    Boolean isProductionPlanned;
    @ApiModelProperty(hidden = true)
    Date createdDate;
    @ApiModelProperty(hidden = true)
    Date updatedDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "controlId", referencedColumnName = "id")
    List<Batch> batch;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qualityId", referencedColumnName = "id")
    Quality qualityId;


    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date(System.currentTimeMillis());
    }

}
