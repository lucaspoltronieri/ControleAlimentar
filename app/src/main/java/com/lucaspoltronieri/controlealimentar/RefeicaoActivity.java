package com.lucaspoltronieri.controlealimentar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RefeicaoActivity extends AppCompatActivity {

    private Spinner spinnerTipoRefeicao;
    private EditText editTextDate;
    private RadioGroup radioGroupFome;
    private CheckBox checkBoxForaHorario;
    private Button buttonSalvar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeicao);

        //Referência dos elementos que compõe a activity

        spinnerTipoRefeicao = findViewById(R.id.spinnerTipoRefeicao);
        editTextDate = findViewById(R.id.editTextDate);
        radioGroupFome = findViewById(R.id.radioGroupFome);
        checkBoxForaHorario = findViewById(R.id.checkBoxForaHorario);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        buttonLimpar = findViewById(R.id.buttonLimpar);

        //limpar campos
        buttonLimpar.setOnClickListener(v ->{
            spinnerTipoRefeicao.setSelection(0);
            radioGroupFome.clearCheck();
            checkBoxForaHorario.setChecked(false);
            editTextDate.setText(null);

            Toast.makeText(this,
                    R.string.formulario_limpo_com_sucesso,
                    Toast.LENGTH_SHORT).show();

            spinnerTipoRefeicao.requestFocus();

        });

        //salvar
        buttonSalvar.setOnClickListener(v ->{

            String tipoRefeicao = spinnerTipoRefeicao.getSelectedItem().toString();
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
            RadioButton radioButtonSelecionado = findViewById(selectedRadiId);
            String apetite = radioButtonSelecionado.getText().toString();
            boolean foraHorario = checkBoxForaHorario.isChecked();

            //lançar dados em um string
            String resumo =
                     getString(R.string.tipo) + tipoRefeicao + "\n"
                    +getString(R.string.stringdata) + data + "\n"
                    +getString(R.string.fora_do_horario) +(foraHorario ? getString(R.string.sim) : getString(R.string.nao)) + "\n"
                    +getString(R.string.apetite) + apetite;

            Toast.makeText(this,resumo, Toast.LENGTH_LONG).show();
        });
    }
}