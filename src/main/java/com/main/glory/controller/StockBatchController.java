package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.Constant;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.StockDataBatchData.BatchData;
import com.main.glory.model.StockDataBatchData.StockMast;
import com.main.glory.model.StockDataBatchData.request.*;
import com.main.glory.model.StockDataBatchData.response.*;
import com.main.glory.servicesImpl.BatchImpl;
import com.main.glory.servicesImpl.LogServiceImpl;
import com.main.glory.servicesImpl.StockBatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StockBatchController extends ControllerConfig {

    @Autowired
    private BatchImpl batchService;

    @Autowired
    private StockBatchServiceImpl stockBatchService;

    @Autowired
    LogServiceImpl logService;

    @Autowired
    HttpServletRequest request;

    @Value("${spring.application.debugAll}")
    Boolean debugAll=true;

    @GetMapping("/stockBatch/isBatchExists/{name}/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isBatchIdUnique(@PathVariable(value="name") String name, @PathVariable(value="id") Long id){
        GeneralResponse<Boolean, Object> result;
        try{
            Boolean isPresent = batchService.isBatchIdExists(name, id);
            if(isPresent)
            result = new GeneralResponse<>(isPresent, Constant.Batch_Id_Found+isPresent, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else {
                result = new GeneralResponse<>(isPresent, Constant.Batch_Id_Not_Found+isPresent, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        }catch (Exception e){
            e.printStackTrace();
            result = new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @PostMapping("/stockBatch")
    public ResponseEntity<GeneralResponse<Long,Object>> createBatch(@RequestBody AddStockBatch stockMast, @RequestHeader Map<String, String> headers) throws Exception {
        GeneralResponse<Long,Object> result = null;
        try {
            Long flag = stockBatchService.saveStockBatch(stockMast,headers.get("id"));
            if (flag != null)
                result = new GeneralResponse<>(flag, Constant.StockBatch_Added, true, System.currentTimeMillis(), HttpStatus.OK,stockMast);

            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,stockMast);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping("/stockBatch/getWtByStockIdAndBatchId/{batchId}/{stockId}")
    public ResponseEntity<GeneralResponse<WTByStockAndBatch,Object>> getWtByStockIdAndBatchId(@PathVariable(name = "batchId")String batchId,@PathVariable(name = "stockId")Long stockId) throws Exception {
        GeneralResponse<WTByStockAndBatch,Object> result;
        try {
            WTByStockAndBatch qty= stockBatchService.getWtByStockAndBatchId(stockId,batchId);

                result= new GeneralResponse<>(qty, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }



    @GetMapping("/stockBatch/batch/ByQualityAndParty/{qualityId}/{partyId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> getBatchById(@PathVariable(value = "qualityId") Long qualityId, @PathVariable(value = "partyId") Long partyId) {
        GeneralResponse<List<GetAllBatch>,Object> result;
        try {
            if (qualityId != null && partyId != null) {
                List<GetAllBatch> batchData = stockBatchService.getBatchByPartyAndQuality(qualityId, partyId);

                result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            } else {
                result = new GeneralResponse<>(null, Constant.Null_Record_Passed, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/all/{getBy}/{id}")
    public ResponseEntity<GeneralResponse<List<GetAllStockWithPartyNameResponse>,Object>> getAllStockBatch(@PathVariable(value = "getBy") String getBy, @PathVariable(value = "id") Long id) throws Exception {

        GeneralResponse<List<GetAllStockWithPartyNameResponse>,Object> result;

        try {
            List<GetAllStockWithPartyNameResponse> stockMast = null;
            switch (getBy) {
                case "own":
                    stockMast = stockBatchService.getAllStockBatch(getBy, id);
                    if (stockMast == null) {
                        result= new GeneralResponse<>(null, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    } else {
                        result= new GeneralResponse<>(stockMast, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    }
                    break;

                case "group":
                    stockMast = stockBatchService.getAllStockBatch(getBy, id);
                    if (stockMast == null) {
                        result= new GeneralResponse<>(null, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    } else {
                        result= new GeneralResponse<>(stockMast, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    }
                    break;

                case "all":
                    stockMast = stockBatchService.getAllStockBatch(null, null);
                    if (stockMast == null) {
                        result= new GeneralResponse<>(null, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    } else {
                        result= new GeneralResponse<>(stockMast, Constant.StockBatch_Not_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    }
                    break;
                default:
                    result= new GeneralResponse<>(null, Constant.GetBy_String_Wrong, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());


            }
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        logService.saveLog(result,request,debugAll);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/stockBatch/{id}")
    public ResponseEntity<GeneralResponse<StockMast,Object>> getStockMastById(@PathVariable(value = "id") Long id) {
        GeneralResponse<StockMast,Object> result;
        try {
            if (id != null) {
                Optional<StockMast> stockMast = stockBatchService.getStockBatchById(id);
                if (stockMast.isPresent()) {
                    result= new GeneralResponse<>(stockMast.get(), Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                } else {
                    result= new GeneralResponse<>(null, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                }
            } else {
                result= new GeneralResponse<>(null, Constant.Null_Record_Passed, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/getAllBatchWithoutFilter")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> getAllBatchWithoutFilter() {
        GeneralResponse<List<GetAllBatch>,Object> result;
        try {

            List<GetAllBatch> stockMast = stockBatchService.getAllBatchWithoutFilter();
            if (!stockMast.isEmpty()) {
                result= new GeneralResponse<>(stockMast, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(null, Constant.StockBatch_Not_Found , false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }

            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/batch/{controlId}/{batchId}")
    public ResponseEntity<GeneralResponse<List<BatchData>,Object>> getBatchById(@PathVariable(value = "batchId") String batchId, @PathVariable(value = "controlId") Long controlId) {
        GeneralResponse<List<BatchData>,Object> result;
        try {
            if (batchId != null) {
                List<BatchData> batchData = stockBatchService.getBatchById(batchId, controlId);

                result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            } else {
                result = new GeneralResponse<>(null, Constant.Null_Record_Passed, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/batch/ByQualityAndPartyWithoutProductionPlan/{qualityId}/{partyId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> ByQualityAndPartyWithoutProductionPlan(@PathVariable(value = "qualityId") Long qualityId, @PathVariable(value = "partyId") Long partyId,@RequestHeader Map<String, String> headers) {

        GeneralResponse<List<GetAllBatch>,Object> result;
        try {
            if (qualityId != null && partyId != null) {
                List<GetAllBatch> batchData = stockBatchService.byQualityAndPartyWithoutProductionPlan(qualityId, partyId,headers.get("id"));

                if(batchData.isEmpty())
                    result = new GeneralResponse<>(batchData, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                else
                result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            } else {
                result = new GeneralResponse<>(null, Constant.Null_Record_Passed, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/batch/ByQualityAndPartyWithProductionPlan/{qualityId}/{partyId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> ByQualityAndPartyWithProducctionPlan(@PathVariable(value = "qualityId") Long qualityId, @PathVariable(value = "partyId") Long partyId) {
        GeneralResponse<List<GetAllBatch>,Object> result;
        try {
            if (qualityId != null && partyId != null) {
                List<GetAllBatch> batchData = stockBatchService.byQualityAndPartyWithProductionPlan(qualityId, partyId);

                result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            } else {
                result= new GeneralResponse<>(null, Constant.Null_Record_Passed, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    //get complete stock and batch based on party id and qualityid
    @GetMapping("/stockBatch/stockBatchDataList/ByQualityAndParty/{qualityId}/{partyId}")
    public ResponseEntity<GeneralResponse<List<StockMast>,Object>> getStockBatchListById(@PathVariable(value = "qualityId") Long qualityId, @PathVariable(value = "partyId") Long partyId) {

        GeneralResponse<List<StockMast>,Object> result;
        try {
            if (qualityId != null && partyId != null) {
                List<StockMast> batchData = stockBatchService.getStockBatchListById(qualityId, partyId);

                result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            } else {
                result = new GeneralResponse<>(null, Constant.Null_Record_Passed, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/batch/all")
    public ResponseEntity<GeneralResponse<List<BatchToPartyAndQuality>,Object>> getAllBatch(@RequestHeader Map<String, String> headers) {
        GeneralResponse<List<BatchToPartyAndQuality>,Object> result;
        try {

            List<BatchToPartyAndQuality> batchData = stockBatchService.getAllBatchDetail(headers.get("id"));

            if(batchData.isEmpty())
                result = new GeneralResponse<>(batchData, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
            result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    //batch by quality
    @GetMapping("/stockBatch/batch/byQualityId/{id}")
    public ResponseEntity<GeneralResponse<List<BatchData>,Object>> getAllBatchByQualityId(@PathVariable(name = "id") Long qualityId) {
        GeneralResponse<List<BatchData>,Object> result;
        try {

            List<BatchData> batchData = stockBatchService.getAllBatchByQualityId(qualityId);

            result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/BatchToPartyAndQuality/{controlId}/{batchId}")
    public ResponseEntity<GeneralResponse<BatchToPartyAndQuality,Object>> getPartyAndQualityByBatch(@PathVariable(name = "controlId") Long controlId, @PathVariable(name = "batchId") String batchId) {
        GeneralResponse<BatchToPartyAndQuality,Object> result;
        try {

            BatchToPartyAndQuality batchData = stockBatchService.getPartyAndQualityByBatch(controlId, batchId);

            if(batchData!=null)
            result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result = new GeneralResponse<>(batchData, Constant.StockBatch_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/IsBatchAvailableWithId/{controlId}/{batchId}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> IsBatchAvailable(@PathVariable(name = "controlId") Long controlId, @PathVariable(name = "batchId") String batchId) {
        GeneralResponse<Boolean,Object> result;
        try {

            Boolean batchDataFlag = stockBatchService.IsBatchAvailable(controlId, batchId);

            if (batchDataFlag == true) {
                result = new GeneralResponse<>(true, Constant.Batch_Id_Not_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result = new GeneralResponse<>(false, Constant.Batch_Id_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            }
            logService.saveLog(result,request,debugAll);


        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }


    @GetMapping("/stockBatch/batchWithoutExtra/ByQualityAndParty/{qualityId}/{partyId}")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> getBatchWithoutProductionPlanById(@PathVariable(value = "qualityId") Long qualityId, @PathVariable(value = "partyId") Long partyId) {
        GeneralResponse<List<GetAllBatch>,Object> result;
        try {
            if (qualityId != null && partyId != null) {
                List<GetAllBatch> batchData = stockBatchService.getBatchWithoutProductionPlanByPartyAndQuality(qualityId, partyId);

                result = new GeneralResponse<>(batchData, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            } else {
                throw new Exception(Constant.Null_Record_Passed);
                //result =new GeneralResponse<>(null, "Null id passed", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }


    @PutMapping("/stockBatch")
    public ResponseEntity<GeneralResponse<Long,Object>> updateStockBatch(@RequestBody AddStockBatch stockMast,@RequestHeader Map<String, String> headers) {
        GeneralResponse<Long,Object> result;
        try {
            stockBatchService.updateBatch(stockMast,headers.get("id"));
            result = new GeneralResponse<>(stockMast.getId(), Constant.StockBatch_Updated, true, System.currentTimeMillis(), HttpStatus.OK,stockMast);
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,stockMast);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping("/stockBatch/MergeBatch")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateBatchMerge(@RequestBody List<MergeSplitBatch> batchData1) {
        GeneralResponse<Boolean,Object> result;
        try {
            stockBatchService.updateBatchForMerge(batchData1);
            result = new GeneralResponse<>(true, Constant.StockBatch_Updated, true, System.currentTimeMillis(), HttpStatus.OK,batchData1);
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,batchData1);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping("/stockBatch/SplitBatch")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateBatchSplit(@RequestBody List<MergeSplitBatch> batchData1) {
        GeneralResponse<Boolean,Object> result;
        try {
            stockBatchService.updateBatchSplit(batchData1);
            result = new GeneralResponse<>(true, Constant.StockBatch_Updated, true, System.currentTimeMillis(), HttpStatus.OK,batchData1);
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,batchData1);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode())) ;
    }


    @DeleteMapping("/stockBatch/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteStockBatch(@PathVariable(value = "id") Long id) {
        GeneralResponse<Boolean,Object> result;
        try {
            stockBatchService.deleteStockBatch(id);
            result = new GeneralResponse<>(true, Constant.StockBatch_Deleted, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping("/batchGr/delete/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteBatchGr(@PathVariable(value = "id") Long id) {
        GeneralResponse<Boolean,Object> result;
        try {
            stockBatchService.deleteBatchGr(id);
            result = new GeneralResponse<>(true, Constant.Batch_Data_Deleted, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping("/stockBatch/delete/{controlId}/{batchId}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteBatchByControlIdAndBatchID(@PathVariable(name = "controlId") Long controlId, @PathVariable(name = "batchId") String batchId) {
        GeneralResponse<Boolean,Object> result;
        try {
            Boolean flag = stockBatchService.deleteStockBatchWithControlAndBatchID(controlId, batchId);

            if (flag == true)
                result= new GeneralResponse<>(true, Constant.Batch_Data_Deleted, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result = new GeneralResponse<>(false, Constant.Batch_Data_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    @GetMapping("/stockBatch/getAllBatchForFinishMtr")
    public ResponseEntity<GeneralResponse<List<GetAllBatchWithProduction>,Object>> getAllBatchWithoutBillGenerated(@RequestHeader Map<String, String> headers) {
        GeneralResponse<List<GetAllBatchWithProduction>,Object> response;

        try {
            List<GetAllBatchWithProduction> flag = stockBatchService.getAllBatchWithoutBillGenerated(headers.get("id"));

            if (!flag.isEmpty())
                response= new GeneralResponse<>(flag, Constant.Batch_Data_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                response= new GeneralResponse<>(flag, Constant.Batch_Data_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(response,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            response= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(response,request,true);
        }
        return new ResponseEntity<>(response,HttpStatus.valueOf(response.getStatusCode()));

    }




    //gt the job card detail by batch and stock Id
    @GetMapping("/stockBatch/get/getJobCardBy")
    public ResponseEntity<GeneralResponse<JobCard,Object>> getJobCardByStockIdAndBatchId(@RequestParam(name = "batchId")String batchId, @RequestParam(name = "stockId")Long stockId) throws Exception {
        GeneralResponse<JobCard,Object> result;
        try {
            if(batchId.isEmpty() || stockId==null)
                throw new Exception(Constant.Null_Record_Passed);

            JobCard qty= stockBatchService.getJobCardByStockIdAndBatchId(stockId,batchId);

            result= new GeneralResponse<>(qty, Constant.StockBatch_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        } catch (Exception e) {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }



    //get all batch for additional slip
    @GetMapping("/stockBatch/batch/forAdditionalSlip")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> getBatchForAdditionalSlip(@RequestHeader Map<String, String> headers) {
        GeneralResponse<List<GetAllBatch>,Object> result;
        try {

                List<GetAllBatch> batchData = stockBatchService.getAllBatchForAdditionalSlip(headers.get("id"));

                if(batchData.isEmpty())
                    result = new GeneralResponse<>(batchData, Constant.Batch_Data_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                else
                result = new GeneralResponse<>(batchData, Constant.Batch_Data_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);


        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }


    //get all batch for additional slip
    @GetMapping("/stockBatch/batch/forRedyeingSlip")
    public ResponseEntity<GeneralResponse<List<GetAllBatch>,Object>> getBatchForReDyeingByPartyAndQualityId(@RequestHeader Map<String, String> headers) {
        GeneralResponse<List<GetAllBatch>,Object> result;
        try {

            List<GetAllBatch> batchData = stockBatchService.getAllBatchForRedyeingSlip(headers.get("id"));

            result = new GeneralResponse<>(batchData, Constant.Batch_Data_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }



}
