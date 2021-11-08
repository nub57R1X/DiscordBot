package com.kishore.discordbot.discordbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;

public class Login implements Listener {
    private DiscordBot plugin;
    private Connection conn;
    private Statement state;
    private ResultSet result;
    private JDA jda;
    public Login(DiscordBot plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
        String ip = "jdbc:mysql://de1-db.falix.gg:3306/s317530_57r1xsmp";
        String username = "u317530_mPijXdqyEB";
        String pass = "Q.4SJV2VH4upW2Ke@.Ym99s5";
        System.out.println("Boi boi bot boi");
        try {
            conn = DriverManager.getConnection(ip,username,pass);
            state = conn.createStatement();
            result = null;
        }
        catch(Exception error) {

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        try{
            result = state.executeQuery("select ip from register where ign = '" + e.getPlayer().getName() + "'");
            if(!(result.next())){

            }
            else{
                String ip = result.getString("ip");
                if(ip.equals(e.getPlayer().getAddress().getHostString())){

                }
                else{
                    result = state.executeQuery("select * from discord where ign = '" + e.getPlayer().getName() + "'");
                    if(!(result.next())){

                    }
                    else{
                        System.out.println("buri burio saimon");
                        String id = result.getString("id");
                        User user = jda.retrieveUserById(id).complete();
                        PrivateChannel dm = user.openPrivateChannel().complete();
                        dm.sendMessage("<@" + id + ">\nYour Account " + e.getPlayer().getName() + " Joined The Server with a Different IP than Last Time").queue();
                        dm.sendMessage("If This isn't You Please Lock Your Account to Safe Guard it").queue();
                    }
                }
            }
        }
        catch (Exception error){

        }
    }
}
