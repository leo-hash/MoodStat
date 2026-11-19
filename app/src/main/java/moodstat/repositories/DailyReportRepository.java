package moodstat.repositories;

import androidx.lifecycle.LiveData;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import moodstat.database.DAOs.DailyReportDAO;
import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;
import moodstat.database.relations.DailyReportWithText;


/**
 * encapsulates the business logic for accessing the data binding objects of the database
 * via executer (in an own thread)
 */
public class DailyReportRepository {
    private final DailyReportDAO dailyReportDAO;
    private final ExecutorService executor;

    public DailyReportRepository(DailyReportDAO dao){
        this.dailyReportDAO = dao;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void saveReport(DailyReportEntity report,
                           List<MoodTextEntity> moodTexts,
                           List<ProductivityTextEntity> productivityTexts,
                           Consumer<Long> callback){
        executor.execute(() -> {
            long id = dailyReportDAO.insertReportWithTexts(
                    report,
                    moodTexts,
                    productivityTexts
            );

            callback.accept(id);
        });
    }

    public LiveData<DailyReportWithText> getReport(long id) {
        return dailyReportDAO.getReportWithTextLive(id);
    }

    public LiveData<DailyReportWithText> getReportByDate(LocalDate date){
        return dailyReportDAO.getReportByDate(date);
    }
}
