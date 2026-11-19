package moodstat.activities.daily_report;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.example.moodstat.R;

public class DailyReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_daily_report);


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        // mood Fragment initialisation
        int minInputs = 3;
        InputFragment moodFragment = InputFragment.newInstance(
                "Wie geht es dir heute?",
                "Liste mindestens " + minInputs +  " Dinge auf, die heute schön waren.",
                minInputs
        );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.moodFragmentContainer, moodFragment)
                .commit();


        // succes Fragment initialisation
        InputFragment successFragment = InputFragment.newInstance(
                "Wie erfolgreich warst du heute darin, deine Ziele umzusetzten?",
                "Liste mindestens " + minInputs +  " Dinge auf, die du heute gut gemacht hast.",
                minInputs
        );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.successFragmentContainer, successFragment)
                .commit();


    }
}