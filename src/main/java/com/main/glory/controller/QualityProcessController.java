package com.main.glory.controller;

import com.main.glory.model.GeneralResponse;
import com.main.glory.model.party.Party;
import com.main.glory.model.qualityProcess.QualityProcessMast;
import com.main.glory.model.qualityProcess.response.ListResponse;
import com.main.glory.servicesImpl.QualityProcessImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.main.glory.config.ControllerConfig;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QualityProcessController extends ControllerConfig {

	@Autowired
	QualityProcessImpl qualityProcess;

	@Autowired
	ModelMapper modelMapper;

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

	@GetMapping("/qualityprocess/all/{getBy}/{id}")
	public GeneralResponse<List<ListResponse>> getQualityProcessList(@PathVariable(value = "getBy")String getBy, @PathVariable(value = "id")Long id){
		try {
			List<QualityProcessMast> qualityProcessMasts = null;
			List<ListResponse> res = new ArrayList<>();
			switch (getBy) {
				case "own":
					qualityProcessMasts = qualityProcess.qualityProcessMasts(getBy, id);
					if(!qualityProcessMasts.isEmpty()){
						qualityProcessMasts.forEach(e -> {
							res.add(modelMapper.map(e, ListResponse.class));
						});
						return new GeneralResponse<>(res, "Fetched Successfully", true, System.currentTimeMillis(),HttpStatus.OK);
					}else
						return new GeneralResponse<>(null, "No data found with userId: "+id, false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);

				case "group":
					qualityProcessMasts = qualityProcess.qualityProcessMasts(getBy, id);
					if(!qualityProcessMasts.isEmpty()){
						qualityProcessMasts.forEach(e -> {
							res.add(modelMapper.map(e, ListResponse.class));
						});
						return new GeneralResponse<>(res, "Fetched Successfully", true, System.currentTimeMillis(),HttpStatus.OK);
					}else
						return new GeneralResponse<>(null, "No data found with userHeadId: "+id, false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);

				case "all":
					qualityProcessMasts = qualityProcess.qualityProcessMasts(null, null);
					if(!qualityProcessMasts.isEmpty()){
						qualityProcessMasts.forEach(e -> {
							res.add(modelMapper.map(e, ListResponse.class));
						});
						return new GeneralResponse<>(res, "Fetched Successfully", true, System.currentTimeMillis(),HttpStatus.OK);
					}else
						return new GeneralResponse<>(null, "No data added yet", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);

				default:
					return new GeneralResponse<>(null, "GetBy string is wrong", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
			}

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
