package com.main.glory.servicesImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.main.glory.model.quality.QualityWithPartyName;
import com.main.glory.model.quality.request.AddQualityRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.glory.Dao.PartyDao;
import com.main.glory.Dao.QualityDao;
import com.main.glory.model.quality.Quality;
import com.main.glory.services.QualityServiceInterface;

@Service("qualityServiceImp")
public class QualityServiceImp implements QualityServiceInterface{

	@Autowired
	private QualityDao qualityDao;
	
	@Autowired
	private PartyDao partyDao;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public int saveQuality(AddQualityRequest qualityDto) throws Exception {

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		Quality quality = modelMapper.map(qualityDto, Quality.class);

		System.out.println(quality);

		Optional<Quality> quality1 = qualityDao.findByQualityId(qualityDto.getQualityId());
		if(quality1.isPresent()){
			throw new Exception("Entry already exist for Quality Id:"+ qualityDto.getQualityId());
		}
		qualityDao.save(quality);
		return 1;
	}

	@Override
	public List<QualityWithPartyName> getAllQuality() {
		List<QualityWithPartyName> qualityListobject=qualityDao.findAllWithPartyName();
		return qualityListobject;
	}


	@Override
	public boolean updateQuality(Quality quality) throws Exception {
		var partyIndex = qualityDao.findById(quality.getId());
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
	public Optional<Quality> getQualityByID(Long id) {
		var quality =qualityDao.findById(id);
		return quality;
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
