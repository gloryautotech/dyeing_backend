package com.main.glory.Dao.task;

import com.main.glory.model.task.TaskData;
import com.main.glory.model.task.request.TaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TaskDataDao extends JpaRepository<TaskData,Long> {

    @Query("select t from TaskData t where t.controlId=:id")
    List<TaskData> getTaskDataByControlId(Long id);

    @Modifying
    @Transactional
    @Query("delete from TaskData x where x.id=:id")
    void deleteTaskDataById(Long id);


    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where t.taskStatus=:status AND Date(t.taskDate)=Date(:date)")
    List<TaskDetail> getTaskDetailByDateAndStatus(@Param("date") Date date,@Param("status") String status);

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t ")
    List<TaskDetail> getTaskDetail();

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where Date(t.taskDate)=Date(:date)")
    List<TaskDetail> getTaskDetailByDate(Date date);

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where t.taskStatus=:status")
    List<TaskDetail> getTaskDetailByStatus(String status);

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where t.controlId IN (select tt.id from TaskMast tt where tt.assignUserId=:assignId OR tt.createdBy=:createdById)")
    List<TaskDetail> getTaskDetailByCreatedByAssignById(Long assignId, Long createdById);

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where t.controlId IN  (select tt.id from TaskMast tt where tt.assignUserId=:assignId)")
    List<TaskDetail> getTaskDetailAssignBy(Long assignId);

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where t.approved=:approvedFlag")
    List<TaskDetail> getTaskDetailByApproved(Boolean approvedFlag);

    @Query("select new com.main.glory.model.task.request.TaskDetail(t,(select tt from TaskMast tt where tt.id=t.controlId) as taskMast,(select u from UserData u where u.id=t.assignUserId) as userData,(select d from Department d where d.id=(select u.departmentId from UserData u where u.id=t.assignUserId)) as deptData,(select r from ReportType r where r.id=(select tt.reportId from TaskMast tt where tt.id=t.controlId))as reportData) from TaskData t where t.approved=:approvedFlag AND t.assignUserId=:id")
    List<TaskDetail> getTaskDetailByApprovedAndAssignId(Long id, Boolean approvedFlag);

    @Modifying
    @Transactional
    @Query("update TaskData t set t.approved=:approvedFlag where t.id=:id")
    void updateTaskWithIdAndFlag(Long id, Boolean approvedFlag);

    @Query("select x from TaskData x where x.id=:id")
    TaskData getTaskDetailById(Long id);
}
