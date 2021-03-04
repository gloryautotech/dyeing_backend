package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.dyeingSlip.DyeingSlipData;
import com.main.glory.model.dyeingSlip.DyeingSlipMast;
import com.main.glory.model.dyeingSlip.request.AddAdditionDyeingSlipModel;
import com.main.glory.model.dyeingSlip.request.AddAddtionalSlip;
import com.main.glory.model.dyeingSlip.request.SlipFormatData;
import com.main.glory.model.dyeingSlip.responce.GetAllAdditionalDyeingSlip;
import com.main.glory.model.dyeingSlip.responce.GetAllAdditionalSlip;
import com.main.glory.servicesImpl.DyeingSlipServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DyeingSlipController extends ControllerConfig {

    @Autowired
    DyeingSlipServiceImpl dyeingSlipService;

    @PutMapping("/dyeingSlip")
    public ResponseEntity<GeneralResponse<Boolean>> updateDyeingSlip(@RequestBody DyeingSlipMast data){
        GeneralResponse<Boolean> result;
        try {
            if(data == null){
                result = new GeneralResponse<>(false, "No data passed, please send valid data", false, System.currentTimeMillis(), HttpStatus.OK);
            }else {
                dyeingSlipService.updateDyeingSlip(data);
                result = new GeneralResponse<>(true, "Data updated Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/dyeingSlip/{batchId}/{productionId}")
    public ResponseEntity<GeneralResponse<SlipFormatData>> getDyeingSlipByBatchIdProductionId(@PathVariable(name = "batchId") String batchId, @PathVariable(name = "productionId") Long productionId){
        GeneralResponse<SlipFormatData> result;
        try {
            if(batchId == null || productionId == null){
                result = new GeneralResponse<>(null, "No data passed, please send valid data", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
            }else {
                SlipFormatData data = dyeingSlipService.getDyeingSlipByBatchStockId(batchId, productionId);
                if(data!=null)
                result = new GeneralResponse<>(data, "Data fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                else
                    result = new GeneralResponse<>(null, "data not found for given id", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/dyeingSlip/all")
    public ResponseEntity<GeneralResponse<List<DyeingSlipMast>>> getAllDyeingSlip(){
        GeneralResponse<List<DyeingSlipMast>> result;
        try {

                List<DyeingSlipMast> data = dyeingSlipService.getAllDyeingSlip();
                if(data!=null)
                    result = new GeneralResponse<>(data, "Data fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                else
                    result = new GeneralResponse<>(null, "data not found", false, System.currentTimeMillis(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/dyeingSlip/additionalDyeingslip/all")
    public ResponseEntity<GeneralResponse<List<GetAllAdditionalDyeingSlip>>> getAllAddtionalDyeignSlip(){
        GeneralResponse<List<GetAllAdditionalDyeingSlip>> result;
        try {

            List<GetAllAdditionalDyeingSlip> data = dyeingSlipService.getAllAddtionalDyeignSlip();
            if(!data.isEmpty())
                result = new GeneralResponse<>(data, "Data fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            else
                result = new GeneralResponse<>(null, "data not found", false, System.currentTimeMillis(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //add addition dyeing slip
    @PostMapping("/dyeingSlip/add/additionalDyeingSlip/")
    public ResponseEntity<GeneralResponse<Boolean>> addAddtionalDyeingSlip(@RequestBody AddAddtionalSlip addAdditionDyeingSlipModel){
        GeneralResponse<Boolean> result;
        try {
            if(addAdditionDyeingSlipModel ==null)
            result = new GeneralResponse<>(false,"info can't be null",false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);

            dyeingSlipService.addAddtionalSlipData(addAdditionDyeingSlipModel);
            result = new GeneralResponse<>(true, "Data added Successfully", true, System.currentTimeMillis(), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //update addition dyeing slip
    @PutMapping("/dyeingSlip/update/additionalDyeingSlip/")
    public ResponseEntity<GeneralResponse<Boolean>> updateAddtionalDyeingSlip(@RequestBody AddAddtionalSlip addAdditionDyeingSlipModel){
        GeneralResponse<Boolean> result;
        try {
            if(addAdditionDyeingSlipModel ==null)
                result = new GeneralResponse<>(false,"info can't be null",false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);

           dyeingSlipService.updateAddtionalDyeingSlip(addAdditionDyeingSlipModel);

           result = new GeneralResponse<>(true, "Data updated Successfully", true, System.currentTimeMillis(), HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //get addition dyeing slip
    @GetMapping("/dyeingSlip/getAdditionalDyeingSlipBy/{id}")
    public ResponseEntity<GeneralResponse<GetAllAdditionalDyeingSlip>> getAdditionalDyeingSlipBy(@PathVariable(name = "id") Long id){
        GeneralResponse<GetAllAdditionalDyeingSlip> result;
        try {
            if(id ==null)
                result = new GeneralResponse<>(null,"info can't be null",false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);

            GetAllAdditionalDyeingSlip data = dyeingSlipService.getAdditionalDyeingSlipById(id);
            if(data!=null)
                result = new GeneralResponse<>(data, "Data fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            else
                result = new GeneralResponse<>(data, "data not found", false, System.currentTimeMillis(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @DeleteMapping("/dyeingSlip/deleteAdditionalDyeingSlipBy/{id}")
    public ResponseEntity<GeneralResponse<Boolean>> deleteAdditionalDyeingSlipBy(@PathVariable(name = "id") Long id){
        GeneralResponse<Boolean> result;
        try {
            if(id ==null)
                result = new GeneralResponse<>(null,"info can't be null",false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);

            Boolean data = dyeingSlipService.deleteAdditionalDyeingSlipById(id);
            if(data)
                result = new GeneralResponse<>(data, "Data deleted Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            else
                result = new GeneralResponse<>(data, "data not found", false, System.currentTimeMillis(), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            result=  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }






}
