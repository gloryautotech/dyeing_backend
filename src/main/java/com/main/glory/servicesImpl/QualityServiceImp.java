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
import com.main.glory.model.quality.response.GetAllQualtiy;
import com.main.glory.model.quality.response.GetQualityResponse;
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

		Optional<Quality> quality1 = qualityDao.findByQualityEntryId(qualityDto.getQualityId());
		if(quality1.isPresent())
			throw new Exception("Quality id is already exist");

		qualityDao.save(quality);
		return 1;
	}



	@Override
	public List<GetQualityResponse> getAllQuality(Long id, String getBy) {
		List<QualityWithPartyName> qualityListobject = null;
		List<GetQualityResponse> quality = null;
		if(id == null){
			qualityListobject=qualityDao.findAllWithPartyName();
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			quality = modelMapper.map(qualityListobject, List.class);
		}
		else if(getBy.equals("group")){
			qualityListobject=qualityDao.findAllWithPartyNameByUserHeadId(id);
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			quality = modelMapper.map(qualityListobject, List.class);
		}
		else if(getBy.equals("own")){
			qualityListobject=qualityDao.findAllWithPartyNameByCreatedBy(id);
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			quality = modelMapper.map(qualityListobject, List.class);
		}

		return quality;
	}


	@Override
	public boolean updateQuality(UpdateQualityRequest qualityDto) throws Exception {
		var qualityData = qualityDao.findById(qualityDto.getId());
		if(!qualityData.isPresent())
			return false;
		else {
			qualityData.get().setPartyId(qualityDto.getPartyId());
			qualityData.get().setQualityId(qualityDto.getQualityId());
			qualityData.get().setQualityName(qualityDto.getQualityName());
			qualityData.get().setQualityType(qualityDto.getQualityType());
			qualityData.get().setUnit(qualityDto.getUnit());
			qualityData.get().setWtPer100m(qualityDto.getWtPer100m());
			qualityData.get().setRemark(qualityDto.getRemark());
			qualityData.get().setUpdatedBy(qualityDto.getUpdatedBy());
			qualityData.get().setQualityDate(qualityDto.getQualityDate());
			qualityDao.save(qualityData.get());
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


	public QualityParty getAllQualityWithParty(Long id) throws Exception {

		Optional<Quality> qualityLists=qualityDao.findById(id);


		if(!qualityLists.isPresent())
			throw new Exception("No quality found");


		QualityParty qualityParties = new QualityParty(qualityLists.get());

		Optional<Party> party=partyDao.findById(qualityLists.get().getPartyId());

		qualityParties.setPartyName(party.get().getPartyName());


		return qualityParties;


	}

	public PartyQuality getAllPartyWithQuality(Long partyId) throws Exception{

		Optional<List<Quality>> qualityList = qualityDao.findByPartyId(partyId);

		Optional<Party> partName=partyDao.findById(partyId);
		if(!partName.isPresent() || partName.get().getIsDelete()==true)
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

				QualityData qualityData = new QualityData(quality);

				qualityData.setPartyName(partName.get().getPartyName());
				qualityDataList.add(qualityData);



			}
			partyQualityData.setQualityDataList(qualityDataList);
			partyQualityData.setPartyId(partyId);


			partyQualityData.setPartyName(partName.get().getPartyName());





		return partyQualityData;



	}

    public List<GetAllQualtiy> getAllQualityData() {
		List<Quality> qualities = qualityDao.findAll();
		List<GetAllQualtiy> getAllQualtiyList =new ArrayList<>();
		for(Quality quality:qualities)
		{
			Optional<Party> partyName=partyDao.findById(quality.getPartyId());
			if(!partyName.isPresent() || partyName.get().getIsDelete()==true)
				continue;

			GetAllQualtiy getAllQualtiy=new GetAllQualtiy(quality);
			getAllQualtiy.setPartyName(partyName.get().getPartyName());
			getAllQualtiyList.add(getAllQualtiy);
		}
		return getAllQualtiyList;
    }

	public List<PartyQuality> getAllPartyWithQualityByMaster(Long userHeadId) throws Exception {

		Optional<List<Quality>> QualityList = qualityDao.findByUserHeadId(userHeadId);

		if(!QualityList.isPresent())
		{
			throw new Exception("No quality found for master:"+userHeadId);
		}
		List<Party> partyList = partyDao.findByUserHeadId(userHeadId);
		if(partyList.isEmpty())
		{
			throw new Exception("No party found for master:"+userHeadId);
		}

		List<PartyQuality> partyQualityList=new ArrayList<>();
		for(Party party:partyList)
		{
			Optional<List<Quality>> quality = qualityDao.findByPartyId(party.getId());
			if(quality.isPresent())
			{
				PartyQuality partyQuality=new PartyQuality();

				if(party.getIsDelete()==true)
					continue;

				partyQuality.setPartyId(party.getId());
				partyQuality.setPartyName(party.getPartyName());
				List<QualityData> qualityDataList=new ArrayList<>();
				for(Quality quality1:quality.get())
				{
					QualityData qualityData=new QualityData(quality1);
					qualityDataList.add(qualityData);

				}
				partyQuality.setQualityDataList(qualityDataList);
				partyQualityList.add(partyQuality);
			}

		}

		return partyQualityList;
	}
}
