package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.jet.request.AddJetData;
import com.main.glory.model.jet.JetMast;
import com.main.glory.model.jet.request.ChangeStatus;
import com.main.glory.model.jet.request.UpdateJetData;
import com.main.glory.model.jet.responce.GetAllJetMast;
import com.main.glory.model.jet.responce.GetJetData;
import com.main.glory.model.jet.responce.GetStatus;
import com.main.glory.servicesImpl.JetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JetController extends ControllerConfig {

    @Autowired
    JetServiceImpl jetService;

    @PostMapping(value="/addJet")
    public GeneralResponse<Boolean> saveJet(@RequestBody JetMast jetMast) throws Exception {
        if(jetMast==null)
        {
            return new GeneralResponse<Boolean>(false, "jet info is null", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        boolean flag;
        try {

            jetService.saveJet(jetMast);
            return new GeneralResponse<Boolean>(null, "Jet Data added successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/addJetData")
    public GeneralResponse<Boolean> saveJetData(@RequestBody List<AddJetData> jetData) throws Exception {
        if(jetData==null)
        {
            return new GeneralResponse<Boolean>(false, "jet info is null", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        boolean flag;
        try {

            jetService.saveJetData(jetData);
            return new GeneralResponse<Boolean>(null, "Jet Data added successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/updateJetData")
    public GeneralResponse<Boolean> updateJetData(@RequestBody List<UpdateJetData> jetDataToUpdate) throws Exception {
        if(jetDataToUpdate==null)
        {
            return new GeneralResponse<Boolean>(false, "jet info is null", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        boolean flag;
        try {

            jetService.updateJetData(jetDataToUpdate);
            return new GeneralResponse<Boolean>(null, "updated successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value="/updateJetData/productionStatus/")
    public GeneralResponse<Boolean> updateJetData(@RequestBody ChangeStatus jetDataToUpdate) throws Exception {
        if(jetDataToUpdate==null)
        {
            return new GeneralResponse<Boolean>(false, "jet info is null", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        boolean flag;
        try {

            jetService.updateProductionStatus(jetDataToUpdate);
            return new GeneralResponse<Boolean>(null, "updated successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value="/getJetData/{id}")
    public GeneralResponse<List<GetJetData>> getJetData(@PathVariable(name = "id") Long id) throws Exception {
        if(id==null)
        {
            return new GeneralResponse<>(null, "jet info is null", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        boolean flag;
        try {

            List<GetJetData> jetDataList = jetService.getJetData(id);
            return new GeneralResponse<>(jetDataList, "Jet Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/getAllJetDetail")
    public GeneralResponse<List<GetAllJetMast>> getAllJetData()  throws Exception {

        boolean flag;
        try {

            List<GetAllJetMast> jetMastList = jetService.getAllJetData();
            return new GeneralResponse<>(jetMastList, "Jet Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/getJetDataWithInQueueProdution/byJetId/{id}")
    public GeneralResponse<List<GetJetData>> getJetDataWithInQueueProdution(@PathVariable(name = "id") Long id) throws Exception {
        if(id==null)
        {
            return new GeneralResponse<>(null, "jet info is null", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        boolean flag;
        try {

            List<GetJetData> jetDataList = jetService.getJetDataWithInQueueProdution(id);
            return new GeneralResponse<>(jetDataList, "Jet Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/getJet/allStatusList")
    public GeneralResponse<List<GetStatus>> getJetStatusList() throws Exception {

        boolean flag;
        try {

            List<GetStatus> jetDataList = jetService.getJetStatusList();
            return new GeneralResponse<>(jetDataList, "Jet status list fetched successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }


}
