package com.tusueldo.pokedex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tusueldo.pokedex.interfaces.PokeApiService;
import com.tusueldo.pokedex.modelo.Pokemon;
import com.tusueldo.pokedex.modelo.PokemonResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;
    private int offset;
    private static int iteracion = 1;
    private boolean aptoParaCargar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = gridLayoutManager.getChildCount();
                    int totalITemCount = gridLayoutManager.getItemCount();
                    int pastVisibleITem = gridLayoutManager.findFirstVisibleItemPosition();
                    Log.i(TAG, iteracion + " visibleITem: " + visibleItemCount + " totalITemCount: " + totalITemCount + " pastVisibleITem: " + pastVisibleITem);
                    iteracion++;

                    if (aptoParaCargar) {
                        if (visibleItemCount + pastVisibleITem >= totalITemCount) {
                            Log.i(TAG, "Llegamos al final");
                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonResponse> pokemonResponseCall = service.obtenerListaPokemon(20, offset);
        pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    PokemonResponse pokemonResponse = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonResponse.getResults();
                    listaPokemonAdapter.adicionarListaPokemon(listaPokemon);

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
