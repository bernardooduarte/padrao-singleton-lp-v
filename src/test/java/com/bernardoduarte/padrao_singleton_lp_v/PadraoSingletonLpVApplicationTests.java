package com.bernardoduarte.padrao_singleton_lp_v;

import com.bernardoduarte.padrao_singleton_lp_v.config.ConfiguracaoSistema;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PadraoSingletonLpVApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testSingletonRetornaMesmaInstancia() {
        ConfiguracaoSistema inst1 = ConfiguracaoSistema.getInstance();
        ConfiguracaoSistema inst2 = ConfiguracaoSistema.getInstance();
        ConfiguracaoSistema inst3 = ConfiguracaoSistema.getInstance();

        assertSame(inst1, inst2, "Segunda chamada deve retornar mesma instância");
        assertSame(inst2, inst3, "Terceira chamada deve retornar mesma instância");
    }

    @Test
    void testConfiguracoesPeristemEntreChamadas() {
        ConfiguracaoSistema config1 = ConfiguracaoSistema.getInstance();
        String moedaBaseAnterior = config1.getMoedaBase();
        String moedaCotadaAnterior = config1.getMoedaCotada();
        double taxaAnterior = config1.getTaxa();

        config1.setMoedaBase("EUR");
        config1.setMoedaCotada("BRL");
        config1.setTaxa(5.65);

        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstance();
        assertEquals("EUR", config2.getMoedaBase(), "Mudança da moeda base deve persistir no singleton");
        assertEquals("BRL", config2.getMoedaCotada(), "Mudança da moeda cotada deve persistir no singleton");
        assertEquals(5.65, config2.getTaxa(), 0.0001, "Mudança da taxa deve persistir no singleton");

        config1.setMoedaBase(moedaBaseAnterior);
        config1.setMoedaCotada(moedaCotadaAnterior);
        config1.setTaxa(taxaAnterior);
    }

    @Test
    void testThreadSafety() throws InterruptedException {
        final int NUM_THREADS = 100;
        CountDownLatch iniciar = new CountDownLatch(1);
        CountDownLatch terminar = new CountDownLatch(NUM_THREADS);
        
        AtomicInteger countInstanciasUnicas = new AtomicInteger(0);
        ConfiguracaoSistema instanciaEsperada = ConfiguracaoSistema.getInstance();

        for (int i = 0; i < NUM_THREADS; i++) {
            new Thread(() -> {
                try {
                    iniciar.await();
                    ConfiguracaoSistema inst = ConfiguracaoSistema.getInstance();
                    
                    if (inst == instanciaEsperada) {
                        countInstanciasUnicas.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    terminar.countDown();
                }
            }).start();
        }

        iniciar.countDown();
        terminar.await();

        assertEquals(NUM_THREADS, countInstanciasUnicas.get(), 
            "Todas as threads devem ver mesma instância do singleton");
    }

    @Test
    void testInicializacaoCorreta() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        
        assertEquals("USD", config.getMoedaBase(), "Moeda base padrão incorreta");
        assertEquals("BRL", config.getMoedaCotada(), "Moeda cotada padrão incorreta");
        assertEquals(5.10, config.getTaxa(), 0.0001, "Taxa padrão incorreta");
    }
}
