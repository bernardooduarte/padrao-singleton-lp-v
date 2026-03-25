package com.bernardoduarte.padrao_singleton_lp_v.config;

public class ConfiguracaoSistema {

    private static volatile ConfiguracaoSistema instancia;

    private String nomeSistema;
    private String versao;
    private String tema;

    private ConfiguracaoSistema() {
        this.nomeSistema = "Padrão Singleton LP V";
        this.versao = "1.0.0";
        this.tema = "Escuro";
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

    public String getNomeSistema() {
        return nomeSistema;
    }

    public void setNomeSistema(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}