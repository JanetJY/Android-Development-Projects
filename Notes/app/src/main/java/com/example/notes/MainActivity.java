package com.example.notes;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button addNote;
    Button[] buttons;
    ArrayList<String> notes = new ArrayList<>();;
    int count;

    String note ="";
    ActivityResultLauncher<Intent> NoteInputActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()==RESULT_OK){

                    Intent intent = result.getData();
                    int num = intent.getIntExtra("NUM", 0);
                    String text = intent.getStringExtra("TEXT");

                    if(num > notes.size())
                    {
                        notes.add(text);
                        buttons[notes.size()-1].setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        notes.set(num-1, text);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNote = findViewById(R.id.button);

        Button noteOne = findViewById(R.id.note1);
        Button noteTwo = findViewById(R.id.note2);
        Button noteThree = findViewById(R.id.note3);
        Button noteFour = findViewById(R.id.note4);
        count = 0;
        buttons = new Button[]{noteOne, noteTwo, noteThree, noteFour};

        setInvisible();


        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNoteInputActivity(1, notes.get(0), "Update");
            }
        });

        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNoteInputActivity(2, notes.get(1), "Update");
            }
        });

        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNoteInputActivity(3, notes.get(2), "Update");
            }
        });

        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNoteInputActivity(4, notes.get(3), "Update");
            }
        });

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if(count < 5) {
                    startNoteInputActivity(count, "Please write a note...", "Save");
                }
            }
        });
    }

    private void startNoteInputActivity(int num, String text, String label) {
        Intent intent = NoteInputActivity.getNoteInputIntent(this, num, text, label);
        NoteInputActivityLauncher.launch(intent);
    }

    private void setInvisible()
    {
        for (Button note : buttons)
        {
            note.setVisibility(View.INVISIBLE);
        }
    }


}