package com.example.fuelsystem.Service;

import com.example.fuelsystem.Models.Fuel;
import com.example.fuelsystem.Repositories.FuelRepository;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

public class FuelService implements CrudListener<Fuel> {
    private final FuelRepository fuelRepository;

    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @Override
    public Collection<Fuel> findAll() {
        return fuelRepository.findAll();
    }

    @Override
    public Fuel add(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    @Override
    public Fuel update(Fuel fuel) {
        return fuelRepository.save(fuel);
    }

    @Override
    public void delete(Fuel fuel) {
        fuelRepository.delete(fuel);
    }
}
