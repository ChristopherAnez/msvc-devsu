package com.canez.msvcclientepersona.service;

import com.canez.msvcclientepersona.dto.ClientePersonaDto;
import com.canez.msvcclientepersona.entity.Cliente;
import com.canez.msvcclientepersona.entity.Persona;
import com.canez.msvcclientepersona.repository.ClienteRepository;
import com.canez.msvcclientepersona.repository.PersonaRepository;
import com.canez.msvcclientepersona.util.EntityDtoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    public Mono<ClientePersonaDto> getClientByPersonaId(Integer id) {
        return this.personaRepository.findById(id)
                .flatMap(persona -> this.clienteRepository.findByPersonaId(id)
                        .map(cliente -> EntityDtoUtil.toResponse(persona, cliente))
                        .onErrorResume(ex -> {
                            log.info("Error al buscar a la persona ", ex);
                            return Mono.empty();
                        }));
    }

    public Flux<ClientePersonaDto> getAllClients() {
        return this.clienteRepository.findAll()
                .flatMap(cliente -> this.personaRepository.findById(cliente.getPersonaId())
                        .map(persona -> EntityDtoUtil.toResponse(persona, cliente)))
                .onErrorResume(ex -> {
                    log.info("Error al buscar clientes ", ex);
                    return Flux.empty();
                });
    }

    @Transactional
    public Mono<ClientePersonaDto> create(Mono<ClientePersonaDto> mono) {
        return mono
                .flatMap(dto -> {
                    Persona persona = EntityDtoUtil.toPersona(dto);
                    Cliente cliente = EntityDtoUtil.toCliente(dto);
                    return this.personaRepository.save(persona)
                            .flatMap(nPersona -> {
                                cliente.setPersonaId(nPersona.getId());
                                return this.clienteRepository.save(cliente)
                                        .map(nCliente -> EntityDtoUtil.toResponse(nPersona, nCliente));
                            });
                })
                .onErrorResume(ex -> {
                    log.info("Error creando al cliente ", ex);
                    return Mono.empty();
                });
    }

    @Transactional
    public Mono<Void> deleteClientByPersonaId(Integer personaId) {
        return this.clienteRepository.findByPersonaId(personaId)
                .flatMap(this.clienteRepository::delete)
                .then(this.personaRepository.findById(personaId))
                .flatMap(this.personaRepository::delete)
                .onErrorResume(ex -> {
                    log.info("Error borrando al cliente: ", ex);
                    return Mono.empty();
                });
    }


    @Transactional
    public Mono<ClientePersonaDto> updateClientByPersonId(Integer personaId, Mono<ClientePersonaDto> mono) {
        return mono
                .flatMap(dto -> {
                    return this.personaRepository.findById(personaId)
                            .flatMap(persona -> {
                                EntityDtoUtil.updatePersonaFromDto(dto, persona);
                                return this.personaRepository.save(persona)
                                        .flatMap(savedPersona -> this.clienteRepository.findByPersonaId(personaId)
                                                .flatMap(cliente -> {
                                                    EntityDtoUtil.updateClienteFromDto(dto, cliente);
                                                    return this.clienteRepository.save(cliente)
                                                            .map(savedCliente -> EntityDtoUtil.toResponse(savedPersona, savedCliente));
                                                })
                                        );
                            })
                            .onErrorResume(ex -> {
                                log.info("Error al actualizar al cliente: ", ex);
                                return Mono.empty();
                            });
                });
    }

}
