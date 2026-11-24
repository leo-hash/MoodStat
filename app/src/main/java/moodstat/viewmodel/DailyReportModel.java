package moodstat.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import moodstat.database.entities.DailyReportEntity;
import moodstat.database.entities.MoodTextEntity;
import moodstat.database.entities.ProductivityTextEntity;
import moodstat.database.relations.DailyReportWithText;

/**
 * DailyReportModel stores the formular data before it's stored in the database
 * -> frontend data representation
 */
public class DailyReportModel {
    private MutableLiveData<Float> moodValue = new MutableLiveData<>(50.0F);
    private MutableLiveData<Float> productionValue = new MutableLiveData<>(50.0F);;

    private MutableLiveData<List<String>> moodTexts = new MutableLiveData<>();
    private MutableLiveData<List<String>> productivityTexts = new MutableLiveData<>();



    public void setDataFromReport(DailyReportWithText report){
        moodValue = new MutableLiveData<>(report.dailyReport.moodValue);
        productionValue = new MutableLiveData<>(report.dailyReport.productivityValue);

        List<String> moodTextList = Collections.emptyList();
        for (MoodTextEntity text : report.moodTexts){
            moodTextList.add(text.toString());
        }
        productivityTexts = new MutableLiveData<>(moodTextList);

        List<String> prodTextList = Collections.emptyList();
        for (ProductivityTextEntity text : report.productivityTexts){
            prodTextList.add(text.toString());
        }
        productivityTexts = new MutableLiveData<>(prodTextList);
    }

    public void updateReportWithData(DailyReportWithText report){
        if (report == null){
            report = new DailyReportWithText();
        }

        if (report.dailyReport == null){
            report.dailyReport = new DailyReportEntity(
                    moodValue.getValue(),
                    productionValue.getValue(),
                    LocalDate.now()
            );
        }

        if (report.moodTexts == null){
            report.moodTexts = new ArrayList<MoodTextEntity>();
        }

        if (report.productivityTexts == null){
            report.moodTexts = new ArrayList<MoodTextEntity>();
        }

        report.dailyReport.moodValue = moodValue.getValue();
        report.dailyReport.productivityValue = productionValue.getValue();

        List<MoodTextEntity> oldEntities = report.moodTexts;
        List<MoodTextEntity> updatedEntities = report.moodTexts;
        List<String> newTexts = moodTexts.getValue();

        // TODO: newTexts == null -> newTexts.size() leads to nullPointerException
//        for (int i = 0 ; i < newTexts.size(); i++ ){
//            MoodTextEntity entity;
//
//            if (i < oldEntities.size()){
//                // copy old entities to save ids
//                entity = oldEntities.get(i);
//                entity.text = newTexts.get(i);
//            } else {
//                // create new entity
//                entity = new MoodTextEntity(
//                        report.dailyReport.id,
//                        newTexts.get(i)
//                );
//            }
//            updatedEntities.add(entity);
//        }
//        report.moodTexts = updatedEntities;

        // TODO: do the same with productivityTexts, maybe polymorph -> Interface?

    }


    @NonNull
    @Override
    public String toString(){
        String string = "DailyReportModel: { ";
        string += "moodValue: " + moodValue.getValue() + ", ";
        string += "productionValue: " + productionValue.getValue() + ", ";


        string += "moodTexts: [";
        if (moodTexts.getValue() != null) {
            for (String text : moodTexts.getValue()) {
                string += text + ", ";
            }
        }
        string += "], ";

        string += "productivityTexts: [";

        if ( productivityTexts.getValue() != null ) {
            for (String text : productivityTexts.getValue()) {
                string += text + ", ";
            }
        }
        string += "], ";

        string += "}";
        return string;
    }


    public MutableLiveData<Float> getMoodValue() {
        return moodValue;
    }

    public MutableLiveData<Float> getProductionValue() {
        return productionValue;
    }

    public MutableLiveData<List<String>> getMoodTexts() {
        return moodTexts;
    }
    public MutableLiveData<List<String>>getProductivityTexts() {
        return productivityTexts;
    }

    public void setMoodValue(MutableLiveData<Float> moodValue) {
        this.moodValue = moodValue;
    }

    public void setProductionValue(MutableLiveData<Float> productionValue){
        this.productionValue = productionValue;
    }


    public void setMoodTexts(MutableLiveData<List<String>> moodTexts) {
        this.moodTexts = moodTexts;
    }

    public void setProductivityTexts(MutableLiveData<List<String>> productivityTexts) {
        this.productivityTexts = productivityTexts;
    }
}
