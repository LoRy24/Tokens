package net.thirdshift.tokens.commands.redeem.redeemcommands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import net.thirdshift.tokens.messages.messageData.PlayerSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionsRedeemModule extends RedeemModule {
    public FactionsRedeemModule(){
        super();
    }

    @Override
    public String getCommand() {
        return "factions";
    }

    @Override
    public String[] getCommandAliases() {
        return new String[]{"faction", "f"};
    }

    @Override
    public void redeem(Player player, int toRedeem) {
        List<Object> objects = new ArrayList<>();
        objects.add(toRedeem);
        objects.add(new PlayerSender(player));

        if(tokensHandler.hasTokens(player, toRedeem)){
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
            if(fPlayer != null ){
                fPlayer.setPowerBoost(fPlayer.getPowerBoost() + (double)(toRedeem * plugin.tokenToFactionPower));
                objects.add(fPlayer);
                tokensHandler.setTokens(player, tokensHandler.getTokens(player) - toRedeem);
                player.sendMessage(plugin.messageHandler.useMessage("redeem.factions", objects));
            }else{
                plugin.getLogger().severe("Couldn't get FPlayer for "+player.getName());
            }
        }else{
            player.sendMessage(plugin.messageHandler.useMessage("redeem.errors.not-enough", objects));
        }
    }
}
