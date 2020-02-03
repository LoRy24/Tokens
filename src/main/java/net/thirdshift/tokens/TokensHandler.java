package net.thirdshift.tokens;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TokensHandler {
    private Tokens plugin;
    public TokensHandler(Tokens instance){this.plugin=instance;}

    public void addTokens(Player player, int tokensIn){
        if(plugin.mysqlEnabled){
            plugin.getMySQL().addTokens(player, tokensIn);
        }//TODO: add SQLLite
        player.sendMessage(ChatColor.GOLD+""+tokensIn+""+ChatColor.RESET+" Tokens were added to your balance.");
    }

    public int getTokens(Player player){
        if(plugin.mysqlEnabled){
            return plugin.getMySQL().getTokens(player);
        }//TODO: add SQLLite
        return 10;
    }

    public void setTokens(Player player, int tokensIn){
        if(plugin.mysqlEnabled){
            plugin.getMySQL().setTokens(player, tokensIn);
        }//TODO: add SQLLite
    }

    public void removeTokens(Player player, int tokensIn){
        if(plugin.mysqlEnabled){
            plugin.getMySQL().removeTokens(player, tokensIn);
        }//TODO: add SQLLite
    }

}
