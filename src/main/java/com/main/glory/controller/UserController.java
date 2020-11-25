package com.main.glory.controller;


import com.main.glory.config.ControllerConfig;
import com.main.glory.model.GeneralResponse;
import com.main.glory.model.party.Party;
import com.main.glory.model.user.Request.UserAddRequest;
import com.main.glory.model.user.Request.UserUpdateRequest;
import com.main.glory.model.user.UserData;
import com.main.glory.model.user.UserRequest;
import com.main.glory.model.user.response.LoginResponse;
import com.main.glory.model.user.response.getAllUserInfo;
import com.main.glory.servicesImpl.UserServiceImpl;
import com.main.glory.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController extends ControllerConfig {

    private UserServiceImpl userService;

    private JwtUtil jwtUtil;

    @Autowired
    public UserController(UserServiceImpl userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/user/{id}")
    public GeneralResponse<UserData> getUserById(@PathVariable(value = "id") Long id)
    {
        if(id!=null)
        {
            var userObj=userService.getUserById(id);
            if(userObj!=null)
            {
                return new GeneralResponse<UserData>(userObj, "Fetch Success", true, System.currentTimeMillis(), HttpStatus.FOUND);
            }
            return new GeneralResponse<UserData>(null, "No such id", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
        }
        return new GeneralResponse<>(null, "Null Id Passed!", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/userHead")
    public GeneralResponse<List<getAllUserInfo>> getAllHead()
    {

        if(userService.getAllHeadUser()!=null) {
            return new GeneralResponse<>(userService.getAllHeadUser(), "User Head Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
        }
        else
        {
            return new GeneralResponse<>(null, "User Head Not Available ", true, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/AllUsers/{getBy}/{id}")
    public GeneralResponse<List<getAllUserInfo>> getAllUser(@PathVariable(value = "getBy")String getBy, @PathVariable(value = "id")Long id)
    {   List<getAllUserInfo> users = null;
        try{
            switch (getBy) {
                case "own":
                    if(userService.getAllHeadUser()!=null) {
                        users = userService.getAllUser(getBy, id);
                        if(!users.isEmpty())
                            return new GeneralResponse<>(users, "User Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                        else
                            return new GeneralResponse<>(null, "User Not Available with userId: "+id, false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
                    }
                    else
                        return new GeneralResponse<>(null, "User Not Available", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);

                case "group":
                    if(userService.getAllHeadUser()!=null) {
                        users = userService.getAllUser(getBy, id);
                        if(!users.isEmpty())
                            return new GeneralResponse<>(users, "User Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                        else
                            return new GeneralResponse<>(null, "User Not Available with userHeadId: "+id, false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
                    }
                    else
                        return new GeneralResponse<>(null, "User Not Available", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);

                case "all":
                    if(userService.getAllHeadUser()!=null) {
                        users = userService.getAllUser(null, null);
                        return new GeneralResponse<>(users, "User Fetched successfully", true, System.currentTimeMillis(), HttpStatus.OK);
                    }
                    else
                        return new GeneralResponse<>(null, "User Not Available ", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);

                default:
                    return new GeneralResponse<>(null, "GetBy string is wrong", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new GeneralResponse<>(null, "Internal Server Error", false, System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user")
    public GeneralResponse<Boolean> createUser(@RequestBody UserAddRequest userData) throws Exception{

        try{
            userService.createUser(userData);
            return new GeneralResponse<>(true,"User created successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public GeneralResponse<LoginResponse> login(@RequestBody UserRequest userData) throws Exception{

        try{
            var user = userService.checkUser(userData.getUserName(),userData.getPassword());
            if(user!=null){
                LoginResponse loginResponse = new LoginResponse();
                var token = jwtUtil.generateToken(user, "accessToken");
                loginResponse.setAccessToken(token);
                token = jwtUtil.generateToken(user, "refreshToken");
                loginResponse.setRefreshToken(token);
                return new GeneralResponse<>(loginResponse,"successfully logged in", true, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
            {
                return new GeneralResponse<>(null,"Wrong Creds", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralResponse<>(null, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/user")
    public GeneralResponse<Boolean> upddateUser(@RequestBody UserUpdateRequest userData) throws Exception{

        try{
            int flag = userService.isAvailable(userData);

            if(flag==1){
                return new GeneralResponse<>(true,"User Updated successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }
            else
            {
                return new GeneralResponse<>(true,"User not Updated ", true, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new GeneralResponse<>(false, e.getMessage(), false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping(value="/user/{id}")
    public GeneralResponse<Boolean> deleteUserDetailsByID(@PathVariable(value = "id") Long id)
    {
        if(id!=null)
        {
            boolean flag=userService.deleteUserById(id);
            if(flag)
            {
                return new GeneralResponse<Boolean>(true, "Deleted successfully", true, System.currentTimeMillis(), HttpStatus.OK);
            }else{
                return new GeneralResponse<Boolean>(false, "no such id found", false, System.currentTimeMillis(), HttpStatus.NOT_FOUND);
            }
        }
        return new GeneralResponse<Boolean>(false, "Null party object", false, System.currentTimeMillis(), HttpStatus.BAD_REQUEST);
    }

}
