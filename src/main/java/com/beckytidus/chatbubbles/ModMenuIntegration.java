package com.beckytidus.chatbubbles;

import com.beckytidus.chatbubbles.config.ChatBubblesConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ChatBubblesConfigScreen::create;
    }
}
