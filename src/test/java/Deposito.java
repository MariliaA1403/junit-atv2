package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Deposito {

    private DigitalWallet digitalWallet;

    @BeforeEach
    void setup() {
        digitalWallet = new DigitalWallet("UsuarioTeste", 0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 0.01, 999.99})
    void depositoValoresValidosAtualizaSaldo(double valor) {
        double saldoInicial = digitalWallet.getBalance();
        digitalWallet.deposit(valor);
        assertEquals(saldoInicial + valor, digitalWallet.getBalance(), 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -10.0, -999.99})
    void depositoComValorInvalidoDeveLancarExcecao(double valor) {
        assertThrows(IllegalArgumentException.class, () -> digitalWallet.deposit(valor));
    }
}
