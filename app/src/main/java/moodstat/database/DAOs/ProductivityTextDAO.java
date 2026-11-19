package moodstat.database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;

@Dao
public interface ProductivityTextDAO {
    @Insert
    void insert(ProductivityTextEntity text);
    @Insert
    void insertAll(List<ProductivityTextEntity> texts);

    @Query("SELECT * FROM productivity_text")
    List<ProductivityTextEntity> getAllEntries();

    @Query("SELECT * FROM productivity_text WHERE id = :id")
    ProductivityTextEntity getEntryById(long id);

    @Delete
    void delete(ProductivityTextEntity moodText);

}
