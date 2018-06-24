package com.asmes.meniere.models;

import java.io.Serializable;

/**
 * Created by stefy83 on 25/02/2018.
 */

public class TriggerModel implements Serializable{

    public String name;
    public int contador;


    public TriggerModel() {
    }

    public TriggerModel(String name, int contador) {
        this.name = name;
        this.contador = contador;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}





