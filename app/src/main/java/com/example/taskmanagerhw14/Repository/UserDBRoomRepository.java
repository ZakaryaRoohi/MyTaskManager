package com.example.taskmanagerhw14.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerhw14.Utils.UserType;
import com.example.taskmanagerhw14.database.room.TaskManagerDB;
import com.example.taskmanagerhw14.model.User;

import java.util.List;

public class UserDBRoomRepository {


    public static UserDBRoomRepository sUserRepository;
    private TaskManagerDB mDatabase;
    private static Context sContext;

    private UserDBRoomRepository() {
        mDatabase = Room.databaseBuilder(sContext, TaskManagerDB.class, "UserDB.db")
                .allowMainThreadQueries()
                .build();
    }
    public static UserDBRoomRepository getInstance(Context context) {
        sContext = context.getApplicationContext();
        if (sUserRepository == null){
            sUserRepository = new UserDBRoomRepository();
            User admin = new User("admin","admin", UserType.ADMIN);
        sUserRepository.add(admin);}
        return sUserRepository;
    }

    public List<User> getList() {
        return mDatabase.userDateBaseDAO().getUsers();
    }


    public User get(Long id) {

        return mDatabase.userDateBaseDAO().getUser(id);
    }
    public User get(String userName) {
        return mDatabase.userDateBaseDAO().getUser(userName);
    }


    public void add(User user) {
        mDatabase.userDateBaseDAO().insertUser(user);

    }
    public void remove(User user) {
        mDatabase.userDateBaseDAO().deleteUser(user);

    }

    public void removeAll() {
        mDatabase.userDateBaseDAO().deleteAllUsers();

    }
    public void update(User user) {
        mDatabase.userDateBaseDAO().updateUser(user);
    }
    public UserType getUserType(Long userId){
        User findUser = mDatabase.userDateBaseDAO().getUser(userId);
        return findUser.getUserType();
    }
}
