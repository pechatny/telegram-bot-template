package com.pechatny.congratulation.organazer.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class Bot extends TelegramLongPollingBot {
    private final Logger logger = LoggerFactory.getLogger(Bot.class);
    BotProperties props;

    Bot(BotProperties props) {
        super(props.token());
        this.props = props;
    }

    @Override
    public void onUpdateReceived(Update update) {
        logger.info("Start convert operation!");
        if (update.hasMessage()) {
            String message = getMessage(update);
            Long chatId = update.getMessage().getChatId();

            logger.info("Input message: {}", message);
            SendMessage sendMessage = new SendMessage();

            sendMessage.setText(message);
            sendMessage.setChatId(chatId);
            logger.info("Output message: {}", message);

            try {
                execute(sendMessage);
                logger.info("End of convert operation!");
            } catch (TelegramApiException e) {
                logger.error("Convert operation error! Message: {}", message);
                sendMessage.setText("Ошибка при конвертации сообщения!");
                sendMessage.setChatId(chatId);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            }
        }
    }

    private String getMessage(Update update) {

        return update.getMessage().getText();
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return props.name();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
