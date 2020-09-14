package com.example.taskmanagerhw14.Repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerhw14.Utils.UserType;
import com.example.taskmanagerhw14.database.room.TaskManagerDB;
import com.example.taskmanagerhw14.model.Task;
import com.example.taskmanagerhw14.model.User;

import java.io.File;
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
        if (sUserRepository == null) {
            sUserRepository = new UserDBRoomRepository();
            if (sUserRepository.getList().size() == 0) {
                User admin = new User("admin", "1", UserType.ADMIN);
                sUserRepository.add(admin);
            }
        }
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

    public UserType getUserType(Long userId) {
        User findUser = mDatabase.userDateBaseDAO().getUser(userId);
        return findUser.getUserType();
    }

    public static Long checkUserExist(UserDBRoomRepository userDBRoomRepository, String username, String password) {
        for (User user : userDBRoomRepository.getList()) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password))
                return user.getId();
        }
        return null;
    }

    public static Long checkUserExist(UserDBRoomRepository userDBRoomRepository, String username) {
        for (User user : userDBRoomRepository.getList()) {
            if (user.getUserName().equals(username))
                return user.getId();
        }
        return null;
    }

    public File getPhotoFile(Context context, User user) {
        File photoFile = new File(context.getFilesDir(), user.getPhotoFileName());
        return photoFile;
    }
}
