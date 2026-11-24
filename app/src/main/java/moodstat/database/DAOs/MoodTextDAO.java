package moodstat.database.DAOs;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import moodstat.database.entities.MoodTextEntity;

@Dao
public interface MoodTextDAO {
    @Insert
    void insert(MoodTextEntity text);
    @Insert
    void insertAll(List<MoodTextEntity> texts);

    @Update
    void update(MoodTextEntity text);

    @Update
    void updateAll(List<MoodTextEntity> texts);

    @Query("SELECT * FROM mood_text WHERE id = :id")
    MoodTextEntity getEntryById(long id);

    @Query("SELECT * FROM mood_text")
    List<MoodTextEntity> getAllEntries();

    @Query("SELECT * FROM mood_text WHERE dailyReportId = :dailyReportId")
    List<MoodTextEntity> getEntryByReportId(long dailyReportId);

    @Delete
    void delete(MoodTextEntity moodText);

    @Delete
    void deleteAll(List<MoodTextEntity> texts);

}
