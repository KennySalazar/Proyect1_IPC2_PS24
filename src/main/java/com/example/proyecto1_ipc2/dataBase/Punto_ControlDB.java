package com.example.proyecto1_ipc2.dataBase;

import com.example.proyecto1_ipc2.modelo.Punto_Control;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Punto_ControlDB {
   /* private List<Punto_Control> puntoControls;

    public Punto_ControlDB(){
        puntoControls = new ArrayList<>();
    }

    public void create(Punto_Control puntoControl) {
        puntoControl.setId(puntoControls.size() + 1);
        puntoControls.add(puntoControl);

    }

    public List<Punto_Control> readAll() {
        return puntoControls;
    }

    public Optional<Punto_Control> read(int id) {
        return puntoControls.stream().filter(s -> s.getId() == id).findFirst();
    }

    public void update(Punto_Control puntoControl) {
        var puntoContorlDB = puntoControls.stream().filter(s -> s.getId() == puntoControl.getId()).findFirst().orElse(null);

        if (puntoContorlDB != null){
            puntoContorlDB.setId(puntoControl.getId());
            puntoContorlDB.setHoraIngreso(puntoControl.getHoraIngreso());
            puntoContorlDB.setHoraSalida(puntoControl.getHoraSalida());
            puntoContorlDB.setTarifa(puntoControl.getTarifa());
        }
    }

    public void delete(int id){
        puntoControls.stream().filter(s -> s.getId() == id).findFirst().ifPresent(studentDB ->puntoControls.remove(studentDB) );
    } */
}
