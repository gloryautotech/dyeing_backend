package com.main.glory.model.shade;

import com.main.glory.model.quality.Quality;
import com.main.glory.model.shade.requestmodals.AddShadeMast;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
	//@Column(unique = true,nullable = false)
	String partyShadeNo;
	//@Column(nullable = false)
	Long processId;

	//@Column(nullable = false)

	Long qualityEntryId;

	//@Column(nullable = false)

	Long partyId;
	String colorTone;
	@ApiModelProperty(hidden = true)
	String createdBy;
	@ApiModelProperty(hidden = true)
	String updatedBy;
	@ApiModelProperty(hidden = true)
	Date createdDate;
	@ApiModelProperty(hidden = true)
	Date updatedDate;;
	//@Column(nullable = false)
	Long userHeadId;
	//@Column(nullable = false)
	Long cuttingId;
	String remark;
	//@Column(nullable = false)
	String category;
	//@Column(nullable = false)
	String labColorNo;
	String processName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "controlId", referencedColumnName = "id")
	List<ShadeData> shadeDataList;

	@PreUpdate
	protected void onUpdate(){ this.updatedDate = new Date(System.currentTimeMillis()); }

	public ShadeMast(AddShadeMast addShadeMast)
	{
		this.id=addShadeMast.getPartyId();
		this.partyShadeNo=addShadeMast.getPartyShadeNo();
		this.processId=addShadeMast.getProcessId();
		this.partyId=addShadeMast.getPartyId();
		this.colorTone=addShadeMast.getColorTone();
		this.createdBy=null;
		this.updatedBy=null;
		this.cuttingId=addShadeMast.getCuttingId();
		this.remark=addShadeMast.getRemark();
		this.category=addShadeMast.getCategory();
		this.labColorNo=addShadeMast.getLabColorNo();
		this.processName=addShadeMast.getProcessName();

	}
}
