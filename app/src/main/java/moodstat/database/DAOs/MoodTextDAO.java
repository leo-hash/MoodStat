package moodstat.database.DAOs;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import moodstat.database.entities.MoodTextEntity;

@Dao
public interface MoodTextDAO {
    @Insert
    void insert(MoodTextEntity text);
    @Insert
    void insertAll(List<MoodTextEntity> texts);

    @Query("SELECT * FROM mood_text")
    List<MoodTextEntity> getAllEntries();

    @Query("SELECT * FROM mood_text WHERE id = :id")
    MoodTextEntity getEntryById(long id);

    @Delete
    void delete(MoodTextEntity moodText);

}
