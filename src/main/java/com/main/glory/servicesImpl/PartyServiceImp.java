package com.main.glory.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.main.glory.model.party.request.AddParty;
import com.main.glory.model.party.request.PartyWithName;
import com.main.glory.model.quality.Quality;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.glory.Dao.PartyDao;
import com.main.glory.model.party.Party;
import com.main.glory.services.PartyServiceInterface;

@Service("partyServiceImp")
public class PartyServiceImp implements PartyServiceInterface{

	@Autowired
	private PartyDao partyDao;

	@Autowired
	ModelMapper modelMapper;

	public void saveParty(AddParty party) throws Exception {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		if(party!=null)
		{
			Party partyData = new Party(party);

			if(party.getGSTIN().isEmpty())
			{
				partyDao.save(partyData);
				return;
			}

			Optional<Party> gstAvailable = partyDao.findByGSTIN(party.getGSTIN());
			if(gstAvailable.isPresent())
				throw new Exception("GST No."+party.getGSTIN()+" is already exist");

			partyDao.save(partyData);

		}
	}

	@Override
	public List<Party> getAllPartyDetails(Long id,String getBy) {
		List<Party> partyDetailsList = null;
		if(id == null){
			partyDetailsList=partyDao.findAll();
		}
		else if(getBy.equals("group")){
			partyDetailsList=partyDao.findByUserHeadId(id);
		}
		else if(getBy.equals("own")){
			partyDetailsList=partyDao.findByCreatedBy(id);
		}
		return partyDetailsList;
	}

	@Override
	public Party getPartyDetailById(Long id) throws Exception {
		var partyData=partyDao.findById(id);
		if(partyData.isEmpty())
			return null;
		else
		{
			if(partyData.get().getIsDelete()==true)
			{
				throw new Exception("Data is not available for use because data is softly deleted");
			}
			else
				return partyData.get();
		}

	}

	@Override
	public boolean editPartyDetails(Party party) throws Exception {
  		var partyIndex= partyDao.findById(party.getId());
		if(!partyIndex.isPresent())
		return false;
		else
		partyDao.save(party);
		  return true;	
	}

	@Override
	public boolean deletePartyById(Long id) {

  		Optional<Party> partyIndex= partyDao.findById(id);
		if(!partyIndex.isPresent())
		return false;
		else
		{
			partyIndex.get().setIsDelete(false);
			partyDao.save(partyIndex.get());
			return true;
		}


	}

	@Override
	public String getPartyNameByPartyId(Long partyId) {
		String partyName=partyDao.getPartyNameByPartyId(partyId);
		return partyName;
	}

    public List<PartyWithName> getAllPartyNameWithId() {
		try {
			List<Party> partyAll = partyDao.findAll();

			List<PartyWithName> partyWithNameList = new ArrayList<>();
			if(!partyAll.isEmpty()) {
				partyAll.forEach(e ->
				{
					if(e.getIsDelete()==false)
					{
						PartyWithName partyWithName = new PartyWithName(e.getId(), e.getPartyName());
						System.out.println(partyWithName.getId());
						partyWithNameList.add(partyWithName);

					}

				});
			}
			return partyWithNameList;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
    }
}
