package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.TfaToken;

@Repository
@Transactional
public interface TfaTokenRepository extends JpaRepository<TfaToken, UUID> {
	@Query(value = "SELECT * FROM tfa_token WHERE token=?1", nativeQuery = true)
	TfaToken findByToken(@Param("token") String token);
}
