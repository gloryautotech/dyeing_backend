package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.admin.*;
import com.main.glory.model.admin.request.DepartmentResponse;
import com.main.glory.model.color.ColorMast;
import com.main.glory.model.jet.JetMast;
import com.main.glory.model.jet.request.AddJet;
import com.main.glory.model.quality.QualityName;
import com.main.glory.model.task.ReportType;
import com.main.glory.servicesImpl.AdminServciceImpl;
import com.main.glory.servicesImpl.JetServiceImpl;
import com.main.glory.servicesImpl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger.schema.ApiModelProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AdminController extends ControllerConfig {

    @Autowired
    JetServiceImpl jetService;

    @Autowired
    AdminServciceImpl adminServcice;

    @Value("${spring.application.debugAll}")
    Boolean debugAll=true;

    @Autowired
    HttpServletRequest request;

    @Autowired
    LogServiceImpl logService;



    @PostMapping(value="/admin/jet/addJet")
    public ResponseEntity<GeneralResponse<Boolean,Object>> saveJet(@RequestBody AddJet jetMast) throws Exception {

        GeneralResponse<Boolean,Object> result;
        if(jetMast==null)
        {
            result =  new GeneralResponse<>(false, "jet info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }

        boolean flag;
        try {

            jetService.saveJet(jetMast);
            result= new GeneralResponse<>(null, "Jet Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,jetMast);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,jetMast);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @PostMapping(value="/admin/quality/add/qualityName/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> saveQualityName(@RequestBody QualityName qualityName) throws Exception {
       GeneralResponse<Boolean,Object> result;
        if(qualityName==null)
        {
            result =  new GeneralResponse<>(false, "qualityName info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.saveQualityName(qualityName);
            result= new GeneralResponse<>(null, "Quality Name Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,qualityName);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result =new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,qualityName);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @PutMapping(value="/admin/quality/update/qualityName/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateQualityName(@RequestBody QualityName qualityName) throws Exception {
        GeneralResponse<Boolean,Object> result;
        if(qualityName==null)
        {
            result = new GeneralResponse<>(false, "qualityName info is null", false, System.currentTimeMillis(), HttpStatus.OK,qualityName);
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.updateQualityName(qualityName);
            result =  new GeneralResponse<>(null, "Quality Name Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,qualityName);

            logService.saveLog(result,request,debugAll);
        }
        catch(Exception e)
        {
            result =  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,qualityName);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping(value="/admin/quality/delete/qualityName/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteQualityName(@PathVariable(name = "id") Long id) throws Exception {
        GeneralResponse<Boolean,Object> result;
        if(id==null)
        {
            result= new GeneralResponse<>(false, "id info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.deleteQualityNameById(id);
            result =  new GeneralResponse<>(null, "Quality name deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {

            result =  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @PutMapping(value="/admin/jet/updateJet")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateJet(@RequestBody AddJet jetMast) throws Exception {
        GeneralResponse<Boolean,Object> result;
        if(jetMast==null)
        {
            result =  new GeneralResponse<>(false, "jet info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            jetService.updateJet(jetMast);
            result =  new GeneralResponse<>(null, "Jet updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,jetMast);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,jetMast);
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/jet/getJetById/{id}")
    public ResponseEntity<GeneralResponse<JetMast,Object>> getJetMast(@PathVariable(name = "id")Long id) throws Exception {
       GeneralResponse<JetMast,Object> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "jet info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            JetMast jetMast = jetService.getJetMastById(id);
            result= new GeneralResponse<>(jetMast, "Jet data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @PostMapping(value="/admin/add/company/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> saveCompany(@RequestBody Company c) throws Exception {

        GeneralResponse<Boolean,Object> result;
        if(c.getName()==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.saveCompanyName(c);
            result= new GeneralResponse<>(null, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,c);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,c);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping(value="/admin/get/company/{id}")
    public ResponseEntity<GeneralResponse<Company,Object>> getCompanyById(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Company,Object> result;
        if(id==null)
        {
            result= new GeneralResponse<>(null, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            Company c  = adminServcice.getCompanyById(id);
            result= new GeneralResponse<>(c, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @PutMapping(value="/admin/update/company/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateCompany(@RequestBody Company company) throws Exception {

        GeneralResponse<Boolean,Object> result;
        if(company==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.updateCompany(company);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,company);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,company);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping(value="/admin/update/department/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateDepartMent(@RequestBody Department department) throws Exception {

        GeneralResponse<Boolean,Object> result;
        if(department==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.updateDepartment(department);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,department);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,department);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }



    @PostMapping(value="/admin/add/department/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> saveDepartment(@RequestBody Department c) throws Exception {

        GeneralResponse<Boolean,Object> result;
        if(c.getName()==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.saveDepartment(c);
            result= new GeneralResponse<>(null, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,c);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,c);
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @DeleteMapping(value="/admin/delete/companyBy/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteCompany(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result=null;
        if(id==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            flag = adminServcice.deleteCompanyById(id);
            if(flag) {
                result = new GeneralResponse<>(null, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK, request.getRequestURI());
                logService.saveLog(result,request,debugAll);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping(value="/admin/delete/department/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteDepartment(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result=null;
        if(id==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            flag = adminServcice.deleteDepartmentById(id);
            if(flag) {
                result = new GeneralResponse<>(null, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK, request.getRequestURI());
                logService.saveLog(result,request,true);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    /*@PostMapping(value="/admin/add/approvedBy/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> saveApprovedBy(@RequestBody ApprovedBy data) throws Exception {

        GeneralResponse<Boolean,Object> result;
        if(data==null)
        {
            result= new GeneralResponse<>(false, " info is null", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        boolean flag;
        try {

            adminServcice.saveApprovedBy(data);
            result= new GeneralResponse<>(null, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,data);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,data);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

*/
    @GetMapping(value="/admin/get/approvedBy")
    public ResponseEntity<GeneralResponse<List<Authorize>,Object>> getAllApproved() throws Exception {

        GeneralResponse<List<Authorize>,Object> result;

        boolean flag;
        try {

            List<Authorize> list = adminServcice.getApprovedByList();
            if(list.isEmpty())
                result= new GeneralResponse<>(null, " data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
  /*  @PutMapping(value="/admin/update/approvedBy/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateApproved(@RequestBody ApprovedBy approvedBy) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            adminServcice.updateApprovedBy(approvedBy);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,approvedBy);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,approvedBy);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping(value="/admin/get/approvedBy/{id}")
    public ResponseEntity<GeneralResponse<ApprovedBy,Object>> getApprovedById(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<ApprovedBy,Object> result;

        boolean flag;
        try {

            if(id==null)
                throw new Exception("data can't be null");

            ApprovedBy data =adminServcice.getApprovedById(id);
            result= new GeneralResponse<>(data, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }*/

    @GetMapping(value="/admin/get/department")
    public ResponseEntity<GeneralResponse<List<DepartmentResponse>,Object>> getAllDepartment(@RequestHeader Map<String, String> headers) throws Exception {

        GeneralResponse<List<DepartmentResponse>,Object> result;

        boolean flag;
        try {


            List<DepartmentResponse> list = adminServcice.getAllDepartmentListByHeaderId(headers.get("id"));
            if(list.isEmpty())
                result= new GeneralResponse<>(null, " data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,true);
        }

        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping(value="/admin/get/department/{id}")
    public ResponseEntity<GeneralResponse<DepartmentResponse,Object>> getDepartmentById(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<DepartmentResponse,Object> result;

        boolean flag;
        try {

            DepartmentResponse list = adminServcice.getDepartmentById(id);
            result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    /*@GetMapping(value="/admin/update/department/")
    public GeneralResponse<Boolean> getDepartmentById(@RequestBody Department department) throws Exception {

        GeneralResponse<Boolean> result;

        boolean flag;
        try {

            adminServcice.updateDepartment(department);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return result;
    }*/
    @GetMapping(value="/admin/get/allJet")
    public ResponseEntity<GeneralResponse<List<AddJet>,Object>> getAllJet() throws Exception {

        GeneralResponse<List<AddJet>,Object> result;

        boolean flag;
        try {

            List<AddJet> list = jetService.getAllJet();
            if(list.isEmpty())
                result= new GeneralResponse<>(list, " data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/jet/deleteTable/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isJetDeletable(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        try {

            Boolean flag = jetService.getJetIsDeletable(id);
            if(flag)
                result= new GeneralResponse<>(flag, "data is deletable", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(flag, "data is not deletable", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

   /* @GetMapping(value="/admin/approvedBy/deleteTable/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isApprovedByDeletable(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        try {

            Boolean flag = adminServcice.getApprovedByDeletable(id);
            if(flag)
                result= new GeneralResponse<>(flag, "data is deletable", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(flag, "data is not deletable", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
*/
    @GetMapping(value="/admin/department/deleteTable/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isDepartMentDeletable(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        try {

            Boolean flag = adminServcice.getDepartmentIsDelatable(id);
            if(flag)
                result= new GeneralResponse<>(flag, "data is deletable", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(flag, "data is not deletable", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/company/deletable/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isCompanyDeletable(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        try {

            Boolean flag = adminServcice.getCompanyIsDelatable(id);
            if(flag)
                result= new GeneralResponse<>(flag, "data is deletable", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(flag, "data is not deletable", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @GetMapping(value="/admin/get/allCompany")
    public ResponseEntity<GeneralResponse<List<Company>,Object>> getAllCompany() throws Exception {

        GeneralResponse<List<Company>,Object> result;

        boolean flag;
        try {

            List<Company> list = adminServcice.getAllCompany();
            if(list.isEmpty())
                result= new GeneralResponse<>(list, " data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);
        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping(value="/admin/delete/jet/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteJetById(@PathVariable(name = "id") Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            Boolean list = jetService.deleteJetMastByJetId(id);
            if(list==false)
                result= new GeneralResponse<>(list, " data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    /*@DeleteMapping(value="/admin/delete/department/{id}")
    public GeneralResponse<Boolean> deleteDepartmentById(@PathVariable(name = "id") Long id) throws Exception {

        GeneralResponse<Boolean> result;

        boolean flag;
        try {

            Boolean list = adminServcice.deleteDepartmentById(id);
            if(list==false)
                result= new GeneralResponse<>(null, " data not found", false, System.currentTimeMillis(), HttpStatus.OK);
            else
                result= new GeneralResponse<>(list, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return result;
    }*/

    /*@DeleteMapping(value="/admin/delete/approved/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteApprovedById(@PathVariable(name = "id") Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            Boolean list = adminServcice.deleteApprovedById(id);

            result= new GeneralResponse<>(list, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
*/
    @PostMapping(value="/admin/add/invoiceSequence/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addInvoiceSequence(@RequestBody InvoiceSequence record) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {


            if(record == null)
                throw new Exception("null data passed");

            adminServcice.addInvoiceSequence(record);

            result= new GeneralResponse<>(true, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/invoiceSequence/")
    public ResponseEntity<GeneralResponse<InvoiceSequence,Object>> getInvoiceSequence() throws Exception {

        GeneralResponse<InvoiceSequence,Object> result=null;

        boolean flag;
        try {


            InvoiceSequence invoiceSequence = adminServcice.getInvoiceSequence();
            if(invoiceSequence!=null)
                result= new GeneralResponse<>(invoiceSequence, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @PutMapping(value="/admin/update/invoiceSequence/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateInvoiceSequence(@RequestBody InvoiceSequence invoiceSequence) throws Exception {

        GeneralResponse<Boolean,Object> result=null;

        boolean flag;
        try {


            adminServcice.updateInvoiceSequence(invoiceSequence);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,invoiceSequence);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,invoiceSequence);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping(value="/admin/get/invoiceSequence/{id}")
    public ResponseEntity<GeneralResponse<InvoiceSequence,Object>> getInvoiceSequenceById(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<InvoiceSequence,Object> result=null;

        boolean flag;
        try {
            if(id==null)
                throw new Exception("id can't null");

            InvoiceSequence invoiceSequence = adminServcice.getInvoiceSequenceById(id);
            if(invoiceSequence!=null)
                result= new GeneralResponse<>(invoiceSequence, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(invoiceSequence, " Data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PostMapping(value="/admin/add/batchSequence/")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addBatchSequence(@RequestBody BatchSequence record) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {


            if(record == null)
                throw new Exception("null data passed");

            adminServcice.addBatchSequence(record);

            result= new GeneralResponse<>(true, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,record);

            logService.saveLog(result,request,debugAll);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @PutMapping(value="/admin/update/batchSequence/")
    public ResponseEntity<GeneralResponse<BatchSequence,Object>> updateBatchSequence(@RequestBody BatchSequence record) throws Exception {

        GeneralResponse<BatchSequence,Object> result;

        boolean flag;
        try {


            if(record == null)
                throw new Exception("null data passed");

            BatchSequence id = adminServcice.updateBatchSequence(record);

            result= new GeneralResponse<>(id, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/batchSequence")
    public ResponseEntity<GeneralResponse<BatchSequence,Object>> getBatchSequence(@RequestParam(name = "update")Boolean update) throws Exception {

        GeneralResponse<BatchSequence,Object> result;

        boolean flag;
        try {

            BatchSequence batchSequence = adminServcice.getBatchSequence(update);
            result= new GeneralResponse<>(batchSequence, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/batchSequence/{id}")
    public ResponseEntity<GeneralResponse<BatchSequence,Object>> getBatchSequenceById(@PathVariable(name = "id")Long id) throws Exception {

        GeneralResponse<BatchSequence,Object> result;

        boolean flag;
        try {
            if(id==null)
                throw new Exception("null id passed");

            BatchSequence batchSequence = adminServcice.getBatchSequenceById(id);
            result= new GeneralResponse<>(batchSequence, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    /*@PostMapping(value="/admin/add/receiver")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addReceiver(@RequestBody ReceiverBy record) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {
            if(record==null)
                throw new Exception("null record passed");

            adminServcice.addReceiver(record);
            result= new GeneralResponse<>(true, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping(value="/admin/update/receiver")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateReceiver(@RequestBody ReceiverBy record) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {
            if(record==null)
                throw new Exception("null record passed");

            adminServcice.updateReceiver(record);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }*/
    @GetMapping(value="/admin/get/receiver")
    public ResponseEntity<GeneralResponse<List<Authorize>,Object>> getAllReceiver() throws Exception {

        GeneralResponse<List<Authorize>,Object> result;

        boolean flag;
        try {

            List<Authorize> list = adminServcice.getAllReceiver();
            if(list.isEmpty())
            {
                result= new GeneralResponse<>(list, " no record found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            }
            else
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    /*@GetMapping(value="/admin/get/receiver/{id}")
    public ResponseEntity<GeneralResponse<ReceiverBy,Object>> getReceiverById(@PathVariable(name = "id") Long id) throws Exception {

        GeneralResponse<ReceiverBy,Object> result;

        boolean flag;
        try {
            if(id==null)
                throw new Exception("null record passed");

            ReceiverBy list = adminServcice.getReceiverById(id);
            if(list==null)
            {
                result= new GeneralResponse<>(list, " no record found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            else
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @DeleteMapping(value="/admin/delete/receiver/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteReceiverById(@PathVariable(name = "id") Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {
            if(id==null)
                throw new Exception("null record passed");

            adminServcice.deleteReceiverById(id);
            result= new GeneralResponse<>(true, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
*/
    @PostMapping(value="/admin/add/reportType")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addReportType(@RequestBody ReportType record) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {


            if(record == null)
                throw new Exception("null data passed");

            adminServcice.addReportType(record);

            result= new GeneralResponse<>(true, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,debugAll);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,record);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/reportType")
    public ResponseEntity<GeneralResponse<List<ReportType>,Object>> getAllReportType() throws Exception {

        GeneralResponse<List<ReportType>,Object> result;

        boolean flag;
        try {


            List<ReportType> reportTypeList = adminServcice.getAllReportType();

            if(reportTypeList.isEmpty())
            {
                result= new GeneralResponse<>(reportTypeList, " Data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            else
            {
                result= new GeneralResponse<>(reportTypeList, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);



        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/reportTypeBy")
    public ResponseEntity<GeneralResponse<ReportType,Object>> getReportTypeById(@RequestParam(name = "id")Long id) throws Exception {

        GeneralResponse<ReportType,Object> result;

        boolean flag;
        try {


            if(id==null)
                throw new Exception("null id passed");

            ReportType reportTypeList = adminServcice.getReportTypeById(id);

            if(reportTypeList==null)
            {
                result= new GeneralResponse<>(reportTypeList, " Data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            else
            {
                result= new GeneralResponse<>(reportTypeList, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }

            logService.saveLog(result,request,debugAll);


        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/update/reportType")
    public ResponseEntity<GeneralResponse<Boolean,Object>> getReportTypeById(@RequestBody ReportType record) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            if(record==null)
                throw new Exception("null record passed");

            flag = adminServcice.updateReportType(record);

            if(flag==false)
            {
                result= new GeneralResponse<>(false, " Data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            else
            {
                result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }

            logService.saveLog(result,request,debugAll);


        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping(value="/admin/delete/reportTypeBy")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteReportTypeById(@RequestParam(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            if(id==null)
                throw new Exception("null record passed");

            adminServcice.deleteReportTypeById(id);
            result= new GeneralResponse<>(true, " Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);




        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PostMapping(value="/admin/add/authorize")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addAuthorize(@RequestBody Authorize authorize) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            if(authorize==null)
                throw new Exception("null record passed");

            adminServcice.addAuthorize(authorize);
            result= new GeneralResponse<>(true, " Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);




        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping(value="/admin/update/authorize")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateAuthorize(@RequestBody Authorize authorize) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {

            if(authorize==null)
                throw new Exception("null record passed");

            adminServcice.updateAuthorize(authorize);
            result= new GeneralResponse<>(true, " Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);




        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/authorize/all")
    public ResponseEntity<GeneralResponse<List<Authorize>,Object>> getAllAuthorize() throws Exception {

        GeneralResponse<List<Authorize>,Object> result;

        boolean flag;
        try {



            List<Authorize> list = adminServcice.getAllAuthorize();
            if(!list.isEmpty())
            result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, "data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());


            logService.saveLog(result,request,debugAll);




        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/admin/get/authorize")
    public ResponseEntity<GeneralResponse<Authorize,Object>> getAuthorizeById(@RequestParam(name = "id")Long id) throws Exception {

        GeneralResponse<Authorize,Object> result;

        boolean flag;
        try {



            Authorize list = adminServcice.getAuthorizeById(id);
            if(list!=null)
                result= new GeneralResponse<>(list, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            else
                result= new GeneralResponse<>(list, "data not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());


            logService.saveLog(result,request,debugAll);




        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @DeleteMapping(value="/admin/delete/authorize")
    public ResponseEntity<GeneralResponse<Boolean,Object>> deleteAuthorizeById(@RequestParam(name = "id")Long id) throws Exception {

        GeneralResponse<Boolean,Object> result;

        boolean flag;
        try {



            adminServcice.deleteAuthorizeById(id);

            result= new GeneralResponse<>(true, " Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

            logService.saveLog(result,request,debugAll);




        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }



}
