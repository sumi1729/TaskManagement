package com.Taskmanagement.database;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DbExecutor {
    private static final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    public static void execute(Runnable task) {
        dbExecutor.execute(task);
    }

    public static void shutdown() {
        dbExecutor.shutdown();
    }
}
