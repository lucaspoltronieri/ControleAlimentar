package com.lucaspoltronieri.controlealimentar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RefeicaoActivity extends AppCompatActivity {

    public static final String KEY_DATA = "KEY_DATA";
    public static final String KEY_APETITE = "KEY_APETITE";
    public static final String KEY_FORAHORARIO = "KEY_FORAHORARIO";
    public static final String KEY_TIPOREFEICAO = "KEY_TIPOREFEICAO";
    public static final String KEY_MODO = "MODO";
    public static final int MODO_NOVO = 0;
    public static final int MODO_EDITAR = 1;
    private Spinner spinnerTipoRefeicao;
    private EditText editTextDate;
    private RadioGroup radioGroupFome;
    private CheckBox checkBoxForaHorario;
    private int modo;

    //limpar campos
    public void limparCampos(){
        spinnerTipoRefeicao.setSelection(0);
        radioGroupFome.clearCheck();
        checkBoxForaHorario.setChecked(false);
        editTextDate.setText(null);

        Toast.makeText(this,
                R.string.formulario_limpo_com_sucesso,
                Toast.LENGTH_SHORT).show();

        spinnerTipoRefeicao.requestFocus();

    }

    public void salvar(){
        int tipoRefeicao = spinnerTipoRefeicao.getSelectedItemPosition();
        String data = editTextDate.getText().toString().trim();
        //validação
        if(data.isEmpty()){
            editTextDate.requestFocus();
            Toast.makeText(this,
                    R.string.preencha_a_data_da_refeicao,
                    Toast.LENGTH_SHORT).show();
            editTextDate.requestFocus();
            return;
        }
        int selectedRadiId = radioGroupFome.getCheckedRadioButtonId();

        //valida se selecionado algum radioButton
        if (selectedRadiId == -1){
            Toast.makeText(this,
                    R.string.selecione_o_estado_do_apetite,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        //pega o valor selecionado do groupRadio
        RadioButton rbSelecionado = findViewById(selectedRadiId);
        int apetite = Integer.parseInt(String.valueOf(rbSelecionado.getTag()));

        boolean foraHorario = checkBoxForaHorario.isChecked();

        Intent intentResposta = new Intent();
        intentResposta.putExtra(KEY_TIPOREFEICAO, tipoRefeicao);
        intentResposta.putExtra(KEY_DATA, data);
        intentResposta.putExtra(KEY_APETITE, apetite);
        intentResposta.putExtra(KEY_FORAHORARIO, foraHorario);

        setResult(RefeicaoActivity.RESULT_OK, intentResposta);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Habilitar o Tema salvo no SharedPreferences
        ThemeUtils.applySavedTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);

        //Referência dos elementos que compõe a activity

        spinnerTipoRefeicao = findViewById(R.id.spinnerTipoRefeicao);
        editTextDate = findViewById(R.id.editTextDate);
        radioGroupFome = findViewById(R.id.radioGroupFome);
        checkBoxForaHorario = findViewById(R.id.checkBoxForaHorario);

        Intent intentAbertura = getIntent();
        Bundle bundle = intentAbertura.getExtras();
        if (bundle !=null){
            modo = bundle.getInt(KEY_MODO);

            if(modo == MODO_NOVO){
                setTitle(getString(R.string.nova_refeicao));
            }
            else {
                setTitle(getString(R.string.editar_refeicao));

                int tipoRefeicao = bundle.getInt(RefeicaoActivity.KEY_TIPOREFEICAO);
                String data = bundle.getString(RefeicaoActivity.KEY_DATA);
                int apetite = bundle.getInt(RefeicaoActivity.KEY_APETITE);
                boolean foraHorario = bundle.getBoolean(RefeicaoActivity.KEY_FORAHORARIO);

                spinnerTipoRefeicao.setSelection(tipoRefeicao);
                editTextDate.setText(data);
                checkBoxForaHorario.setChecked(foraHorario);

                if (apetite == 0) {
                    ((RadioButton) findViewById(R.id.radioButtonComFome)).setChecked(true);
                } else {
                    ((RadioButton) findViewById(R.id.radioButtonSemFome)).setChecked(true);
                }

            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refeicao_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int idMenuItem = item.getItemId();

        if(idMenuItem == R.id.menuItemAdicionar){
            salvar();
            return true;

        } else if (idMenuItem == R.id.menuItemLimpar) {
            limparCampos();
            return true;

        }
        else {
            return super.onOptionsItemSelected(item);
        }

    }
}