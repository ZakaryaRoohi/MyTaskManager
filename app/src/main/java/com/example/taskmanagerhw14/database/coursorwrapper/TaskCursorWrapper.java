package com.example.taskmanagerhw14.database.coursorwrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.taskmanagerhw14.Utils.TaskState;
import com.example.taskmanagerhw14.database.TaskDBSchema;
import com.example.taskmanagerhw14.model.Task;

import java.util.Date;
import java.util.UUID;

//public class TaskCursorWrapper extends CursorWrapper {
//    public TaskCursorWrapper(Cursor cursor) {
//        super(cursor);
//    }
//    public Task getTask(){
//
//        String stringUUID = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.UUID));
//        String title = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.TITLE));
//        Date taskDate = new Date(getLong(getColumnIndex(TaskDBSchema.TaskTable.COLS.TaskDate)));
//        String description = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.DESCRIPTION));
//        String username = getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.USERNAME));
//        TaskState taskState =TaskState.valueOf(getString(getColumnIndex(TaskDBSchema.TaskTable.COLS.STATE)));
//
//        return new Task(UUID.fromString(stringUUID),title,description,taskDate,username, taskState);
//
//    }
//}
