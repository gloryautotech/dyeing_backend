package com.main.glory.Dao.dyeingSlip;

import com.main.glory.model.dyeingSlip.DyeingSlipItemData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DyeingSlipItemDataDao extends JpaRepository<DyeingSlipItemData,Long> {


    @Query("select dt from DyeingSlipItemData dt where dt.controlId=(select dd.id from DyeingSlipData dd where dd.controlId=:id AND dd.processType='addition')")
    List<DyeingSlipItemData> getDyeingItemByMastId(Long id);
}
