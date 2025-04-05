package com.example.cabfinder.Repo;


import com.example.cabfinder.models.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CabRepo extends JpaRepository<Cab,Long> {
//    @Query("SELECT c FROM Cab c WHERE " +
//            "(:model IS NULL OR LOWER(c.model) LIKE LOWER(CONCAT('%', :model, '%'))) AND " +
//            "(:type IS NULL OR LOWER(c.type) = LOWER(:type)) AND " +
//            "(:minSeatCapacity IS NULL OR c.seatCapacity >= :minSeatCapacity) AND " +
//            "(:maxSeatCapacity IS NULL OR c.seatCapacity <= :maxSeatCapacity) AND " +
//            "(:maxFarePerKm IS NULL OR c.farePerKm <= :maxFarePerKm) AND " +
//            "(:maxFarePerDay IS NULL OR c.farePerDay <= :maxFarePerDay)")
//    List<Cab> searchCabs(@Param("model") String model,
//                         @Param("type") String type,
//                         @Param("minSeatCapacity") Integer minSeatCapacity,
//                         @Param("maxSeatCapacity") Integer maxSeatCapacity,
//                         @Param("maxFarePerKm") Double maxFarePerKm,
//                         @Param("maxFarePerDay") Double maxFarePerDay);

}