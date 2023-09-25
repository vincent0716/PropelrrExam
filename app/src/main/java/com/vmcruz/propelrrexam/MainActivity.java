package com.vmcruz.propelrrexam;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.vmcruz.propelrrexam.databinding.ActivityMainBinding;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiInterface= APIClient.getClient().create(APIInterface.class);

        initListeners();
    }

    private void initListeners() {
        binding.spGender.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.genders)));
        binding.edtBdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = new DatePicker(binding.edtBdate,binding.tvAge);
                datePicker.show(getSupportFragmentManager(),"tag");

            }
        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(initFields()){
                    Call<ApiResponse> stringCall =apiInterface.saveInfo(binding.edtFullName.getText().toString(),binding.edtEmail.getText().toString(),binding.edtMobile.getText().toString(),binding.tvAge.getText().toString());
                    stringCall.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if(response.isSuccessful()){
                                if (response.body() != null) {
                                    showDialog(response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            Log.e("TAG", Objects.requireNonNull(t.getMessage()));
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this, "Please check the formats", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void showDialog(String res){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Result");
        alertDialog.setMessage(res);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(null);
        alertDialog.show();
    }


    private boolean initFields(){
        int age = Integer.parseInt(binding.tvAge.getText().toString());
        String regex = "^(09|\\+639)\\d{9}$";

        // Compile the regular expression pattern
        Pattern patternMob = Pattern.compile(regex);

        String pattern = "^[a-zA-Z,\\. ]*$";

        if(!Pattern.matches(pattern,binding.edtFullName.getText().toString()) || binding.edtFullName.getText().toString().isEmpty()){
            binding.edtFullName.setError("Incorrect Format");
            return false;
        }
        else{
            binding.edtFullName.setError(null);
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString()).matches() || binding.edtEmail.getText().toString().isEmpty()){
            binding.edtEmail.setError("Incorrect Format");
            return false;
        }
        else{
            binding.edtEmail.setError(null);
        }

        if(binding.edtMobile.getText().toString().isEmpty() || !Pattern.compile(regex).matcher(binding.edtMobile.getText().toString()).matches()){
            binding.edtMobile.setError("Incorrect Format");
            return false;
        }
        else{
            binding.edtBdate.setError(null);
        }

        if(binding.edtBdate.getText().toString().isEmpty() || binding.tvAge.getText().toString().isEmpty() ||  age < 18){
            binding.edtBdate.setError("You must be 18 years old");
            return false;
        }
        else{
            binding.edtBdate.setError(null);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}