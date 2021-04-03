package com.main.glory.servicesImpl;

import com.main.glory.Dao.admin.DepartmentDao;
import com.main.glory.Dao.task.TaskDataDao;
import com.main.glory.Dao.task.TaskDataImageDao;
import com.main.glory.Dao.task.TaskImageDao;
import com.main.glory.Dao.task.TaskMastDao;
import com.main.glory.Dao.user.UserDao;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.admin.Department;
import com.main.glory.model.task.TaskData;
import com.main.glory.model.task.TaskMast;
import com.main.glory.model.task.response.TaskResponse;
import com.main.glory.model.user.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("taskServiceImpl")
public class TaskServiceImpl {

    @Autowired
    TaskDataImageDao taskDataImageDao;


    @Autowired
    UserServiceImpl userService;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    TaskMastDao taskMastDao;

    @Autowired
    TaskDataDao taskDataDao;

    @Autowired
    TaskImageDao taskImageDao;




    public void saveTask(TaskMast record) throws Exception {


        //check the user and department is exit aur not

        Department departmentExist =departmentDao.getDepartmentById(record.getDepartmentId());
        UserData userDataExist = userService.getUserById(record.getId());

        if(departmentExist==null || userDataExist==null)
            throw new Exception("no department or user found");


        TaskMast taskMast = taskMastDao.save(record);

        //List<TaskData> taskDataList=new ArrayList<>();
        TaskData taskData =new TaskData(taskMast);
        taskDataDao.save(taskData);


        //Long differenceInDays = record.getEndDate() - record.getStartDate();









    }

    public TaskResponse getTaskById(Long id) {
        TaskMast taskMast = taskMastDao.getTaskMastById(id);

        TaskResponse taskResponse =null;
        if(taskMast!=null) {

            List<TaskData> taskDataList = taskDataDao.getTaskDataByControlId(taskMast.getId());
             taskResponse = new TaskResponse(taskMast, taskDataList);

        }
        return taskResponse;
    }

    public List<TaskResponse> getAllTask() {
        List<TaskMast> taskMastList = taskMastDao.getAllTask();
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for(TaskMast taskMast:taskMastList)
        {
            TaskResponse taskResponse = getTaskById(taskMast.getId());
            if(taskResponse==null)
                continue;
            taskResponseList.add(taskResponse);

        }
        return taskResponseList;



    }

    public boolean deleteTaskById(Long id) throws Exception {
        TaskMast taskMastExist = taskMastDao.getTaskMastById(id);
        if(taskMastExist==null)
            throw new Exception("no record found");


        List<TaskData> taskDataList = taskDataDao.getTaskDataByControlId(taskMastExist.getId());

        //check the any task is completed
        for(TaskData taskData:taskDataList)
        {
            if(taskData.getIsCompleted())
            {
                throw new Exception("remove the task which are completed");
            }
        }

        for(TaskData taskData:taskDataList)
        {
            taskDataDao.deleteTaskDataById(taskData.getId());
            //remove the taskData image as well if the condition is satisfied
            taskDataImageDao.deleteTaskDataImageByControlId(taskData.getId());

        }

        return true;

    }

    public List<TaskMast> getTaskByReportId(Long reportId) {
        return taskMastDao.getTaskByReportId(reportId);
    }
}
