package com.kishore.discordbot.discordbot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Whitelist extends ListenerAdapter {
    ArrayList<String> admin = new ArrayList<>();
    private String prefix = "%";
    private DiscordBot plugin;
    public Whitelist(DiscordBot plugin){
        this.plugin = plugin;
        admin.add("604707040849297408");
        admin.add("560342702785298433");
        admin.add("750711771051851829");
    }
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        User user = e.getAuthor();
        String [] msg = e.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase(prefix + "whitelist")){
            if(!(admin.contains(user.getId()))){
                e.getMessage().reply("You Don't have The Permission to Use this Command\nIf You think this was Wrong Then Please Contact <@604707040849297408>").queue();
            }
            else{
                if(msg.length != 2){
                    e.getMessage().reply("This Command can Have 1 and only 1 Value\nFor Example : whitelist 57R1X").queue();
                }
                else{
                    String player = msg[1];
                    Bukkit.reloadWhitelist();
                    if(Bukkit.getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(player))){
                        e.getMessage().reply("This Player is already Whitelisted").queue();
                    }
                    else{
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "whitelist add " + player;
                        try{
                            boolean check = Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(console, command)).get();
                            if(check){
                                e.getMessage().reply(player + " Has been Whitelisted By " + e.getAuthor()).queue();
                            }
                        }
                        catch(Exception error){

                        }
                    }
                }
            }
        }
    }
}
