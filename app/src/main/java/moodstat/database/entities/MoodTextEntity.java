package moodstat.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "mood_text",
        foreignKeys = @ForeignKey(
                entity = DailyReportEntity.class,
                parentColumns = "id",
                childColumns = "dailyReportId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("dailyReportId")}
)
public class MoodTextEntity implements TextEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long dailyReportId;
    private String text;

    public MoodTextEntity(long dailyReportId, String text){
        this.dailyReportId = dailyReportId;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString(){
        return "MoodTextEntity: {" +
                "id: " + id + ", " +
                "dailyReportId: " + dailyReportId + ", " +
                "text: " + text +
                "}";
    }

    public long getId() {
        return id;
    }

    public long getDailyReportId() {
        return dailyReportId;
    }

    public String getText() {
        return text;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDailyReportId(long dailyReportId) {
        this.dailyReportId = dailyReportId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
