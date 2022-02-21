package me.ziue.wandabilities.abilities;

import me.ziue.wandabilities.WandAbilities;
import me.ziue.wandabilities.listeners.Ability;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class LightningWand extends Ability {

    // Explanatory on how it's done is showed in @TeleportWand.java

    private final WandAbilities plugin = WandAbilities.getWandAbilities();

    public LightningWand() {
        super("LIGHTING_WAND");
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (!isAbility(event.getItem())) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);

            Player player = event.getPlayer();

            HashSet<Material> materialHashSet = new HashSet<Material>();

            int range = 100;
            materialHashSet.add(Material.AIR);
            Block block = player.getTargetBlock(materialHashSet, range); // Get the block the player is looking at, must be within 100 blocks

            player.getWorld().strikeLightning(block.getLocation()); // Summon lightning where the player is looking.

            plugin.getAbilityManager().playerMessage(player, this.getAbility());
        }
    }
}
