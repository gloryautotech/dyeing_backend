package com.main.glory.model.shade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.glory.model.StockDataBatchData.request.BatchDetail;
import com.main.glory.model.productionPlan.ProductionPlan;
import com.main.glory.model.shade.requestmodals.AddShadeMast;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ShadeMast")
@ToString
	public class ShadeMast {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String partyShadeNo;
	Long processId;
	Long qualityEntryId;
	Long partyId;
	String colorTone;
	Long createdBy;
	Long updatedBy;
	@ApiModelProperty(hidden = true)
	Date createdDate;
	@ApiModelProperty(hidden = true)
	Date updatedDate;;
	Long userHeadId;
	Long cuttingId;
	String remark;
	String category;
	String labColorNo;
	String processName;
	String apcNo;
	Boolean pending;
	Double extraRate;
	@Column(columnDefinition = "varchar(255) default 'Not mentioned'")
	String colorName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "controlId", referencedColumnName = "id")
	List<ShadeData> shadeDataList;


	@PrePersist
	protected void onCreate(){this.createdDate=new Date(System.currentTimeMillis());}
	@PreUpdate
	protected void onUpdate(){ this.updatedDate = new Date(System.currentTimeMillis()); }

	public ShadeMast(AddShadeMast addShadeMast)
	{
		this.apcNo =addShadeMast.getApcNo();
		this.pending=addShadeMast.getPending();
		this.id=addShadeMast.getPartyId();
		this.partyShadeNo=addShadeMast.getPartyShadeNo();
		this.processId=addShadeMast.getProcessId();
		this.partyId=addShadeMast.getPartyId();
		this.colorTone=addShadeMast.getColorTone();
		this.createdBy=addShadeMast.getCreatedBy();
		this.cuttingId=addShadeMast.getCuttingId();
		this.remark=addShadeMast.getRemark();
		this.category=addShadeMast.getCategory();
		this.labColorNo=addShadeMast.getLabColorNo();
		this.processName=addShadeMast.getProcessName();
		this.userHeadId=addShadeMast.getUserHeadId();
		this.extraRate = addShadeMast.getExtraRate();
		this.colorName=addShadeMast.getColorName();
		//this.shadeDataList=addShadeMast.getShadeDataList();
	}

	public ShadeMast(ShadeMast addShadeMast)
	{
		this.apcNo =addShadeMast.getApcNo();
		this.pending=addShadeMast.getPending();
		this.id=addShadeMast.getId();
		this.partyShadeNo=addShadeMast.getPartyShadeNo();
		this.processId=addShadeMast.getProcessId();
		this.partyId=addShadeMast.getPartyId();
		this.colorTone=addShadeMast.getColorTone();
		this.createdBy=addShadeMast.getCreatedBy();
		this.cuttingId=addShadeMast.getCuttingId();
		this.remark=addShadeMast.getRemark();
		this.category=addShadeMast.getCategory();
		this.labColorNo=addShadeMast.getLabColorNo();
		this.processName=addShadeMast.getProcessName();
		this.userHeadId=addShadeMast.getUserHeadId();
		this.extraRate=addShadeMast.getExtraRate();
		//this.shadeDataList=addShadeMast.getShadeDataList();
	}

	//for get shade by id
	public ShadeMast(ShadeMast addShadeMast,String processName)
	{
		this.id=addShadeMast.getId();
		this.partyShadeNo=addShadeMast.getPartyShadeNo();
		this.processId=addShadeMast.getProcessId();
		this.qualityEntryId=addShadeMast.getQualityEntryId();
		this.partyId=addShadeMast.getPartyId();
		this.colorTone=addShadeMast.getColorTone();
		this.createdBy = addShadeMast.getCreatedBy();
		this.updatedBy = addShadeMast.getUpdatedBy();
		this.createdDate = addShadeMast.getCreatedDate();
		this.updatedDate = addShadeMast.getUpdatedDate();
		this.userHeadId = addShadeMast.getUserHeadId();
		this.cuttingId=addShadeMast.getCuttingId();
		this.remark=addShadeMast.getRemark();
		this.category=addShadeMast.getCategory();
		this.labColorNo=addShadeMast.getLabColorNo();
		this.processName=processName;
		this.apcNo =addShadeMast.getApcNo();
		this.pending=addShadeMast.getPending();
		this.extraRate=addShadeMast.getExtraRate();
		this.colorName = addShadeMast.getColorName();
		this.shadeDataList = addShadeMast.getShadeDataList();

	}
}
