package com.tusueldo.pokedex.modelo;

import java.util.ArrayList;

/**
 * Created by David on 20/11/2016.
 */

public class PokemonResponse {

    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
