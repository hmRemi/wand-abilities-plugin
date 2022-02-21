package me.ziue.wandabilities.abilities;

import me.ziue.wandabilities.WandAbilities;
import me.ziue.wandabilities.listeners.Ability;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;

public class TeleportWand extends Ability {

    private final WandAbilities plugin = WandAbilities.getWandAbilities();

    public TeleportWand() {
        super("TELEPORT_WAND");
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (!isAbility(event.getItem())) return; // If item isn't an ability, return.

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) { // Only do action, if RIGHT CLICK in air or block.
            event.setCancelled(true);

            Player player = event.getPlayer();

            HashSet<Material> materialHashSet = new HashSet<Material>(); // HashSet method from BillyGalbreath.
            materialHashSet.add(Material.AIR);

            // Get the block the player is looking at, must be within 100 blocks
            Block block = player.getTargetBlock(materialHashSet, 100);

            // Teleport to where the player is looking.
            player.teleport(block.getLocation());

            // Send message to player. (config.yml)
            plugin.getAbilityManager().playerMessage(player, this.getAbility());
        }
    }
}
