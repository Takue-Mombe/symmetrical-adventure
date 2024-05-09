package com.example.fuelsystem.Service;

import com.example.fuelsystem.Models.Requests;
import com.example.fuelsystem.Repositories.RequestsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RequestService {
    private final RequestsRepository requestsRepository;

    public RequestService(RequestsRepository requestsRepository) {
        this.requestsRepository = requestsRepository;
    }
    public Requests saveRequest(Requests requests){
        return requestsRepository.save(requests);
    }
    public List<Requests> findAll(){
        return  requestsRepository.findAll();
    }

}
