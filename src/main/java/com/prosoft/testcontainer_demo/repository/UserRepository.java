package com.prosoft.testcontainer_demo.repository;

import com.prosoft.testcontainer_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNameAndEmail(String name, String email);

    List<User> findByName(String name);

}
