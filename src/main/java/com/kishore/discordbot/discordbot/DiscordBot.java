package com.kishore.discordbot.discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class DiscordBot extends JavaPlugin {
    private JDABuilder bot;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        String token = "ODg0NDI4Njk3ODgzNTM3NDI4.YTYWaQ.KITRrwJKiOhe5KK_ibhgYPWYIag";
        bot = JDABuilder.createDefault(token);
        bot.setActivity(Activity.playing("57R1X SMP"));
        bot.setStatus(OnlineStatus.DO_NOT_DISTURB);
        Commands cmd = new Commands();
        Whitelist white = new Whitelist(this);
        Ban ban = new Ban(this);
        Kick kick = new Kick(this);
        Unban unban = new Unban(this);
        new Login(this);
        bot.addEventListeners(white);
        bot.addEventListeners(ban);
        bot.addEventListeners(unban);
        bot.addEventListeners(kick);
        bot.addEventListeners(cmd);
        try {
            bot.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().info("Bot Started");
    }

    @Override
    public void onDisable() {

    }
}
