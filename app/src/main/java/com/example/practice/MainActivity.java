package com.example.practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edtName, edtNumber;
    Button btnInit, btnInput, btnUpdate, btnDelete, btnSelect;
    TextView tvName, tvNumber;
    TextView groupName, groupNumber;

    MyDBHelper myDB;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText)findViewById(R.id.edtName);
        edtNumber = (EditText)findViewById(R.id.edtNumber);
        btnInit = (Button)findViewById(R.id.btnInit);
        btnInput = (Button)findViewById(R.id.btnInput);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnSelect = (Button)findViewById(R.id.btnSelect);
        tvName = (TextView)findViewById(R.id.tvName);
        tvNumber = (TextView)findViewById(R.id.tvNumber);
        groupName = (TextView)findViewById(R.id.groupName);
        groupNumber = (TextView)findViewById(R.id.groupNumber);

        myDB = new MyDBHelper(this);



        }
        class MyDBHelper extends SQLiteOpenHelper{
            public MyDBHelper(@Nullable Context context) { // 생성자 생성
                super(context, "groupDB", null, 1); // 장소, database 명, , 버전
            }

            @Override // @Override가 존재하는 이유: 메서드 사용중 오타가 있으면 생성이 아니라 오버레이드(부모)에 없는 메서드라고 알려줌
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE groupTBL (gName TEXT PRIMARY KEY, gNumber INTEGER); ");
                // 생성 테이블: groupTBL, 필드명1: gName Type: TEXT, PRIMARY KEY :(중복을 허용하지 않음, null 허용하지 않음), 필드명2: gNumber, Type: INTEGER
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE groupTBL"); // 만약 groupTBL 테이블이 존재한다면 groupTBL 테이블을 삭제한다.
                onCreate(db); // groupTBL 테이블을 삭제하고 다시 생성한다.
            }
    }
}
