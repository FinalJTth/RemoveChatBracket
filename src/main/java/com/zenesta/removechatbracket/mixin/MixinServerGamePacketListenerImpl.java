package com.zenesta.removechatbracket.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import com.zenesta.removechatbracket.common.RemoveChatBracket;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Stream;

// import static com.zenesta.removechatbracket.common.RemoveChatBracket.CUSTOM_CHAT_TYPE;
import static com.zenesta.removechatbracket.common.RemoveChatBracket.MOD_ID;

@Mixin(ServerGamePacketListenerImpl.class)
public class MixinServerGamePacketListenerImpl {
    @Unique @Final
    private final String removeChatBracket$method = "broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;)V";
    @Unique
    private final String removeChatBracket$targetType = "Lnet/minecraft/server/players/PlayerList;";
    @Unique
    private final String removeChatBracket$targetMethod = "broadcastChatMessage";
    @Unique
    private final String removeChatBracket$arguments = "(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)";
    @Unique
    private final String removeChatBracket$returnType = "V";
    @Unique
    private final String removeChatBracket$target = removeChatBracket$targetType + removeChatBracket$targetMethod + removeChatBracket$arguments + removeChatBracket$returnType;

    @Shadow
    public ServerPlayer player;

    @ModifyArg(method = removeChatBracket$method, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"), index = 2)
    private ChatType.Bound inject(ChatType.Bound chatTypeBound) {
        // return ChatType.bind(ResourceKey.create(Registries.CHAT_TYPE, new ResourceLocation("minecraft:custom_chat")), player);
        // return ChatType.bind(Objects.requireNonNull(CUSTOM_CHAT_TYPE.getKey()), player);
        // ChatType.Bound ctb = ChatType.bind(ResourceKey.create(Registries.CHAT_TYPE, new ResourceLocation("minecraft:custom_chat")), player);
        // ctb.decorate(new ChatType(ChatTypeDecoration.withSender("custom.chat.type.text"), ChatTypeDecoration.withSender("chat.type.text.narrate")).bind()).
        //ChatType newChatType = new ChatType(ChatTypeDecoration.withSender("custom.chat.type.text"), ChatTypeDecoration.withSender("chat.type.text.narrate"));
        // return newChatType.bind(chatTypeBound.name());

        return chatTypeBound;
    }
}
