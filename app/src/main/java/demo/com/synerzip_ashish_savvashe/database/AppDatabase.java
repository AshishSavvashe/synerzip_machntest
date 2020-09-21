package demo.com.synerzip_ashish_savvashe.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import demo.com.synerzip_ashish_savvashe.database.dao.ResponseDao;
import demo.com.synerzip_ashish_savvashe.models.weatherresponse1;

@Database(entities = {weatherresponse1.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ResponseDao responseDao();
    public static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "assignment_database")
                            .addCallback(sRoomDatabaseCallback).allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };

}
