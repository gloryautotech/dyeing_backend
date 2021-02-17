package com.main.glory.servicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.main.glory.Dao.user.UserDao;
import com.main.glory.model.basic.PartyQuality;
import com.main.glory.model.basic.QualityData;
import com.main.glory.model.basic.QualityParty;
import com.main.glory.model.party.Party;
import com.main.glory.model.quality.QualityWithPartyName;
import com.main.glory.model.quality.request.AddQualityRequest;
import com.main.glory.model.quality.request.UpdateQualityRequest;
import com.main.glory.model.quality.response.GetAllQualtiy;
import com.main.glory.model.quality.response.GetQualityResponse;
import com.main.glory.model.user.UserData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.glory.Dao.PartyDao;
import com.main.glory.Dao.QualityDao;
import com.main.glory.model.quality.Quality;
import com.main.glory.services.QualityServiceInterface;

@Service("qualityServiceImp")
public class QualityServiceImp implements QualityServiceInterface {

    @Autowired
    UserDao userDao;

    @Autowired
    private QualityDao qualityDao;

    @Autowired
    private PartyDao partyDao;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public int saveQuality(AddQualityRequest qualityDto) throws Exception {

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        Quality quality = new Quality(qualityDto);

        String quality1 = qualityDao.isQualityNameExist(qualityDto.getQualityId());
        if (quality1 != null)
            throw new Exception("Quality id is already exist");

        if(quality.getUnit().equals("weight"))
            quality.setWtPer100m(1.0);

        qualityDao.save(quality);
        return 1;
    }


    @Override
    public List<GetQualityResponse> getAllQuality(Long id, String getBy) throws Exception {
        List<QualityWithPartyName> qualityListobject = null;
        List<GetQualityResponse> quality = null;
        if (id == null) {
            qualityListobject = qualityDao.findAllWithPartyName();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            quality = modelMapper.map(qualityListobject, List.class);
        } else if (getBy.equals("group")) {
            UserData userData = userDao.findUserById(id);

            if(userData.getUserHeadId()==0) {
                //master user
                qualityListobject = qualityDao.findAllWithPartyByCreatedAndHeadId(id,id);
                modelMapper.getConfiguration().setAmbiguityIgnored(true);
                quality = modelMapper.map(qualityListobject, List.class);
            }
            else
            {
                qualityListobject = qualityDao.findAllWithPartyByCreatedAndHeadId(id,userData.getUserHeadId());
                modelMapper.getConfiguration().setAmbiguityIgnored(true);
                quality = modelMapper.map(qualityListobject, List.class);
            }



        } else if (getBy.equals("own")) {
            qualityListobject = qualityDao.findAllWithPartyNameByCreatedBy(id);
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            quality = modelMapper.map(qualityListobject, List.class);
        }

        if (quality.isEmpty())
            throw new Exception("quality not added yet");
        return quality;
    }


