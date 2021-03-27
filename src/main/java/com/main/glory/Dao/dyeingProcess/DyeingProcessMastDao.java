package com.main.glory.Dao.dyeingProcess;

import com.main.glory.model.dyeingProcess.DyeingProcessMast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface DyeingProcessMastDao extends JpaRepository<DyeingProcessMast,Long> {
    @Query("select q from DyeingProcessMast q")
    List<DyeingProcessMast> getAllProcess();

    @Query("select x from DyeingProcessMast x where x.id=:processId")
    DyeingProcessMast getDyeingProcessById(Long processId);


    @Modifying
    @Transactional
    @Query("delete from DyeingProcessMast d where d.id=:id")
    void deleteByProcessId(Long id);

    @Query("select d from DyeingProcessMast d where LOWER(d.processName)=LOWER(:processName)")
    DyeingProcessMast getDyeingProcessByName(String processName);

    @Query("select d from DyeingProcessMast d where LOWER(d.processName)=LOWER(:name) AND d.id!=:id")
    DyeingProcessMast getDyeingProcessByNameExceptId(String name, Long id);

    @Query("select d from DyeingProcessMast d where d.createdBy=:userId OR d.userHeadId=:userHeadId")
    List<DyeingProcessMast> getAllDyeingProcessByCreatedAndHead(Long userId, Long userHeadId);
}
