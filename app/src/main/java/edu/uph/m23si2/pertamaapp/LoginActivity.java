package edu.uph.m23si2.pertamaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si2.pertamaapp.api.ApiResponse;
import edu.uph.m23si2.pertamaapp.api.ApiService;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import edu.uph.m23si2.pertamaapp.model.Matakuliah;
import edu.uph.m23si2.pertamaapp.model.Prodi;
import edu.uph.m23si2.pertamaapp.model.Provinsi;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtNama, edtPassword;
    Spinner sprProvinsi;
    List<Provinsi> provinsiList =  new ArrayList<>();
    List<String> namaProvinsi = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true) // sementara aktifkan untuk demo
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        //initData();
        sprProvinsi = findViewById(R.id.sprProvinsi);
        adapter =new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,namaProvinsi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sprProvinsi.setAdapter(adapter);
        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wilayah.id")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        //panggil API
        apiService.getProvinsi().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    provinsiList = response.body().getData();
                    namaProvinsi.clear();
                    for(Provinsi p: provinsiList){
                        if(p.getName()!=null){
                            Log.d("Provinsi", p.getName());
                            namaProvinsi.add(p.getName());
                        }
                    }

                    adapter.notifyDataSetChanged();

                    sprProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Provinsi selected = provinsiList.get(position);
                            Log.d("Provinsi", selected.getCode() + " - " + selected.getName());

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Gagal :"+t.getMessage(),Toast.LENGTH_LONG);
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        edtNama = findViewById(R.id.edtNama);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDashboard();
            }
        });



    }

    public void initData(){ // menambahkan data prodi dan matakuliah
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Number maxId = r.where(Mahasiswa.class).max("studentID");
            Prodi prodiSI = r.createObject(Prodi.class,0);
            prodiSI.setFakultas("Fakultas Teknologi Informasi");
            prodiSI.setNama("Sistem Informasi");

            Matakuliah matMobile = r.createObject(Matakuliah.class,0);
            matMobile.setNama("Pemrograman Mobile Lanjut");
            matMobile.setSks(3);
            matMobile.setProdi(prodiSI);

            Matakuliah matPBO = r.createObject(Matakuliah.class,1);
            matPBO.setNama("Pemrograman Berorientasi Objek");
            matPBO.setSks(3);
            matPBO.setProdi(prodiSI);

        });
        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();

    }
    public void toProfil(){
        Intent intent = new Intent(this,ProfilActivity.class);
        intent.putExtra("nama",edtNama.getText().toString());
        startActivity(intent);
    }
    public void toDashboard(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}