package com.github.grigorevmaximandreevich.privateclub.repository;

import com.github.grigorevmaximandreevich.privateclub.model.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    @Query("SELECT g FROM Guest g LEFT JOIN FETCH g.qrCodes")
    List<Guest> findAllWithQrCodes();

    @Query("SELECT g FROM Guest g LEFT JOIN FETCH g.qrCodes WHERE g.id = :id")
    Optional<Guest> findByIdWithQrCodes(Long id);

}
