package com.example.animal.animal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{
	public Animal findByNoticeNo(String noticeNo);
	
	public Page<Animal> findBySido(String sido, Pageable pageable);
	
	public Page<Animal> findBySidoGugun(String sido, String gugun, Pageable pageable);
	
	public Page<Animal> findBySidoAndType(String sido, String type, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugunAndType(String sido, String gugun, String type, Pageable pageable);
	
	public Page<Animal> findBySidoAndTypeAndProcessState(String sido, String type, String status, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugunAndTypeAndProcessState(String sido, String gugun, String type, String status, Pageable pageable);
	
	public Page<Animal> findByType(String type, Pageable pageable);	
	
	public Page<Animal> findByTypeAndProcessState(String type, String status, Pageable pageable);
	
	public Page<Animal> findBySidoAndProcessState(String sido, String status, Pageable pageable);
	
	public Page<Animal> findBySidoAndGugunAndProcessState(String sido, String gugun, String status, Pageable pageable);
	
	public Page<Animal> findByProcessState(String status, Pageable pageable);	
}
