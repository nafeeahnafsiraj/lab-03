package com.example.listycitylab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditCity.Listener {

    private ArrayList<City> cityData;
    private CustomList cityAdapter;
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityData = new ArrayList<>();
        cityData.add(new City("Edmonton", "AB"));
        cityData.add(new City("Vancouver", "BC"));
        cityData.add(new City("Toronto", "ON"));
        cityData.add(new City("Hamilton", "ON"));

        cityAdapter = new CustomList(this, cityData);
        cityList = findViewById(R.id.city_list);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = cityData.get(position);
            EditCity dialog = EditCity.newInstance(position, selected);
            dialog.show(getSupportFragmentManager(), "edit_city");
        });
    }

    @Override
    public void onCityEdited(int position, String newName, String newProvince) {
        City c = cityData.get(position);
        c.setCity(newName);
        c.setProvince(newProvince);
        cityAdapter.notifyDataSetChanged();
    }
}
