package moodstat.database.relations;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;

/**
 * a way to connect the daily_report table with mood_text and productivity_text
 * combines all 3 entity objects in one class for easy usage
 */
public class DailyReportWithText {

    @Embedded
    public DailyReportEntity dailyReport;

    @Relation(
            parentColumn = "id",
            entityColumn = "dailyReportId"
    )
    public List<MoodTextEntity> moodTexts = new ArrayList<>();

    @Relation(
            parentColumn = "id",
            entityColumn = "dailyReportId"
    )
    public List<ProductivityTextEntity> productivityTexts = new ArrayList<>();



    @NonNull
    @Override
    public String toString(){
        String string = "DailyReportWithText: {dailyReport: " + dailyReport + ", ";

        string += "moodTexts: [";
        for (MoodTextEntity text : moodTexts){
            string += text.toString() + ", ";
        }
        string += "], ";

        string += "productivityTexts: [";
        for (ProductivityTextEntity text : productivityTexts){
            string += text.toString() + ", ";
        }
        string += "], ";

        string += "}";
        return string;
    }
}