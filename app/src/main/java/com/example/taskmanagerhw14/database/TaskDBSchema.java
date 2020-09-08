package com.example.taskmanagerhw14.database;

import java.util.UUID;

public class TaskDBSchema {
    public static final String NAME = "TaskDB.dp";
    public static final int VERSION = 1;

    public static final class TaskTable {
        public static final String NAME = "TaskTable";

        public static final class COLS{
            public static final String ID = "id";
            public static final String UUID="uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION ="description";
            public static final String TaskDate = "taskDate";
            public static final String STATE = "state";
            public static final String USERNAME= "user";
        }
    }
}
