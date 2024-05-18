package com.example.pricepilot;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Product.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
  public abstract ProductDAO productDAO();

  public static AppDatabase create(Context context) {
    return Room.databaseBuilder(
        context,
        AppDatabase.class,
        ProductDbContract.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build();
  }
}
