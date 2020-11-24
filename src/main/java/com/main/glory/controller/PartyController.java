package com.main.glory.controller;

import java.util.List;

import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.party.request.AddParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.glory.model.party.Party;
import com.main.glory.servicesImpl.PartyServiceImp;

@RestController
@RequestMapping("/api")
public class PartyController  extends ControllerConfig {

	@Autowired 
	private PartyServiceImp partyServiceImp;
	
	@PostMapping(value="/party")
	public GeneralResponse<Boolean> saveParty(@RequestBody AddParty party)
	{
		int flag=partyServiceImp.saveParty(party);
		if(flag!=1)
		{
			return new GeneralResponse<Boolean>(null, "Please Enter Valid Data", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
		}else
			return new GeneralResponse<Boolean>(null, "Party Data Saved Successfully", true, System.currentTimeMillis(), HttpStatus.CREATED);
	}
	
	@GetMapping(value="/party/all/{getBy}/{id}")
	public GeneralResponse<List<Party>> getPartyList(@PathVariable(value = "id") Long id,@PathVariable( value = "getBy") String getBy)
	{
		try{
			if(getBy.equals("own")){
				var x = partyServiceImp.getAllPartyDetails(id,getBy);
				if(!x.isEmpty())
					return new GeneralResponse<List<Party>>(x, "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
				else
					return new GeneralResponse<List<Party>>(x, "No party added yet with userId: "+id, true, System.currentTimeMillis(), HttpStatus.FOUND);
			}
			else if(getBy.equals("group")){
				var x = partyServiceImp.getAllPartyDetails(id, getBy);
				if(!x.isEmpty())
					return new GeneralResponse<List<Party>>(x, "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
				else
					return new GeneralResponse<List<Party>>(x, "No party added yet with userHeadId: "+id, true, System.currentTimeMillis(), HttpStatus.FOUND);
			}
			else if(getBy.equals("all")){
				var x = partyServiceImp.getAllPartyDetails(null, null);
				if(!x.isEmpty())
					return new GeneralResponse<List<Party>>(x, "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
				else
					return new GeneralResponse<List<Party>>(x, "No party added yet", true, System.currentTimeMillis(), HttpStatus.FOUND);
			}else{
				return new GeneralResponse<List<Party>>(null, "GetBy string is wrong.", false, System.currentTimeMillis(), HttpStatus.FOUND);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new GeneralResponse<List<Party>>(null, "Internal Server Error", false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value="/party/{id}")
	public GeneralResponse<Party> getPartyDetailsById(@PathVariable(value = "id") Long id)
	{
           if(id!=null)
		   {
			   Party partyObject=partyServiceImp.getPartyDetailById(id);
			   if(partyObject!=null)
			   {
			   	    return new GeneralResponse<Party>(partyObject, "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
			   }
			   return new GeneralResponse<Party>(null, "No such id", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
		   }
           return new GeneralResponse<>(null, "Null Id Passed!", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
	}

	@PutMapping(value="/party")
	public GeneralResponse<Boolean> updateParty(@RequestBody Party party) throws Exception
	{
		if(party!=null)
		{
			 boolean flag=partyServiceImp.editPartyDetails(party);
			 if(flag) {
				 return new GeneralResponse<Boolean>(true, "updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);
			 }else{
				 return new GeneralResponse<Boolean>(false, "no such id found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
			 }
		}
		return new GeneralResponse<Boolean>(false, "Null party object", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(value="/party/{id}")
	public GeneralResponse<Boolean> deletePartyDetailsByID(@PathVariable(value = "id") Long id)
	{
		if(id!=null)
		{
			boolean flag=partyServiceImp.deletePartyById(id);
			if(flag)
			{
				return new GeneralResponse<Boolean>(true, "Deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK);
			}else{
				return new GeneralResponse<Boolean>(false, "no such id found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
			}
		}
		return new GeneralResponse<Boolean>(false, "Null party object", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
	}
}