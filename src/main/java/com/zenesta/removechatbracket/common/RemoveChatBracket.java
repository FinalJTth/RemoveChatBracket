package com.zenesta.removechatbracket.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("removechatbracket")
public class RemoveChatBracket {
    public static final String MOD_ID = "removechatbracket";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    // private static final DeferredRegister<ChatType> CHAT_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.CHAT_TYPE, MOD_ID);
    // public static final RegistryObject<ChatType> CUSTOM_CHAT_TYPE = CHAT_TYPE_DEFERRED_REGISTER.register("custom_chat", () -> new ChatType(ChatTypeDecoration.withSender("custom.chat.type.text"), ChatTypeDecoration.withSender("chat.type.text.narrate")));

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents {
        @SubscribeEvent
        public static void register(final RegisterEvent event) {
            // event.register(Registries.CHAT_TYPE, (helper) -> helper.register(ResourceKey.create(Registries.CHAT_TYPE, new ResourceLocation("minecraft:custom_chat")), new ChatType(ChatTypeDecoration.withSender("custom.chat.type.text"), ChatTypeDecoration.withSender("chat.type.text.narrate"))));
            // event.register(ResourceKey.create(Registries.CHAT_TYPE, new ResourceLocation("custom_chat")), (helper) -> helper.register("custom_chat", new ChatType(ChatTypeDecoration.withSender("custom.chat.type.text"), ChatTypeDecoration.withSender("chat.type.text.narrate"))));
        }
    }
}