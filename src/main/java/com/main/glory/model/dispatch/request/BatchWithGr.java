package com.main.glory.model.dispatch.request;

import com.main.glory.model.StockDataBatchData.BatchData;
import com.main.glory.model.dispatch.response.GetBatchByInvoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BatchWithGr {
    String batchId;
    Long controlId;
    List<BatchData> batchDataList;

    public BatchWithGr(GetBatchByInvoice batch) {
        this.batchId=batch.getBatchId();
        this.controlId=batch.getStockId();
    }
}
