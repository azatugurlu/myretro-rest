package com.azat.myretro.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azat.myretro.model.UserAccountInformation;

@Repository
@Transactional
public interface UserAccountInformationRepository extends JpaRepository<UserAccountInformation, UUID>{

}
