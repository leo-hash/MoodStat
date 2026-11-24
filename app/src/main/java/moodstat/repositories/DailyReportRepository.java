package moodstat.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

import moodstat.database.AppDatabase;
import moodstat.database.DAOs.DailyReportDAO;
import moodstat.database.DAOs.MoodTextDAO;
import moodstat.database.DAOs.ProductivityTextDAO;
import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;
import moodstat.database.entities.TextEntity;
import moodstat.database.relations.DailyReportWithText;


/**
 * encapsulates the business logic for accessing the data binding objects of the database
 * via executer (in an own thread)
 */
public class DailyReportRepository {
    private final AppDatabase database;

    private final DailyReportDAO dailyReportDAO;
    private final MoodTextDAO moodTextDAO;
    private final ProductivityTextDAO productivityTextDAO;

    private final ExecutorService executor;

    public DailyReportRepository(AppDatabase database){
        this.database = database;

        this.dailyReportDAO = database.DailyReportDAO();
        this.moodTextDAO = database.MoodTextDAO();
        this.productivityTextDAO = database.ProductivityTextDAO();

        this.executor = Executors.newSingleThreadExecutor();
    }

    public void saveReport(DailyReportEntity report,
                           List<MoodTextEntity> moodTexts,
                           List<ProductivityTextEntity> productivityTexts,
                           Consumer<Long> callback){
        executor.execute(() -> {
            long id = insertReportWithTexts(
                    report,
                    moodTexts,
                    productivityTexts
            );

            callback.accept(id);
        });
    }

    public void getReport(long id, Consumer<DailyReportWithText> callback) {
        executor.execute(() -> {
            DailyReportWithText report = getReportWithText(
                    () -> dailyReportDAO.getEntryById(id)
            );
            callback.accept(report);
        });
    }

    public void getReport(LocalDate date, Consumer<DailyReportWithText> callback) {
        executor.execute(() -> {
            DailyReportWithText report = getReportWithText(
                    () -> dailyReportDAO.getEntryByDate(date)
            );
            callback.accept(report);
        });
    }

    private DailyReportWithText getReportWithText(Supplier<DailyReportEntity> query){
        return database.runInTransaction(() -> {
            DailyReportEntity report = query.get();
            DailyReportWithText reportWithText = new DailyReportWithText();

            reportWithText.moodTexts = moodTextDAO.getEntryByReportId(report.id);
            reportWithText.productivityTexts = productivityTextDAO.getEntryByReportId(report.id);

            return reportWithText;
        });
    }


    private long insertReportWithTexts(DailyReportEntity dailyReport,
                                       List<MoodTextEntity> moodTexts,
                                       List<ProductivityTextEntity> productivityTexts){
        return database.runInTransaction(() -> {
            long reportId = dailyReportDAO.insert(dailyReport);

            // set reportId and insert all moodTexts
            for (MoodTextEntity text : moodTexts){
                text.setDailyReportId(reportId);
            }
            moodTextDAO.insertAll(moodTexts);

            // set reportId and insert all productivityTexts
            for (ProductivityTextEntity text : productivityTexts) {
                text.setDailyReportId(reportId);
            }
            productivityTextDAO.insertAll(productivityTexts);

            return reportId;
        });

    }


    public long updateReportWithTexts(
            DailyReportEntity report,
            List<MoodTextEntity> moodTexts,
            List<ProductivityTextEntity> productivityTexts){
        return database.runInTransaction(() -> {

            dailyReportDAO.update(report);

            // sync and update moodText
            List<List<MoodTextEntity>> moodLists = syncTextEntities(
                    report.id,
                    moodTexts,
                    moodTextDAO.getEntryByReportId(report.id)
            );

            moodTextDAO.insertAll(moodLists.get(0));
            moodTextDAO.updateAll(moodLists.get(1));
            moodTextDAO.deleteAll(moodLists.get(2));

            // sync and update productivityText
            List<List<ProductivityTextEntity>> productivityList = syncTextEntities(
                    report.id,
                    productivityTexts,
                    productivityTextDAO.getEntryByReportId(report.id)
            );

            productivityTextDAO.insertAll(productivityList.get(0));
            productivityTextDAO.updateAll(productivityList.get(1));
            productivityTextDAO.deleteAll(productivityList.get(2));

            return report.id;
        });
    }

    /**
     *
     * @param reportId id of the current report
     * @param newTexts new texts of type TextEntity coming from the frontend -> insert/update?
     * @param oldTexts old database of type TextEntity entries -> update/delete?
     * @return List with 3 elements:
     * 1. List<TextEntity> toInsert,
     * 2. List<TextEntity> toUpdate,
     * 3. List<TextEntity> toDelete
     */
    public <T extends TextEntity> List<List<T>> syncTextEntities(long reportId,
                                                   List<T> newTexts,
                                                   List<T> oldTexts){
        Map<Long, T> oldEntries = new HashMap<>();
        Set<Long> newEntryIds = new HashSet<>();

        List<T> toInsert = new ArrayList<>();
        List<T> toUpdate = new ArrayList<>();
        List<T> toDelete = new ArrayList<>();

        for ( T entity : oldTexts ){
            oldEntries.put(entity.getId(), entity);
        }

        for (T entity : newTexts){
            entity.setDailyReportId(reportId);

            if (entity.getId() == 0){
                // new entities
                toInsert.add(entity);

            } else {
                if ( !oldEntries.containsKey(entity.getId()) ){
                    // ID != 0 AND Not in oldEntries -> no new entry(insert) and no old entry(update)
                    throw new IllegalArgumentException("Unknown TextEntity ID: " + entity.getId());
                }

                if ( !newEntryIds.add(entity.getId()) ){
                    throw new IllegalArgumentException("Two TextEntities with same ID: " + entity.getId());
                }

                toUpdate.add(entity);
            }
        }

        // find all old entities, which no longer exist in new list
        for (T entity : oldTexts){
            if (!newEntryIds.contains(entity.getId())){
                toDelete.add(entity);
            }
        }

        return Arrays.asList(toInsert, toUpdate, toDelete);
    }
}
