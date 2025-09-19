package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCity extends DialogFragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_CITY = "city_obj";

    public interface Listener {
        void onCityEdited(int position, String newName, String newProvince);
    }

    private Listener listener;

    public static EditCity newInstance(int position, City city) {
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putSerializable(ARG_CITY, city);

        EditCity fragment = new EditCity();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCity.Listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.edit_city, null);

        EditText nameField = view.findViewById(R.id.edit_city_name);
        EditText provinceField = view.findViewById(R.id.edit_city_province);

        Bundle args = getArguments();
        int pos = args.getInt(ARG_POSITION);
        City city = (City) args.getSerializable(ARG_CITY);

        if (city != null) {
            nameField.setText(city.getCity());
            provinceField.setText(city.getProvince());
        }

        return new AlertDialog.Builder(getContext())
                .setTitle("Add/Edit City")
                .setView(view)
                .setPositiveButton("OK", (d, which) -> {
                    if (listener != null) {
                        listener.onCityEdited(
                                pos,
                                nameField.getText().toString(),
                                provinceField.getText().toString()
                        );
                    }
                })
                .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                .create();
    }
}
