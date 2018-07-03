package com.example.yuhei.shukatsuapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yuhei.shukatsuapp.todoDatabase.HelpTodo;
import com.example.yuhei.shukatsuapp.todoDatabase.TodoDatabaseAccess;

public class TodoEditActivity extends AppCompatActivity {

    private EditText etText;
    private Button btnSave;
    private Button btnCancel;
    private HelpTodo todo;

//    Button savebtn,okbtn,addbtn,viewbtn;
//    EditText deadlineadd,ccomentadd;
//    TextView deadlinetxt,actortxt;
//    Dialog AddDialog,ViewDialog;
//    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_edit);


//        pref = getSharedPreferences("DetailPref", Context.MODE_PRIVATE);
//        addbtn = findViewById(R.id.add);
//        viewbtn = findViewById(R.id.view);




        this.etText = findViewById(R.id.etText);
        this.btnSave = findViewById(R.id.btnSave);
        this.btnCancel = findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            todo = (HelpTodo) bundle.get("TODO");
            if (todo != null) {
                this.etText.setText(todo.getText());
            }
        }

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });
    }

    public void onSaveClicked(){
        TodoDatabaseAccess databaseAccess2 =TodoDatabaseAccess.getInstance(this);
        databaseAccess2.open();
        if (todo == null){
            HelpTodo temp1 = new HelpTodo();
            temp1.setText(etText.getText().toString());
            databaseAccess2.save(temp1);
        }else{
            todo.setText(etText.getText().toString());
            databaseAccess2.update(todo);
        }
        databaseAccess2.close();
        this.finish();
    }

    public void onCancelClicked(){
        this.finish();
    }


}
