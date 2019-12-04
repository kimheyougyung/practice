package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edtName, edtNumber;
    Button btnInit, btnInput, btnSelect, btnDelete, btnUpdate;
    TextView tvName, tvNumber; //실행한 결과
    TextView groupName, groupNumber; //SELETE 고정

    MyDBHelper myDB; //객체변수 선언
    SQLiteDatabase sqlDB; //INSERT, SELECT, UPDATE 쿼리를 실행할 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        btnInit = (Button) findViewById(R.id.btnInit);
        btnInput = (Button) findViewById(R.id.btnInput);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        tvName = (TextView) findViewById(R.id.tvName);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        groupName = (TextView) findViewById(R.id.groupName);
        groupNumber = (TextView) findViewById(R.id.groupNumber);

        myDB = new MyDBHelper(this);

        btnInit.setOnClickListener(new View.OnClickListener() { //초기화하는버튼
            @Override
            public void onClick(View v) {
                sqlDB = myDB.getWritableDatabase(); //읽고 쓰기
                myDB.onUpgrade(sqlDB, 1, 2); //1버전은사라지고 2버전 생성
                sqlDB.close(); //닫기는 꼭
            }
        });

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDB.getWritableDatabase(); //읽고 쓰기
                sqlDB.execSQL("INSERT INTO groupTBL VALUES ('" + edtName.getText().toString() + "'," + edtNumber.getText().toString() + ");"); // INSERT 쿼리 실행 //데이터삽입
                //    sqlDB.execSQL("INSERT INTO groupTBL (gName, gNumber) VALUES ('" + edtName.getText.toString() +"'," + edtPersons.getText.toString() + "); "); //

                //    sqlDB.execSQL("INSERT INTO groupTBL VALUES ('동방신기', 5); "); // 실제 SQLite INSERT 문
                //    sqlDB.execSQL("INSERT INTO groupTBL (gName, gNumber) VALUES ('동방신기', 5); "); // 실제 SQLite INSERT 문

                edtName.setText("");
                edtNumber.setText("");
                Toast.makeText(getApplicationContext(), "DB 저장저장~", Toast.LENGTH_SHORT).show();
                sqlDB.close();
                btnSelect.callOnClick();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sqlDB = myDB.getWritableDatabase();
                sqlDB = myDB.getReadableDatabase();

                Cursor cursor;
                String strNames = "\n\n";
                String strNumbers = "\n\n";

                if (edtName.getText().toString().equals("")) {
                    cursor = sqlDB.rawQuery("SELECT *  FROM groupTBL;", null); //조회하기 전체
                    while (cursor.moveToNext()) {//moveToNext() 한줄씩 띄며 db보여주기
                        strNames += cursor.getString(0) + "\n"; //0번째 필드 //문자 Text
                        strNumbers += cursor.getInt(1) + "\n"; //1번째 필드 //정수 Integer
                        // cursor.getdouble(); //실수 real
                    }
                    tvName.setText(strNames);
                    tvNumber.setText(strNumbers);
                } else {
                    cursor = sqlDB.rawQuery("SELECT * FROM groupTBL WHERE gName = '" + edtName.getText().toString() + "';", null); //Where gName = '호호'
                    while (cursor.moveToNext()) {//moveToNext() 한줄씩 띄며 db보여주기
                        strNames += cursor.getString(0) + "\n"; //0번째 필드 //문자 Text
                        strNumbers += cursor.getInt(1) + "\n"; //1번째 필드 //정수 Integer
                        // cursor.getdouble(); //실수 real
                    }
                    tvName.setText(strNames);
                    tvNumber.setText(strNumbers);
                }

                Cursor cursor2;
                String strNames2 = "\n\n";
                String strNumbers2 = "\n\n";

                cursor2 = sqlDB.rawQuery("SELECT *  FROM groupTBL;", null); //조회하기 전체
                while (cursor2.moveToNext()) {//moveToNext() 한줄씩 띄며 db보여주기
                    strNames2 += cursor2.getString(0) + "\n"; //0번째 필드 //문자 Text
                    strNumbers2 += cursor2.getInt(1) + "\n"; //1번째 필드 //정수 Integer
                    // cursor.getdouble(); //실수 real
                }
                groupName.setText(strNames2);
                groupNumber.setText(strNumbers2);

                cursor.close();
                sqlDB.close();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDB.getWritableDatabase();
                sqlDB.execSQL("UPDATE groupTBL SET gNumber = " + edtNumber.getText().toString() + " WHERE  gName = '" + edtName.getText().toString() + "'; ");
                sqlDB.close();
                btnSelect.callOnClick();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myDB.getWritableDatabase();

                sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '" + edtName.getText().toString() + "';");

                sqlDB.close();
                btnSelect.callOnClick();
            }
        });

    }


    class MyDBHelper extends SQLiteOpenHelper {

        public MyDBHelper(@Nullable Context context) { //생성자 생성
            super(context, "groupDB", null, 1); //장소, database이름, , 버전
        }

        @Override // @Override가 존재하는 이유 : 메서드 사용중 오타가 있으면 생성이아니라 오버레이드(부모)에 없는 메서드라고 알려줌
        public void onCreate(SQLiteDatabase db) { //Database의 Table 생성 !!!!!!!!!!!!!!!!!!!!!!!!!

            // db.execSQL("CREATE TABLE 그룹명 (필드명 Type, 필드명 Type, ...); "); //테이블(필드) 생성 메서드!!!!!!!!!
            db.execSQL("CREATE TABLE groupTBL (gName TEXT PRIMARY KEY, gNumber INTEGER); "); //생성 테이블: groupTBL 필드명1: gName Type: TEXT 기본키(중복을 허용하지않음,null허용하지않음), 필드명2: gNumber Type: INTEGER
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int ldVersion, int newVersion) { //??? 기능? //DELETE FROM groupTBL 동일 //
            db.execSQL("DROP TABLE IF EXISTS groupTBL"); //삭제 테이블 만약 존재 groupTBL
            onCreate(db); // db호출
        }
    }
}