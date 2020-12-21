package com.main.glory.model.productionPlan;

import com.main.glory.model.jet.JetData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ProductionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String batchId;
    Long stockId;
    Long partyId;
    Long qualityEntryId;
    Long shadeId;

}
