package moodstat.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import moodstat.database.DAOs.DailyReportDAO;
import moodstat.database.DAOs.MoodTextDAO;
import moodstat.database.DAOs.ProductivityTextDAO;
import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;


@Database(
        entities = {
                DailyReportEntity.class,
                MoodTextEntity.class,
                ProductivityTextEntity.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase{
    private static volatile AppDatabase INSTANCE;


    public abstract DailyReportDAO DailyReportDAO();
    public abstract MoodTextDAO MoodTextDAO();
    public abstract ProductivityTextDAO ProductivityTextDAO();


    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {

                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "my_database"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }
}

