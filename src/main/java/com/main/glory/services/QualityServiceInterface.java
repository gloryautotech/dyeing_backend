package com.main.glory.services;
import java.util.List;
import com.main.glory.model.Quality;
public interface QualityServiceInterface {

	public int saveQuality(Quality obj);
	public List<Quality> getAllQuality();
	public boolean updateQuality(Quality obj) throws Exception;
	public boolean deleteQualityById(Long id);
}
