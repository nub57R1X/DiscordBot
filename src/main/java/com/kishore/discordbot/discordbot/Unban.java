package com.kishore.discordbot.discordbot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Unban extends ListenerAdapter {
    ArrayList<String> admin = new ArrayList<>();
    private String prefix = "%";
    private DiscordBot plugin;
    public Unban(DiscordBot plugin){
        this.plugin = plugin;
        admin.add("604707040849297408");
        admin.add("560342702785298433");
        admin.add("750711771051851829");
    }
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        User user = e.getAuthor();
        String [] msg = e.getMessage().getContentRaw().split(" ");
        if(msg[0].equalsIgnoreCase(prefix + "unban")){
            if(!(admin.contains(user.getId()))){
                e.getMessage().reply("You Don't have The Permission to Use this Command\nIf You think this was Wrong Then Please Contact <@604707040849297408>").queue();
            }
            else{
                if(msg.length !=2){
                    e.getMessage().reply("This Command can Have 1 and only 1 Value\nFor Example : unban 57R1X").queue();
                }
                else{
                    String player = msg[1];
                    Player p = Bukkit.getPlayer(player);
                    if(Bukkit.getServer().getBanList(BanList.Type.NAME).isBanned(player)){
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String cmd = "pardon " + player;
                        try{
                            boolean check = Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(console, cmd)).get();
                            if(check){
                                e.getMessage().reply(player + " has been Unbanned By " + e.getAuthor()).queue();
                            }
                        }
                        catch(Exception error){

                        }
                    }
                    else{
                        e.getMessage().reply("This Player is not Banned").queue();
                    }
                }
            }
        }
    }
}
