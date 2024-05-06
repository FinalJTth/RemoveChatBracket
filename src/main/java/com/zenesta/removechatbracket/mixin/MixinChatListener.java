package com.zenesta.removechatbracket.mixin;

import com.mojang.authlib.GameProfile;
import com.zenesta.removechatbracket.common.chat.ChatFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.*;
import org.spongepowered.asm.mixin.*;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.function.BooleanSupplier;

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

        Component displayComponent = ChatFormatter.format(playerchatmessage.decoratedContent(), pBoundChatType);

        Instant instant = Instant.now();
        this.handleMessage(pChatMessage.signature(), () -> {
            boolean flag1 = this.showMessageToPlayer(pBoundChatType, pChatMessage, displayComponent, pGameProfile, flag, instant);
            ClientPacketListener clientpacketlistener = this.minecraft.getConnection();
            if (clientpacketlistener != null) {
                clientpacketlistener.markMessageAsProcessed(pChatMessage, flag1);
            }

            return flag1;
        });
    }

    /*
    @ModifyVariable(method = "handlePlayerChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lcom/mojang/authlib/GameProfile;L/net/minecraft/network/chat/ChatType$Bound;)V", at = @At("STORE"), ordinal = 0)
    private Component inject(Component component) {
        return ChatFormatter.format(playerchatmessage.decoratedContent(), pBoundChatType);
    }
    */
}
