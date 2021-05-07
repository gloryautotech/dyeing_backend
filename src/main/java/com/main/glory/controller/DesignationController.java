package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.ConstantFile;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.designation.Designation;
import com.main.glory.servicesImpl.DesignationServiceImpl;
import com.main.glory.servicesImpl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DesignationController extends ControllerConfig {

    @Autowired
    LogServiceImpl logService;

    ConstantFile constantFile;

    @Autowired
    HttpServletRequest request;

    @Value("${spring.application.debugAll}")
    Boolean debugAll=true;

    @Autowired
    private DesignationServiceImpl designationService;

    @PostMapping("/user/designation")
    public ResponseEntity<GeneralResponse<Boolean,Object>> saveDesignation(@RequestBody Designation designationData) throws Exception{

        GeneralResponse<Boolean,Object> result;
        try{
            int flag = designationService.createDesignation(designationData);
            if(flag==1){
                result = new GeneralResponse<>(true, constantFile.Designation_Added, true, System.currentTimeMillis(), HttpStatus.OK,designationData);
            }
            else
            {
                result = new GeneralResponse<>(true, constantFile.Designation_Not_Added, true, System.currentTimeMillis(), HttpStatus.OK,designationData);
            }
            logService.saveLog(result,request,debugAll);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,designationData);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping(value="/user/designation/deleteTable/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isDesignationDeletable(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        try {

            Boolean flag = designationService.getDesignationIsDelatable(id);
            if(flag)
                result = new GeneralResponse<>(flag, constantFile.User_Exist, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI()+"?"+request.getQueryString());
            else
                result = new GeneralResponse<>(flag, constantFile.Designation_Deletable, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI()+"?"+request.getQueryString());

            logService.saveLog(result,request,debugAll);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI()+"?"+request.getQueryString());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @GetMapping("/user/designation")
    public ResponseEntity<GeneralResponse<List<Designation>,Object>> getDesignation() throws Exception{

        GeneralResponse<List<Designation>,Object> result;
        try{
            List<Designation> data = designationService.getDesignation();
            if(!data.isEmpty()){
                result = new GeneralResponse<>(data, constantFile.Designation_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI()+"?"+request.getQueryString());
            }
            else
            {
                result = new GeneralResponse<>(data, constantFile.Designation_Not_Found, false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI()+"?"+request.getQueryString());
            }
            logService.saveLog(result,request,debugAll);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI()+"?"+request.getQueryString());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @DeleteMapping("/user/designation/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteDesignationById(@PathVariable(name = "id") Long id) throws Exception{

        GeneralResponse<Boolean,Object> result;
        try{
            Boolean flag = designationService.deleteDesignationById(id);
            if(flag){
                result = new GeneralResponse<>(flag, constantFile.Designation_Deleted, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI()+"?"+request.getQueryString());
            }
            else
            {
                result = new GeneralResponse<>(flag, constantFile.Designation_Not_Found, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI()+"?"+request.getQueryString());
            }
            logService.saveLog(result,request,debugAll);
        }
        catch (Exception e){
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,request.getRequestURI()+"?"+request.getQueryString());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping("/user/update/designation/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateDesignation(@RequestBody Designation designation) throws Exception{

        GeneralResponse<Boolean,Object> result;
        try{

            designationService.updateDesignation(designation);
            result = new GeneralResponse<>(true, constantFile.Designation_Updated, true, System.currentTimeMillis(), HttpStatus.OK,designation);

            logService.saveLog(result,request,debugAll);


        }
        catch (Exception e){
            e.printStackTrace();
            result = new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST,designation);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


}
