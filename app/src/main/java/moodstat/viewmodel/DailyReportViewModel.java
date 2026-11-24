package moodstat.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;

import moodstat.database.relations.DailyReportWithText;
import moodstat.repositories.DailyReportRepository;


/**
 * stores UI state and connects it to the repository (where the business logic is)
 * LiveData-object is used to store data and use the Observer pattern to watch for changes of the data.
 * Instead of a normal Observable it's aware of the Life Cycle of app components.
 */
public class DailyReportViewModel extends ViewModel {
    private static final String TAG = "ViewModel";
    private final DailyReportRepository repo;
    private final MutableLiveData<Long> savedReportId = new MutableLiveData<>();

    private LocalDate currentDate;

    private DailyReportModel data = new DailyReportModel();
    private DailyReportWithText report;


    public DailyReportViewModel(DailyReportRepository repo){
        this.repo = repo;
        this.currentDate = LocalDate.now();
        loadReport();
    }

    public void loadReport(){
        Log.d(TAG, "Try to load report");
        repo.getReport(currentDate, reportData ->{
            report = reportData;
            if (report != null) {
                data.setDataFromReport(report);
            } else {
//                TODO: should be maybe in DailyReportData.updateReportWithData()
                report = new DailyReportWithText();
                Log.d(TAG, "Report Empty, created new one");
            }
            Log.d(TAG, "Report: " + report);
        });
        Log.d(TAG, "Loading Successful");
    }

    public void loadReport(LocalDate date){
        repo.getReport(date, reportData ->{
            report = reportData;
//            if (report != null) {
                data.setDataFromReport(report);
//            } else {
                // TODO: handle null pointer, just creating a new object could send the wrong message
                // maybe just let the exception happen, till better solution is found
//                report = new DailyReportWithText();
//            }
        });
    }

    public void loadReport(long id){
        repo.getReport(id, reportData ->{
            report = reportData;
            data.setDataFromReport(report);
        });
    }

    public  void saveReport(){
        Log.d(TAG, "Try to save report");
        Log.d(TAG, "DailyReportData (Frontend data): " + data);

        data.updateReportWithData(report);

        repo.saveReport(
                report.dailyReport,
                report.moodTexts,
                report.productivityTexts,
                id -> savedReportId.postValue(id)
        );
        Log.d(TAG, "Saving Successful. Report: " + report);

    }


    //    ----- GETTER -----

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public DailyReportModel getData(){
        return data;
    }

    //    ----- SETTER -----

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }
}
