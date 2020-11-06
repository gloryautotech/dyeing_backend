package com.main.glory.controller;

import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.Program;
import com.main.glory.servicesImpl.ProgramServiceImpl;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController extends ControllerConfig {

    @Autowired
    private ProgramServiceImpl programServiceImpl;

    @PostMapping(value="/program")
    public GeneralResponse<Boolean> saveProgram(@RequestBody Program program) throws Exception {
        if(program==null)
        {
            return new GeneralResponse<Boolean>(false, "Program id is null", true, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
        boolean flag=programServiceImpl.saveProgram(program);
        if(!flag)
        {
            System.out.println("Something went wrong");
            return new GeneralResponse<Boolean>(false, "Data Not found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
        }else
            return new GeneralResponse<Boolean>(true, "Program added successfully", true, System.currentTimeMillis(), HttpStatus.OK);
    }

    @GetMapping(value="/program/all")
    public List<Program> getProgramList() throws Exception {
        return programServiceImpl.getAllProgram();
    }

    @GetMapping(value="/program/{id}")
    public GeneralResponse<Program> getProgramDetailsById(@PathVariable(value = "id") Long id) throws Exception {
        if(id!=null)
        {
            Program programObject=programServiceImpl.getProgramById(id);
            if(programObject!=null)
            {
                return new GeneralResponse<>(programObject,"Data Get successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        }
        return new GeneralResponse<>(null, "Data not found", true, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
    }

    @PutMapping(value="/program")
    public GeneralResponse<Boolean>  updateProgram(@RequestBody Program program) throws Exception
    {
        if(program!=null)
        {
            boolean flag=programServiceImpl.updateProgramByID(program);
            if(flag) {
                return new GeneralResponse<Boolean>(true, "Data updated sucessfully !", true, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
            {
                return new GeneralResponse<Boolean>(false, "Data not updated !", true, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }
        }
        return new GeneralResponse<Boolean>(false, "Data Not Found by id: !", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value="/program/{id}")
    public GeneralResponse<Boolean> deleteProgramDetailsByID(@PathVariable(value = "id") Long id) throws Exception {
        if(id!=null)
        {
            boolean flag=programServiceImpl.deleteProgramById(id);
            if(flag)
            {
                return new GeneralResponse<>(true,"Deleted Successfully",true,System.currentTimeMillis(),HttpStatus.OK);
            }
            else
            {
                return new GeneralResponse<>(false,"Data not found",false,System.currentTimeMillis(),HttpStatus.NOT_FOUND);
            }
        }
        return new GeneralResponse<>(false,"Data not deleted",false,System.currentTimeMillis(),HttpStatus.BAD_REQUEST);
    }
}
