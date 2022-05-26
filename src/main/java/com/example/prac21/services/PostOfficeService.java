package com.example.prac21.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.prac21.entities.PostOffice;
import com.example.prac21.entities.PostOfficeOnload;
import com.example.prac21.repositories.PostOfficeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostOfficeService {
    private final SessionFactory sessionFactory;

    private final PostOfficeRepository officeRepository;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    public List<PostOffice> getOffices() {
        log.info("Find all offices");
        return officeRepository.findAll();
    }

    public PostOffice getOffice(Long id) {
        PostOffice postOffice = officeRepository.findById(id).orElse(null);
        log.info("office with id {} : {}", id, postOffice);
        return postOffice;
    }

    public PostOffice addOffice(PostOfficeOnload onload) {
        PostOffice postOffice = new PostOffice(onload.getOfficeName(), onload.getCityName());
        log.info("create office {}", postOffice);
        officeRepository.save(postOffice);
        return postOffice;
    }

    public PostOffice modifyOffice(PostOfficeOnload onload, Long id) {
        PostOffice officeFromDB = officeRepository.findById(id).orElse(null);
        log.info("modify departure, {}", officeFromDB);
        if (!Objects.isNull(officeFromDB)){
            officeFromDB.setOfficeName(onload.getOfficeName());
            officeFromDB.setCityName(onload.getCityName());
            officeRepository.save(officeFromDB);
        }
        return officeFromDB;
    }

    public void deleteOffice(Long id) {
        PostOffice office = officeRepository.findById(id).orElse(null);
        if (!Objects.isNull(office)){
            log.info("delete office, {}", office);
            officeRepository.delete(office);
            return;
        }
        log.info("trying to delete unexisting PO");
    }

    public List<PostOffice> filterOffices(String field, String param) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PostOffice> departureCriteriaQuery = builder.createQuery(PostOffice.class);
        Root<PostOffice> root = departureCriteriaQuery.from(PostOffice.class);
        if (Arrays.stream(PostOffice.class.getDeclaredFields()).anyMatch(x -> x.getName().equals(field))) {
            departureCriteriaQuery.select(root).where(builder.equal(root.get(field), param));
            Query query = session.createQuery(departureCriteriaQuery);
            return query.getResultList();
        }
        log.info("wrong field in filter request");
        return new ArrayList<PostOffice>(0);
    }
}
