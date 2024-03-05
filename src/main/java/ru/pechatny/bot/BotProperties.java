package com.pechatny.congratulation.organazer.bot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bot")
public record BotProperties(String name, String token) { }
