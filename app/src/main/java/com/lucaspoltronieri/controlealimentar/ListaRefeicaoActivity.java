package com.lucaspoltronieri.controlealimentar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
        setTitle(getString(R.string.controle_alimentar));

        listViewRefeicao = findViewById(R.id.listViewRefeicao);


        listViewRefeicao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                Refeicao refeicao = (Refeicao) listViewRefeicao.getItemAtPosition(position);

                String[] tipoRefeicao = getResources().getStringArray(R.array.tiposRefeicao);

                String selected = getString(R.string.tipo_refeicao) + ": " + tipoRefeicao[refeicao.getTipoRefeicao()] + "\n"
                        + getString(R.string.clicada);

                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG).show();
            }
        });

        popularListaRefeicao();
    }

    private void popularListaRefeicao(){

        listaRefeicao = new ArrayList<>();

        refeicaoAdapter = new RefeicaoAdapter(this, listaRefeicao);

        listViewRefeicao.setAdapter(refeicaoAdapter);
    }

    public void abrirSobre(View view){
        startActivity(new Intent(this,SobreActivity.class));
    }

    ActivityResultLauncher<Intent> launcherNovaRefeicao = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if(result.getResultCode() == ListaRefeicaoActivity.RESULT_OK){
                        Intent intent = result.getData();

                        Bundle bundle = intent.getExtras();

                        if(bundle !=null){
                            int tipoRefeicao = bundle.getInt(RefeicaoActivity.KEY_TIPOREFEICAO);
                            String data = bundle.getString(RefeicaoActivity.KEY_DATA);
                            int apetite = bundle.getInt(RefeicaoActivity.KEY_APETITE);
                            boolean foraHorario = bundle.getBoolean(RefeicaoActivity.KEY_FORAHORARIO);

                            Refeicao refeicao = new Refeicao(tipoRefeicao,data,apetite,foraHorario);
                            listaRefeicao.add(refeicao);
                            refeicaoAdapter.notifyDataSetChanged();
                        }



                    }

                }
            }
    );

    public void abrirNovaRefeicao(View view){
        Intent intentAbertura = new Intent(this, RefeicaoActivity.class);
        launcherNovaRefeicao.launch(intentAbertura);
    }

}