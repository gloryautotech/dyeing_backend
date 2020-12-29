package com.main.glory.servicesImpl;

import com.main.glory.Dao.machine.ThermopackDao;
import com.main.glory.model.machine.AddMachineInfo.AddThermopackInfo;
import com.main.glory.model.machine.BoilerMachineRecord;
import com.main.glory.model.machine.MachineMast;
import com.main.glory.model.machine.Thermopack;
import com.main.glory.model.machine.request.GetRecordBasedOnFilter;
import com.main.glory.model.machine.response.BoilerFilter;
import com.main.glory.model.machine.response.ThermopackFilterRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("thermopackImpl")
public class ThermopackImpl {

    @Autowired
    ThermopackDao thermopackDao;

    @Autowired
    MachineServiceImpl machineService;

    public void saveThermopackRecord(List<AddThermopackInfo> thermopackRecord) throws Exception {


        List<Thermopack> list=new ArrayList<>();
        int i=0;
        for(AddThermopackInfo thermopack:thermopackRecord)
        {
            //check the machine is available or not
            MachineMast machineMastExist= machineService.getMachineByMachineId(thermopack.getControlId());

            Thermopack thermopackData=new Thermopack(thermopack);

            list.add(thermopackData);
            i++;
        }


        thermopackDao.saveAll(list);
    }

    public List<Thermopack> getAllMachineRecord() throws Exception{

        List<Thermopack> thermopackList = thermopackDao.findAll();
        if(thermopackList.isEmpty())
            throw new Exception("no data found");

        return thermopackList;

    }

    public List<ThermopackFilterRecord> getDataBasedOnFilter(GetRecordBasedOnFilter record) throws Exception {


        List<ThermopackFilterRecord> thermopackFilterRecordList=new ArrayList<>();

        String attribute = record.getAttribute();//based on attribute
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                "yyyy-MM-dd");

        Date fromDate = datetimeFormatter1.parse(record.getFromDate());

        Date toDate = datetimeFormatter1.parse(record.getToDate());

        List<Thermopack> thermopackFilterRecords;

        switch (attribute)
        {
            case "forwardTemp":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getForwardTemp());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;
            case "returnTemp":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getReturnTemp());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;
            case "stackTemp":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getStackTemp());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;

            case "furnaceTemp":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getFurnaceTemp());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;
            case "pumpData":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getPumpData());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;
            case "idFan":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getIdFan());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;
            case "fdFan":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getFdFan());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;

            case "screwFeeder":

                thermopackFilterRecords=thermopackDao.findByControlIdAndTime(record.getControlId(),record.getToTime(),record.getToTime(),fromDate,toDate);

                if(thermopackFilterRecords.isEmpty())
                    throw new Exception("no data found");


                for(Thermopack thermopack:thermopackFilterRecords)
                {
                    ThermopackFilterRecord thermopackFilterRecord=new ThermopackFilterRecord(thermopack,thermopack.getScrewFeeder());
                    thermopackFilterRecordList.add(thermopackFilterRecord);

                }
                return thermopackFilterRecordList;



            default:throw new Exception("no data found for iven attribute");
        }

    }
}
