package moodstat.database.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(
        tableName = "daily_report",
        indices = {@Index(value = "date", unique = true)}
)

public class DailyReportEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public float moodValue;
    public float productivityValue;
    public LocalDate date;

    public DailyReportEntity(
            float moodValue,
            float productivityValue,
            LocalDate date){
        this.moodValue = moodValue;
        this.productivityValue = productivityValue;
        this.date = date;
    }
}
