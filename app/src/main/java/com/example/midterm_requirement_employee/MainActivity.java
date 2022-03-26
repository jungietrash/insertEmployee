package com.example.midterm_requirement_employee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText name, gender, address, contact, birthday, position;
    Button insertData, updateData, deleteData, viewData;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name_text);
        gender = findViewById(R.id.gender_text);
        address = findViewById(R.id.address_text);
        contact = findViewById(R.id.contact_text);
        birthday = findViewById(R.id.birthday_text);
        position = findViewById(R.id.position_text);

        insertData = findViewById(R.id.insertDataButton);
        updateData = findViewById(R.id.updateDataButton);
        deleteData = findViewById(R.id.deleteDataButton);
        viewData = findViewById(R.id.viewDataButton);


        DB = new DBHelper(this);


        insertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String genderTXT = gender.getText().toString();
                String addressTXT = address.getText().toString();
                String contactTXT = contact.getText().toString();
                String birthdayTXT = birthday.getText().toString();
                String positionTXT = position.getText().toString();


                if(contact.length() ==10 && name.length() >= 1 && gender.length() >= 1 && address.length() >= 1 && birthday.length() >= 1 && position.length() >= 1) {
                    DB.insertuserdata(nameTXT, genderTXT, addressTXT, contactTXT, birthdayTXT, positionTXT);
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                }
                else{
                    contact.setError("Not more or less than 10");
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }
            }
        });


        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String genderTXT = gender.getText().toString();
                String addressTXT = address.getText().toString();
                String contactTXT = contact.getText().toString();
                String birthdayTXT = birthday.getText().toString();
                String positionTXT = position.getText().toString();

                Boolean checkupdatedata = DB.updateuserdata(nameTXT, genderTXT, addressTXT, contactTXT, birthdayTXT, positionTXT);

                if(checkupdatedata)
                    Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DeleteData.class);
                startActivity(i);
            }
        });

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Gender :"+res.getString(2)+"\n");
                    buffer.append("Address :"+res.getString(3)+"\n");
                    buffer.append("Contact :"+res.getString(4)+"\n");
                    buffer.append("Birthday :"+res.getString(5)+"\n");
                    buffer.append("Position :"+res.getString(6)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Worker Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });


    }}

