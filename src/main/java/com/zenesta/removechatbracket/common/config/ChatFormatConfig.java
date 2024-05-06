package com.zenesta.removechatbracket.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.zenesta.removechatbracket.common.RemoveChatBracket;
import com.zenesta.removechatbracket.common.chat.ChatFormatter;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.zenesta.removechatbracket.common.RemoveChatBracket.LOGGER;

public class ChatFormatConfig {
    public static final File FILE = Paths.get(FMLPaths.CONFIGDIR.get().toString(), RemoveChatBracket.MOD_ID + "-chat-format.json").toFile();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type REVIEW_TYPE = new TypeToken<Map<String, String>>() {}.getType();

    public static void initialize() {
        if (!FILE.exists()) {
            createDefaultFile();
        }
        read();
    }

    public static void read() {
        try {
            JsonReader reader = new JsonReader(new FileReader(FILE));
            ChatFormatter.FORMAT_MAP.putAll(GSON.fromJson(reader, REVIEW_TYPE));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage() + " This shouldn't happen in at all.");
            LOGGER.error(e.getStackTrace());
        }
    }

    public static void reload() {
        ChatFormatter.FORMAT_MAP.clear();
        read();
    }

    private static void createDefaultFile() {
        Map<String, String> defaultMap = new LinkedHashMap<>();
        defaultMap.put("chat.type.text", "%s %s");
        defaultMap.put("chat.type.text.narrate", "%s says %s");
        defaultMap.put("chat.type.team.text", "%s %s %s");
        defaultMap.put("chat.type.team.sent", "-> %s %s %s");
        defaultMap.put("chat.type.announce", "[%s] %s");
        defaultMap.put("chat.type.emote", "* %s %s");
        defaultMap.put("_comment.chat.type.text", "Normal chat format");
        defaultMap.put("_comment.chat.type.narrate", "Narrate chat format");
        defaultMap.put("_comment.chat.team.text", "Team chat format for receiving");
        defaultMap.put("_comment.chat.team.sent", "Team chat format for sending");
        defaultMap.put("_comment.chat.announce", "/say command chat format");
        defaultMap.put("_comment.chat.emote", "Emote format");
        try {
            Writer writer = new FileWriter(FILE);
            FILE.createNewFile();
            GSON.toJson(defaultMap, writer);
            writer.close();
        }
        catch (IOException e){
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace());
        }
    }
}
