package com.project.agent_service.repository;

import com.project.agent_service.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgentRepository  extends JpaRepository<Agent, Long> {
    /**
     * Finds the first available agent and applies a pessimistic write lock directly in the native query.
     * The "FOR UPDATE" clause is the MySQL way to lock the selected row(s) for the duration of the transaction.
     * This is crucial for preventing race conditions under high load.
     *
     * @param status The status to search for (e.g., "AVAILABLE").
     * @return An Optional containing the first available agent if found.
     */
    // CORRECTED: Removed the @Lock annotation and added the "FOR UPDATE" clause to the native query.
    @Query(value="SELECT * FROM agents a WHERE a.status = :status ORDER BY a.id ASC LIMIT 1 FOR UPDATE", nativeQuery = true)
    Optional<Agent> findAndLockFirstAvailable(String status);
}
