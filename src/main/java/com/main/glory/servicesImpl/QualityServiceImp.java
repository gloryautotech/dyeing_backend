package com.main.glory.servicesImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.glory.Dao.PartyDao;
import com.main.glory.Dao.QualityDao;
import com.main.glory.model.Quality;
import com.main.glory.services.QualityServiceInterface;

@Service("qualityServiceImp")
public class QualityServiceImp implements QualityServiceInterface{

	@Autowired
	private QualityDao qualityDao;
	
	@Autowired
	private PartyDao partyDao;
	
	@Override
	public int saveQuality(Quality quality) {
		quality.setCreatedDate(new Date(System.currentTimeMillis()));
		qualityDao.save(quality);
		return 1;
	}

	@Override
	public List<Quality> getAllQuality() {
		List<Quality> qualityLisobject=qualityDao.findAll();
		List<Quality> getQualityDataWithPartyName=new ArrayList<Quality>();

qualityLisobject.forEach(c->{
	 if(c.getPartyId()!=null)
	 {
		 String getPartyName= getPartyNameByPartyId(c.getPartyId());
		 if(getPartyName!=null && c.getPartyName()==null)
		 {
		 	c.setPartyName(getPartyName);
			 getQualityDataWithPartyName.add(c);
		 }
	 }

});
		return getQualityDataWithPartyName;
	}

	@Override
	public boolean updateQuality(Quality quality) throws Exception {
		var partyIndex= qualityDao.findById(quality.getId());
		if(!partyIndex.isPresent())
		return false;
		else
			qualityDao.save(quality);
		  return true;	
	}

	@Override
	public boolean deleteQualityById(Long id) {
		var partyIndex= qualityDao.findById(id);
		if(!partyIndex.isPresent())
		return false;
		else
			qualityDao.deleteById(id);
		 return true;	
	}

	@Override
	public List<Quality> getQualityByID(Long id) {
		var findQualityDataById=qualityDao.findById(id);
		if(findQualityDataById.isEmpty())
		{
		  System.out.println("No Record Found");
		  return null;
		}
		List<Quality> getQualityData=qualityDao.getQualityListById(findQualityDataById.get().getQualityId());
		return getQualityData;
	}

	@Override
	public String isQualityAlreadyExist(String qualityId) {
		 String quakit=qualityDao.isQualityNameExist(qualityId);
		 return quakit;
	}

	@Override
	public String getPartyNameByPartyId(Long partyId) {
		String partyName=partyDao.getPartyNameByPartyId(partyId);
		return partyName;
	}


}
