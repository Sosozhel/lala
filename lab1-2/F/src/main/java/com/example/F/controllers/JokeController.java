package com.example.F.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.F.models.Joke;
import com.example.F.service.JokeService;
import java.util.List;

@RestController
@RequestMapping("/jokes")
public class JokeController {
    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public ResponseEntity<List<Joke>> getAllJokes() {
        return ResponseEntity.ok(jokeService.getAllJokes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joke> getJokeById(@PathVariable Long id) {
        return jokeService.getJokeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Joke> createJoke(@RequestBody Joke joke) {
        return ResponseEntity.ok(jokeService.saveJoke(joke));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Joke> updateJoke(
            @PathVariable Long id,
            @RequestBody Joke jokeDetails) {
        return jokeService.getJokeById(id)
                .map(existingJoke -> {
                    existingJoke.setText(jokeDetails.getText());
                    return ResponseEntity.ok(jokeService.saveJoke(existingJoke));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJoke(@PathVariable Long id) {
        jokeService.deleteJoke(id);
        return ResponseEntity.noContent().build();
    }
}