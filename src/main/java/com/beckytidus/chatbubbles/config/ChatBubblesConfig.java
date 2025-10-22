package com.beckytidus.chatbubbles.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChatBubblesConfig {
    public static final ChatBubblesConfig INSTANCE = new ChatBubblesConfig();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("chat-bubbles.json");

    public boolean showPlayerName = false;
    public double bubbleHeight = 0.75;
    public double textScale = 0.025;
    public int displayDuration = 3000;
    public int fadeDuration = 1000;
    public int fadeInDuration = 200;
    public int textColor = 0xFFFFFF;
    public double maxDistance = 64.0;
    public double upwardSpeed = 0.1;
    public int backgroundColor = 0x000000;
    public int backgroundTransparency = 85;

    private ChatBubblesConfig() {}

    public static void load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                ChatBubblesConfig loaded = GSON.fromJson(json, ChatBubblesConfig.class);
                INSTANCE.showPlayerName = loaded.showPlayerName;
                INSTANCE.bubbleHeight = loaded.bubbleHeight;
                INSTANCE.textScale = loaded.textScale;
                INSTANCE.displayDuration = loaded.displayDuration;
                INSTANCE.fadeDuration = loaded.fadeDuration;
                INSTANCE.fadeInDuration = loaded.fadeInDuration;
                INSTANCE.textColor = loaded.textColor;
                INSTANCE.maxDistance = loaded.maxDistance;
                INSTANCE.upwardSpeed = loaded.upwardSpeed;
                INSTANCE.backgroundColor = loaded.backgroundColor;
                INSTANCE.backgroundTransparency = loaded.backgroundTransparency;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            String json = GSON.toJson(INSTANCE);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
