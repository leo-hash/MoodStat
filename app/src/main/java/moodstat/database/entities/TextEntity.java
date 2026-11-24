package moodstat.database.entities;

public interface TextEntity {

    public long getId();
    public long getDailyReportId();
    public String getText();

    public void setId(long id);
    public void setDailyReportId(long dailyReportId);
    public void setText(String text);
}
