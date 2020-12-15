package com.main.glory.model.program.request;

import com.main.glory.model.program.ProgramRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateProgramWithProgramRecord {

    Long id;
    private Long partyId;
    private Long qualityEntryId;
    private String programGivenBy;
    private String remark;
    private String priority;
    private Long updatedBy;
    private Long createdBy;
    private Date createdDate;
    List<ProgramRecord> programRecords;
}
