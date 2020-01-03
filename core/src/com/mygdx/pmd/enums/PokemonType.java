package com.mygdx.pmd.enums;

public enum PokemonType {
    TREEKO;

    @Override
    public String toString() {
       return name().toLowerCase();
    }
}
