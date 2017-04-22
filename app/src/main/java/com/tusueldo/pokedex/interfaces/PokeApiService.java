package com.tusueldo.pokedex.interfaces;

import com.tusueldo.pokedex.modelo.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by David on 20/11/2016.
 */

public interface PokeApiService {

    @GET("pokemon")
    Call<PokemonResponse> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
