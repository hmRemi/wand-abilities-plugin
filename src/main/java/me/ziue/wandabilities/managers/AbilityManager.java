package me.ziue.wandabilities.managers;

import lombok.Getter;
import me.ziue.api.item.ItemBuilder;
import me.ziue.wandabilities.WandAbilities;
import me.ziue.wandabilities.abilities.LightningWand;
import me.ziue.wandabilities.abilities.StrengthWand;
import me.ziue.wandabilities.abilities.TeleportWand;
import me.ziue.wandabilities.listeners.Ability;
import me.ziue.wandabilities.utils.chat.CC;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

@Getter
public class AbilityManager {

    private LightningWand lightningWand;
    private StrengthWand strengthWand;
    private TeleportWand teleportWand;

    public AbilityManager() {
        this.register();
    }

    private void register() {
        // Register wands/abilities
        this.lightningWand = new LightningWand();
        this.strengthWand = new StrengthWand();
        this.teleportWand = new TeleportWand();
    }

    public void load() {
        // Load abilities, one after another
        Ability.getAbilities().forEach(Ability::register);
    }

    public ItemStack getAbility(String ability, int amount) {
        return new ItemBuilder(getMaterial(ability))
                .amount(amount)
                .data(getData(ability))
                .name(getDisplayName(ability))
                .lore(getDescription(ability))
                .build();
    }

    // These are getters, used for customizing messages, items, itemData, ect.

    public String getDisplayName(String ability) {
        return WandAbilities.getWandAbilities().getAbilityConfig().getString( ability + ".ICON.DISPLAYNAME");
    }

    public List<String> getDescription(String ability) {
        return WandAbilities.getWandAbilities().getAbilityConfig().getStringList( ability + ".ICON.DESCRIPTION");
    }

    public Material getMaterial(String ability) {
        return Material.valueOf(WandAbilities.getWandAbilities().getAbilityConfig().getString(ability + ".ICON.MATERIAL"));
    }

    public int getData(String ability) {
        return WandAbilities.getWandAbilities().getAbilityConfig().getInteger(ability + ".ICON.DATA");
    }

    // Get all abilities
    public Set<String> getAbilities() {
        return WandAbilities.getWandAbilities().getAbilityConfig().getConfiguration().getKeys(false);
    }

    public void giveAbility(CommandSender sender, Player player, String key, String abilityName, int amount) {
        player.getInventory().addItem(this.getAbility(key, amount));
        if (player == sender) {
            CC.message(player, WandAbilities.getWandAbilities().getAbilityConfig().getString("RECEIVED_ABILITY")
                    .replace("%ABILITY%", abilityName)
                    .replace("%AMOUNT%", String.valueOf(amount)));
        }
        else {
            CC.message(player, WandAbilities.getWandAbilities().getAbilityConfig().getString("RECEIVED_ABILITY")
                    .replace("%ABILITY%", abilityName)
                    .replace("%AMOUNT%", String.valueOf(amount)));
            CC.sender(sender, WandAbilities.getWandAbilities().getAbilityConfig().getString("GIVE_ABILITY")
                    .replace("%ABILITY%", abilityName)
                    .replace("%AMOUNT%", String.valueOf(amount))
                    .replace("%PLAYER%", player.getName()));
        }
    }

    // This is the message we send to the player that has used an ability.
    public void playerMessage(Player player, String ability) {
        String displayName = getDisplayName(ability);

        WandAbilities.getWandAbilities().getAbilityConfig().getStringList(ability + ".MESSAGE.PLAYER").forEach(
                message -> CC.message(player, message
                        .replace("%ABILITY%", displayName)));
    }

    // This is the message we send to the target, if ability has anything to do with combat. Example: A freeze wand.
    public void targetMessage(Player target, Player player, String ability) {
        String displayName = getDisplayName(ability);

        WandAbilities.getWandAbilities().getAbilityConfig().getStringList(ability + ".MESSAGE.TARGET").forEach(
                message -> CC.message(target, message
                        .replace("%ABILITY%", displayName)
                        .replace("%PLAYER%", player.getName())));
    }

}
