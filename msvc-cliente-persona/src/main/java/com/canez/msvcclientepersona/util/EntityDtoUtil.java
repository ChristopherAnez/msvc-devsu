package com.canez.msvcclientepersona.util;

import com.canez.msvcclientepersona.dto.ClientePersonaDto;
import com.canez.msvcclientepersona.entity.Cliente;
import com.canez.msvcclientepersona.entity.Persona;

public class EntityDtoUtil {

    public static Persona toPersona(ClientePersonaDto request) {
        Persona persona = new Persona();
        persona.setNombre(request.getNombre());
        persona.setGenero(request.getGenero());
        persona.setEdad(request.getEdad());
        persona.setIdentificacion(request.getIdentificacion());
        persona.setDireccion(request.getDireccion());
        persona.setTelefono(request.getTelefono());
        return persona;
    }

    public static Cliente toCliente(ClientePersonaDto request) {
        Cliente cliente = new Cliente();
        cliente.setContrasena(request.getContrasena());
        cliente.setEstado(request.getEstado());
        return cliente;
    }

    public static ClientePersonaDto toResponse(Persona persona, Cliente cliente) {
        ClientePersonaDto response = new ClientePersonaDto();
        response.setPersonaId(persona.getId());
        response.setClienteId(cliente.getClienteId());
        response.setNombre(persona.getNombre());
        response.setGenero(persona.getGenero().trim());
        response.setEdad(persona.getEdad());
        response.setIdentificacion(persona.getIdentificacion());
        response.setDireccion(persona.getDireccion());
        response.setTelefono(persona.getTelefono());
        response.setContrasena(cliente.getContrasena());
        response.setEstado(cliente.getEstado());
        return response;
    }

    public static void updatePersonaFromDto(ClientePersonaDto dto, Persona persona) {
        if (dto.getNombre() != null) {
            persona.setNombre(dto.getNombre());
        }
        if (dto.getGenero() != null) {
            persona.setGenero(dto.getGenero());
        }
        if (dto.getEdad() != null) {
            persona.setEdad(dto.getEdad());
        }
        if (dto.getIdentificacion() != null) {
            persona.setIdentificacion(dto.getIdentificacion());
        }
        if (dto.getDireccion() != null) {
            persona.setDireccion(dto.getDireccion());
        }
        if (dto.getTelefono() != null) {
            persona.setTelefono(dto.getTelefono());
        }
    }

    public static void updateClienteFromDto(ClientePersonaDto dto, Cliente cliente) {
        if (dto.getContrasena() != null) {
            cliente.setContrasena(dto.getContrasena());
        }
        if (dto.getEstado() != null) {
            cliente.setEstado(dto.getEstado());
        }
    }

}
