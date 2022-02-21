package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    ListView listView;
    int idStr;

    String[] CATEG = {"1.\tРаспространение предложения \n","2.\tСоставление рассказа по картинке \n","3.\tПересказ сказки с опорой на картинку \n",
            "4.\tСоставление рассказа по серии картинок \n","5.\tРазговор по телефону двух сказочных персонажей \n", "6.\tПридумывание продолжения к сказке \n",
            "7.\tДобавление нового персонажа к уже известной сказке \n", "8.\tПридумывание истории с определенным предметом \n",
            "9.\tСоставление рассказа по мнемотаблице \n","10.\tСоставление рассказа из личного опыта \n"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        listView = (ListView) findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                //Toast.makeText(StartActivity.this, "Выбран пункт №"+ i +"", Toast.LENGTH_SHORT).show();

                idStr = i*5+1;
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("idStr",idStr);
                startActivity(intent);
            }
        });

    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return CATEG.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.listview_custom, null);

            TextView categ = (TextView)view.findViewById(R.id.categ);
            categ.setText(CATEG[i]);
            return view;
        }
    }
}
