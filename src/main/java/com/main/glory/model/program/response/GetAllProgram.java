package com.main.glory.model.program.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class GetAllProgram {

    Long id;
    Long partyId;
    String partyName;
    String programGivenBy;
    Long qualityEntryId;
    String qualityId;
    String qualityName;
    String qualityType;
    Long userHeadId;
    Long createdBy;
    Long updatedBy;
    String priority;
    String remark;



}
