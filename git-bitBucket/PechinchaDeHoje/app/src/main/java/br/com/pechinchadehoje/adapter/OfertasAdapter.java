package br.com.pechinchadehoje.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.pechinchadehoje.modelo.Oferta;

/**
 * Created by Anderson Uchoa on 15/11/16.
 */

public class OfertasAdapter extends RecyclerView.Adapter<OfertasAdapter.OfertasViewHolder>  {

    private List<Oferta> ofertas;
    private Context context;
    private OnItemClickOferta onItemClickOferta;

    public OfertasAdapter(Context context, List<Oferta> ofertas, OnItemClickOferta onItemClickOferta){
        this.context = context;
        this.ofertas = ofertas;
        this.onItemClickOferta = onItemClickOferta;
    }


    public List<Oferta> getOfertas() {

        return ofertas;
    }

    public void addOfertas(List<Oferta> ofertas) {
        if (this.ofertas != null && ofertas != null) {
            this.ofertas.addAll(ofertas);
            this.notifyDataSetChanged();
        }
    }


    @Override
    public OfertasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(OfertasViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.ofertas.size();
    }

    public void clear() {
        this.ofertas.clear();
    }

    public interface OnItemClickOferta {
        void onItemClick(int item);

    }
    public class OfertasViewHolder extends  RecyclerView.ViewHolder {
        public OfertasViewHolder(View itemView) {
            super(itemView);
        }
    }


}
