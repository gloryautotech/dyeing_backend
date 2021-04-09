package com.main.glory.controller;

import com.main.glory.Dao.PartyDao;
import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.StockDataBatchData.StockMast;
import com.main.glory.model.StockDataBatchData.response.GetAllBatch;
import com.main.glory.model.StockDataBatchData.response.GetAllBatchWithProduction;
import com.main.glory.model.basic.PartyQuality;
import com.main.glory.model.basic.QualityParty;
import com.main.glory.model.shade.requestmodals.GetShadeByPartyAndQuality;
import com.main.glory.servicesImpl.BasicServiceImpl;
import com.main.glory.servicesImpl.QualityServiceImp;
import com.main.glory.servicesImpl.ShadeServiceImpl;
import com.main.glory.servicesImpl.StockBatchServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BasicController extends ControllerConfig {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StockBatchServiceImpl stockBatchService;

    @Autowired
    private QualityServiceImp qualityServiceImp;

    @Autowired
    private PartyDao partyDao;

    @Autowired
    private BasicServiceImpl basicService;

    @Autowired
    ShadeServiceImpl shadeService;

    @GetMapping("/party/ByQuality/{id}")
    public ResponseEntity<GeneralResponse<QualityParty>> ByQuality(@PathVariable(value = "id") Long id) {
        GeneralResponse<QualityParty> result;
        try {

            // String quality="sndkjabn";
            QualityParty qualityParties = qualityServiceImp.getAllQualityWithParty(id);
            if (qualityParties != null) {
                result = new GeneralResponse<>(qualityParties, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            } else {
                result = new GeneralResponse<>(null, "No data found for given id", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/Quality/ByParty/{id}")
    public ResponseEntity<GeneralResponse<PartyQuality>> ByParty(@PathVariable(value = "id") Long partyId) {
        GeneralResponse<PartyQuality> result;
        try {

            // String quality="sndkjabn";
            PartyQuality partyQualities = qualityServiceImp.getAllPartyWithQuality(partyId);
            if (partyQualities != null) {
                result= new GeneralResponse<>(partyQualities, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            } else {
                result= new GeneralResponse<>(null, "No data found for given id", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }

        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/QualityAndParty/ByMaster/{userHeadId}")
    public ResponseEntity<GeneralResponse<List<PartyQuality>>> ByMaster(@PathVariable(value = "userHeadId") Long userHeadId) {
        GeneralResponse<List<PartyQuality>> result;
        try {

            List<PartyQuality> partyQualities = qualityServiceImp.getAllPartyWithQualityByMaster(userHeadId);
            if (partyQualities != null) {
                result= new GeneralResponse<>(partyQualities, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            } else {
                result =  new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

   /* @GetMapping("/batch/getBatchWithoutBillGenerated/")
    public GeneralResponse<List<GetAllBatch>> getAllBatchWithoutBillGenerated() {
        GeneralResponse<List<GetAllBatch>> response;
        try {
            List<GetAllBatch> batchDataList = stockBatchService.getAllBatchWithoutBillGenerated();
            if (batchDataList != null) {
                response= new GeneralResponse<>(batchDataList, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.FOUND);
            } else {
                response= new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }
    */


    //get all batch who's dipatch is not created yet
    @GetMapping("/batch/ByMaster/{userHeadId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatchWithProduction>>> GetBatchByMaster(@PathVariable(value = "userHeadId") Long userHeadId) {
        GeneralResponse<List<GetAllBatchWithProduction>> result;
        try {
            List<GetAllBatchWithProduction> batchDataList = stockBatchService.getAllBatchByMaster(userHeadId);
            if (batchDataList != null) {
                result =  new GeneralResponse<>(batchDataList, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            } else {
                result =  new GeneralResponse<>(null, "No data found ", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }


    @GetMapping("/shade/ByPartyIdAndQualityId/{partyId}/{qualityId}")
    public ResponseEntity<GeneralResponse<List<GetShadeByPartyAndQuality>>> getShadeByPartyAndQuality(@PathVariable(value = "partyId") Long partyId, @PathVariable(value = "qualityId") Long qualityId) {
        GeneralResponse<List<GetShadeByPartyAndQuality>> result;
        try {
            List<GetShadeByPartyAndQuality> shadeListByPartyAndQualities = shadeService.getAllShadeByPartyAndQuality(partyId, qualityId);
            if (shadeListByPartyAndQualities != null) {
                result= new GeneralResponse<>(shadeListByPartyAndQualities, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            } else {
                result =  new GeneralResponse<>(null, "No shade data found for given id", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new  GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }


    //batch by party id
    @GetMapping("/stockBatch/batchListByPartyWithoutProductionPlan/{partyId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>>> getBatchListByPartyWithoutProductionPlan(@PathVariable(value = "partyId") Long partyId) {

        GeneralResponse<List<GetAllBatch>> result;
        try {
            if (partyId != null) {
                List<GetAllBatch> stockMast = stockBatchService.getBatchListByPartyWithoutProductionPlan(partyId);
                if (stockMast != null) {
                    result =  new GeneralResponse<>(stockMast, "Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                } else {
                    result = new GeneralResponse<>(null, "no data found for party: " + partyId, false, System.currentTimeMillis(), HttpStatus.OK);
                }
            } else {
                result = new GeneralResponse<>(null, "Null id passed", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }

        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //batch by qualityId id
    @GetMapping("/stockBatch/batchListByQualityWithoutProductionPlan/{qualityId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>>> getBatchListByQualityWithoutProductionPlan(@PathVariable(value = "qualityId") Long qualityId) {

        GeneralResponse<List<GetAllBatch>> result;

        try {
            if (qualityId != null) {
                List<GetAllBatch> stockMast = stockBatchService.getBatchListByQualityWithoutProductionPlan(qualityId);
                if (stockMast != null) {
                    result = new GeneralResponse<>(stockMast, "Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                } else {
                    result= new GeneralResponse<>(null, "no data found for quality id: " + qualityId, false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
                }
            } else {
                result = new GeneralResponse<>(null, "Null id passed", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }

        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //get the list of stock who's all bathc are not plannned
    @GetMapping("/stockBatch/getAllStockWithoutPlan")
    public ResponseEntity<GeneralResponse<List<StockMast>>> getAllStockWithoutPlan() {
        GeneralResponse<List<StockMast>> result;
        try {
            List<StockMast> stockMast = stockBatchService.getAllStockWithoutPlan();
            if (stockMast != null) {
                result =  new GeneralResponse<>(stockMast, "Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            } else {
                result = new GeneralResponse<>(null, "no data found  "  , false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }


}


