package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteInputActivity extends AppCompatActivity {


    TextView noteIn;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_input);

        noteIn = findViewById(R.id.note);
        button = findViewById(R.id.button);

        Intent intent = getIntent();
        int noteNum = intent.getIntExtra("NUM", 0);

        noteIn.setText(intent.getStringExtra("TEXT"));
        button.setText(intent.getStringExtra("LABEL"));
        setColor(noteNum);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("TEXT", noteIn.getText().toString());
                intent.putExtra("NUM", noteNum);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static Intent getNoteInputIntent(Context c, int noteNum, String text, String bLabel)
    {
        Intent intent = new Intent(c, NoteInputActivity.class);
        intent.putExtra("NUM", noteNum);
        intent.putExtra("TEXT", text);
        intent.putExtra("LABEL", bLabel);
        return intent;
    }

    private void setColor(int no) {
        if (no == 1) {
            noteIn.setBackgroundResource(R.color.noteRed);
        } else if (no == 2) {
            noteIn.setBackgroundResource(R.color.noteYellow);
        } else if (no == 3) {
            noteIn.setBackgroundResource(R.color.notePurple);
        } else {
            noteIn.setBackgroundResource(R.color.noteBlue);
        }
    }

}
