package com.example.taskmanagerhw14.Repository;

import com.example.taskmanagerhw14.Utils.UserType;
import com.example.taskmanagerhw14.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository sUserRepository;
    private List<User> mUsers;
    public static int mNumberOfUsers;

    public static UserRepository getInstance() {
        if (sUserRepository == null)
            sUserRepository = new UserRepository();
        return sUserRepository;
    }

    private UserRepository() {

        mUsers = new ArrayList<>();
        mUsers.add(new User("admin", "admin", UserType.ADMIN));
        for (int i = 1; i < mNumberOfUsers; i++) {
            User user = new User();
            mUsers.add(user);
        }
    }

    public void addUser(User user) {
        mUsers.add(user);
    }

    public boolean checkUserExist(String username, String password) {
        for (User user : mUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }

    public UserType getUserType(String username){
        for (User user : mUsers) {
            if (user.getUsername().equals(username))
                return user.getUserType();
        }
        return null;
    }
    public List<User> getList() {
        List<User> userList = new ArrayList<>();
        for (User user : mUsers) {

                userList.add(user);
        }
        return userList;
    }

    public void deleteUser(User deleteUser){
        for (User user : mUsers) {
                if(user.getUsername().equals(deleteUser.getUsername()))
                    mUsers.remove(user);
        }
    }
}
