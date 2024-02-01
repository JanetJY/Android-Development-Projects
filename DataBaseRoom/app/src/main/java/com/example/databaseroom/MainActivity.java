package com.example.databaseroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StudentDAO studentDAO = StudentDBInstance.getDatabase(getApplicationContext()).studentDAO();


        EditText name = findViewById(R.id.studentName);
        Button add = findViewById(R.id.addButton);
        Button next = findViewById(R.id.nextActivity);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = name.getText().toString();
                Student student = new Student();
                student.setName(studentName);


                studentDAO.insert(student);


                Toast toast = Toast.makeText(MainActivity.this,
                        "A student is added", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        NextActivity.class);
                startActivity(intent);
            }
        });
    }
}