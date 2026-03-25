package com.bernardoduarte.padrao_singleton_lp_v.config.controller;

import com.bernardoduarte.padrao_singleton_lp_v.config.ConfiguracaoSistema;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuracoes")
public class ConfiguracaoController {

    @GetMapping
    public ConfiguracaoSistema verConfiguracoes() {
        return ConfiguracaoSistema.getInstance();
    }

    @PostMapping("/alterar-tema")
    public String alterarTema(@RequestParam String novoTema) {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        config.setTema(novoTema);

        return "Tema alterado com sucesso para: " + novoTema +
                ". Acesse GET /api/configuracoes para verificar.";
    }
}