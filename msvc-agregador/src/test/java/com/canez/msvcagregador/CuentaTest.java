package com.canez.msvcagregador;

import com.canez.msvcagregador.client.CuentaWebClient;
import com.canez.msvcagregador.dto.cuentadto.CuentaUsuarioDto;
import com.canez.msvcagregador.dto.cuentadto.TipoCuenta;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {

    private WebClient client;
    private CuentaWebClient cuentaWebClient;

    @Value("${msvc.cuentas.service}")
    private String baseUrl;

    @BeforeAll
    public void setClient() {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.cuentaWebClient = new CuentaWebClient(baseUrl);
    }

    @Test
    public void crearCuentaTest() {
        CuentaUsuarioDto nuevaCuenta = new CuentaUsuarioDto();
        nuevaCuenta.setId_cliente(1);
        nuevaCuenta.setNumeroCuenta("999999999");
        nuevaCuenta.setTipoCuenta(TipoCuenta.CORRIENTE);
        nuevaCuenta.setSaldoInicial(500);
        nuevaCuenta.setSaldoDisponible(500);
        nuevaCuenta.setEstado(true);

        Mono<CuentaUsuarioDto> cuentaDtoMono = Mono.just(nuevaCuenta);

        Mono<CuentaUsuarioDto> responseMono = this.cuentaWebClient.crearCuenta(cuentaDtoMono);

        StepVerifier.create(responseMono)
                .expectNextMatches(cuenta -> cuenta.getId_cliente() == nuevaCuenta.getId_cliente() && nuevaCuenta.getNumeroCuenta().equals(cuenta.getNumeroCuenta()))
                .verifyComplete();
    }

}
