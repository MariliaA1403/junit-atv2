package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SaldoInicial {

    @ParameterizedTest
    @ValueSource(doubles = {10, 0, 20})
    void saldoInicialDeveSerConfiguradoCorretamente(double saldo) {
        DigitalWallet digitalWallet = new DigitalWallet("UsuarioTeste", saldo);
        assertEquals(saldo, digitalWallet.getBalance(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10, -5, -20})
    void saldoInicialNegativoDeveLancarExcecao(double saldo) {
        assertThrows(IllegalArgumentException.class, () -> {
            new DigitalWallet("UsuarioTeste", saldo);
        });
    }
}
