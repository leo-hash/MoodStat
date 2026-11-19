package moodstat.database.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.time.LocalDate;
import java.util.List;

import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;
import moodstat.database.relations.DailyReportWithText;

@Dao
public abstract class DailyReportDAO {
    @Insert
    public abstract long insertDailyReport(DailyReportEntity dailyReport);

    @Insert
    public abstract void insertMoodTexts(List<MoodTextEntity> moodTexts);

    @Insert
    public abstract void insertProductivityTexts(
            List<ProductivityTextEntity> productivityTexts
    );

    @Insert
    public long insertReportWithTexts(
            DailyReportEntity dailyReport,
            List<MoodTextEntity> moodTexts,
            List<ProductivityTextEntity> productivityTexts){
        long reportId = insertDailyReport(dailyReport);

        for (MoodTextEntity text : moodTexts){
            text.dailyReportId = reportId;
        }

        for (ProductivityTextEntity text : productivityTexts) {
            text.dailyReportId = reportId;
        }

        insertMoodTexts(moodTexts);
        insertProductivityTexts(productivityTexts);

        return reportId;
    }

    @Query("SELECT * FROM daily_report")
    public abstract List<DailyReportEntity> getAllEntries();

    @Query("SELECT * FROM daily_report WHERE id = :id")
    public abstract DailyReportEntity getEntryById(long id);

    @Transaction
    @Query("SELECT * FROM daily_report WHERE id = :id")
    public abstract DailyReportWithText getReportWithText(long id);

    @Transaction
    @Query("SELECT * FROM daily_report WHERE id = :id")
    public abstract LiveData<DailyReportWithText> getReportWithTextLive(long id);

    @Transaction
    @Query("SELECT * FROM daily_report WHERE date = :date LIMIT 1")
    public abstract LiveData<DailyReportWithText> getReportByDate(LocalDate date);

    @Delete
    public abstract void delete(DailyReportEntity dailyReport);

}
