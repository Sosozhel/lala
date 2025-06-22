package com.example.F.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.F.models.Joke;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JokeRepository extends JpaRepository<Joke, Long> {

    @Query(value = "SELECT * FROM jokes ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Joke> findRandomJoke();
}