package ru.samsung.itschool.dbgame;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class StatActivity extends Activity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        DBManager db = DBManager.getInstance(this);
        ArrayList<Result> results = db.getAllResults();
        TextView count= findViewById(R.id.count);
        count.setText(db.getGamesCount() + "");
        TextView max= findViewById(R.id.max);

    }
}
