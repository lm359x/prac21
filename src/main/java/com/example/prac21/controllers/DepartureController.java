package com.example.prac21.controllers;

import com.example.prac21.entities.Departure;
import com.example.prac21.entities.DepartureOnload;
import com.example.prac21.services.DepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/departure")
public class DepartureController {
    @Autowired
    private DepartureService departureService;

    @GetMapping
    public ResponseEntity<List<Departure>> getDeparturesList() {
        return ResponseEntity.ok(departureService.getDepartures());
    }

    @GetMapping("{departureId}")
    public ResponseEntity<Departure> getDeparture(@PathVariable Long departureId) {
        Departure departure = departureService.getDeparture(departureId);
        if(departure==null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(departure);
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            "application/vnd.example.todo_payload+json"})
    public ResponseEntity<Departure> createDeparture(@RequestBody DepartureOnload payload) {
        return ResponseEntity.ok(departureService.addDeparture(payload));
    }

//    @GetMapping(path = "{departureId}/edit", consumes = {
//            MediaType.APPLICATION_JSON_VALUE,
//            "application/vnd.example.todo_payload+json"
//    })
//    public ResponseEntity<Departure> modifyDeparture(@PathVariable Long departureId, @RequestBody DepartureOnload onload){
//        Departure departure = departureService.modifyDeparture(onload, departureId);
//        if (departure==null)
//            return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(departure);
//    }
    @GetMapping("{departureId}/delete")
    public ResponseEntity<Void> deleteDeparture(@PathVariable Long departureId){
        departureService.deleteDeparture(departureId);
        return ResponseEntity.ok().build();
    }
}
