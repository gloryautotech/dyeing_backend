package com.main.glory.servicesImpl;

import com.main.glory.Dao.dyeingProcess.DyeingChemicalDataDao;
import com.main.glory.Dao.dyeingProcess.DyeingProcessDataDao;
import com.main.glory.Dao.dyeingProcess.DyeingProcessMastDao;
import com.main.glory.Dao.dyeingProcess.TagDyeingProcess.TagDyeingProcessDataDao;
import com.main.glory.Dao.dyeingProcess.TagDyeingProcess.TagDyeingProcessMastDao;
import com.main.glory.Dao.user.UserDao;
import com.main.glory.model.ConstantFile;
import com.main.glory.model.dyeingProcess.DyeingChemicalData;
import com.main.glory.model.dyeingProcess.DyeingProcessData;
import com.main.glory.model.dyeingProcess.DyeingProcessMast;
import com.main.glory.model.dyeingProcess.TagDyeingProcess.TagDyeingProcessData;
import com.main.glory.model.dyeingProcess.TagDyeingProcess.TagDyeingProcessMast;
import com.main.glory.model.dyeingProcess.request.GetAllDyeingProcessList;
import com.main.glory.model.shade.ShadeMast;
import com.main.glory.model.supplier.SupplierRate;
import com.main.glory.model.user.Permissions;
import com.main.glory.model.user.UserData;
import com.main.glory.model.user.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dyeingProcessServiceImpl")
public class DyeingProcessServiceImpl {


    @Autowired
    TagDyeingProcessDataDao tagDyeingProcessDataDao;

    @Autowired
    TagDyeingProcessMastDao tagDyeingProcessMastDao;

    @Autowired
    UserDao userDao;
    @Autowired
    SupplierServiceImpl supplierService;

    @Autowired
    ShadeServiceImpl shadeService;
    @Autowired
    DyeingChemicalDataDao dyeingChemicalDataDao;

    @Autowired
    DyeingProcessDataDao dyeingProcessDataDao;

    @Autowired
    DyeingProcessMastDao dyeingProcessMastDao;

    ConstantFile constantFile;

    public void addDyeingProcess(DyeingProcessMast data) throws Exception {
        //check the dyeing process already exist with the name or not
        DyeingProcessMast dyeingProcessMastExistWithName = dyeingProcessMastDao.getDyeingProcessByName(data.getProcessName());
        if(dyeingProcessMastExistWithName!=null)
            throw new Exception(constantFile.DyeingProcess_Name_Exist);
        dyeingProcessMastDao.save(data);
    }

    public List<GetAllDyeingProcessList> getAllDyeingProcess(String id) throws Exception {

        Long userId = Long.parseLong(id);

        UserData userData = userDao.getUserById(userId);
        Long userHeadId=null;

        UserPermission userPermission = userData.getUserPermissionData();
        List<GetAllDyeingProcessList> list = new ArrayList<>();
        List<DyeingProcessMast> processList = null;
        Permissions permissions = new Permissions(userPermission.getPr().intValue());

        /*if (permissions.getViewAll())
        {
            userId=null;
            userHeadId=null;
            processList = dyeingProcessMastDao.getAllProcess();
        }
        else if (permissions.getViewGroup()) {
            //check the user is master or not ?
            //admin
            if(userData.getUserHeadId() == 0)
            {
                userId=null;
                userHeadId=null;
                processList = dyeingProcessMastDao.getAllProcess();
            }
            else if(userData.getUserHeadId() > 0)
            {
                //check for master or operator
                UserData userHead = userDao.getUserById(userData.getUserHeadId());
                    userId=userData.getId();
                    userHeadId=userHead.getId();
                    processList = dyeingProcessMastDao.getAllDyeingProcessByCreatedAndHead(userId,userHeadId);


            }

        }
        else if (permissions.getView()) {
            userId = userData.getId();
            userHeadId=null;
            processList = dyeingProcessMastDao.getAllDyeingProcessByCreatedAndHead(userId,userHeadId);
        }*/




        processList = dyeingProcessMastDao.getAllProcess();
        for(DyeingProcessMast d:processList)
        {
            list.add(new GetAllDyeingProcessList(d));
        }

        return list;

    }

