package com.monaum.Money_Management.module.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmailIgnoreCase(String email);
	Optional<User> findByUserNameIgnoreCase(String userName);

}
