package com.example.prac21.repositories;

import com.example.prac21.entities.Departure;
import com.example.prac21.entities.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DepartureRepository extends JpaRepository<Departure,Long> {
    List<Departure> findDeparturesByDepartureDate(String date);
    List<Departure> findDeparturesByType(String type);

    List<Departure> findDeparturesByOffice(PostOffice office);
}
