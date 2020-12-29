package com.main.glory.model.machine;

import com.main.glory.model.machine.AddMachineInfo.AddBoilerMachineRecord;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table

public class BoilerMachineRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Double streamPressusre;
    Long drumWaterLevel;
    Long feedPump;
    Long flueGasTemp;
    Long bedTemp;
    Double draftPressure;
    Long idFan;
    Long jetRunning;
    Long daOne;
    Long daTwo;
    Long daThree;
    Long screwFeeder;
    Long waterMeter;
    Long loadData;
    Long timeOf;
    Date dateToEnter;
    Date createdDate;
    Long controlId;
    Long userHeadId;

    public BoilerMachineRecord(AddBoilerMachineRecord boilerMachineRecord) throws ParseException {
        this.streamPressusre=boilerMachineRecord.getStreamPressusre();
        this.draftPressure=boilerMachineRecord.getDraftPressure();
        this.feedPump=boilerMachineRecord.getFeedPump();
        this.flueGasTemp= boilerMachineRecord.getBedTemp();
        this.bedTemp=boilerMachineRecord.getBedTemp();
        this.draftPressure=boilerMachineRecord.getDraftPressure();
        this.idFan=boilerMachineRecord.getIdFan();
        this.jetRunning=boilerMachineRecord.getJetRunning();
        this.daOne=boilerMachineRecord.getDaOne();
        this.daThree=boilerMachineRecord.getDaThree();
        this.daTwo=boilerMachineRecord.getDaTwo();
        this.screwFeeder=boilerMachineRecord.getScrewFeeder();
        this.waterMeter=boilerMachineRecord.getWaterMeter();
        this.loadData=boilerMachineRecord.getLoadData();
        this.controlId=boilerMachineRecord.getControlId();
        this.userHeadId=boilerMachineRecord.getUserHeadId();
        this.timeOf=Long.parseLong(boilerMachineRecord.getTimeOf());


        //date to time
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date lFromDate2 = datetimeFormatter1.parse(boilerMachineRecord.getDateToEnter());

        Timestamp fromTS2 = new Timestamp(lFromDate2.getTime());

        this.dateToEnter=fromTS2;



    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date(System.currentTimeMillis()+19800000);
    }


}
