package com.main.glory.services;
import java.util.List;
import java.util.Optional;

import com.main.glory.model.quality.Quality;
import com.main.glory.model.quality.QualityWithPartyName;
import com.main.glory.model.quality.request.AddQualityRequest;

public interface QualityServiceInterface {

	public List<QualityWithPartyName> getAllQuality();
	public int saveQuality(AddQualityRequest obj) throws Exception;
	public boolean updateQuality(Quality obj) throws Exception;
	public boolean deleteQualityById(Long id);
	public Optional<Quality> getQualityByID(Long id);
	public String isQualityAlreadyExist(String qualityId);
	public String getPartyNameByPartyId(Long partyName);
}
