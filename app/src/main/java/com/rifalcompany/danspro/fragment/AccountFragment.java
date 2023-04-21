package com.rifalcompany.danspro.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rifalcompany.danspro.R;
import com.rifalcompany.danspro.activity.MainActivity;

public class AccountFragment extends Fragment {
    TextView tvHello, tvId;
    Button btnLogout;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        tvHello = v.findViewById(R.id.tv_namehello);
        tvId = v.findViewById(R.id.tv_id);
        btnLogout = v.findViewById(R.id.btn_logout);

        // Mengambil objek SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Mengambil nilai
        String id = sharedPreferences.getString("id_account", "");
        String name = sharedPreferences.getString("name_account", "Hello Name");

        tvHello.setText("Hello, " + name);
        tvId.setText(id);

        btnLogout.setOnClickListener(vi -> {
            // Menyimpan nilai
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id_account", "");
            editor.putString("name_account", "");
            editor.apply();
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
            getActivity().finish();
        });

        return v;
    }
}