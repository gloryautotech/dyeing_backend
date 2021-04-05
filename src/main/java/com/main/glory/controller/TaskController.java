package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.jet.request.AddJet;
import com.main.glory.model.task.TaskMast;
import com.main.glory.model.task.request.TaskDetail;
import com.main.glory.model.task.response.TaskResponse;
import com.main.glory.servicesImpl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @GetMapping(value="/task/getBy")
    public ResponseEntity<GeneralResponse<TaskResponse>> getTaskById(@RequestParam(name = "id")Long id) throws Exception {
        GeneralResponse<TaskResponse> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

        boolean flag;
        try {

            TaskResponse taskResponse = taskService.getTaskById(id);
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

    @GetMapping(value="/task/get")
    public ResponseEntity<GeneralResponse<List<TaskResponse>>> getAllTask(@RequestParam(name = "id")Long id) throws Exception {
        GeneralResponse<List<TaskResponse>> result;
        if(id==null)
        {
            result =  new GeneralResponse<>(null, "info is null", false, System.currentTimeMillis(), HttpStatus.OK);
        }

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
    }
    @DeleteMapping(value="/task/deleteBy")
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


    //filter task api
    @GetMapping(value="/task/getByDateAndStatus")
    public ResponseEntity<GeneralResponse<List<TaskDetail>>> getAllTaskDetailByDateAndStatus(@RequestParam(name = "date") Date date, @RequestParam(name = "status")String status) throws Exception {
        GeneralResponse<List<TaskDetail>> result;

        boolean flag;
        try {

            List<TaskDetail> taskResponse = taskService.getAllTaskByDateAndStatus(date,status);
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

}
