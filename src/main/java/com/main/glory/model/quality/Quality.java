package com.main.glory.model.quality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.glory.model.StockDataBatchData.StockMast;

import com.main.glory.model.productionPlan.ProductionPlan;
import com.main.glory.model.program.Program;
import com.main.glory.model.quality.request.AddQualityRequest;
import com.main.glory.model.shade.ShadeData;
import com.main.glory.model.shade.ShadeMast;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="quality")
@ToString
public class Quality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private String  qualityId;
	String qualityName;
	private String qualityType;
	private String unit;
	private Long  partyId;
	private Double wtPer100m;
	private String remark;
	private  Date createdDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private Date qualityDate;
	private Long userHeadId;
	private Double rate;
	private String HSN="9988";
	Long qualityNameId;

	public Quality(Quality other) {
		this.id = other.id;
		this.qualityId = other.qualityId;
		//this.qualityName = other.qualityName;
		this.qualityType = other.qualityType;
		this.unit = other.unit;
		this.partyId = other.partyId;
		this.wtPer100m = other.wtPer100m;
		this.remark = other.remark;
		this.createdDate = other.createdDate;
		this.createdBy = other.createdBy;
		this.updatedBy = other.updatedBy;
		this.updatedDate = other.updatedDate;
//		this.qualityDate = other.qualityDate;
		this.userHeadId = other.userHeadId;
		this.qualityNameId=other.qualityNameId;
	}

	//for adding the quality
	public Quality(AddQualityRequest qualityDto) {
		this.qualityId=qualityDto.getQualityId();
		//this.qualityName=qualityDto.getQualityName();
		this.qualityType=qualityDto.getQualityType();
		this.unit=qualityDto.getUnit();
		this.partyId=qualityDto.getPartyId();
		this.wtPer100m=qualityDto.getWtPer100m();
		this.remark=qualityDto.getRemark();
		this.createdBy=qualityDto.getCreatedBy();
		this.updatedBy=qualityDto.getUpdatedBy();
		this.userHeadId=qualityDto.getUserHeadId();
		this.rate=qualityDto.getRate();

	}

	@PrePersist
	protected void onCreate() {
		this.createdDate = new Date(System.currentTimeMillis());
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = new Date(System.currentTimeMillis());
	}
}
