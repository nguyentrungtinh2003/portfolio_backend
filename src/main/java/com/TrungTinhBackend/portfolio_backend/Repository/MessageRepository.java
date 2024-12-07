package com.TrungTinhBackend.portfolio_backend.Repository;

import com.TrungTinhBackend.portfolio_backend.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
