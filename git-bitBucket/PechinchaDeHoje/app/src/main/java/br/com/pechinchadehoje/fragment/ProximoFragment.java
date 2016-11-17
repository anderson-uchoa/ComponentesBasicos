package br.com.pechinchadehoje.fragment;


import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.pechinchadehoje.R;
import br.com.pechinchadehoje.activity.OfertaActivity;
import br.com.pechinchadehoje.adapter.OfertasAdapter;
import br.com.pechinchadehoje.controlador.ControladorOferta;
import br.com.pechinchadehoje.modelo.Oferta;
import br.com.pechinchadehoje.modelo.OnEventCarregamento;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.util.LocationUtil;
import de.greenrobot.event.EventBus;

public class ProximoFragment extends BaseFragment  implements  OfertasAdapter.OnItemClickOferta {

    private List<Oferta> ofertas;
    private OfertasAdapter ofertasAdapter;
    private RecyclerView mRecycleViewLojas;
    private LinearLayoutManager mLayoutManager;
    private TextView textoVazio;
    private ProgressBar mProgressBar;
    private EventBus mEventBus;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LocationUtil locationUtil;
    private BuscarTodasAsOfertasPorGeolocalizacao buscarTodasAsOfertasPorGeolocalizacao;
    private int limit = 20;
    private int skip = 0;
    private Location location;
    private int distancia = 10;

    private int lastKnownFirst = 0;
    private boolean fromScroll = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (!EventBus.getDefault().isRegistered(this)) {
            mEventBus = EventBus.getDefault();
            mEventBus.register(this);
        }
        View view = inflater.inflate(R.layout.fragment_proximo, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.primary,
                R.color.primary_light_tab);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (locationUtil != null) {
                    buscarTodasAsOfertasPorGeolocalizacao = new BuscarTodasAsOfertasPorGeolocalizacao();
                    buscarTodasAsOfertasPorGeolocalizacao.execute(locationUtil.getLastLocation());
                }
            }
        });


        mRecycleViewLojas = (RecyclerView) view.findViewById(R.id.recyclerViewOfertas);

        textoVazio = (TextView) view.findViewById(R.id.txt_vazio);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycleViewLojas.setLayoutManager(mLayoutManager);
        mRecycleViewLojas.setItemAnimator(new DefaultItemAnimator());
        mRecycleViewLojas.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastKnownFirst = mLayoutManager.findFirstVisibleItemPosition();

                if (ofertasAdapter.getItemCount() - 1 == mLayoutManager.findLastCompletelyVisibleItemPosition()) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    skip = ofertasAdapter.getItemCount();
                    fromScroll = true;
                    buscarTodasAsOfertasPorGeolocalizacao = new BuscarTodasAsOfertasPorGeolocalizacao();
                    buscarTodasAsOfertasPorGeolocalizacao.execute(location);
                }
            }
        });

        locationUtil = new LocationUtil(getContext(), new OnLocationAvailable());


        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(ProximoFragment.class.toString(), item.toString());

        int itemId = item.getItemId();

        if (itemId == R.id.action_advanced_search) {
        //TODO FAZER ALGO AQUI
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (locationUtil == null) {
            locationUtil = new LocationUtil(getContext(), null);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);

        if (locationUtil != null) {
            locationUtil.disconnect();
            locationUtil = null;
        }
    }


    public void onEvent(OnEventCarregamento eventCarregamento) {

        if (eventCarregamento.carregarOfertas) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }


    public void onEvent(final List<Oferta> ofertas) {

        if (ofertas != null && ofertas.size() > 0) {
            ProximoFragment.this.ofertas = ofertas;

            if (ofertasAdapter == null) {
                ofertasAdapter = new OfertasAdapter(getActivity(), ofertas, ProximoFragment.this);
                mRecycleViewLojas.setAdapter(ofertasAdapter);
            } else {
                if (!fromScroll) {
                    ofertasAdapter.clear();
                }
                ofertasAdapter.addOfertas(ofertas);
            }

            this.ofertas = ofertasAdapter.getOfertas();
            textoVazio.setVisibility(View.GONE);
        } else {
            if (!fromScroll) {
                if (ofertasAdapter != null) {
                    ofertasAdapter.clear();
                    ofertasAdapter.notifyDataSetChanged();
                } else {
                    ofertasAdapter = new OfertasAdapter(getActivity(), ofertas, ProximoFragment.this);
                    mRecycleViewLojas.setAdapter(ofertasAdapter);
                }
                textoVazio.setVisibility(View.VISIBLE);
            }
        }

        mProgressBar.setVisibility(View.GONE);
    }



    @Override
    public void onItemClick(int item) {

        Oferta oferta = this.ofertas.get(item);

        Bundle params = new Bundle();
        params.putSerializable(OfertaActivity.OFERTA_INTENT, oferta);
        params.putSerializable(OfertaActivity.LOAD_OFERTA, true);

        Intent intent = new Intent(getContext(), OfertaActivity.class);
        intent.putExtras(params);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (buscarTodasAsOfertasPorGeolocalizacao != null) {
            buscarTodasAsOfertasPorGeolocalizacao.cancel(true);
            buscarTodasAsOfertasPorGeolocalizacao = null;
        }
        super.onDestroy();
    }


    private class OnLocationAvailable implements LocationUtil.CallbackLocation {

        @Override
        public void onLocationAvaiable(Location location) {
            buscarTodasAsOfertasPorGeolocalizacao = new BuscarTodasAsOfertasPorGeolocalizacao();
            ProximoFragment.this.location = location;
            buscarTodasAsOfertasPorGeolocalizacao.execute(location);
        }

    }

    private class BuscarTodasAsOfertasPorGeolocalizacao extends AsyncTask<Location, Void, List<Oferta>> {
            private ControladorOferta controladorOferta;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            controladorOferta = new ControladorOferta(getActivity());
        }

        @Override
        protected List<Oferta> doInBackground(Location... locations) {
                if (locations == null || locations.length == 0){
                    return  null;
                }
            Location location = locations[0];

            if (location == null) {
                return null;
            }

            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();

            try {
                List<Oferta> ofertas = controladorOferta.buscaoOfertasPorGeolocalizacao(latitude, longitude, limit, skip, distancia);

                return ofertas;
            } catch (FalhaDeParametrosException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<Oferta> ofertas) {
            super.onPostExecute(ofertas);

            if (ofertas != null){
                mEventBus.post(ofertas);
            }else {
                mEventBus.post(new ArrayList<Oferta>());
            }

            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }



}

