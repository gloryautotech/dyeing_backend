package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.StockDataBatchData.response.GetAllStockWithPartyNameResponse;
import com.main.glory.model.jet.request.AddJet;
import com.main.glory.model.task.TaskData;
import com.main.glory.model.task.TaskMast;
import com.main.glory.model.task.request.TaskDetail;
import com.main.glory.model.task.request.TaskFilter;
import com.main.glory.model.task.response.TaskMastResponse;
import com.main.glory.model.task.response.TaskResponse;
import com.main.glory.servicesImpl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController extends ControllerConfig {

    @Autowired
    TaskServiceImpl taskService;

    @PostMapping(value="/task/add")
    public ResponseEntity<GeneralResponse<Boolean>> saveTask(@RequestBody TaskMast record) throws Exception {
        GeneralResponse<Boolean> result;
        if(record==null)
        {
            result =  new GeneralResponse<>(false, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

        boolean flag;
        try {

            taskService.saveTask(record);
            result =  new GeneralResponse<>(null, "Task Data added successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result= new GeneralResponse<Boolean>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    @GetMapping(value="/task/taskMast/getBy")
    public ResponseEntity<GeneralResponse<TaskMastResponse>> getTaskMastById(@RequestParam(name = "taskDataId")Long id) throws Exception {
        GeneralResponse<TaskMastResponse> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

        boolean flag;
        try {

            TaskMastResponse taskResponse = taskService.getTaskById(id);
            if(taskResponse==null)
            {
                result =  new GeneralResponse<>(taskResponse, "Data not found", false, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
            result =  new GeneralResponse<>(taskResponse, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @GetMapping(value="/task/taskData/getBy")
    public ResponseEntity<GeneralResponse<TaskData>> getTaskDataById(@RequestParam(name = "id")Long id) throws Exception {
        GeneralResponse<TaskData> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

        boolean flag;
        try {

            TaskData taskResponse = taskService.getTaskDataById(id);
            if(taskResponse==null)
            {
                result =  new GeneralResponse<>(taskResponse, "Data not found", false, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
                result =  new GeneralResponse<>(taskResponse, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    /*@GetMapping(value="/task/get")
    public ResponseEntity<GeneralResponse<List<TaskResponse>>> getAllTask() throws Exception {
        GeneralResponse<List<TaskResponse>> result;
       *//* if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }*//*

        boolean flag;
        try {

            List<TaskResponse> taskResponse = taskService.getAllTask();
            if(taskResponse.isEmpty())
            {
                result =  new GeneralResponse<>(taskResponse, "Data not found", false, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
                result =  new GeneralResponse<>(taskResponse, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }*/
    @DeleteMapping(value="/task/taskMast/deleteBy")
    public ResponseEntity<GeneralResponse<Boolean>> deleteTaskById(@RequestParam(name = "id")Long id) throws Exception {
        GeneralResponse<Boolean> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

        boolean flag;
        try {

            flag = taskService.deleteTaskById(id);
            if(flag==true)
            {
                result =  new GeneralResponse<>(true, "Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
                result =  new GeneralResponse<>(false, "Unable to remove the record", false, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }
    @DeleteMapping(value="/task/taskData/deleteBy")
    public ResponseEntity<GeneralResponse<Boolean>> deleteTaskDataById(@RequestParam(name = "id")Long id) throws Exception {
        GeneralResponse<Boolean> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

        boolean flag;
        try {

            flag = taskService.deleteTaskDataById(id);
            if(flag==true)
            {
                result =  new GeneralResponse<>(true, "Data deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
                result =  new GeneralResponse<>(false, "Unable to remove the record", false, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    //filter task api
    @PostMapping(value="/task/getByDateAndStatus")
    public ResponseEntity<GeneralResponse<List<TaskDetail>>> getAllTaskDetailByDateAndStatus(@RequestBody TaskFilter record) throws Exception {
        GeneralResponse<List<TaskDetail>> result;

        boolean flag;
        try {

            List<TaskDetail> taskResponse = taskService.getAllTaskByDateAndStatus(record);
            if(taskResponse.isEmpty())
            {
                result =  new GeneralResponse<>(taskResponse, "Data not found", false, System.currentTimeMillis(), HttpStatus.OK);
            }
            else

                result =  new GeneralResponse<>(taskResponse, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    //get task by filte
    @GetMapping("/task/all")
    public ResponseEntity<GeneralResponse<List<TaskDetail>>> getAllStockBatch(@RequestParam(name = "getBy") String getBy, @RequestParam(name = "id") Long id,@RequestHeader Map<String, String> headers) throws Exception {

        GeneralResponse<List<TaskDetail>> result;

        try {
            List<TaskDetail> record = null;
            switch (getBy) {
                case "assign":
                    record = taskService.getAllTaskDetail(getBy, id,headers.get("id"));
                    if (record.isEmpty()) {
                        result= new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.OK);
                    } else {
                        result= new GeneralResponse<>(record, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                    }
                    break;

                case "assignAndCreated":
                    record = taskService.getAllTaskDetail(getBy, id,headers.get("id"));
                    if (record.isEmpty()) {
                        result= new GeneralResponse<>(null, "No data found", false, System.currentTimeMillis(), HttpStatus.OK);
                    } else {
                        result= new GeneralResponse<>(record, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                    }
                    break;

                case "all":
                    record = taskService.getAllTaskDetail(null, null,headers.get("id"));
                    if (record.isEmpty()) {
                        result= new GeneralResponse<>(null, "No data added yet", false, System.currentTimeMillis(), HttpStatus.OK);
                    } else {
                        result= new GeneralResponse<>(record, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                    }
                    break;
                default:
                    result= new GeneralResponse<>(null, "GetBy string is wrong", false, System.currentTimeMillis(), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result= new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatusCode()));
    }

    //filter task api
    @GetMapping(value="/task/get/approved")
    public ResponseEntity<GeneralResponse<List<TaskDetail>>> getAllTaskApprovedFlagWithId(@RequestParam(name = "id")Long id,@RequestParam(name = "approved") Boolean approvedFlag) throws Exception {
        GeneralResponse<List<TaskDetail>> result;

        boolean flag;
        try {

            List<TaskDetail> taskResponse = taskService.getAllApprovedOrNot(id,approvedFlag);
            if(taskResponse.isEmpty())
            {
                result =  new GeneralResponse<>(taskResponse, "Data not found", false, System.currentTimeMillis(), HttpStatus.OK);
            }
            else {
                result = new GeneralResponse<>(taskResponse, "Data fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }


    //task update by id and status
    //filter task api
    @GetMapping(value="/task/update/approved")
    public ResponseEntity<GeneralResponse<Boolean>> updateTaskApprovedStatus(@RequestParam(name = "id")Long id,@RequestParam(name = "approved") Boolean approvedFlag) throws Exception {
        GeneralResponse<Boolean> result;

        boolean flag;
        try {

            taskService.updateTaskByIdAndFlag(id,approvedFlag);
            result =  new GeneralResponse<>(true, "Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }

    //filter task api
    @PutMapping(value="/task/taskData/update")
    public ResponseEntity<GeneralResponse<Boolean>> updateTaskData(@RequestBody TaskData taskData) throws Exception {
        GeneralResponse<Boolean> result;

        boolean flag;
        try {

            if(taskData==null)
                throw new Exception("null record passed");

            taskService.updateTaskData(taskData);
            result =  new GeneralResponse<>(true, "Data updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            result =  new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.OK);
        }
        return new ResponseEntity<>(result,HttpStatus.valueOf(result.getStatusCode()));
    }








}
