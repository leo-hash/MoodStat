package moodstat.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import moodstat.repositories.DailyReportRepository;

public class DailyReportViewModelFactory implements ViewModelProvider.Factory {
    private final DailyReportRepository repo;

    public DailyReportViewModelFactory(DailyReportRepository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DailyReportViewModel.class)) {
            return (T) new DailyReportViewModel(repo);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
