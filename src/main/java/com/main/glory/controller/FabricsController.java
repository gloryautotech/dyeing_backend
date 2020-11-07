package com.main.glory.controller;

import java.util.List;

import com.main.glory.Lookup.FabInMasterLookUp.MasterLookUpPartyRecord;
import com.main.glory.Lookup.FabInMasterLookUp.MasterLookUpWithRecord;
import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.fabric.FabStockData;
import com.main.glory.model.fabric.FabStockMast;
import com.main.glory.servicesImpl.PartyServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.main.glory.servicesImpl.FabricsServiceImpl;

@RestController
@RequestMapping("/api")
public class FabricsController extends ControllerConfig {

    @Autowired
    private FabricsServiceImpl fabricsServiceImpl;
    @Autowired
    private PartyServiceImp partyServiceImp;

    @PostMapping("/fabric")
    public GeneralResponse<Boolean> addFabricIn(@RequestBody FabStockMast fabStockMast) throws Exception {
        try {
            fabricsServiceImpl.saveFabrics(fabStockMast);
            return new GeneralResponse<>(true, "Data Saved Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            String msg = e.getMessage();
            String cause = e.getCause().getMessage();
            if(cause.equals("BR") || msg.contains("null"))
                return new GeneralResponse<Boolean>(false, msg, false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
            return new GeneralResponse<Boolean>(false, msg, false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fabrics/all")
    public GeneralResponse<List<MasterLookUpWithRecord>> getFabList() {
        try {
            var x = fabricsServiceImpl.getFabStockMasterListRecord();

            return new GeneralResponse<>(x, "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new GeneralResponse<>(null, "Internal Server Error", false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/fabric/{id}")
    public GeneralResponse<FabStockMast> getFabStockDataById(@PathVariable(value = "id") Long id) {

        if (id != null) {
            var fabData = fabricsServiceImpl.getFabRecordById(id);
            if (!fabData.isEmpty()) {
                return new GeneralResponse<>(fabData.get(), "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
            } else
                return new GeneralResponse<>(null, "No such id", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
        } else
            return new GeneralResponse<>(null, "Null Id Passed!", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/fabric")
    public GeneralResponse<Boolean> updateFabricIn(@RequestBody FabStockMast fabStockMast) throws Exception {
//        if (fabrics != null) {
//            boolean flag = fabricsServiceImpl.updateFabricsDetails(fabrics);
//            if (flag) {
//            }
//        }
//        return new GeneralResponse<Boolean>(false, "Null Party Object", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        try {
            fabricsServiceImpl.updateFabric(fabStockMast);
            return new GeneralResponse<Boolean>(true, "updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);
        } catch (Exception e) {
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/fabric/{id}")
    public GeneralResponse<Boolean> deleteFabDetailsByID(@PathVariable(value = "id") Long id) {
        try{
            fabricsServiceImpl.deleteFabricsById(id);
            return new GeneralResponse<>(true, "deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK);
        } catch (Exception e) {
            return new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/fabric/gr/{quality_id}")
    public GeneralResponse<List<FabStockData>> getGrListByQualityId(@PathVariable(value= "quality_id") Long id){
        try{
            var grData = fabricsServiceImpl.getGrList(id);
            System.out.println(grData);
            if(grData != null){
                return new GeneralResponse<List<FabStockData>>(grData, "fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
            else{
                return new GeneralResponse<>(null, "no fabric data for given id", true, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }
}
