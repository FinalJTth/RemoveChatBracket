package com.zenesta.removechatbracket.common.registry;

import com.zenesta.removechatbracket.common.RemoveChatBracket;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import static com.zenesta.removechatbracket.common.RemoveChatBracket.LOGGER;

public class CustomChatType {
    /*
    public static final ChatTypeDecoration DEFAULT_CHAT_DECORATION = ChatTypeDecoration.withSender("custom.chat.type.text");
    public static final ResourceKey<ChatType> CHAT = ResourceKey.create(Registries.CHAT_TYPE, new ResourceLocation(RemoveChatBracket.MOD_ID, "chat_custom"));

    public static void bootstrap(BootstapContext<ChatType> pContext) {
        pContext.register(CHAT, new ChatType(DEFAULT_CHAT_DECORATION, ChatTypeDecoration.withSender("chat.type.text.narrate")));
    }
    */
}