    @Override
    public boolean updateQuality(UpdateQualityRequest qualityDto) throws Exception {
        var qualityData = qualityDao.findById(qualityDto.getId());
        if (!qualityData.isPresent())
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
            qualityData.get().setRate(qualityDto.getRate());
            qualityDao.save(qualityData.get());
            return true;
        }
    }

    @Override
    public boolean deleteQualityById(Long id) {
        var partyIndex = qualityDao.findById(id);
        if (!partyIndex.isPresent())
            return false;
        else
            qualityDao.deleteById(id);
        return true;
    }

    @Override
    public GetQualityResponse getQualityByID(Long id) {
        Optional<Quality> quality = qualityDao.findById(id);
        if (!quality.isPresent())
            return null;
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        GetQualityResponse quality1 = modelMapper.map(quality.get(), GetQualityResponse.class);

        return quality1;
    }

    @Override
    public String isQualityAlreadyExist(String qualityId) {
        String quakit = qualityDao.isQualityNameExist(qualityId);
        return quakit;
    }

    @Override
    public String getPartyNameByPartyId(Long partyId) {
        String partyName = partyDao.getPartyNameByPartyId(partyId);
        return partyName;
    }


    public QualityParty getAllQualityWithParty(Long id) throws Exception {

        Optional<Quality> qualityLists = qualityDao.findById(id);


        if (!qualityLists.isPresent())
            throw new Exception("No quality found");


        QualityParty qualityParties = new QualityParty(qualityLists.get());

        Optional<Party> party = partyDao.findById(qualityLists.get().getPartyId());

        qualityParties.setPartyName(party.get().getPartyName());

        if (qualityParties == null)
            throw new Exception("no data faund");

        return qualityParties;


    }

    public PartyQuality getAllPartyWithQuality(Long partyId) throws Exception {

        Optional<List<Quality>> qualityList = qualityDao.findByPartyId(partyId);

        Optional<Party> partName = partyDao.findById(partyId);
        if (!partName.isPresent())
            throw new Exception("No such Party id available with id:" + partyId);
        if (!qualityList.isPresent()) {
            throw new Exception("Add Quality data for partyId:" + partyId);
        }
        PartyQuality partyQualityData = new PartyQuality();

        List<QualityData> qualityDataList = new ArrayList<>();
        for (Quality quality : qualityList.get()) {

            if (qualityList.get().isEmpty())
                continue;

            QualityData qualityData = new QualityData(quality);
            qualityData.setPartyName(partName.get().getPartyName());
            qualityDataList.add(qualityData);


        }
        partyQualityData.setQualityDataList(qualityDataList);
        partyQualityData.setPartyId(partyId);


        partyQualityData.setPartyName(partName.get().getPartyName());


        if (partyQualityData == null)
            throw new Exception("no data faund");

        return partyQualityData;


    }

    public List<GetAllQualtiy> getAllQualityData() throws Exception {
        List<Quality> qualities = qualityDao.getAllQuality();
        List<GetAllQualtiy> getAllQualtiyList = new ArrayList<>();
        for (Quality quality : qualities) {
            Optional<Party> partyName = partyDao.findById(quality.getPartyId());
            if (!partyName.isPresent())
                continue;

            GetAllQualtiy getAllQualtiy = new GetAllQualtiy(quality);
            getAllQualtiy.setPartyName(partyName.get().getPartyName());
            getAllQualtiyList.add(getAllQualtiy);
        }
        if (getAllQualtiyList.isEmpty())
            throw new Exception("no data found");
        return getAllQualtiyList;
    }

    public List<PartyQuality> getAllPartyWithQualityByMaster(Long userHeadId) throws Exception {

        Optional<List<Quality>> QualityList = qualityDao.findByUserHeadId(userHeadId);

        if (!QualityList.isPresent()) {
            throw new Exception("No quality found for master:" + userHeadId);
        }
        List<Party> partyList = partyDao.findByUserHeadId(userHeadId);
        if (partyList.isEmpty()) {
            throw new Exception("No party found for master:" + userHeadId);
        }

        List<PartyQuality> partyQualityList = new ArrayList<>();
        for (Party party : partyList) {
            Optional<List<Quality>> quality = qualityDao.findByPartyId(party.getId());
            if (quality.isPresent()) {
                PartyQuality partyQuality = new PartyQuality();
                partyQuality.setPartyId(party.getId());
                partyQuality.setPartyName(party.getPartyName());
                List<QualityData> qualityDataList = new ArrayList<>();
                for (Quality quality1 : quality.get()) {
                    QualityData qualityData = new QualityData(quality1);
                    qualityDataList.add(qualityData);

                }
                partyQuality.setQualityDataList(qualityDataList);
                partyQualityList.add(partyQuality);
            }

        }

        if (partyQualityList.isEmpty())
            throw new Exception("no data found");

        return partyQualityList;
    }

    public Optional<Quality> getQualityByIDAndPartyId(Long qualityEntryId, Long partyId) throws Exception {
        Optional<Quality> quality = qualityDao.findByPartyIdAndQualityId(qualityEntryId, partyId);

        if (!quality.isPresent())
            throw new Exception("Quality data not found for party");

        return quality;

    }
}
