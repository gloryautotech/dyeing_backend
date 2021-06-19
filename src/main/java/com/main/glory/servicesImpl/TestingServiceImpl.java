package com.main.glory.servicesImpl;

import com.main.glory.Dao.StockAndBatch.BatchDao;
import com.main.glory.Dao.StockAndBatch.StockMastDao;
import com.main.glory.model.StockDataBatchData.BatchData;
import com.main.glory.model.StockDataBatchData.StockMast;
import com.main.glory.model.StockDataBatchData.request.AddStockBatch;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("testingServicecimpl")
@Transactional
public class TestingServiceImpl {

    @Autowired
    StockMastDao stockMastDao;

    @Autowired
    BatchDao batchDao;




    public void saveStockMast(AddStockBatch stockMast) throws Exception {

        StockMast data = new StockMast(stockMast);

        StockMast x = stockMastDao.save(data);

        x.setBatchData(stockMast.getBatchData());
        saveBatchData(x);


    }



    private void saveBatchData(StockMast x) throws Exception {
        List<BatchData> batchDataList = new ArrayList<>();
        for(BatchData batchData:x.getBatchData())
        {
            BatchData batchDataExistWithName = batchDao.getIsBatchId(batchData.getBatchId());

            if(batchDataExistWithName!=null)
                throw new Exception("batch id exist");

            batchData.setControlId(x.getId());
            batchDataList.add(batchData);
        }

        batchDao.saveAll(batchDataList);
    }
}
