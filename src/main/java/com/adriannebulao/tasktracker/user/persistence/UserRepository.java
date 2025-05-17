package com.adriannebulao.tasktracker.user.persistence;

import com.adriannebulao.tasktracker.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}
