package com.beckytidus.chatbubbles.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ChatBubblesConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Text.literal("Chat Bubbles Config"))
            .setSavingRunnable(ChatBubblesConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Player Name"), ChatBubblesConfig.INSTANCE.showPlayerName)
            .setDefaultValue(false)
            .setTooltip(Text.literal("Include player name in chat bubble"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.showPlayerName = value)
            .build());

        general.addEntry(entryBuilder.startDoubleField(Text.literal("Bubble Height"), ChatBubblesConfig.INSTANCE.bubbleHeight)
            .setDefaultValue(0.75)
            .setMin(0.0)
            .setMax(5.0)
            .setTooltip(Text.literal("Starting height above player's head (in blocks)"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.bubbleHeight = value)
            .build());

        general.addEntry(entryBuilder.startDoubleField(Text.literal("Text Scale"), ChatBubblesConfig.INSTANCE.textScale)
            .setDefaultValue(0.025)
            .setMin(0.01)
            .setMax(0.1)
            .setTooltip(Text.literal("Size of the text"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.textScale = value)
            .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Display Duration (ms)"), ChatBubblesConfig.INSTANCE.displayDuration)
            .setDefaultValue(3000)
            .setMin(500)
            .setMax(30000)
            .setTooltip(Text.literal("How long the bubble stays visible before fading"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.displayDuration = value)
            .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Fade Out Duration (ms)"), ChatBubblesConfig.INSTANCE.fadeDuration)
            .setDefaultValue(1000)
            .setMin(0)
            .setMax(5000)
            .setTooltip(Text.literal("How long the fade-out animation takes"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.fadeDuration = value)
            .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Fade In Duration (ms)"), ChatBubblesConfig.INSTANCE.fadeInDuration)
            .setDefaultValue(200)
            .setMin(0)
            .setMax(2000)
            .setTooltip(Text.literal("How long the fade-in animation takes when bubble appears"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.fadeInDuration = value)
            .build());

        general.addEntry(entryBuilder.startColorField(Text.literal("Text Color"), ChatBubblesConfig.INSTANCE.textColor)
            .setDefaultValue(0xFFFFFF)
            .setTooltip(Text.literal("Color of the chat bubble text"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.textColor = value)
            .build());

        general.addEntry(entryBuilder.startDoubleField(Text.literal("Max Distance"), ChatBubblesConfig.INSTANCE.maxDistance)
            .setDefaultValue(64.0)
            .setMin(8.0)
            .setMax(256.0)
            .setTooltip(Text.literal("Maximum distance to render chat bubbles (in blocks)"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.maxDistance = value)
            .build());

        general.addEntry(entryBuilder.startDoubleField(Text.literal("Upward Speed"), ChatBubblesConfig.INSTANCE.upwardSpeed)
            .setDefaultValue(0.1)
            .setMin(0.0)
            .setMax(5.0)
            .setTooltip(Text.literal("How fast bubbles float upward (blocks per second)"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.upwardSpeed = value)
            .build());

        general.addEntry(entryBuilder.startColorField(Text.literal("Background Color"), ChatBubblesConfig.INSTANCE.backgroundColor)
            .setDefaultValue(0x000000)
            .setTooltip(Text.literal("Color of the background behind text"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.backgroundColor = value)
            .build());

        general.addEntry(entryBuilder.startIntSlider(Text.literal("Background Transparency"), ChatBubblesConfig.INSTANCE.backgroundTransparency, 0, 100)
            .setDefaultValue(85)
            .setTooltip(Text.literal("Background transparency (0% = fully opaque, 100% = invisible)"))
            .setSaveConsumer(value -> ChatBubblesConfig.INSTANCE.backgroundTransparency = value)
            .build());

        return builder.build();
    }
}
