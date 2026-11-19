package com.github.grigorevmaximandreevich.privateclub.repository;

import com.github.grigorevmaximandreevich.privateclub.model.entity.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QRCodeRepository extends JpaRepository<Long, QRCode> {

    Optional<QRCode> findByCode(UUID code);

    @Query("SELECT q FROM QRCode q JOIN FETCH q.guest WHERE q.code = :code AND q.isActive = true")
    Optional<QRCode> findByCodeAndIsActiveTrueWithGuest(UUID code);

    @Query("SELECT q FROM QRCode q JOIN FETCH q.guest")
    List<QRCode> findAllWithGuest();
}
