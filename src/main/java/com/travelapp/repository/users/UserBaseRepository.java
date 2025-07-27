package com.travelapp.repository.users;

import com.travelapp.models.users.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBaseRepository extends JpaRepository<UserBase, Long>, JpaSpecificationExecutor<UserBase> {

    @Query("SELECT u FROM UserBase u WHERE u.id = :id")
    Optional<UserBase> findUserById(Long id);
}
