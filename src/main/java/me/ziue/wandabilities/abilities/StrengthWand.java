package me.ziue.wandabilities.abilities;

import me.ziue.wandabilities.WandAbilities;
import me.ziue.wandabilities.listeners.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrengthWand extends Ability {

    // Explanatory on how it's done is showed in @TeleportWand.java

    private final WandAbilities plugin = WandAbilities.getWandAbilities();

    public StrengthWand() {
        super("STRENGTH_WAND");
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (!isAbility(event.getItem())) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);

            Player player = event.getPlayer();


            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 6, 1));

            plugin.getAbilityManager().playerMessage(player, this.getAbility());
        }
    }
}
