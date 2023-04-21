package com.rifalcompany.danspro.activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rifalcompany.danspro.R;
import com.rifalcompany.danspro.api.apiClient;
import com.rifalcompany.danspro.api.apiService;
import com.rifalcompany.danspro.api.responseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView ivBack, ivCompany;
    TextView tvCompany, tvLocation, tvWeb, tvTitle, tvFt, tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        String id_job = bundle.getString("id_job");

        ivBack = findViewById(R.id.iv_backpressed);
        ivBack.setOnClickListener(v->{
            onBackPressed();
        });

        tvCompany = findViewById(R.id.tv_company_name);
        tvWeb = findViewById(R.id.tv_company_website);
        tvTitle = findViewById(R.id.tv_title);
        tvFt = findViewById(R.id.tv_ft);
        tvDesc = findViewById(R.id.tv_desc);
        tvLocation = findViewById(R.id.tv_company_location);

        /* call api detail item */
        apiService api = apiClient.getClient().create(apiService.class);
        Call<ResponseBody> callDetail = api.getDetailItems(id_job);
        callDetail.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> callDetail, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResponse = responseUtils.getResponseJson(response.body());
                        String company_url = jsonResponse.getString("company_url");
                        tvWeb.setOnClickListener(v->{
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(company_url));
                            startActivity(intent);
                        });

                        String company = jsonResponse.getString("company");
                        String title = jsonResponse.getString("title");
                        String location = jsonResponse.getString("location");
                        String desc = jsonResponse.getString("description");
                        String type = jsonResponse.getString("type");
                        tvCompany.setText(company);
                        tvTitle.setText(title);
                        tvLocation.setText(location);

                        tvDesc.setText(Html.fromHtml(desc));

                        String ftApi = type.toLowerCase();
                        String ftStr = "Full Time".toLowerCase();

                        tvFt.setText("No");
                        if (ftApi.equals(ftStr)){
                            tvFt.setText("Yes");
                        }

                        String company_logo = jsonResponse.getString("company_logo");
                        //ngecek dulu gambarnya ada atau ga
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            URL url = new URL(company_logo);
                            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();
                            int code = connection.getResponseCode();

                            if (code == 200 && !company_logo.isEmpty()) {
                                Glide.with(DetailActivity.this)
                                        .load(company_logo)
                                        .into(ivCompany);
                            }
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> callDetail, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /* call api detail item */

    }
}