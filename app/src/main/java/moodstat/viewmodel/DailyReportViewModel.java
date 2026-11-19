package moodstat.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.util.List;

import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;
import moodstat.database.relations.DailyReportWithText;
import moodstat.repositories.DailyReportRepository;


/**
 * stores UI state and connects it to the repository (where the business logic is)
 * LiveData-object is used to store data and use the Observer pattern to watch for changes of the data.
 * Instead of a normal Observable it's aware of the Life Cycle of app components.
 */
public class DailyReportViewModel extends ViewModel {
    private final DailyReportRepository repo;
    private final MutableLiveData<Long> savedReportId = new MutableLiveData<>();

    public DailyReportViewModel(DailyReportRepository repo){
        this.repo = repo;
    }

    public LiveData<DailyReportWithText> getReport(long id){
        return repo.getReport(id);
    }

    public LiveData<DailyReportWithText> getReportByDate(LocalDate date) {
        return repo.getReportByDate(date);
    }

    public LiveData<Long> getSavedReportId(){
        return savedReportId;
    }

    public void saveReport(
            DailyReportEntity report,
            List<MoodTextEntity> moodTexts,
            List<ProductivityTextEntity> productivityTexts){
        repo.saveReport(
                report,
                moodTexts,
                productivityTexts,
                id -> savedReportId.postValue(id)
        );
    }


}
