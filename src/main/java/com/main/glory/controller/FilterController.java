package com.main.glory.controller;

import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.StockDataBatchData.StockMast;
import com.main.glory.model.StockDataBatchData.request.GetStockBasedOnFilter;
import com.main.glory.model.StockDataBatchData.response.GetAllStockWithoutBatches;
import com.main.glory.model.StockDataBatchData.response.GetBatchWithControlId;
import com.main.glory.model.dispatch.request.GetInvoiceBasedOnFilter;
import com.main.glory.model.dispatch.request.InvoiceWithBatch;
import com.main.glory.servicesImpl.BatchImpl;
import com.main.glory.servicesImpl.DispatchMastImpl;
import com.main.glory.servicesImpl.StockBatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FilterController extends ControllerConfig {

    @Autowired
    private BatchImpl batchService;

    @Autowired
    DispatchMastImpl dispatchMastService;

    @Autowired
    private StockBatchServiceImpl stockBatchService;


    //get batches without production plan
    @GetMapping("/stockBatch/batch/withoutProductionPlan/all")
    public ResponseEntity<GeneralResponse<List<GetBatchWithControlId>>> getAllBatchWithoutProductionPlan(){
        GeneralResponse<List<GetBatchWithControlId>> result;
        try{

            List<GetBatchWithControlId> stockMast = stockBatchService.getAllBatchWithoutProductionPlan();
            if(!stockMast.isEmpty()){
                result = new GeneralResponse<>(stockMast, "Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }else{
                result = new GeneralResponse<>(null, "no data found ", false, System.currentTimeMillis(), HttpStatus.OK);
            }

        }catch(Exception e){
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }

        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //get stock without batches
    @GetMapping("/stockBatch/batch/stockWithoutBatches/all")
    public ResponseEntity<GeneralResponse<List<GetAllStockWithoutBatches>>> stockWithoutBatches(){
        GeneralResponse<List<GetAllStockWithoutBatches>> result;
        try{

            List<GetAllStockWithoutBatches> stockMast = stockBatchService.getStockListWithoutBatches();
            if(!stockMast.isEmpty()){
                result = new GeneralResponse<>(stockMast, "Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }else{
                result = new GeneralResponse<>(null, "no data found ", false, System.currentTimeMillis(), HttpStatus.OK);
            }

        }catch(Exception e){
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    //get stock based on filter
    @PostMapping("/stockBatch/getStockBasedOnFilter")
    public ResponseEntity<GeneralResponse<List<StockMast>>> getStockBasedOnFilter(@RequestBody GetStockBasedOnFilter filter){
        GeneralResponse<List<StockMast>> result;
        try{

            List<StockMast> stockFiltersData = stockBatchService.getStockBasedOnFilter(filter);
            if(!stockFiltersData.isEmpty())
                result = new GeneralResponse<>(stockFiltersData, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            else
                result = new GeneralResponse<>(null, "no data found", false, System.currentTimeMillis(), HttpStatus.OK);

        }catch(Exception e){
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }

    //get invoice number based on filter
    /*@PostMapping("/dispatch/getInvoiceListBasedOnFilter")
    public GeneralResponse<List<InvoiceWithBatch>> getInvoiceListBasedOnFilter(@RequestBody GetInvoiceBasedOnFilter filter){
        try{

            List<InvoiceWithBatch> filterData = dispatchMastService.getInvoiceListBasedOnFilter(filter);
            if(!filterData.isEmpty())
                result = new GeneralResponse<>(filterData, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            else
                result = new GeneralResponse<>(null, "no data found", false, System.currentTimeMillis(), HttpStatus.OK);

        }catch(Exception e){
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }

    }*/



}
