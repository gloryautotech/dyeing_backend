package com.main.glory.Dao.StockAndBatch;

import com.main.glory.model.StockDataBatchData.StockMast;
import com.main.glory.model.StockDataBatchData.response.GetAllBatchResponse;
import com.main.glory.model.StockDataBatchData.response.GetAllStockWithPartyNameResponse;
import com.main.glory.model.quality.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockMastDao extends JpaRepository<StockMast, Long> {

 @Query("select new com.main.glory.model.StockDataBatchData.response.GetAllStockWithPartyNameResponse(sm, (Select p.partyName from Party p where p.id = sm.partyId)) from StockMast sm")
 Optional<List<GetAllStockWithPartyNameResponse>> getAllStockWithPartyName();

 List<StockMast> findByQualityId(Long qualityId);
}
