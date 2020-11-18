package com.main.glory.servicesImpl;

import com.main.glory.Dao.PartyDao;
import com.main.glory.Dao.ProgramDao;
import com.main.glory.Dao.ProgramRecordDao;
import com.main.glory.Dao.QualityDao;
import com.main.glory.Dao.StockAndBatch.BatchDao;
import com.main.glory.model.Party;
import com.main.glory.model.Program;
import com.main.glory.model.ProgramRecord;
import com.main.glory.model.StockDataBatchData.BatchData;
import com.main.glory.model.StockDataBatchData.StockMast;
import com.main.glory.model.StockDataBatchData.response.GetAllBatchResponse;
import com.main.glory.model.quality.Quality;
import com.main.glory.services.ProgramServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("programServiceImpl")
public class ProgramServiceImpl implements ProgramServiceInterface {

    @Autowired
    private ProgramDao programDao;

    @Autowired
    private ProgramRecordDao programRecordDao;

    @Autowired
    private BatchImpl batchService;

    @Autowired
    private StockBatchServiceImpl stockBatchService;

    @Autowired
    private QualityDao qualityDao;

    @Autowired
    private PartyDao partyDao;


    @Autowired
    BatchDao batchDao;

    @Override
//    @Transactional
    public boolean saveProgram(Program program) throws Exception {
        try {

            var pdata = programDao.findById(program.getId());

            System.out.println("programData:"+pdata.get().getId());
            if (pdata.isPresent()) {
                var record =program.getProgram_record();

                //if the program is same but the record is diffrent
                ProgramRecord programRecord = new ProgramRecord();

                programRecord.setColour_tone(record.get(0).getColour_tone());
                programRecord.setLot_no(record.get(0).getLot_no());
                programRecord.setParty_shade_no(record.get(0).getParty_shade_no());
                programRecord.setProgramControlId(record.get(0).getProgramControlId());
                programRecord.setQuantity(record.get(0).getQuantity());
                programRecord.setRemark(record.get(0).getRemark());
                programRecord.setShade_no(record.get(0).getShade_no());
                programRecord.setId(null);
                System.out.println("RecordData:"+programRecord.getColour_tone());
                programRecordDao.saveAndFlush(programRecord);
                return true;


            } else {
                Optional<Quality> dataQuality = qualityDao.findByQualityId(program.getQuality_id());
                if (dataQuality.isPresent()) {

                    Optional<Party> party = partyDao.findById(program.getParty_id());
                    if (party.isPresent()) {
                        programDao.saveAndFlush(program);
                        return true;
                    } else {
                        return false;
                    }

                }
            }} catch(Exception ex){
                ex.printStackTrace();
            }



        return false;
    }

    @Override
    @Transactional
    public List<Program> getAllProgram() throws Exception {
        return programDao.findAll();
    }

    @Override
    public Program getProgramById(Long id) throws Exception {
        if (id != null) {
            var findProgram = programDao.findById(id);
            if (findProgram.isEmpty()) {
                System.out.println("No Record Found");
                return null;
            } else
                return findProgram.get();
        }
        return null;
    }

    @Override
    public boolean deleteProgramById(Long id) throws Exception {
        if (id != null) {
            var findProgram = programDao.findById(id);
            if (findProgram.isPresent()) {
                programDao.deleteById(id);
                return true;
            } else {
                System.out.println("Record not Found");
                return false;
            }
        }
            return false;
    }

    @Override
    public boolean updateProgramByID(Program bm) throws Exception {

        try {
            if (bm.getId() != null) {
                var findProgram = programDao.findById(bm.getId());
                if (findProgram.isPresent()) {
                    Optional<Quality> qid = qualityDao.findById(bm.getQuality_entry_id());
                    Optional<Quality> qualityId = qualityDao.findByQualityId(qid.get().getQualityId());

                    //If the user enter wrong quality_id then still it will store the information based on quality_entry_id
                    if (qid.isPresent() && qualityId.isPresent()) {
                        bm.setQuality_entry_id(qid.get().getId());
                        bm.setQuality_id(qid.get().getQualityId());
                        // System.out.println("qid:"+qid.get().getId());
                        //System.out.println("qid:"+qid.get().getQualityId());
                        programDao.saveAndFlush(bm);
                        return true;
                    } else
                        return false;
                } else {
                    System.out.println("Record not Found");
                    return false;
                }


            }
        }catch(Exception e)
        {
            return false;
        }
        return false;
    }

    public List<GetAllBatchResponse> getAllBatchByQuality(String qualityId) {
        Optional<Quality> quality = qualityDao.findByQualityId(qualityId);
        try {
            if (quality.isPresent())
            {
                //System.out.print(quality.get().getId());
                List<GetAllBatchResponse> allBatch=new ArrayList<>();
                List<StockMast> stock= stockBatchService.getAllStockBatch(quality.get().getId());
                //System.out.println("stockc:"+stock.toString());
                stock.forEach(e->{
                  //  System.out.println("e:"+e.getId());
                    List<GetAllBatchResponse> batchDataList = batchDao.findAllQTYControlId(e.getId());
                    //System.out.println("BatchData:"+batchDataList.toString());
                    allBatch.addAll(batchDataList);
                });

                //System.out.print(allBatch);
                return allBatch;

            } else
                throw new Exception("Quality not found");
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        return null;
    }
}
