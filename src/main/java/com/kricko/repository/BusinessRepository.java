package com.kricko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kricko.domain.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Business b SET b.name=:name, b.firstname=:firstname, b.lastname=:lastname, b.address1=:address1,"
            + "b.address2=:address2, b.city=:city, b.county=:county, b.postcode=:postcode,"
            + "b.email=:email, b.tel=:tel WHERE b.id = :id")
    void updateById(@Param("id") Long id, @Param("name") String name,@Param("firstname") String firstname, @Param("lastname") String lastname,
                    @Param("address1") String address1, @Param("address2") String address2, @Param("city") String city, @Param("county") String county,
                    @Param("postcode") String postcode, @Param("email") String email, @Param("tel") String tel);
}
