package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class Estorno {

    static Stream<Arguments> dadosParaEstorno() {
        return Stream.of(
            Arguments.of(100.0, 10.0, 110.0),
            Arguments.of(0.0, 5.0, 5.0),
            Arguments.of(50.0, 0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("dadosParaEstorno")
    void estornoAumentaSaldoQuandoCarteiraValida(double saldoInicial, double valorEstorno, double saldoEsperado) {
        DigitalWallet wallet = new DigitalWallet("UsuarioTeste", saldoInicial);
        wallet.unlock();
        wallet.verify();
        assumeTrue(!wallet.isLocked() && wallet.isVerified());
        
        wallet.refund(valorEstorno);
        
        assertEquals(saldoEsperado, wallet.getBalance(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = { -10.0, 0.0, -0.1 })
    void estornoComValorInvalidoDeveLancarExcecao(double valor) {
        DigitalWallet wallet = new DigitalWallet("UsuarioTeste", 100.0);
        wallet.verify();
        wallet.unlock();
        assumeTrue(!wallet.isLocked() && wallet.isVerified());

        assertThrows(IllegalArgumentException.class, () -> wallet.refund(valor));
    }

    @Test
    void estornoSemCarteiraVerificadaOuComBloqueioDeveLancarExcecao() {
        DigitalWallet wallet = new DigitalWallet("UsuarioTeste", 100.0);
        
        // Nem verificada nem desbloqueada
        assertThrows(IllegalStateException.class, () -> wallet.refund(10.0));
    }
}
