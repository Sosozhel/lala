package com.example.F.service;

import org.springframework.stereotype.Service;
import com.example.F.models.Joke;
import com.example.F.repository.JokeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JokeService {
    private final JokeRepository jokeRepository;
    private final Random random = new Random();

    public JokeService(JokeRepository jokeRepository) {
        this.jokeRepository = jokeRepository;
    }

    public List<Joke> getAllJokes() {
        return jokeRepository.findAll();
    }

    public Optional<Joke> getJokeById(Long id) {
        return jokeRepository.findById(id);
    }

    public Joke saveJoke(Joke joke) {
        return jokeRepository.save(joke);
    }

    public void deleteJoke(Long id) {
        jokeRepository.deleteById(id);
    }

    public Optional<Joke> getRandomJoke() {
        List<Joke> jokes = getAllJokes();
        if (jokes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(jokes.get(random.nextInt(jokes.size())));
    }
}