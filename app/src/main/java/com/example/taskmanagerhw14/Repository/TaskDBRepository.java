package com.example.taskmanagerhw14.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.database.TaskBaseHelper;
import com.example.taskmanagerhw14.database.TaskDBSchema;

import com.example.taskmanagerhw14.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//public class TaskDBRepository implements IRepository<Task> {
//    private static TaskDBRepository sTaskDBRepository;
//    public static Context mContext;
//    private SQLiteDatabase mDatabase;
//
//    public static TaskDBRepository getInstance(Context context) {
//        mContext = context.getApplicationContext();
//        if (sTaskDBRepository == null)
//            sTaskDBRepository = new TaskDBRepository();
//        return sTaskDBRepository;
//    }
//
//    private TaskDBRepository() {
//        TaskBaseHelper taskBaseHelper = new TaskBaseHelper(mContext);
//        mDatabase = taskBaseHelper.getWritableDatabase();
//    }
//
//    @Override
//    public List<Task> getList(TaskState taskState) {
//
//        List<Task> tasks = new ArrayList<>();
//        String selection = TaskDBSchema.TaskTable.COLS.STATE + "=?";
//        String[] selectionArgs = new String[]{taskState.toString()};
//        TaskCursorWrapper cursorWrapper = queryTasks(selection, selectionArgs);
//        try {
//            cursorWrapper.moveToFirst();
//            while (!cursorWrapper.isAfterLast()) {
//                tasks.add(cursorWrapper.getTask());
//                cursorWrapper.moveToNext();
//            }
//        } finally {
//            cursorWrapper.close();
//        }
//        return tasks;
//    }
//    @Override
//    public List<Task> getList(TaskState taskState, String username) {
//
//        List<Task> tasks = new ArrayList<>();
//        String selection = TaskDBSchema.TaskTable.COLS.STATE + "=?" + "and" + TaskDBSchema.TaskTable.COLS.USERNAME + "=?";
//        String[] selectionArgs = new String[]{taskState.toString(), username};
//        TaskCursorWrapper cursorWrapper = queryTasks(selection, selectionArgs);
//        try {
//            cursorWrapper.moveToFirst();
//            while (!cursorWrapper.isAfterLast()) {
//                tasks.add(cursorWrapper.getTask());
//                cursorWrapper.moveToNext();
//            }
//        } finally {
//            cursorWrapper.close();
//        }
//        return tasks;
//
//
//    }
//
//    @Override
//    public List<Task> getList() {
//        List<Task> tasks = new ArrayList<>();
//        TaskCursorWrapper cursorWrapper = queryTasks(null, null);
//        try {
//            cursorWrapper.moveToFirst();
//            while (!cursorWrapper.isAfterLast()) {
//                tasks.add(cursorWrapper.getTask());
//                cursorWrapper.moveToNext();
//            }
//        } finally {
//            cursorWrapper.close();
//        }
//        return tasks;
//    }
//
//    private TaskCursorWrapper queryTasks(String selection, String[] selectionArgs) {
//        Cursor cursor = mDatabase.query(TaskDBSchema.TaskTable.NAME,
//                null, selection, selectionArgs, null, null, null);
//        TaskCursorWrapper taskCursorWrapper = new TaskCursorWrapper(cursor);
//        return taskCursorWrapper;
//    }
//
//    @Override
//    public Task get(UUID uuid) {
//        String selection = TaskDBSchema.TaskTable.COLS.UUID + "=?";
//        String[] selectionArgs = new String[]{uuid.toString()};
//        TaskCursorWrapper taskCursorWrapper = queryTasks(selection, selectionArgs);
//        try {
//            taskCursorWrapper.moveToFirst();
//            return taskCursorWrapper.getTask();
//        } finally {
//            taskCursorWrapper.close();
//        }
//    }
//
//    @Override
//    public void setList(List<Task> list) {
//
//    }
//
//    @Override
//    public void update(Task task) {
//        ContentValues values = getTaskContentValue(task);
//        String where = TaskDBSchema.TaskTable.COLS.UUID + "=?";
//        String[] whereArgs = new String[]{task.getId().toString()};
//        mDatabase.update(TaskDBSchema.TaskTable.NAME, values, where, whereArgs);
//
//    }
//
//    private ContentValues getTaskContentValue(Task task) {
//        ContentValues values = new ContentValues();
//        values.put(TaskDBSchema.TaskTable.COLS.UUID, task.getId().toString());
//        values.put(TaskDBSchema.TaskTable.COLS.TITLE, task.getTaskTitle());
//        values.put(TaskDBSchema.TaskTable.COLS.DESCRIPTION, task.getTaskDescription());
//        values.put(TaskDBSchema.TaskTable.COLS.USERNAME, task.getTaskDescription());
//        values.put(TaskDBSchema.TaskTable.COLS.TaskDate, task.getTaskDate().getTime());
////        if(task.getTaskState().toString()!=null)
////            values.put(TaskDBSchema.TaskTable.COLS.STATE, task.getTaskState().toString());
//        return values;
//    }
//
//    @Override
//    public void delete(Task task) {
//        String where = TaskDBSchema.TaskTable.COLS.UUID + "=?";
//        String[] whereArgs = new String[]{task.getId().toString()};
//        mDatabase.delete(TaskDBSchema.TaskTable.NAME, where, whereArgs);
//    }
//
//    @Override
//    public void insert(Task task) {
//        ContentValues values = getTaskContentValue(task);
//        mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
//    }
//
//    @Override
//    public void clearTaskRepository() {
//        mDatabase = null;
//    }
//
//    @Override
//    public void deleteUserTask(String username) {
//        String where = TaskDBSchema.TaskTable.COLS.USERNAME + "=?";
//        String[] whereArgs = new String[]{username};
//        mDatabase.delete(TaskDBSchema.TaskTable.NAME, where, whereArgs);
//    }
//
//    @Override
//    public void delete(UUID taskId) {
//
//        String where = TaskDBSchema.TaskTable.COLS.UUID + "=?";
//        String[] whereArgs = new String[]{taskId.toString()};
//        mDatabase.delete(TaskDBSchema.TaskTable.NAME, where, whereArgs);
//    }
//
//    @Override
//    public void insertList(List<Task> list) {
//
//    }
//
//    @Override
//    public int getPosition(UUID uuid) {
//        List<Task> tasks = getList();
//        for (int i = 0; i < tasks.size(); i++) {
//            if (tasks.get(i).getId().equals(uuid))
//                return i;
//        }
//        return -1;
//    }
//
//    @Override
//    public void addTask(TaskState taskState) {
//        Task task = new Task();
//        task.setTaskTitle("Task : " + (sTaskDBRepository.getList().size() + 1));
//        task.setTaskDescription("demoTask");
//        task.setTaskDate(new Date());
//        task.setUser("Todo");
//        task.setTaskState(taskState);
//        ContentValues values = getTaskContentValue(task);
//        mDatabase.insert(TaskDBSchema.TaskTable.NAME, null, values);
//
//    }
//    @Override
//    public boolean checkTaskExists(Task task) {
//
//        return CheckIsDataAlreadyInDBorNot(TaskDBSchema.NAME, TaskDBSchema.TaskTable.COLS.USERNAME, task.getUsername());
//    }
//
//    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
//                                               String dbfield, String fieldValue) {
//        TaskBaseHelper taskBaseHelper = new TaskBaseHelper(mContext);
//        SQLiteDatabase sqldb = taskBaseHelper.getWritableDatabase();
//        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
//        Cursor cursor = sqldb.rawQuery(Query, null);
//        if (cursor.getCount() <= 0) {
//            cursor.close();
//            return false;
//        }
//        cursor.close();
//        return true;
//    }
//}