    public DyeingProcessMast getDyeingProcessById(Long processId) {

        DyeingProcessMast dyeingProcessMast = dyeingProcessMastDao.getDyeingProcessById(processId);
        DyeingProcessMast x = new DyeingProcessMast(dyeingProcessMast);
        int i=0;
        List<DyeingProcessData> dyeingProcessDataList = new ArrayList<>();
        for(DyeingProcessData dyeingProcessData:x.getDyeingProcessData())
        {
            List<DyeingChemicalData> dyeingChemicalDataList = new ArrayList<>();

            for(DyeingChemicalData dyeingChemicalData:dyeingProcessData.getDyeingChemicalData())
            {
                SupplierRate supplierRate = supplierService.getSupplierRateByItemId(dyeingChemicalData.getItemId());
                dyeingChemicalData.setItemName(supplierRate.getItemName());
                dyeingChemicalDataList.add(dyeingChemicalData);
            }

            dyeingProcessData.setDyeingChemicalData(dyeingChemicalDataList);

            dyeingProcessDataList.add(dyeingProcessData);
        }
        x.setDyeingProcessData(dyeingProcessDataList);

        return x;
    }

    public List<DyeingProcessData> getDyeingProcessDataById(Long id) throws Exception {
        List<DyeingProcessData> dyeingProcessDataList = dyeingProcessDataDao.findDyeingProcessDataByControlId(id);
        if(dyeingProcessDataList.isEmpty())
            throw new Exception(constantFile.DyeingProcess_Not_Found+id);
        return dyeingProcessDataList;
    }

    public List<DyeingChemicalData> getChemicalListByDyeingProcessDataId(Long id) {

        List<DyeingChemicalData> list = dyeingChemicalDataDao.getChemicalListByControlId(id);
        return list;
    }

    public void updateDyeingProcess(DyeingProcessMast data) throws Exception {

        DyeingProcessMast dyeingProcessMastExist = dyeingProcessMastDao.getDyeingProcessById(data.getId());

        if(dyeingProcessMastExist==null)
        {
            throw new Exception(constantFile.DyeingProcess_Not_Found+data.getId());
        }
        List<ShadeMast> getAllShade= shadeService.getAllShadeByProcessId(dyeingProcessMastExist.getId());
        DyeingProcessMast x = dyeingProcessMastDao.save(data);

        if(!getAllShade.isEmpty()) {
            for (ShadeMast s : getAllShade) {
                shadeService.updateShadeProcessId(s.getId(), x.getId());
            }
        }



    }

    public Boolean deleteByProcessId(Long id) throws Exception {
        Boolean flag=false;
        DyeingProcessMast dyeingProcessMastExist = dyeingProcessMastDao.getDyeingProcessById(id);

        if(dyeingProcessMastExist!=null)
        {
            flag=true;
            //check the process is related to shade or not

            List<ShadeMast> shadeMastList = shadeService.getAllShadeMastByProcessIdForDeleteProcess(dyeingProcessMastExist.getId());
            if(!shadeMastList.isEmpty())
            throw new Exception(constantFile.Shade_Exist);

            dyeingProcessMastDao.deleteByProcessId(id);
        }
        else
            flag = false;
        return flag;

    }

    public DyeingProcessData getDyeingProcessDataByItemId(Long supplierItemId) {
        return null;
    }

    public List<DyeingChemicalData> getDyeingProcessChemicalDataByItemId(Long id) {
        List<DyeingChemicalData> chemicalList = dyeingChemicalDataDao.getChemicalDataByItemId(id);
        return chemicalList;
    }

