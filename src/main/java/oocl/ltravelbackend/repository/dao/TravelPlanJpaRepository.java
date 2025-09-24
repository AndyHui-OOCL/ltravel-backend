package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.TravelPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TravelPlanJpaRepository extends JpaRepository<TravelPlan, Long> {

    @Query("SELECT tp FROM TravelPlan tp WHERE " +
           "(:city IS NULL OR :city = '' OR tp.cityName LIKE %:city%) AND " +
           "(:tag IS NULL OR :tag = '' OR tp.tag LIKE %:tag%)")
    Page<TravelPlan> findFilteredTravelPlans(@Param("city") String city,
                                           @Param("tag") String tag,
                                           Pageable pageable);
}
