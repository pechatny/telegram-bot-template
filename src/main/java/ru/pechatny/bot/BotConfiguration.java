package com.pechatny.congratulation.organazer.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfiguration {
    private final BotProperties props;

    public BotConfiguration(BotProperties props) {
        this.props = props;
    }

    @PostConstruct
    public void registerBot() {
        try {
            var converterBot = new Bot(props);
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(converterBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
