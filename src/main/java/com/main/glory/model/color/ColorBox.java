package com.main.glory.model.color;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "color_box")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColorBox {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long box_no;
	Long control_id;
	Boolean issued;
	Date issued_date;
}
