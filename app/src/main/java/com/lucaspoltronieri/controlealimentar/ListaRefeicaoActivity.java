package com.lucaspoltronieri.controlealimentar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import java.util.ArrayList;
import java.util.List;

public class ListaRefeicaoActivity extends AppCompatActivity {

    private ListView listViewRefeicao;
    private List<Refeicao> listaRefeicao;
    private RefeicaoAdapter refeicaoAdapter;
    private int posicaoSelecionada = -1;

    private ActionMode actionMode;
    private View viewSelecionada;
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.refeicao_item_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            int idMenuItem = item.getItemId();

            if (idMenuItem == R.id.menuItemEditar) {
                mode.finish();
                editarRefeicao();
                return true;

            } else if (idMenuItem == R.id.menuItemExcluir) {
                excluirRefeicao();
                mode.finish();
                return true;

            } else {
                return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(viewSelecionada !=null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);

            }
            actionMode = null;
            viewSelecionada = null;
            listViewRefeicao.setEnabled(true);

        }
    };

    ActivityResultLauncher<Intent> launcherNovaRefeicao =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == ListaRefeicaoActivity.RESULT_OK) {
                                Intent intent = result.getData();
                                Bundle bundle = intent.getExtras();
                                if (bundle != null) {
                                    int tipoRefeicao = bundle.getInt(RefeicaoActivity.KEY_TIPOREFEICAO);
                                    String data = bundle.getString(RefeicaoActivity.KEY_DATA);
                                    int apetite = bundle.getInt(RefeicaoActivity.KEY_APETITE);
                                    boolean foraHorario = bundle.getBoolean(RefeicaoActivity.KEY_FORAHORARIO);

                                    Refeicao refeicao = new Refeicao(tipoRefeicao, data, apetite, foraHorario);
                                    listaRefeicao.add(refeicao);
                                    refeicaoAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });

    ActivityResultLauncher<Intent> launcherEditarRefeicao =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == ListaRefeicaoActivity.RESULT_OK) {
                                Intent intent = result.getData();
                                Bundle bundle = intent.getExtras();
                                if (bundle != null) {
                                    int tipoRefeicao = bundle.getInt(RefeicaoActivity.KEY_TIPOREFEICAO);
                                    String data = bundle.getString(RefeicaoActivity.KEY_DATA);
                                    int apetite = bundle.getInt(RefeicaoActivity.KEY_APETITE);
                                    boolean foraHorario = bundle.getBoolean(RefeicaoActivity.KEY_FORAHORARIO);

                                    Refeicao refeicao = listaRefeicao.get(posicaoSelecionada);
                                    refeicao.setTipoRefeicao(tipoRefeicao);
                                    refeicao.setData(data);
                                    refeicao.setApetite(apetite);
                                    refeicao.setForaHorario(foraHorario);

                                    refeicaoAdapter.notifyDataSetChanged();
                                }
                            }
                            posicaoSelecionada = -1;
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Habilitar o Tema salvo no SharedPreferences
        ThemeUtils.applySavedTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_refeicao);
        setTitle(getString(R.string.controle_alimentar));

        listViewRefeicao = findViewById(R.id.listViewRefeicao);

        popularListaRefeicao();
        //registerForContextMenu(listViewRefeicao);

        // Clique curto = editar
        listViewRefeicao.setOnItemClickListener((parent, view, position, id) -> {
            posicaoSelecionada = position;
            editarRefeicao();
        });

        listViewRefeicao.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(actionMode != null){
                    return false;
                }
                posicaoSelecionada = position;
                viewSelecionada = view;
                viewSelecionada.setBackgroundColor(Color.RED);
                listViewRefeicao.setEnabled(false);
                actionMode = startSupportActionMode(callback);
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_refeicao_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();

        if (idMenuItem == R.id.menuItemAdicionar) {
            abrirNovaRefeicao();
            return true;

        } else if (idMenuItem == R.id.menuItemSobre) {
            abrirSobre();
            return true;

        } else if (idMenuItem == R.id.menuItemConfig) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void popularListaRefeicao() {
        listaRefeicao = new ArrayList<>();
        refeicaoAdapter = new RefeicaoAdapter(this, listaRefeicao);
        listViewRefeicao.setAdapter(refeicaoAdapter);
    }

    private void abrirNovaRefeicao() {
        Intent intentAbertura = new Intent(this, RefeicaoActivity.class);
        intentAbertura.putExtra(RefeicaoActivity.KEY_MODO, RefeicaoActivity.MODO_NOVO);
        launcherNovaRefeicao.launch(intentAbertura);
    }

    public void abrirSobre() {
        startActivity(new Intent(this, SobreActivity.class));
    }

    private void editarRefeicao() {
        Refeicao refeicao = listaRefeicao.get(posicaoSelecionada);

        Intent intentAbertura = new Intent(this, RefeicaoActivity.class);
        intentAbertura.putExtra(RefeicaoActivity.KEY_MODO, RefeicaoActivity.MODO_EDITAR);
        intentAbertura.putExtra(RefeicaoActivity.KEY_TIPOREFEICAO, refeicao.getTipoRefeicao());
        intentAbertura.putExtra(RefeicaoActivity.KEY_DATA, refeicao.getData());
        intentAbertura.putExtra(RefeicaoActivity.KEY_APETITE, refeicao.getApetite());
        intentAbertura.putExtra(RefeicaoActivity.KEY_FORAHORARIO, refeicao.isForaHorario());

        launcherEditarRefeicao.launch(intentAbertura);

    }

    private void excluirRefeicao() {
        listaRefeicao.remove(posicaoSelecionada);
        refeicaoAdapter.notifyDataSetChanged();
    }
}
