package com.example.animal.request;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, Integer> {
	public List<AdoptionRequest> findByRequestNoAndName(long requestNo, String name);

}
