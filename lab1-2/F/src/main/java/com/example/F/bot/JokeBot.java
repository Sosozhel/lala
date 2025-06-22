package com.example.F.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.F.service.JokeService;
import com.example.F.models.Joke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JokeBot extends TelegramLongPollingBot {
    private final String botUsername;
    private final JokeService jokeService;
    private List<Joke> jokeQueue = new ArrayList<>();
    private int currentIndex = 0;

    public JokeBot(@Value("${telegram.bot.token}") String botToken,
                   @Value("${telegram.bot.name}") String botUsername,
                   JokeService jokeService) {
        super(botToken);
        this.botUsername = botUsername;
        this.jokeService = jokeService;
        initializeQueue();
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if ("/start".equals(messageText)) {
                sendMessage(chatId, "Привет! Я бот для анекдотов. Напиши /joke");
            } else if ("/joke".equals(messageText)) {
                sendNextJoke(chatId);
            } else if ("/refresh".equals(messageText)) {
                initializeQueue();
                            }
        }
    }

    private void initializeQueue() {
        jokeQueue = jokeService.getAllJokes();
        Collections.shuffle(jokeQueue);
        currentIndex = 0;
    }


    private void sendNextJoke(long chatId) {
        if (jokeQueue.isEmpty()) {
            sendMessage(chatId, "В базе нет анекдотов");
            return;
        }

        if (currentIndex >= jokeQueue.size()) {
            initializeQueue();
            sendMessage(chatId, "Анекдоты закончились:( Начнем сначала");
        }

        Joke joke = jokeQueue.get(currentIndex++);
        sendMessage(chatId, "Вот анекдот:\n" + joke.getText());
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}