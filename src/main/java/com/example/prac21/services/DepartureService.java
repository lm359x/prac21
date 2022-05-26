package com.example.prac21.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.prac21.entities.Departure;
import com.example.prac21.entities.DepartureOnload;
import com.example.prac21.entities.PostOffice;
import com.example.prac21.repositories.DepartureRepository;
import com.example.prac21.repositories.PostOfficeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartureService {
    private final SessionFactory sessionFactory;
    @Autowired
    private final DepartureRepository departureRepository;
    @Autowired
    private final PostOfficeRepository officeRepository;
    private final com.example.prac21.services.PostOfficeService postOfficeService;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<Departure> getDepartures() {
        log.info("Find all departures");
        return departureRepository.findAll();
    }

    public Departure getDeparture(Long id) {
        Departure departure = departureRepository.findById(id).orElse(null);
        log.info("Departure with id {}, {}",id,departure);
        return departure;
    }

    public Departure addDeparture(DepartureOnload onload) {
        Departure departure = new Departure(onload.getType(), onload.getDepartureDate());
        log.info("Create departure {}", departure);
        departure.setOffice(postOfficeService.getOffice(onload.getOfficeId()));
        departureRepository.save(departure);
        return departure;
    }

    public Departure modifyDeparture(DepartureOnload onload, Long id) {
            log.info("modify departure, {}", id);
            Departure departureFromDB = departureRepository.getById(id);
            departureFromDB.setType(onload.getType());
            departureFromDB.setDepartureDate(onload.getDepartureDate());
            departureRepository.save(departureFromDB);
            return departureFromDB;
    }

    public void deleteDeparture(Long id) {
        Departure departure = departureRepository.findById(id).orElse(null);
        if(!Objects.isNull(departure)){
            log.info("delete departure {}", departure);
            departureRepository.delete(departure);
            return;
        }
        log.info("try to delete unexisting entity");
    }

    public List<Departure> filterDepartures(String field, String param) {
        switch (field) {
            case "type" -> {
                return departureRepository.findDeparturesByType(param);
            }
            case "date" -> {
                return departureRepository.findDeparturesByDepartureDate(param.replace('_','*'));
            }
            case "office" -> {
                PostOffice office = officeRepository.findById(Long.parseLong(param)).orElse(null);
                return departureRepository.findDeparturesByOffice(office);
            }
        }
        log.info("wrong field in filter request");
        return new ArrayList<Departure>(0);

    }

}
