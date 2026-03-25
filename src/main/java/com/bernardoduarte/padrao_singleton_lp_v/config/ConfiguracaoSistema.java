package com.bernardoduarte.padrao_singleton_lp_v.config;

public class ConfiguracaoSistema {

    private static volatile ConfiguracaoSistema instancia;

    private String moedaBase;
    private String moedaCotada;
    private double taxa;

    private ConfiguracaoSistema() {
        this.moedaBase = "USD";
        this.moedaCotada = "BRL";
        this.taxa = 5.10;
    }

    public static ConfiguracaoSistema getInstance() {
        if (instancia == null) {
            synchronized (ConfiguracaoSistema.class) {
                if (instancia == null) {
                    instancia = new ConfiguracaoSistema();
                }
            }
        }
        return instancia;
    }

    public String getMoedaBase() {
        return moedaBase;
    }

    public void setMoedaBase(String moedaBase) {
        this.moedaBase = moedaBase;
    }

    public String getMoedaCotada() {
        return moedaCotada;
    }

    public void setMoedaCotada(String moedaCotada) {
        this.moedaCotada = moedaCotada;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }
}