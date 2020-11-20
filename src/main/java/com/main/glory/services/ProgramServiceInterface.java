package com.main.glory.services;

import com.main.glory.model.program.Program;
import com.main.glory.model.program.response.GetAllProgram;

import java.util.List;

public interface ProgramServiceInterface {
    public boolean saveProgram(Program program) throws Exception;
    public List<GetAllProgram> getAllProgram() throws Exception;
    public Program getProgramById(Long id) throws Exception;
    public boolean deleteProgramById(Long id) throws Exception;
    public boolean updateProgramByID(Program bm) throws Exception;
}
