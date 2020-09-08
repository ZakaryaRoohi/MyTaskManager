package com.example.taskmanagerhw14.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import static com.example.taskmanagerhw14.database.TaskDBSchema.NAME;

public class TaskBaseHelper extends SQLiteOpenHelper {

    public TaskBaseHelper(@Nullable Context context) {
        super(context, NAME, null, TaskDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TaskDBSchema.TaskTable.NAME + "(" +
                TaskDBSchema.TaskTable.COLS.ID + " integer primary key autoincrement," +
                TaskDBSchema.TaskTable.COLS.UUID + " text," +
                TaskDBSchema.TaskTable.COLS.TITLE + " text," +
                TaskDBSchema.TaskTable.COLS.TaskDate + " long," +
                TaskDBSchema.TaskTable.COLS.USERNAME + " text," +
                TaskDBSchema.TaskTable.COLS.DESCRIPTION + " text," +
                TaskDBSchema.TaskTable.COLS.STATE + " text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
