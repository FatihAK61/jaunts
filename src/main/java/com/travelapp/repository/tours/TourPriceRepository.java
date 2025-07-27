package com.travelapp.repository.tours;

import com.travelapp.models.tours.TourPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TourPriceRepository extends JpaRepository<TourPrice, Long>, JpaSpecificationExecutor<TourPrice> {
}
