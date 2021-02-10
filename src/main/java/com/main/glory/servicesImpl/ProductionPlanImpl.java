package com.main.glory.servicesImpl;

import com.main.glory.Dao.StockAndBatch.BatchDao;
import com.main.glory.Dao.productionPlan.ProductionPlanDao;
import com.main.glory.model.StockDataBatchData.BatchData;
import com.main.glory.model.productionPlan.request.GetAllProductionWithShadeData;
import com.main.glory.model.productionPlan.ProductionPlan;
import com.main.glory.model.quality.Quality;
import com.main.glory.model.shade.ShadeMast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("productionPlanServiceImpl")
public class ProductionPlanImpl {

    @Autowired
    ProductionPlanDao productionPlanDao;

    @Autowired
    QualityServiceImp qualityServiceImp;

    @Autowired
    PartyServiceImp partyServiceImp;

    @Autowired
    ShadeServiceImpl shadeService;

    @Autowired
    StockBatchServiceImpl stockBatchService;

    @Autowired
    BatchImpl batchService;

    @Autowired
    BatchDao batchDao;

    public Long saveProductionPlan(ProductionPlan productionPlan) throws Exception {

        Optional<Quality> qualityIsExist= qualityServiceImp.getQualityByIDAndPartyId(productionPlan.getQualityEntryId(),productionPlan.getPartyId());

        Optional<ShadeMast> shadeMastExist=shadeService.getShadeMastById(productionPlan.getShadeId());

        if(qualityIsExist.isPresent() && shadeMastExist.isPresent())
        {
            List<BatchData> batchDataList = batchService.getBatchById(productionPlan.getBatchId(),productionPlan.getStockId());
            if(batchDataList.isEmpty())
                throw new Exception("No batch data found");

            //ProductionPlan shadeAndStockIsExist = productionPlan.findByStockIdAndShadeId(productionPlan.)

            for(BatchData batchData:batchDataList)
            {
                if(batchData.getIsProductionPlanned()==false)
                    batchData.setIsProductionPlanned(true);
                batchDao.save(batchData);
            }
            productionPlanDao.save(productionPlan);
            return productionPlan.getId();

        }

        else
            throw new Exception("unable to insert the record");

    }
    @Transactional
    public ProductionPlan getProductionData(Long id) throws Exception{
        Optional<ProductionPlan> productionPlan = productionPlanDao.getByProductionId(id);

        if(productionPlan.isEmpty())
            throw new Exception("data not found for production:");

        return productionPlan.get();

    }

    public Boolean deleteById(Long id) {
        Optional<ProductionPlan> productionPlan = productionPlanDao.findById(id);
        if (productionPlan.isEmpty())
            return false;

        productionPlanDao.deleteById(id);
        return true;

    }

    public List<GetAllProductionWithShadeData> getAllProductionData() throws Exception{

        Optional<List<GetAllProductionWithShadeData>> productionWithShadeData = productionPlanDao.getAllProductionWithColorTone();
        if(productionWithShadeData.isEmpty())
            throw new Exception("no data found");

        return productionWithShadeData.get();


    }

    public void updateProductionPlan(ProductionPlan productionPlan) throws Exception{

        Optional<ProductionPlan> productionPlanExist = productionPlanDao.findById(productionPlan.getId());
        if(productionPlanExist.isEmpty())
            throw new Exception("no data found");

        productionPlanDao.save(productionPlan);

    }

    public List<GetAllProductionWithShadeData> getAllProductionDataWithAndWithoutPlan() throws Exception {
        Optional<List<GetAllProductionWithShadeData>> productionWithShadeData = productionPlanDao.getAllProduction();
        if(productionWithShadeData.isEmpty())
            throw new Exception("no data found");


        return productionWithShadeData.get();


    }

    //list of production who are not added yet into the jet
    public List<ProductionPlan> getAllProductionListByPartyAndQuality(Long partyId, Long qualityEntryId) {
        List<ProductionPlan> list = productionPlanDao.getProductionByPartyAndQuality(partyId,qualityEntryId);
        return list;

    }

    public ProductionPlan getProductionDataByBatchAndStock(String batchId, Long controlId) {
        ProductionPlan productionPlan=productionPlanDao.getProductionByBatchAndStockId(batchId,controlId);
        return productionPlan;
    }

/*
    public List<BatchData> getAllBatch(Long partyId, Long qualityEntryId, String batchId) {

    }*/
}
