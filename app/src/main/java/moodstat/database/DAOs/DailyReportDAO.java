package moodstat.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;

import moodstat.database.entities.DailyReportEntity;

@Dao
public interface DailyReportDAO {
    @Insert
    long insert(DailyReportEntity dailyReport);


    @Update
    void update(DailyReportEntity report);


    @Query("SELECT * FROM daily_report")
    List<DailyReportEntity> getAllEntries();

    @Query("SELECT * FROM daily_report WHERE id = :id")
    DailyReportEntity getEntryById(long id);


    @Query("SELECT * FROM daily_report WHERE date = :date LIMIT 1")
    DailyReportEntity getEntryByDate(LocalDate date);

    @Delete
    void delete(DailyReportEntity dailyReport);





}
