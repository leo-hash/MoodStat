package moodstat.daily_report;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moodstat.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_TITLE = "title";
    private static final String ARG_N_TEXT_FIELDS = "nTextFields";

    // parameter objects
    private String mTitle;
    private Integer mNTextField;
    private ArrayList<EditText> inputFields = new ArrayList<>();


    public InputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title displayed title
     * @param nTextFields number of text fields
     * @return A new instance of fragment InputFragment.
     */
    public static InputFragment newInstance(String title, int nTextFields) {
        InputFragment fragment = new InputFragment();

        // retrieve arguments
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_N_TEXT_FIELDS, nTextFields);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mNTextField = getArguments().getInt(ARG_N_TEXT_FIELDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        TextView titleView = view.findViewById(R.id.fragmentTitle);
        LinearLayout inputContainer = view.findViewById(R.id.inputContainer);

        titleView.setText(mTitle);

        // create n text fields
        for (int i = 0; i < mNTextField ; i++){
            createNewTextField(i, inputContainer);
        }

        // create add button
        Button addButton = view.findViewById(R.id.addButton);

        addButton.setOnClickListener( v -> {
            createNewTextField(mNTextField, inputContainer);
            mNTextField++;

        });
        return view;
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


    public ArrayList<String> getInputs() {
        ArrayList<String> results = new ArrayList<>();

        for (EditText field : inputFields ) {
            results.add(field.getText().toString());
        }

        return results;
    }
}