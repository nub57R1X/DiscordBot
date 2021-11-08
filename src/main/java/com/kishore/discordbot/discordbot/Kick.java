package com.kishore.discordbot.discordbot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Kick extends ListenerAdapter {
    ArrayList<String> admin = new ArrayList<>();
    private String prefix = "%";
    private DiscordBot plugin;
    public Kick(DiscordBot plugin){
        this.plugin = plugin;
        admin.add("604707040849297408");
        admin.add("560342702785298433");
        admin.add("750711771051851829");
    }
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        User user = e.getAuthor();
        String [] msg = e.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase(prefix + "kick")){
            if(!(admin.contains(user.getId()))){
                e.getMessage().reply("You Don't have The Permission to Use this Command\nIf You think this was Wrong Then Please Contact <@604707040849297408>").queue();
            }
            else{
                if(msg.length != 3){
                    e.getMessage().reply("This Command can Have 2 and only 2 Values\nFor Example : kick 57R1X hacker_bhai").queue();
                }
                else{
                    String name = msg[1];
                    if(Bukkit.getOnlinePlayers().contains(name)){
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String  cmd = "kick " + name + " " + msg[2];
                        try{
                            boolean check = Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(console, cmd)).get();
                            if(check){
                                e.getMessage().reply(name + " has been Kicked By " + e.getAuthor() + " for the Reason " + msg[2]).queue();
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
