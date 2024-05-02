package com.zenesta.removechatbracket.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.*;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.function.BooleanSupplier;

import static com.zenesta.removechatbracket.common.RemoveChatBracket.LOGGER;

@Mixin(ChatListener.class)
public class MixinChatListener {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Shadow
    private boolean showMessageToPlayer(ChatType.Bound pBoundChatType, PlayerChatMessage pChatMessage, Component pDecoratedServerContent, GameProfile pGameProfile, boolean pOnlyShowSecureChat, Instant pTimestamp) {
        return true;
    }

    @Shadow
    private void handleMessage(@Nullable MessageSignature pSignature, BooleanSupplier pHandler) { }

    /**
     * @author Zenesta
     * @reason Test
     */
    @Overwrite
    public void handlePlayerChatMessage(PlayerChatMessage pChatMessage, GameProfile pGameProfile, ChatType.Bound pBoundChatType) {
        boolean flag = this.minecraft.options.onlyShowSecureChat().get();
        PlayerChatMessage playerchatmessage = flag ? pChatMessage.removeUnsignedContent() : pChatMessage;

        Component displayComponent = removeChatBracket$removeChatBracket(playerchatmessage.decoratedContent(), pBoundChatType);

        Instant instant = Instant.now();
        this.handleMessage(pChatMessage.signature(), () -> {
            boolean flag1 = this.showMessageToPlayer(pBoundChatType, pChatMessage, displayComponent, pGameProfile, flag, instant);
            ClientPacketListener clientpacketlistener = this.minecraft.getConnection();
            if (clientpacketlistener != null) {
                clientpacketlistener.markMessageAsProcessed(pChatMessage, flag1);
            }

            return flag1;
        });

        Minecraft.getInstance().player.displayClientMessage(Component.literal(pBoundChatType.chatType().chat().translationKey()), true);
    }

    @Unique
    private Component removeChatBracket$removeChatBracket(Component pContent, ChatType.Bound pBoundChatType) {
        Component displayComponent;

        ChatTypeDecoration decoration = pBoundChatType.chatType().chat();
        // Extract Component from ChatTypeDecoration.
        Object[] aobject = this.removeChatBracket$resolveParameters(pContent, pBoundChatType);

        if (pBoundChatType.chatType().chat().translationKey().equals("chat.type.text"))
            displayComponent = Component.translatable("custom.chat.type.text", aobject).withStyle(decoration.style());
        else
            displayComponent = pBoundChatType.decorate(pContent);

        return displayComponent;
    }

    @Unique
    private Component[] removeChatBracket$resolveParameters(Component pContent, ChatType.Bound pBoundChatType) {
        Component[] acomponent = new Component[pBoundChatType.chatType().chat().parameters().size()];

        for(int i = 0; i < acomponent.length; ++i) {
            ChatTypeDecoration.Parameter chattypedecoration$parameter = pBoundChatType.chatType().chat().parameters().get(i);
            acomponent[i] = chattypedecoration$parameter.select(pContent, pBoundChatType);
        }

        return acomponent;
    }
}
