package com.example.clientservice.service;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.ClientResponse;
import com.example.clientservice.entity.Client;
import com.example.clientservice.entity.Passport;
import com.example.clientservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public ClientResponse createClient(ClientRequest request) {
        if (clientRepository.existsByPassportSerialAndPassportNumber(
                request.getPassport().getSerial(), 
                request.getPassport().getNumber())) {
            throw new RuntimeException("Client with such passport already exists");
        }

        Client client = new Client();
        client.setFullName(request.getFullName());
        client.setBirthDay(request.getBirthDay());
        
        Passport passport = new Passport();
        passport.setSerial(request.getPassport().getSerial());
        passport.setNumber(request.getPassport().getNumber());
        client.setPassport(passport);
        
        client.setAddress(request.getAddress());

        client = clientRepository.save(client);
        return convertToClientResponse(client);
    }

    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        return convertToClientResponse(client);
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll().stream()
                .map(this::convertToClientResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClientResponse updateClient(Long id, ClientRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        
        // Check if passport has changed and if new passport exists for another client
        if (!client.getPassport().getSerial().equals(request.getPassport().getSerial()) ||
            !client.getPassport().getNumber().equals(request.getPassport().getNumber())) {
            
            boolean passportExists = clientRepository.existsByPassportSerialAndPassportNumber(
                    request.getPassport().getSerial(), 
                    request.getPassport().getNumber());
                    
            if (passportExists) {
                throw new RuntimeException("Client with such passport already exists");
            }
        }
        
        client.setFullName(request.getFullName());
        client.setBirthDay(request.getBirthDay());
        
        Passport passport = client.getPassport();
        passport.setSerial(request.getPassport().getSerial());
        passport.setNumber(request.getPassport().getNumber());
        
        client.setAddress(request.getAddress());
        
        client = clientRepository.save(client);
        return convertToClientResponse(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Client not found");
        }
        clientRepository.deleteById(id);
    }

    private ClientResponse convertToClientResponse(Client client) {
        ClientResponse response = new ClientResponse();
        response.setId(client.getId());
        response.setFullName(client.getFullName());
        response.setBirthDay(client.getBirthDay());
        
        ClientResponse.PassportDto passportDto = new ClientResponse.PassportDto();
        passportDto.setSerial(client.getPassport().getSerial());
        passportDto.setNumber(client.getPassport().getNumber());
        response.setPassport(passportDto);
        
        response.setAddress(client.getAddress());
        response.setCreatedAt(client.getCreatedAt());
        response.setUpdatedAt(client.getUpdatedAt());
        
        return response;
    }
} 