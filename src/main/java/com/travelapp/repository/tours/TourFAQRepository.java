package com.travelapp.repository.tours;

import com.travelapp.models.tours.TourFAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourFAQRepository extends JpaRepository<TourFAQ, Long>, JpaSpecificationExecutor<TourFAQ> {
}
