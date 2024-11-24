package com.TrungTinhBackend.portfolio_backend.Repository;

import com.TrungTinhBackend.portfolio_backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
