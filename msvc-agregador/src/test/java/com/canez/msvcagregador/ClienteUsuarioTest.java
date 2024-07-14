package com.canez.msvcagregador;

import com.canez.msvcagregador.client.ClienteWebClient;
import com.canez.msvcagregador.dto.clientedto.ClientePersonaDto;
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
class ClienteUsuarioTest {

    private WebClient client;
    private ClienteWebClient clienteWebClient;

    @Value("${msvc.cliente.service}")
    private String baseUrl;

    @BeforeAll
    public void setClient() {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.clienteWebClient = new ClienteWebClient(baseUrl);
    }

    @Test
    void obtenerClienteByIdTest() {
        Mono<ClientePersonaDto> responseMono = this.clienteWebClient.listarById(1);
        var nombreTest = "Christopher Añez";
        StepVerifier.create(responseMono)
                .expectNextMatches(clientePersonaDto -> clientePersonaDto.getClienteId() == 1 && nombreTest.equals(clientePersonaDto.getNombre()))
                .verifyComplete();
    }

    @Test
    public void crearClienteTest() {
        ClientePersonaDto nuevoCliente = new ClientePersonaDto();
        nuevoCliente.setNombre("Cliente Prueba 2");
        nuevoCliente.setGenero("Masculino");
        nuevoCliente.setEdad(27);
        nuevoCliente.setIdentificacion("DNI-V0000001");
        nuevoCliente.setDireccion("Av 3F, Calle 78, Edif. Dr. Portillo, APT 14C");
        nuevoCliente.setTelefono("+584246105820");
        nuevoCliente.setContrasena("MiPassword01");
        nuevoCliente.setEstado(true);

        Mono<ClientePersonaDto> clienteDtoMono = Mono.just(nuevoCliente);
        Mono<ClientePersonaDto> responseMono = this.clienteWebClient.crearCliente(clienteDtoMono);
        StepVerifier.create(responseMono)
                .expectNextMatches(cliente -> nuevoCliente.getNombre().equals(cliente.getNombre()))
                .verifyComplete();
    }

}
