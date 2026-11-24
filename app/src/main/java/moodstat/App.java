package moodstat;

import android.app.Application;

import moodstat.database.AppDatabase;
import moodstat.repositories.DailyReportRepository;


/**
 * class is created once when the application is started
 * initializes database and provides global access to repositories
 */
public class App extends Application {
    private DailyReportRepository repo;

    @Override
    public void onCreate(){
        super.onCreate();

        AppDatabase db = AppDatabase.getInstance(this);
        repo = new DailyReportRepository(db);
    }

    public DailyReportRepository getRepo() {
        return repo;
    }
}