    public Boolean dyeingProcessExistWithName(String name, Long id) {
        //if the id is not null then it is inserr request and check in entire db or else/
        //check the record except the given id

        if(id==null) {
            DyeingProcessMast dyeingProcessMast = dyeingProcessMastDao.getDyeingProcessByName(name);
            if (dyeingProcessMast == null)
                return true;
            else
                return false;
        }
        else
        {
            DyeingProcessMast dyeingProcessMast = dyeingProcessMastDao.getDyeingProcessByNameExceptId(name,id);
            if (dyeingProcessMast == null)
                return true;
            else
                return false;
        }
    }


    public void addTagDyeingProcess(TagDyeingProcessMast data) throws Exception {

        //Check that the same tag name procecss exit or not
        TagDyeingProcessMast tagDyeingProcessMastExist = tagDyeingProcessMastDao.getTagDyeingProcessNameExistExceptId(data.getTagProcessName(),0l);

        if(tagDyeingProcessMastExist!=null)
            throw new Exception(ConstantFile.TagDyeingProcess_Name_Exist);

        //check that the supplier item exist or not
        for(TagDyeingProcessData record : data.getDyeingTagDataList())
        {
            SupplierRate supplierRate = supplierService.getSupplierRateByItemId(record.getItemId());
            if(supplierRate==null)
                throw new Exception(ConstantFile.SupplierRate_Not_Exist);
        }

        tagDyeingProcessMastDao.save(data);
    }

    public void updateTagDyeingProcess(TagDyeingProcessMast data) throws Exception {

        //check that the record is exist or not
        TagDyeingProcessMast tagDyeingProcessMastExist = tagDyeingProcessMastDao.getTagDyeingProcessById(data.getId());
        if(tagDyeingProcessMastExist==null)
            throw new Exception(ConstantFile.TagDyeingProcess_Not_Exist);

        //Check that the same tag name procecss exit or not
        tagDyeingProcessMastExist = tagDyeingProcessMastDao.getTagDyeingProcessNameExistExceptId(data.getTagProcessName(),data.getId());

        if(tagDyeingProcessMastExist!=null)
            throw new Exception(ConstantFile.TagDyeingProcess_Name_Exist);

        //check that the supplier item exist or not
        for(TagDyeingProcessData record : data.getDyeingTagDataList())
        {
            SupplierRate supplierRate = supplierService.getSupplierRateByItemId(record.getItemId());
            if(supplierRate==null)
                throw new Exception(ConstantFile.SupplierRate_Not_Exist);
        }

        tagDyeingProcessMastDao.save(data);
    }

    public TagDyeingProcessMast getTagDyeignProcessById(Long id) {

        return tagDyeingProcessMastDao.getTagDyeingProcessById(id);
    }

    public List<TagDyeingProcessMast> getAllTagDyeignProcess() {
        return tagDyeingProcessMastDao.getAllTagDyeingProcessMast();
    }

    public TagDyeingProcessMast getTagDyeingProcessExist(Long id, String name) {

        TagDyeingProcessMast tagDyeingProcessMast = null;
        if(id==0 || id == null)
        {
            tagDyeingProcessMast= tagDyeingProcessMastDao.getTagDyeingProcessNameExistExceptId(name,0l);
        }
        else
        {
            tagDyeingProcessMast= tagDyeingProcessMastDao.getTagDyeingProcessNameExistExceptId(name,id);
        }
        return tagDyeingProcessMast;
    }

    public void deleteTagDyeignProcessById(Long id) throws Exception {

        //check the tag dyeing process is exist or not
        TagDyeingProcessMast tagDyeingProcessMast = tagDyeingProcessMastDao.getTagDyeingProcessById(id);
        if(tagDyeingProcessMast==null)
            throw new Exception(ConstantFile.TagDyeingProcess_Not_Exist);

        tagDyeingProcessMastDao.deleteTagDyeingProcessById(id);
        //delete the child data as well
        tagDyeingProcessDataDao.deleteTagProcessDataByControlId(id);
    }
}
