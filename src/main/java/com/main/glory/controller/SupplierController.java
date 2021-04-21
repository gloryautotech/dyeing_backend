package com.main.glory.controller;

import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.party.Party;
import com.main.glory.model.supplier.SupplierRate;
import com.main.glory.model.supplier.requestmodals.AddSupplierRateRequest;
import com.main.glory.model.supplier.Supplier;
import com.main.glory.model.supplier.requestmodals.UpdateSupplierRatesRequest;
import com.main.glory.model.supplier.requestmodals.UpdateSupplierRequest;
import com.main.glory.model.supplier.responce.GetAllSupplierWithName;
import com.main.glory.model.supplier.responce.GetItemWithSupplier;
import com.main.glory.model.supplier.responce.GetSupplierWithRateAndItem;
import com.main.glory.model.supplier.responce.RateAndItem;
import com.main.glory.servicesImpl.LogServiceImpl;
import com.main.glory.servicesImpl.SupplierServiceImpl;
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
public class SupplierController extends ControllerConfig {

    @Autowired
    SupplierServiceImpl supplierService;

    @Autowired
    LogServiceImpl logService;

    @Autowired
    HttpServletRequest request;

    @Value("${spring.application.debugAll}")
    Boolean debugAll=true;



    @GetMapping("/supplier/isSupplierNameExists/{name}/{id}")
    public ResponseEntity<GeneralResponse<Boolean,Object>> isUniqueSupplierName(@PathVariable("name") String name, @PathVariable("id") Long id){
        GeneralResponse<Boolean,Object> result;
        try{
            Boolean isPresent = supplierService.isUniqueName(id, name);
            result =  new GeneralResponse<>(isPresent, "Supplier name exists:"+isPresent, true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);
        }catch (Exception e){
            result =new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PostMapping("/supplier")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addSupplier(@RequestBody Supplier supplier,@RequestHeader Map<String, String> headers){
        GeneralResponse<Boolean,Object> result;
        try{
            Boolean flag = supplierService.addSupplier(supplier,headers.get("id"));
            if(flag) {
                result= new GeneralResponse<>(true, "Supplier added successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(false, "Invalid Data Sent", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            result= new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PostMapping("/supplier/rates")
    public ResponseEntity<GeneralResponse<Boolean,Object>> addSupplierRates(@RequestBody AddSupplierRateRequest addSupplierRateRequest){

        GeneralResponse<Boolean,Object> result;
        try{
            Boolean flag = supplierService.addSupplierRates(addSupplierRateRequest);
            if(flag) {
                result= new GeneralResponse<>(true, "Supplier rates added successfully", true, System.currentTimeMillis(), HttpStatus.OK,addSupplierRateRequest);
            } else {
                result= new GeneralResponse<>(false, "Invalid Data Sent", false, System.currentTimeMillis(), HttpStatus.OK,addSupplierRateRequest);
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            result =  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,addSupplierRateRequest);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/supplier/rates/exist")
    public ResponseEntity<GeneralResponse<Boolean,Object>> getSupplierRateExist(@RequestParam(name = "name") String name,@RequestParam(name = "id") Long id){

        GeneralResponse<Boolean,Object> result;
        try{
            Boolean flag = supplierService.supplierRateExist(name,id);
            if(flag) {
                result= new GeneralResponse<>(true, "Supplier rate found", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(false, "supplier rate not found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            result =  new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @GetMapping("/supplier/rate/{id}")
    public ResponseEntity<GeneralResponse<Object,Object>> getSupplierAlongWithRates(@PathVariable("id") Long id){
        GeneralResponse<Object,Object> result;
        try{
            if(id == null){
                throw new Exception("null record passed");//result= new GeneralResponse<>(null, "Id cannot be null", false, System.currentTimeMillis(), HttpStatus.OK);
            }

            Object obj =  supplierService.getSupplier(id);
            if(obj != null){
                result= new GeneralResponse<>(obj, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(null, "No Such Data Found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/supplier/GetItemWithRateBy/{id}")
    public ResponseEntity<GeneralResponse<List<RateAndItem>,Object>> getSupplierItemWithRateById(@PathVariable("id") Long id){
        GeneralResponse<List<RateAndItem>,Object> result;

        try{
            if(id == null){
                throw new Exception("null record passed");//result= new GeneralResponse<>(null, "Id cannot be null", false, System.currentTimeMillis(), HttpStatus.OK);
            }

            List<RateAndItem> item =  supplierService.getSupplierItemAndRate(id);
            if(item != null){
                result= new GeneralResponse<>(item, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(null, "No Such Data Found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping("/supplier/rates/all")
    public ResponseEntity<GeneralResponse<Object,Object>> getAllSupplierRates(){
        GeneralResponse<Object,Object> result;
        try{
            Object obj = supplierService.getAllRates();
            if(obj != null){
                result= new GeneralResponse<>(obj, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(null, "No Such Data Found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping("/supplier/getItemWithSupplierName/all")
    public ResponseEntity<GeneralResponse<List<GetItemWithSupplier>,Object>> getAllItemWithSupplierName(){
        GeneralResponse<List<GetItemWithSupplier>,Object> result;
        try{
            List<GetItemWithSupplier> obj = supplierService.getAllItemWithSupplierName();
            if(obj != null){
                result= new GeneralResponse<>(obj, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(obj, "No Such Data Found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    @GetMapping("/supplier/all/{getBy}/{id}")
    public ResponseEntity<GeneralResponse<List,Object>> getAllSupplier(@PathVariable(value = "id") Long id,@PathVariable( value = "getBy") String getBy){
        GeneralResponse<List,Object> result;
        try{
            switch (getBy) {
                case "own":
                    List obj = supplierService.getAllSupplier(getBy, id);
                    if(!obj.isEmpty()){
                        result= new GeneralResponse<>(obj, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    } else
                        result= new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());

                    break;
                case "group":
                    List obj1 = supplierService.getAllSupplier(getBy, id);

                    if(!obj1.isEmpty()){
                        result= new GeneralResponse<>(obj1, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    } else {
                        result= new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    }
                    break;

                case "all":
                    List obj2 = supplierService.getAllSupplier(null, null);
                    if(!obj2.isEmpty()){
                        result= new GeneralResponse<>(obj2, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    } else {
                        result= new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
                    }

                    break;
                default:
                    result= new GeneralResponse<>(null, "GetBy string is wrong", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());


            }
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }

        logService.saveLog(result,request,debugAll);
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));

    }
    @GetMapping("/supplier/all")
    public ResponseEntity<GeneralResponse<List<GetAllSupplierWithName>,Object>> getAllSupplierName(){
        GeneralResponse<List<GetAllSupplierWithName>,Object> result;
        try{

            List<GetAllSupplierWithName> object =supplierService.getAllSupplierName();

            result= new GeneralResponse<>(object, "Data found", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,debugAll);

        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping("/supplier")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateSupplier(@RequestBody UpdateSupplierRequest updateSupplierRequest){
        GeneralResponse<Boolean,Object> result;
        try{
            Boolean flag = supplierService.updateSupplier(updateSupplierRequest);
            if (flag) {
                result= new GeneralResponse<>(null, "Supplier Details Updated Successfully", true, System.currentTimeMillis(), HttpStatus.OK,updateSupplierRequest);
            } else {
                result= new GeneralResponse<>(null, "Send Valid Data", false, System.currentTimeMillis(), HttpStatus.OK,updateSupplierRequest);
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,updateSupplierRequest);
            logService.saveLog(result,request,debugAll);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @PutMapping("/supplier/rates")
    public ResponseEntity<GeneralResponse<Boolean,Object>> updateSupplier(@RequestBody UpdateSupplierRatesRequest updateSupplierRequest){

        GeneralResponse<Boolean,Object> result;
        try{
            supplierService.updateSupplierRatesWithValidation(updateSupplierRequest);

                result= new GeneralResponse<>(null, "Supplier Details Updated Successfully", true, System.currentTimeMillis(), HttpStatus.OK,updateSupplierRequest);

            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,updateSupplierRequest);
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }



    //get color item of supplier
    @GetMapping("/supplier/rate/colorItemsById/{supplierId}")
    public ResponseEntity<GeneralResponse<List<SupplierRate>,Object>> getAllColorItemBySupplierId(@PathVariable(name = "supplierId") Long supplierId){


        GeneralResponse<List<SupplierRate>,Object> result;
        try{
            List<SupplierRate> obj = supplierService.getAllColorItemBySupplierId(supplierId);
            if(obj != null){
                result= new GeneralResponse<>(obj, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(null, "No Such Data Found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //get color item of supplier
    @GetMapping("/supplier/rate/chemicalItemsById/{supplierId}")
    public ResponseEntity<GeneralResponse<List<SupplierRate>,Object>> getAllChemicalItemBySupplierId(@PathVariable(name ="supplierId") Long supplierId){

        GeneralResponse<List<SupplierRate>,Object> result;
        try{
            List<SupplierRate> obj = supplierService.getAllChemicalItemBySupplierId(supplierId);
            if(obj != null){
                result= new GeneralResponse<>(obj, "Data Fetched Successfully", true, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            } else {
                result= new GeneralResponse<>(null, "No Such Data Found", false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            }
            logService.saveLog(result,request,debugAll);
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK,request.getRequestURI());
            logService.saveLog(result,request,true);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
}
