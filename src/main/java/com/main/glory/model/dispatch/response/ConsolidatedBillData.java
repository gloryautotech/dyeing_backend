package com.main.glory.model.dispatch.response;

import com.main.glory.model.dispatch.DispatchMast;
import com.main.glory.model.party.Party;
import com.main.glory.model.quality.response.GetQualityResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsolidatedBillData {
    String batchId;
    Date invoiceDate;
    String partyName;
    String qualityName;
    Long pcs;
    Double totalMtr;
    Double totalFinishMtr;
    Double rate;
    Double amt;


    public ConsolidatedBillData(Party party, GetQualityResponse quality, String batchId, Long pcs, Double totalBatchMtr, Double totalFinishMtr, Double amt, Double rate, DispatchMast dispatchMast) {

        this.batchId = batchId;
        this.invoiceDate = dispatchMast.getCreatedDate();
        this.partyName = party.getPartyName();
        this.qualityName = quality.getQualityName();
        this.pcs =pcs;
        this.totalMtr = totalBatchMtr;
        this.totalFinishMtr = totalFinishMtr;
        this.rate = rate;
        this.amt = amt;
    }
}
