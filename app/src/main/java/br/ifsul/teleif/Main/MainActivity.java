package br.ifsul.teleif.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.ifsul.teleif.API.RestService;
import br.ifsul.teleif.API.apiConnection;
import br.ifsul.teleif.Models.DadosSorteio;
import br.ifsul.teleif.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView nomeSorteio, dataSorteio, numeroSorteio;
    ListView numerosSorteados;

    Spinner tipoSorteio;

    ConstraintLayout mainLayout;

    String[] tipoSorteioLista = {"Mega-Sena", "Quina", "LotoFacil", "LotoMania", "TimeMania", "Dia-da-Sorte"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeSorteio = findViewById(R.id.txt_NomeSorteio);
        dataSorteio = findViewById(R.id.txt_NumeroDataSorteio);
        numeroSorteio = findViewById(R.id.txt_NumeroSorteio);
        numerosSorteados = findViewById(R.id.Lv_NumerosSorteados);
        tipoSorteio = findViewById(R.id.sp_TipoSorteio);
        mainLayout = findViewById(R.id.layoutPrincipal);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoSorteioLista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoSorteio.setAdapter(adapter);

        tipoSorteio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String itemSelecionado = tipoSorteioLista[position];
                dadosSorteio(itemSelecionado.replaceAll("[^a-zA-Z]", "").toLowerCase());

                if ("Mega-Sena".equals(itemSelecionado)) {
                    mainLayout.setBackgroundResource(R.drawable.bg_mega);
                } else if ("Quina".equals(itemSelecionado)) {
                    mainLayout.setBackgroundResource(R.drawable.bg_quina);
                } else if ("LotoFacil".equals(itemSelecionado)) {
                    mainLayout.setBackgroundResource(R.drawable.bg_lotofacil);
                } else if ("LotoMania".equals(itemSelecionado)) {
                    mainLayout.setBackgroundResource(R.drawable.bg_lotomania);
                } else if ("TimeMania".equals(itemSelecionado)) {
                    mainLayout.setBackgroundResource(R.drawable.bg_timemania);
                } else if ("Dia-da-Sorte".equals(itemSelecionado)) {
                    mainLayout.setBackgroundResource(R.drawable.bg_diadesorte);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Implemente se necessário
            }
        });
    }

    public void dadosSorteio(String dadosSelecionados) {
        RestService restService = apiConnection.createConnectionToAPI();
        Call<ResponseBody> call = restService.ProcurarDados(dadosSelecionados);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        String responseData = response.body().string();
                        System.out.print(responseData);
                        Gson Jason = new Gson();
                        DadosSorteio dados = Jason.fromJson(responseData, DadosSorteio.class);
                        organizarDadosSorteio(dados);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Falha, Tente Novamente");
            }
        });
    }

    public void organizarDadosSorteio(DadosSorteio sorteioUtilizado) {
        nomeSorteio.setText(sorteioUtilizado.getTema().toString());
        dataSorteio.setText(sorteioUtilizado.getData().toString());
        numeroSorteio.setText(sorteioUtilizado.getNumero().toString());

        int[] numerosSorteadosArray = sorteioUtilizado.getNumerosSorteados();
        List<String> numerosSorteadosStringList = new ArrayList<>();

        for (int numero : numerosSorteadosArray) {
            numerosSorteadosStringList.add(String.valueOf(numero));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, numerosSorteadosStringList);
        numerosSorteados.setAdapter(adapter);
    }
}