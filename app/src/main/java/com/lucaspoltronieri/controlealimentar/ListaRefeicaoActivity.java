package com.lucaspoltronieri.controlealimentar;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaRefeicaoActivity extends AppCompatActivity {

    private ListView listViewRefeicao;
    private List<Refeicao> listaRefeicao;

    private RefeicaoAdapter refeicaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_refeicao);

        listViewRefeicao = findViewById(R.id.listViewRefeicao);

        popularListaRefeicao();
    }

    private void popularListaRefeicao(){

        int[] refeicao_tipo = getResources().getIntArray(R.array.refeicao_tipo);
        String[] refeicao_datas = getResources().getStringArray(R.array.refeicao_datas);
        int[] refeicao_fora_horario = getResources().getIntArray(R.array.refeicao_fora_horario);
        int[] refeicao_apetite = getResources().getIntArray(R.array.refeicao_apetite);

        listaRefeicao = new ArrayList<>();

        //criar lista
        for (int i = 0; i < refeicao_datas.length; i++){
            boolean foraHorario = (refeicao_fora_horario[i] ==1);
            Refeicao refeicao = new Refeicao(
                    refeicao_tipo[i],
                    refeicao_datas[i],
                    refeicao_apetite[i],
                    foraHorario
            );
            listaRefeicao.add(refeicao);
        }

        refeicaoAdapter = new RefeicaoAdapter(this, listaRefeicao);

        listViewRefeicao.setAdapter(refeicaoAdapter);
    }
}