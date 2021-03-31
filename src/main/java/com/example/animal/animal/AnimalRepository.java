package com.example.animal.animal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
	public Animal findByNoticeNo(String noticeNo);
	
	public Page<Animal> findBySido(String sido, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugun(String sido, String gugun, Pageable pageable);
	
	public Page<Animal> findByType(String type, Pageable pageable);
	
	public Page<Animal> findByTypeAndKind(String type, String kind, Pageable pageable);
	
	
	
	
}
