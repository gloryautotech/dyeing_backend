package com.main.glory.model.productionPlan.request;

import com.main.glory.model.productionPlan.ProductionPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllProductionWithShadeData extends ProductionPlan {

    String colorTone;

    public GetAllProductionWithShadeData(ProductionPlan productionPlan,String colorTone)
    {
        super(productionPlan);
        this.colorTone=colorTone;
    }

    public GetAllProductionWithShadeData(GetAllProductionWithShadeData g)
    {
        super(g);
        this.colorTone=g.getColorTone();
    }
}
