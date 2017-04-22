package com.tusueldo.pokedex.modelo;

/**
 * Created by David on 20/11/2016.
 */

public class Pokemon {

    private String name;
    private String url;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNumber() {
        String[] split = url.split("/");
        return Integer.parseInt(split[split.length - 1]);
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
