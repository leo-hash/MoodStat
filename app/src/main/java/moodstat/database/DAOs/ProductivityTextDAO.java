package moodstat.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;

@Dao
public interface ProductivityTextDAO {
    @Insert
    void insert(ProductivityTextEntity text);
    @Insert
    void insertAll(List<ProductivityTextEntity> texts);

    @Update
    void update(ProductivityTextEntity text);

    @Update
    void updateAll(List<ProductivityTextEntity> text);

    @Query("SELECT * FROM productivity_text")
    List<ProductivityTextEntity> getAllEntries();

    @Query("SELECT * FROM productivity_text WHERE id = :id")
    ProductivityTextEntity getEntryById(long id);

    @Query("SELECT * FROM productivity_text WHERE dailyReportId = :dailyReportId")
    List<ProductivityTextEntity> getEntryByReportId(long dailyReportId);

    @Delete
    void delete(ProductivityTextEntity moodText);

    @Delete
    void deleteAll(List<ProductivityTextEntity> texts);
}
