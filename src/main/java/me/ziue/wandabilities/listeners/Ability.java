package me.ziue.wandabilities.listeners;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import me.ziue.wandabilities.WandAbilities;
import lombok.Getter;
import lombok.Setter;
import me.ziue.wandabilities.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Ability implements Listener {

    @Getter
    private static final List<Ability> abilities = Lists.newArrayList();

    private final String ability;
    private Table<String, UUID, Long> cooldown = HashBasedTable.create();

    public Ability(String ability) {
        this.ability = ability;
        // Add abilities
        abilities.add(this);
    }

    public void register() {
        // Register events for each ability.
        Bukkit.getPluginManager().registerEvents(this, WandAbilities.getWandAbilities());
    }

    // Check if item is ability, will respond in true or false
    public boolean isAbility(ItemStack itemStack) {
        return (itemStack != null)
                && (itemStack.getType() != Material.AIR)
                && (itemStack.hasItemMeta())
                && (itemStack.getItemMeta().getDisplayName() != null)
                && (itemStack.getItemMeta().getLore() != null)
                && itemStack.getItemMeta().getDisplayName().equals(CC.translate(
                WandAbilities.getWandAbilities().getAbilityManager().getDisplayName(ability)))
                && itemStack.getItemMeta().getLore().equals(CC.translate(
                WandAbilities.getWandAbilities().getAbilityManager().getDescription(ability)));
    }

    // Get name of ability
    public String getName() {
        return WandAbilities.getWandAbilities().getAbilityManager().getDisplayName(this.getAbility());
    }
}
