package moodstat.activities.daily_report;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moodstat.R;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import moodstat.App;
import moodstat.viewmodel.DailyReportViewModel;
import moodstat.viewmodel.DailyReportViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_SLIDER_LABEL = "slider_label";
    private static final String ARG_LIST_LABEL = "list_label";
    private static final String ARG_N_TEXT_FIELDS = "nTextFields";

    // parameter objects
    private String mSliderLabel;
    private String mListLabel;
    private Integer mNTextField;
    private ArrayList<EditText> inputFields = new ArrayList<>();
    private Float sliderValue = null;

    private DailyReportViewModel viewModel;


    public InputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sliderLabel displayed text over the slider
     * @param listLabel displayed text over the list
     * @param nTextFields number of text fields
     * @return A new instance of fragment InputFragment.
     */
    public static InputFragment newInstance(String sliderLabel, String listLabel, int nTextFields) {
        InputFragment fragment = new InputFragment();

        // retrieve arguments
        Bundle args = new Bundle();
        args.putString(ARG_SLIDER_LABEL, sliderLabel);
        args.putString(ARG_LIST_LABEL, listLabel);
        args.putInt(ARG_N_TEXT_FIELDS, nTextFields);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSliderLabel = getArguments().getString(ARG_SLIDER_LABEL);
            mListLabel = getArguments().getString(ARG_LIST_LABEL);
            mNTextField = getArguments().getInt(ARG_N_TEXT_FIELDS);
        }

        // initialise viewModel
        App app = (App) requireActivity().getApplication();
        DailyReportViewModelFactory factory = new DailyReportViewModelFactory(app.getRepo());
        viewModel = new ViewModelProvider(requireActivity(), factory).get(DailyReportViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_input,
                container,
                false
        );

        setupLabels(view);
        setupInputFields(view);
        setupAddButton(view);
        setupSlider(view);

        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalDate today = LocalDate.now();

        Slider slider = view.findViewById(R.id.slider);

        viewModel.getReportByDate(today).observe(
                getViewLifecycleOwner(),
                report ->{
                    if (report == null) {
                        // no current report found -> show standard values
                        return;
                    }

                    // TODO: fill formular with loaded values

                }
        );
    }

    /**
     *
     * @param i list index of new object
     * @param parent The ViewGroup object which should contain the new text field
     */
    public void createNewTextField(int i, ViewGroup parent){
        int number = i + 1;
        TextView numberText = new TextView(requireContext());
        numberText.setText(number + ".");

        // define layout params for text fields
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        params.setMargins(0, 8, 0 ,8 );

        EditText newTextField = new EditText(requireContext());
        newTextField.setLayoutParams(params);

        LinearLayout labeledTextField = new LinearLayout(requireContext());
        labeledTextField.addView(numberText);
        labeledTextField.addView(newTextField);

        parent.addView(labeledTextField);
        inputFields.add(newTextField);
    }

    public void setupLabels(View view){
        TextView sliderLabelView = view.findViewById(R.id.sliderLabel);
        sliderLabelView.setText(mSliderLabel);

        TextView listLabelView = view.findViewById(R.id.listLabel);
        listLabelView.setText(mListLabel);
    }

    public void setupInputFields(View view){
        LinearLayout inputContainer = view.findViewById(R.id.inputContainer);

        for (int i = 0; i < mNTextField ; i++){
            createNewTextField(i, inputContainer);
        }
    }

    public void setupSlider(View view){
        Slider slider = view.findViewById(R.id.slider);

        slider.addOnChangeListener((sliderView, value, fromUser) -> {
            if(fromUser){
                sliderValue = slider.getValue();
            }
        });
    }

    public void setupAddButton(View view){
        LinearLayout inputContainer = view.findViewById(R.id.inputContainer);
        Button addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener( v -> {
            createNewTextField(mNTextField, inputContainer);
            mNTextField++;
        });


    }


    public ArrayList<String> getTextInputs() {
        ArrayList<String> results = new ArrayList<>();

        for (EditText field : inputFields ) {
            results.add(field.getText().toString());
        }

        return results;
    }

    public float getSliderInputs(){
        return sliderValue;
    }
}