package com.sh.migros.repository;

import com.sh.migros.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourierRepository extends JpaRepository<Courier, Integer> {

    Courier findCourierByCourierId(Integer courierId);

}
