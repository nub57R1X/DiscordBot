package com.kishore.discordbot.discordbot;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Commands extends ListenerAdapter {
    private String prefix = "%";
    private Connection conn;
    private Statement state;
    private ResultSet result;
    public Commands() {
        String ip = "jdbc:mysql://de1-db.falix.gg:3306/s317530_57r1xsmp";
        String username = "u317530_mPijXdqyEB";
        String pass = "Q.4SJV2VH4upW2Ke@.Ym99s5";
        try {
            conn = DriverManager.getConnection(ip,username,pass);
            state = conn.createStatement();
            result = null;
        }
        catch(Exception error) {

        }
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String args = e.getMessage().getContentRaw();
        if (args.equalsIgnoreCase(prefix + "lock")) {
            try {
                result = state.executeQuery("select * from discord where id = '" + e.getAuthor().getId() + "'");
                if (!(result.next())) {
                    User target = e.getAuthor();
                    PrivateChannel dm = target.openPrivateChannel().complete();
                    dm.sendMessage("You have to Link Your Discord Account Using Your Minecraft Account to Use this Command").queue();
                    dm.sendMessage("To Link, Log into The Minecraft Server and type The Following").queue();
                    dm.sendMessage("/dclink <id>").queue();
                    dm.sendMessage("For Example : /dclink 270904126974590976").queue();
                    dm.close();
                } else {
                    String ign = result.getString("ign");
                    result = state.executeQuery("select * from mclock where ign = '" + ign + "'");
                    if (!(result.next())) {
                        User target = e.getAuthor();
                        PrivateChannel dm = target.openPrivateChannel().complete();
                        dm.sendMessage("Your Account has Been Locked").queue();
                        int res = state.executeUpdate("insert into mclock values('" + ign + "')");
                        dm.close();
                    } else {
                        User target = e.getAuthor();
                        PrivateChannel dm = target.openPrivateChannel().complete();
                        dm.sendMessage("Your Account is Already Locked").queue();
                        dm.close();
                    }
                }
                result.close();
            } catch (Exception error) {

            }

        } else if (args.equalsIgnoreCase(prefix + "unlock")) {
            try {
                result = state.executeQuery("select * from discord where id = '" + e.getAuthor().getId() + "'");
                if (!(result.next())) {
                    User target = e.getAuthor();
                    PrivateChannel dm = target.openPrivateChannel().complete();
                    dm.sendMessage("You have to Link Your Discord Account Using Your Minecraft Account to Use this Command").queue();
                    dm.sendMessage("To Link, Log into The Minecraft Server and type The Following").queue();
                    dm.sendMessage("/dclink <id>").queue();
                    dm.sendMessage("For Example : /dclink 270904126974590976").queue();
                    dm.close();
                } else {
                    String ign = result.getString("ign");
                    result = state.executeQuery("select * from mclock where ign = '" + ign + "'");
                    if (!(result.next())) {
                        User target = e.getAuthor();
                        PrivateChannel dm = target.openPrivateChannel().complete();
                        dm.sendMessage("Your Account is Already Unlocked").queue();
                        dm.close();
                    } else {
                        User target = e.getAuthor();
                        PrivateChannel dm = target.openPrivateChannel().complete();
                        dm.sendMessage("Your Account has been Unlocked").queue();
                        int res = state.executeUpdate("delete from mclock where ign = '" + ign + "'");
                        dm.close();
                    }
                }
                result.close();
            } catch (Exception error) {

            }
        }
    }
}
