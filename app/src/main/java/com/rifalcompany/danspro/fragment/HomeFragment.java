package com.rifalcompany.danspro.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.rifalcompany.danspro.R;
import com.rifalcompany.danspro.adapter.JobAdapter;
import com.rifalcompany.danspro.api.apiClient;
import com.rifalcompany.danspro.api.apiService;
import com.rifalcompany.danspro.api.jobModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView rvJob;
    private JobAdapter itemAdapter;
    private SearchView searchView;
    private ImageView ivExpand;
    private LinearLayout llExpand;
    private Button btnFilter;
    private EditText etLoc;
    private Switch btnSwitch;
    private String isFulltime;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvJob = view.findViewById(R.id.rv_job);
        rvJob.setLayoutManager(new LinearLayoutManager(getActivity()));
        ivExpand = view.findViewById(R.id.iv_expand);
        llExpand = view.findViewById(R.id.ll_expand);
        llExpand.setVisibility(View.GONE);
        btnFilter = view.findViewById(R.id.btn_filter);
        etLoc = view.findViewById(R.id.et_loc);
        btnSwitch = view.findViewById(R.id.switch_button);

        ivExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llExpand.getVisibility() == View.VISIBLE) {
                    hidellExpand(llExpand);
                } else {
                    showllExpand(llExpand);
                }
            }
        });

        isFulltime = "Full Time";
        btnSwitch.setOnClickListener(v -> {
            if (btnSwitch.isChecked()) {
                isFulltime = "Full Time";
            } else {
                isFulltime = "";
            }
        });

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission
                Toast.makeText(getActivity(), "" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text change
                return false;
            }
        });

        /* call api all item */
        apiService api = apiClient.getClient().create(apiService.class);
        Call<List<jobModel>> call = api.getItems();
        call.enqueue(new Callback<List<jobModel>>() {
            @Override
            public void onResponse(Call<List<jobModel>> call, Response<List<jobModel>> response) {
                if (response.isSuccessful()) {
                    List<jobModel> items = response.body();
                    itemAdapter = new JobAdapter(items, "Full Time", getActivity());
                    rvJob.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<jobModel>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /* call api all item */

        btnFilter.setOnClickListener(v -> {
            /* call api single item */
            Call<List<jobModel>> callSingle = api.getSingleItems(searchView.getQuery().toString(), etLoc.getText().toString());
            callSingle.enqueue(new Callback<List<jobModel>>() {
                @Override
                public void onResponse(Call<List<jobModel>> callSingle, Response<List<jobModel>> response) {
                    if (response.isSuccessful()) {
                        List<jobModel> items = response.body();
                        itemAdapter = new JobAdapter(items, isFulltime, getActivity());
                        rvJob.setAdapter(itemAdapter);
                    }
                }

                @Override
                public void onFailure(Call<List<jobModel>> callSingle, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            /* call api single item */
        });

        return view;
    }

    private void showllExpand(LinearLayout llExpand) {
        llExpand.setVisibility(View.VISIBLE);
        ivExpand.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24));
    }

    private void hidellExpand(LinearLayout llExpand) {
        llExpand.setVisibility(View.GONE);
        ivExpand.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24));
    }
}