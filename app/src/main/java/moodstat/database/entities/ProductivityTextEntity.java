package moodstat.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "productivity_text",
        foreignKeys = @ForeignKey(
                entity = DailyReportEntity.class,
                parentColumns = "id",
                childColumns = "dailyReportId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("dailyReportId")}
)
public class ProductivityTextEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long dailyReportId;
    public String text;

    public ProductivityTextEntity(long dailyReportId, String text){
        this.dailyReportId = dailyReportId;
        this.text = text;
    }
}


