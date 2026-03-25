package com.bernardoduarte.padrao_singleton_lp_v.config.controller;

import com.bernardoduarte.padrao_singleton_lp_v.config.ConfiguracaoSistema;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cambio")
public class ConfiguracaoController {

    @GetMapping
    public ConfiguracaoSistema verTaxa() {
        return ConfiguracaoSistema.getInstance();
    }

    @PostMapping("/atualizar")
    public String atualizarTaxa(@RequestParam String moedaBase,
                                @RequestParam String moedaCotada,
                                @RequestParam double novaTaxa) {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        config.setMoedaBase(moedaBase);
        config.setMoedaCotada(moedaCotada);
        config.setTaxa(novaTaxa);

        return "Taxa de câmbio atualizada com sucesso para " + moedaBase + "/" + moedaCotada +
                " = " + novaTaxa + ". Acesse GET /api/cambio para verificar.";
    }
}