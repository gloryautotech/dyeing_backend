package com.main.glory.controller;

import com.main.glory.model.GeneralResponse;
import com.main.glory.model.qualityProcess.QualityProcessMast;
import com.main.glory.model.qualityProcess.Request.ProcessList;
import com.main.glory.servicesImpl.QualityProcessImpl;
import org.apache.tomcat.jni.Proc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.main.glory.config.ControllerConfig;

import java.util.*;

@RestController
@RequestMapping("/api")
public class QualityProcessController extends ControllerConfig {

	@Autowired
	QualityProcessImpl qualityProcess;

	@PostMapping("/qualityprocess")
	public GeneralResponse<Boolean> addQualityProcess(@RequestBody QualityProcessMast qualityProcessMast){
		try{
			if(qualityProcessMast == null){
				return new GeneralResponse<>(false, "Null data passed", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
			}

			qualityProcess.saveQualityProcess(qualityProcessMast);
			return new GeneralResponse<>(true, "Process Added Successfully", true, System.currentTimeMillis(), HttpStatus.OK);
		}catch (Exception e){
			return new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/qualityprocess/all")
	public GeneralResponse<List<QualityProcessMast>> getQualityProcessList(){
		try {
			List<QualityProcessMast> qualityProcessMasts = qualityProcess.qualityProcessMasts();
			return new GeneralResponse<>(qualityProcessMasts, "Fetched Successfully", true, System.currentTimeMillis(),HttpStatus.OK);
		} catch (Exception e) {
			return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/qualityprocess/demo")
	public GeneralResponse<List<ProcessList>> getQualityDemoProcessList(){
		try {
			List<ProcessList> list =new ArrayList<>();
			list.add(new ProcessList(1,"Demo1"));
			list.add(new ProcessList(2,"Demo2"));
			list.add(new ProcessList(3,"Demo3"));

			//List<QualityProcessMast> qualityProcessMasts = qualityProcess.qualityProcessMasts();
			return new GeneralResponse<>(list, "Fetched Successfully", true, System.currentTimeMillis(),HttpStatus.OK);
		} catch (Exception e) {
			return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/qualityprocess/{id}")
	public GeneralResponse<QualityProcessMast> getQualityProcessList(@PathVariable("id") Long id){
		try {
			QualityProcessMast qualityProcessMasts = qualityProcess.findById(id);
			return new GeneralResponse<>(qualityProcessMasts, "Fetched Successfully", true, System.currentTimeMillis(),HttpStatus.OK);
		} catch (Exception e) {
			return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/qualityprocess")
	public GeneralResponse<Boolean> updateQualityProcess(@RequestBody QualityProcessMast qualityProcessMast){
		try{
			if(qualityProcessMast == null){
				return new GeneralResponse<>(false, "Null data passed", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
			}
			qualityProcess.update(qualityProcessMast);
			return new GeneralResponse<>(true, "updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);
		} catch (Exception e) {
			return new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
