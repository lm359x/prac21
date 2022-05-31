package com.example.prac21.services;

import com.example.prac21.entities.Departure;
import com.example.prac21.entities.PostOffice;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class SchedulerService implements SchedulerServiceMBean {
    @Autowired
    private DepartureService departureService;
    @Autowired
    private PostOfficeService postOfficeService;
    @Autowired
    public SchedulerService(DepartureService departureService, PostOfficeService postOfficeService) {
        this.departureService = departureService;
        this.postOfficeService = postOfficeService;
    }



    @Scheduled(cron = "30000")
    @Override
    public void doLoadOut(){
        String path = "loadouts";
        File directory = new File(path);
        directory.delete();
        File newDirectory = new File(path);
        if(newDirectory.mkdir()){
            System.out.println("directory created!");
        }
        List<Departure> departures = departureService.getDepartures();
        List<JSONObject> departureObjects = new ArrayList<>();
        for(Departure departure: departures){
            JSONObject object = new JSONObject();
            object.put("id",departure.getId());
            object.put("type",departure.getType());
            object.put("departureDate",departure.getDepartureDate());
            object.put("postOffice",departure.getOffice());
            departureObjects.add(object);
        }
        for(JSONObject object: departureObjects){
            try {
                Files.writeString(Paths.get(path+"/deps.json"), object.toString(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<PostOffice> offices = postOfficeService.getOffices();
        List<JSONObject> officeObjects = new ArrayList<>();
        for(PostOffice office: offices){
            JSONObject object = new JSONObject();
            object.put("id",office.getId());
            object.put("name",office.getOfficeName());
            object.put("city",office.getCityName());
            officeObjects.add(object);
        }
        for(JSONObject object: officeObjects){
            try {
                Files.writeString(Paths.get(path+"/offices.json"), object.toString(),StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
