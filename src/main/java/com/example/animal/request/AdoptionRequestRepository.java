package com.example.animal.request;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Long> {
	public List<AdoptionRequest> findByRequestNoAndName(String requestNo, String name);
	
	public List<AdoptionRequest> findByEmail(String email);

}
