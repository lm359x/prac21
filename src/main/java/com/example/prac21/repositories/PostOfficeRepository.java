package com.example.prac21.repositories;

import com.example.prac21.entities.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {

}
