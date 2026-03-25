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
        String temaPrimeira = config1.getTema();

        config1.setTema("Claro");

        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstance();
        assertEquals("Claro", config2.getTema(), "Mudança de tema deve persistir no singleton");

        config1.setTema(temaPrimeira);
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
        
        assertEquals("Padrão Singleton LP V", config.getNomeSistema(), "Nome sistema incorreto");
        assertEquals("1.0.0", config.getVersao(), "Versão incorreta");
        assertEquals("Escuro", config.getTema(), "Tema padrão incorreto");
    }
}
