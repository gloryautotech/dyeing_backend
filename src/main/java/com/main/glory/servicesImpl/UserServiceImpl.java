package com.main.glory.servicesImpl;

import com.main.glory.Dao.user.UserDao;
import com.main.glory.model.user.UserData;
import com.main.glory.services.UserServiceInterface;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userServiceImpl")
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    UserDao userDao;


    public UserData getUserById(Long id) {
        var data=userDao.findById(id);
        if(data.isEmpty())
            return null;
        else
            return data.get();
    }


    public int createUser(UserData userData) {

        if(userData!=null) {System.out.println(userData.toString());
            userDao.saveAndFlush(userData);
            return 1;
        }
        else
        {
            return 0;
        }

    }

    public UserData checkUser(String userName,String password) {
        return userDao.findByUserNameAndPassword(userName, password);
    }

    public List<UserData> getAllHeadUser() {
        List<UserData> adminList = userDao.findByUserHeadId(0l);
        List<UserData> userHeads = new ArrayList<>();
        adminList.forEach(e -> {
            List<UserData> users = userDao.findByUserHeadId(e.getId());
            userHeads.addAll(users);
        });
        return userHeads;
    }

    public int isAvailable(UserData userData) {

        var userData1 = userDao.findById(userData.getId());
        if(!userData1.isPresent())
        {
            return 0;
        }
        userDao.save(userData);
        return 1;

    }

    public boolean deletePartyById(Long id) {
        var userIndex= userDao.findById(id);
        if(!userIndex.isPresent())
            return false;
        else
            userDao.deleteById(id);
        return true;

    }
}
