package com.zenesta.removechatbracket.common.chat;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ChatFormatter {
    public static final Map<String, String> FORMAT_MAP = new HashMap<>();

    public static Component format(Component pContent, ChatType.Bound pBoundChatType) {
        Component displayComponent = pBoundChatType.decorate(pContent);

        ChatTypeDecoration decoration = pBoundChatType.chatType().chat();
        // Extract Component from ChatTypeDecoration.
        Object[] aobject = resolveParameters(pContent, pBoundChatType);

        for (Map.Entry<String, String> entry : FORMAT_MAP.entrySet()) {
            if (pBoundChatType.chatType().chat().translationKey().equals(entry.getKey()))
            {
                displayComponent = Component.translatable(entry.getValue(), aobject).withStyle(decoration.style());
                break;
            }
        }

        return displayComponent;
    }

    private static Component[] resolveParameters(Component pContent, ChatType.Bound pBoundChatType) {
        Component[] acomponent = new Component[pBoundChatType.chatType().chat().parameters().size()];

        for(int i = 0; i < acomponent.length; ++i) {
            ChatTypeDecoration.Parameter chattypedecoration$parameter = pBoundChatType.chatType().chat().parameters().get(i);
            acomponent[i] = chattypedecoration$parameter.select(pContent, pBoundChatType);
        }

        return acomponent;
    }
}
