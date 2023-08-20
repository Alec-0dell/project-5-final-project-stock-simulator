package org.overlake.mat803.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Stock.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StockDAO stockDAO();

}
