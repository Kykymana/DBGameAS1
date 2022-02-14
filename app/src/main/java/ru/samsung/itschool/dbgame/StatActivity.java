package ru.samsung.itschool.dbgame;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class StatActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        DBManager db = DBManager.getInstance(this);
        TextView textView = findViewById(R.id.count);
        textView.setText(db.getGamesCount() + "");
    }

}
