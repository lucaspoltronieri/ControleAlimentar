package com.lucaspoltronieri.controlealimentar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RefeicaoAdapter extends BaseAdapter {

    private Context context;
    private List<Refeicao> listaRefeicao;
    private String[] tipoRefeicao;
    private String[] apetite;

    private static class RefeicaoHolder{

        public TextView textViewValorTipoRefeicao;
        public TextView textViewValorData;
        public TextView textViewForaHorario;
        public TextView textViewApetite;

    }

    public RefeicaoAdapter(Context context, List<Refeicao> listaRefeicao) {
        this.context = context;
        this.listaRefeicao = listaRefeicao;

        tipoRefeicao = context.getResources().getStringArray(R.array.tiposRefeicao);
        apetite = context.getResources().getStringArray(R.array.apetite);

    }

    @Override
    public int getCount() {
        return listaRefeicao.size();
    }

    @Override
    public Object getItem(int position) {
        return listaRefeicao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RefeicaoHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lista_refeicao, parent, false);
            holder = new RefeicaoHolder();

            holder.textViewValorTipoRefeicao = convertView.findViewById(R.id.textViewValorTipoRefeicao);
            holder.textViewValorData = convertView.findViewById(R.id.textViewValorData);
            holder.textViewForaHorario = convertView.findViewById(R.id.textViewValorForaHorario);
            holder.textViewApetite = convertView.findViewById(R.id.textViewApetite);

            convertView.setTag(holder);

        } else {

            holder = (RefeicaoHolder) convertView.getTag();
        }

        Refeicao refeicao = listaRefeicao.get(position);

        holder.textViewValorTipoRefeicao.setText(tipoRefeicao[refeicao.getTipoRefeicao()]);
        holder.textViewValorData.setText(refeicao.getData());

        if(refeicao.isForaHorario()){
            holder.textViewForaHorario.setText(R.string.refeicao_fora_horario);
        } else {
            holder.textViewForaHorario.setText(R.string.refeicao_horario_normal);
        }
        holder.textViewApetite.setText(apetite[refeicao.getApetite()]);


        return convertView;
    }
}
