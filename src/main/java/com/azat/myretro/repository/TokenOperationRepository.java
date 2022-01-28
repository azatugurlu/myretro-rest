package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.TokenOperation;

@Repository
@Transactional
public interface TokenOperationRepository extends JpaRepository<TokenOperation, UUID> {
	@Query(value = "SELECT * FROM token_operation WHERE token=?1 and operation_type = ?2", nativeQuery = true)
	TokenOperation findByTokenAndOperationType(@Param("token") String token,@Param("operation_type") String operationType);
}
