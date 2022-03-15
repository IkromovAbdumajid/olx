package com.comapny.repository;

import com.comapny.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>, JpaSpecificationExecutor<ProfileEntity> {

    Optional<ProfileEntity> findByLoginAndPassword(String login, String pswd);

    void deleteByEmail(String email);

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByLogin(String login);


}
