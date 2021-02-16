package com.main.glory.servicesImpl;

import com.main.glory.Dao.dyeingSlip.AdditionDyeingProcessSlipDao;
import com.main.glory.Dao.dyeingSlip.DyeingSlipDataDao;
import com.main.glory.Dao.dyeingSlip.DyeingSlipItemDataDao;
import com.main.glory.Dao.dyeingSlip.DyeingSlipMastDao;
import com.main.glory.model.dyeingSlip.AdditionDyeingProcessSlip;
import com.main.glory.model.dyeingSlip.DyeingSlipData;
import com.main.glory.model.dyeingSlip.DyeingSlipItemData;
import com.main.glory.model.dyeingSlip.DyeingSlipMast;
import com.main.glory.model.dyeingSlip.request.AddAdditionDyeingSlipModel;
import com.main.glory.model.dyeingSlip.request.AddAddtionalSlip;
import com.main.glory.model.dyeingSlip.request.SlipFormatData;
import com.main.glory.model.dyeingSlip.responce.GetAllAdditionalSlip;
import com.main.glory.model.productionPlan.ProductionPlan;
import com.main.glory.model.quality.response.GetQualityResponse;
import com.main.glory.model.shade.ShadeMast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dyeingSlipServiceImpl")
public class DyeingSlipServiceImpl {

    @Autowired
    DyeingSlipDataDao dyeingSlipDataDao;

    @Autowired
    DyeingSlipItemDataDao dyeingSlipItemDataDao;
    @Autowired
    AdditionDyeingProcessSlipDao additionDyeingProcessSlipDao;

    @Autowired
    ProductionPlanImpl productionPlanService;

    @Autowired
    DyeingSlipMastDao dyeingSlipMastDao;

    @Autowired
    StockBatchServiceImpl stockBatchService;

    @Autowired
    PartyServiceImp partyServiceImp;

    @Autowired
    QualityServiceImp qualityServiceImp;

    @Autowired
    ShadeServiceImpl shadeService;

    public void saveDyeingSlipMast(DyeingSlipMast dyeingSlipMast)
    {
        dyeingSlipMastDao.save(dyeingSlipMast);
    }

    public void updateDyeingSlip(DyeingSlipMast data) throws Exception {
        DyeingSlipMast dyeingSlipMast = dyeingSlipMastDao.getDyeingSlipById(data.getId());
        if(dyeingSlipMast==null)
            throw new Exception("no slip data found");

        dyeingSlipMastDao.saveAndFlush(data);

    }

    public SlipFormatData getDyeingSlipByBatchStockId(String batchId, Long productionId) throws Exception {

        DyeingSlipMast dyeingSlipMastExist = dyeingSlipMastDao.findByBatchIdAndProductionId(batchId, productionId);
        SlipFormatData slipFormatData = new SlipFormatData(dyeingSlipMastExist);

        ProductionPlan productionPlan = productionPlanService.getProductionData(dyeingSlipMastExist.getProductionId());
        GetQualityResponse quality=qualityServiceImp.getQualityByID(productionPlan.getQualityEntryId());
        Double wt = stockBatchService.getWtByControlAndBatchId(dyeingSlipMastExist.getStockId(), dyeingSlipMastExist.getBatchId());
        Optional<ShadeMast> shadeMast = shadeService.getShadeMastById(productionPlan.getShadeId());
        if(shadeMast.isEmpty())
            throw new Exception("no shade data found");


        slipFormatData.setColorTone(shadeMast.get().getColorTone());
        slipFormatData.setPartyShadeNo(shadeMast.get().getPartyShadeNo());
        slipFormatData.setTotalWt(wt);
        slipFormatData.setQualityId(quality.getQualityId());
        slipFormatData.setQualityEntryId(quality.getId());

        slipFormatData.setDyeingSlipDataList(dyeingSlipMastExist.getDyeingSlipDataList());


        return  slipFormatData;
    }

    public List<DyeingSlipMast> getAllDyeingSlip() throws Exception {

        List<DyeingSlipMast> dyeingSlipMast = dyeingSlipMastDao.getAllDyeingSlip();
        if(dyeingSlipMast.isEmpty())
            throw new Exception("no data found");
        return dyeingSlipMast;
    }

    public Boolean addAddtionalSlipData(AddAddtionalSlip addAdditionDyeingSlipModel) throws Exception {

        //AdditionDyeingProcessSlip toStoreSlip =new AdditionDyeingProcessSlip();
        try {

            DyeingSlipMast dyeingSlipMast = dyeingSlipMastDao.getDyeingSlipByProductionId(addAdditionDyeingSlipModel.getProductionId());
            if(dyeingSlipMast==null) {
                throw new Exception("no dyeing slip found for given batch or production");
            }


            DyeingSlipData dyeingSlipData = new DyeingSlipData(addAdditionDyeingSlipModel.getDyeingSlipData());
            dyeingSlipData.setControlId(dyeingSlipMast.getId());
            DyeingSlipData x = dyeingSlipDataDao.save(dyeingSlipData);

            List<DyeingSlipItemData> list = new ArrayList<>();
            for(DyeingSlipItemData d:addAdditionDyeingSlipModel.getDyeingSlipData().getDyeingSlipItemData())
            {
                d.setControlId(x.getId());
                list.add(d);

            }

            dyeingSlipItemDataDao.saveAll(list);
            return true;
        }catch (Exception e)
        {
            return false;
        }

    }

    public List<DyeingSlipMast> getAllAddtionalDyeignSlip() throws Exception {
        List<DyeingSlipMast> list =dyeingSlipMastDao.getAllAddtionalDyeingProcess();

        return list;
    }

    public DyeingSlipMast getAdditionalDyeingSlipById(Long id) throws Exception {

        DyeingSlipMast data = dyeingSlipMastDao.getAdditionalDyeingSlipById(id);

        return data;

    }

    public Boolean updateAddtionalDyeingSlip(AddAddtionalSlip addAdditionDyeingSlipModel) throws Exception {

        try {

           /* DyeingSlipMast dyeingSlipMast = dyeingSlipMastDao.getDyeingSlipByProductionId(addAdditionDyeingSlipModel.getProductionId());
            if(dyeingSlipMast==null) {
                throw new Exception("no dyeing slip found for given batch or production");
            }

            DyeingSlipData dyeingSlipData = new DyeingSlipData(addAdditionDyeingSlipModel.getDyeingSlipData());
            dyeingSlipData.setControlId(dyeingSlipMast.getId());
            //  dyeingSlipData.setDyeingSlipItemData(addAdditionDyeingSlipModel.getDyeingSlipData().getDyeingSlipItemData());
            DyeingSlipData x = dyeingSlipDataDao.saveAndFlush(dyeingSlipData);
*/
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;

        }


    }

    public Boolean deleteAdditionalDyeingSlipById(Long id) throws Exception {

        DyeingSlipMast existingDyeingSlip = dyeingSlipMastDao.getAdditionalDyeingSlipById(id);
        if(existingDyeingSlip==null)
            throw new Exception("no additonal slip found");

        dyeingSlipItemDataDao.deleteByDyeingSlipId(id);
        dyeingSlipDataDao.deleteAdditionalSlipDataByDyeingSlipId(id);
        return true;

    }
}
