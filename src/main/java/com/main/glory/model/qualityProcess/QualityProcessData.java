package com.main.glory.model.qualityProcess;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
@Table
public class QualityProcessData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	Long id;
	Long controlId;
	String processType;
	Double temperature;
	Long holdTime;
	Long plcProgramNo;
	Double rateTemperature;
	@ApiModelProperty(hidden = true)
	Date createdDate;
	@ApiModelProperty(hidden = true)
	Date updatedDate;
	String createdBy;
	String updatedBy;

	@Transient
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "id", name = "controlId")
	List<QualityTypeData> qualityTypeData;

	public QualityProcessData() {
		this.createdDate = new Date(System.currentTimeMillis());
	}
}
