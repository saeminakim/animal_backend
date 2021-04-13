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
	
	public Page<Animal> findBySidoAndType(String sido, String type, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugunAndType(String sido, String gugun, String type, Pageable pageable);
	
	public Page<Animal> findBySidoAndTypeAndProcessStateContaining(String sido, String type, String status, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugunAndTypeAndProcessStateContaining(String sido, String gugun, String type, String status, Pageable pageable);
	
	public Page<Animal> findByType(String type, Pageable pageable);	
	
	public Page<Animal> findByTypeAndProcessStateContaining(String type, String status, Pageable pageable);
	
	public Page<Animal> findBySidoAndProcessStateContaining(String sido, String status, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugunAndProcessStateContaining(String sido, String gugun, String status, Pageable pageable);
	
	public Page<Animal> findByProcessStateContaining(String status, Pageable pageable);	
}
