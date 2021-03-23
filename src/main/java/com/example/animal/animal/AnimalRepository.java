package com.example.animal.animal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
	Animal findByNoticeNo(String noticeNo);
}
