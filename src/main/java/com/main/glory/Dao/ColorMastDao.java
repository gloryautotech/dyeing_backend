package com.main.glory.Dao;

import com.main.glory.model.color.ColorMast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ColorMastDao extends JpaRepository<ColorMast, Long> {

	@Query(value = "Select cm.*, (Select cd.* from color_data as cd where cd.purchase_id = cm.id), (Select cb.* from color_box as cb where cb.control_id = cd.id where cb.issued = 0) from color_mast", nativeQuery = true)
	List<ColorMast> getAllActiveData();

}
