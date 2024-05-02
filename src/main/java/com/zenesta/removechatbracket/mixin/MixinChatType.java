package com.zenesta.removechatbracket.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatType.class)
public class MixinChatType {

    @Redirect(method = "bootstrap(Lnet/minecraft/data/worldgen/BootstapContext;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/worldgen/BootstapContext;register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/network/chat/ChatType;)Lnet/minecraft/core/Holder$Reference;"))
    private int injected(Something something, int x) {
        return x + 3;

    }

    /**
     * @author Zenesta
     * @reason Test
     */
    @Inject(method = "bind(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/network/chat/ChatType$Bound;", at = @At("RETURN"), cancellable = true)
    private void inject(CallbackInfoReturnable<ChatType.Bound> cir) {
        ChatType.Bound realReturn = cir.getReturnValue();
        /*
        ChatType newChatType = new ChatType(ChatTypeDecoration.withSender("custom.chat.type.text"), ChatTypeDecoration.withSender("chat.type.text.narrate"));
        ChatType.Bound newReturn = newChatType.bind(realReturn.);
        if (realReturn.chatType().chat().translationKey().equals("chat.type.text"))
            cir.setReturnValue();
         */
        cir.setReturnValue(realReturn);
    }
}
