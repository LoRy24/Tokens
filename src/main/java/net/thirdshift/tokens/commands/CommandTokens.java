package net.thirdshift.tokens.commands;

import net.thirdshift.tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandTokens implements CommandExecutor {

    private Tokens plugin;

    public CommandTokens(Tokens instance){this.plugin=instance;}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length==0){
            if(commandSender instanceof Player){
                commandSender.sendMessage("You have "+ChatColor.GOLD+""+plugin.handler.getTokens((Player) commandSender));
                return true;
            }else return false;
        }
        if(args[0].equalsIgnoreCase("add")) {
            if (commandSender instanceof Player) {
                if(commandSender.hasPermission("tokens.add")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    int num = Integer.parseInt(args[2]);
                    plugin.handler.addTokens(target, num);
                }
            }else{
                Player target = Bukkit.getPlayer(args[1]);
                int num = Integer.parseInt(args[2]);
                plugin.handler.addTokens(target, num);
            }
            return true;
        }else if(args[0].equalsIgnoreCase("give")) {
            if(commandSender instanceof Player){
                if(args.length==3){
                    int num = Integer.parseInt(args[2]);
                    if(plugin.handler.getTokens((Player) commandSender) >= num) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            if(target.equals(commandSender)){
                                commandSender.sendMessage(ChatColor.RED+"You can't give tokens to yourself.");
                                return true;
                            }
                            plugin.handler.removeTokens((Player) commandSender, num);
                            plugin.handler.addTokens(target, num);
                            commandSender.sendMessage("You sent " + ChatColor.GOLD + "" + args[2] + "" + ChatColor.WHITE + " token(s) to " + ChatColor.GREEN + "" + target.getName());
                            target.sendMessage("You received " + ChatColor.GOLD + "" + args[2] + "" + ChatColor.WHITE + " token(s) from " + ChatColor.GREEN + "" + commandSender.getName());
                        } else {
                            commandSender.sendMessage(ChatColor.RED+"Couldn't find player " + args[1] + ". did you spell their username correct?");
                            commandSender.sendMessage(ChatColor.RED+"Try /tokens give <UserName> <Amount>");
                        }
                    }else{
                        commandSender.sendMessage(ChatColor.GRAY+"You don't have "+ChatColor.GOLD+""+num+""+ChatColor.GRAY+" tokens.");
                    }
                }else{
                    commandSender.sendMessage(ChatColor.RED+"Invalid command use! Your arguments were "+ Arrays.toString(args));
                    commandSender.sendMessage(ChatColor.RED+"Try /tokens give <UserName> <Amount>");
                }
                return true;
            }else{
                return false;
            }
        }else if(args[0].equalsIgnoreCase("reload")){
            if( !(commandSender instanceof Player)){
                plugin.reloadConfig();// Console ran the command
                plugin.getLogger().info("Reloading the config file");
                return true;
            }else{
                if(commandSender.hasPermission("tokens.reload")){
                    plugin.reloadConfig();// Player with permission ran the command
                    commandSender.sendMessage(ChatColor.GRAY+"Reloaded the config file");
                    return true;
                }else{
                    return false;// Player without permission ran the command
                }
            }
        }else if(args.length==1) {
            Player target = Bukkit.getPlayer(args[0]);
            if(commandSender.hasPermission("tokens.others")) {
                if (target != null) {
                    commandSender.sendMessage(args[0] + " has " + plugin.handler.getTokens(target) + " tokens.");
                } else {
                    commandSender.sendMessage("Couldn't find a player with the name " + args[0]);
                }
                return true;
            }else{
                return false;// Player without tokens.others perms ran the command
            }
        }else{
           return false;// Server used /tokens command
            //TODO: let server peep others token count
        }
    }
}
