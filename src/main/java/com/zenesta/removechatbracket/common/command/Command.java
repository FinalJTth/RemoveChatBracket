package com.zenesta.removechatbracket.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.zenesta.removechatbracket.common.config.ChatFormatConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class Command {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("removechatbracket")
                .executes(Command::reload)
                .then(Commands.literal("reload").executes(Command::reload));
        dispatcher.register(builder);
    }

    public static int reload(CommandContext<CommandSourceStack> ctx) {
        ChatFormatConfig.reload();
        ctx.getSource().sendSuccess(() -> Component.translatable("removechatbracket.command.reload"), true);
        return com.mojang.brigadier.Command.SINGLE_SUCCESS;
    }
}
