package com.main.glory.controller;

import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.StockDataBatchData.response.StockQualityWise;
import com.main.glory.model.program.Program;
import com.main.glory.model.StockDataBatchData.response.GetAllBatchResponse;
import com.main.glory.model.program.request.AddProgramWithProgramRecord;
import com.main.glory.model.program.request.ShadeIdwithPartyShadeNo;
import com.main.glory.model.program.request.UpdateProgramWithProgramRecord;
import com.main.glory.model.program.response.GetAllProgram;
import com.main.glory.servicesImpl.ProgramServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController extends ControllerConfig {

    @Autowired
    private ProgramServiceImpl programServiceImpl;

    @PostMapping(value="/program")
    public GeneralResponse<Boolean> saveProgram(@RequestBody AddProgramWithProgramRecord program) throws Exception {
        if(program==null)
        {
            return new GeneralResponse<Boolean>(false, "Program id is null", true, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

        try {
                programServiceImpl.saveProgram(program);
                return new GeneralResponse<Boolean>(true, "Program added successfully", true, System.currentTimeMillis(), HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/program/all")
    public GeneralResponse<List<GetAllProgram>> getProgramList() throws Exception {
        try {
            if (programServiceImpl.getAllProgram() != null) {

                return new GeneralResponse<>(programServiceImpl.getAllProgram(), "Data found:", false, System.currentTimeMillis(), HttpStatus.OK);

            }
            return new GeneralResponse<>(null, "Data not found:", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

    }

    //getStock Quality wise Stock and it's qty
    @GetMapping(value="/program/StockQuality/{id}")
    public GeneralResponse<List<StockQualityWise>> getStockListByQualityId(@PathVariable(value = "id") Long id) throws Exception {

        try {
            if (id == null) {
                throw new Exception("Id can't be null");
            }
            else
            {
                List<StockQualityWise> stockQualityWiseList =  programServiceImpl.getAllStockByQuality(id);
                return new GeneralResponse<>(stockQualityWiseList, "Data fetched Succesully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);

        }

       // return programServiceImpl.getAllProgram();
    }

    @GetMapping(value="/program/{id}")
    public GeneralResponse<Program> getProgramDetailsById(@PathVariable(value = "id") Long id) throws Exception {
        if(id!=null)
        {
            Program programObject=programServiceImpl.getProgramById(id);
            if(programObject!=null)
            {
                return new GeneralResponse<>(programObject,"Data Get successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        }
        return new GeneralResponse<>(null, "Data not found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
    }

    //get partshade no with respected shadeId
    @GetMapping(value="/program/PartyShadeDetailPartyWise")
    public GeneralResponse<List<ShadeIdwithPartyShadeNo>> getShadeDetail() throws Exception {

        List<ShadeIdwithPartyShadeNo> listData=programServiceImpl.getShadeDetail();
            if(listData!=null)
            {
                return new GeneralResponse<>(listData,"Data Get successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        return new GeneralResponse<>(null, "Data not found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
    }


    @PutMapping(value="/program")
    public GeneralResponse<Boolean> updateProgram(@RequestBody UpdateProgramWithProgramRecord program) throws Exception
    {
        try
        {
            programServiceImpl.updateProgramByID(program);
            return new GeneralResponse<Boolean>(true, "Data updated sucessfully !", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e) {
            e.printStackTrace();
            return new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value="/program/{id}")
    public GeneralResponse<Boolean> deleteProgramDetailsByID(@PathVariable(value = "id") Long id) throws Exception {
        if(id!=null)
        {
            boolean flag=programServiceImpl.deleteProgramById(id);
            if(flag)
            {
                return new GeneralResponse<>(true,"Deleted Successfully",true,System.currentTimeMillis(),HttpStatus.OK);
            }
            else
            {
                return new GeneralResponse<>(false,"Data not found",false,System.currentTimeMillis(),HttpStatus.NOT_FOUND);
            }
        }
        return new GeneralResponse<>(false,"Data not deleted",false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value="/program/BatchData/{id}")
    public GeneralResponse<List<GetAllBatchResponse>> getBatchDetailByQualityId(@PathVariable(value = "id") String qualityId) throws Exception {
       try
       {
           if(qualityId!=null)
           {
               List<GetAllBatchResponse> flag=programServiceImpl.getAllBatchByQuality(qualityId);
               if(flag!=null)
               {
                   return new GeneralResponse<>(flag,"Batch Fetched Successfully",true,System.currentTimeMillis(),HttpStatus.OK);
               }
               else
               {
                   return new GeneralResponse<>(null,"Data not found",false,System.currentTimeMillis(),HttpStatus.NOT_FOUND);
               }
           }


       }
       catch (Exception e)
       {
           return new GeneralResponse<>(null,e.getMessage(),false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);
       }
        return null;
    }



}
