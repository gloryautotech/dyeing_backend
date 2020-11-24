package com.main.glory.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.main.glory.model.basic.PartyQuality;
import com.main.glory.model.basic.QualityData;
import com.main.glory.model.basic.QualityParty;
import com.main.glory.model.party.Party;
import com.main.glory.model.quality.QualityWithPartyName;
import com.main.glory.model.quality.request.AddQualityRequest;
import com.main.glory.model.quality.request.UpdateQualityRequest;
import com.main.glory.model.quality.response.GetQualityResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.Part;
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


		qualityDao.save(quality);
		return 1;
	}



	@Override
	public List<GetQualityResponse> getAllQuality() {
		List<QualityWithPartyName> qualityListobject=qualityDao.findAllWithPartyName();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		List<GetQualityResponse> quality = modelMapper.map(qualityListobject, List.class);
		return quality;
	}


	@Override
	public boolean updateQuality(UpdateQualityRequest qualityDto) throws Exception {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		Quality quality = modelMapper.map(qualityDto, Quality.class);
		var partyIndex = qualityDao.findById(qualityDto.getId());
		if(!partyIndex.isPresent())
			return false;
		else {
			qualityDao.save(quality);
			return true;
		}
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
	public GetQualityResponse getQualityByID(Long id) {
		Optional<Quality> quality =qualityDao.findById(id);
		if(!quality.isPresent())
			return null;
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		GetQualityResponse quality1 = modelMapper.map(quality.get(), GetQualityResponse.class);
		return quality1;
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


	public List<QualityParty> getAllQualityWithParty() {

		List<QualityWithPartyName> qualityLists=qualityDao.findAllWithPartyName();
		List<QualityParty> qualityParties = new ArrayList<>();

		for(QualityWithPartyName quality : qualityLists)
		{
			QualityParty qualityParty=new QualityParty();

			qualityParty.setQualityEntryId(quality.getId());
			qualityParty.setQualityId(quality.getQualityId());
			qualityParty.setQualityName(quality.getQualityName());
			qualityParty.setQualityType(quality.getQualityType());
			qualityParty.setPartyId(quality.getPartyId());

			qualityParty.setPartyName(quality.getPartyName());

			qualityParties.add(qualityParty);
		}


		return qualityParties;


	}

	public PartyQuality getAllPartyWithQuality(Long partyId) throws Exception{

		Optional<List<Quality>> qualityList = qualityDao.findByPartyId(partyId);

		Optional<Party> partName=partyDao.findById(partyId);
		if(!partName.isPresent())
			throw new Exception("No such Party id available with id:"+partyId);
		if(!qualityList.isPresent()) {
		throw new Exception("Add Quality data for partyId:"+partyId);
		}
		PartyQuality partyQualityData =new PartyQuality();

		List<QualityData> qualityDataList = new ArrayList<>();
			for(Quality quality:qualityList.get())
			{

				if(qualityList.get().isEmpty())
					continue;

				QualityData qualityData = new QualityData();
				qualityData.setQualityEntryId(quality.getId());
				qualityData.setQualityId(quality.getQualityId());
				qualityData.setQualityName(quality.getQualityName());
				qualityData.setQualityType(quality.getQualityType());
				qualityDataList.add(qualityData);



			}
			partyQualityData.setQualityDataList(qualityDataList);
			partyQualityData.setPartyId(partyId);


			partyQualityData.setPartyName(partName.get().getPartyName());





		return partyQualityData;



	}

}
