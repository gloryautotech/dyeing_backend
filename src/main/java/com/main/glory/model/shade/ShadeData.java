package com.main.glory.model.shade;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ShadeData")
public class ShadeData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	Long id;
	@ApiModelProperty(hidden = true)
	Long controlId;
	@Column(nullable = false)
	String itemName;
	@Column(nullable = false)
	Long supplierId;
	@Column(nullable = false)
	Double rate;
	@Column(nullable = false)
	Double amount;
	@Column(nullable = false)
	Double concentration;
	@ApiModelProperty(hidden = true)
	Date createdDate;
	@ApiModelProperty(hidden = true)
	Date updatedDate;
	String createdBy;
	String updatedBy;
	@ApiModelProperty(hidden = true)
	String state;
	@ApiModelProperty(hidden = true)
	Boolean isActive;
	@Column(nullable = false)
	Long supplierItemId;
}
