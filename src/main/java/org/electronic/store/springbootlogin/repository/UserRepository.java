package org.electronic.store.springbootlogin.repository;

import org.electronic.store.springbootlogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
