package me.ziue.wandabilities.commands;

import lombok.Getter;
import lombok.Setter;
import me.ziue.api.command.BaseCommand;
import me.ziue.api.command.Command;
import me.ziue.api.command.CommandArgs;
import me.ziue.wandabilities.WandAbilities;
import me.ziue.wandabilities.listeners.Ability;
import me.ziue.wandabilities.utils.JavaUtils;
import me.ziue.wandabilities.utils.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
@Setter
public class AbilityCommand extends BaseCommand {

    private WandAbilities plugin = WandAbilities.getWandAbilities();

    @Command(name = "ability", permission = "rankedpractice.wandabilities", aliases = "wand")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        // If args is less than 1, prompt usage method, from there we return the command.
        if (args.length < 1) {
            this.getUsage(player, "ability");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "give":

                // If command arguments are invalid, return.
                if (args.length < 4) {
                    CC.sender(player, "&cUsage: /" + "ability" + " give <player> <ability|all> <amount>");
                    return;
                }

                // Register the class "Player" to variable "target", target's name will be received from command usage (args[1]).
                Player target = Bukkit.getPlayer(args[1]);

                // If the target is invalid or not found, return.
                if (target == null) {
                    CC.sender(player, "&cPlayer '" + args[1] + "' not found.");
                    return;
                }

                Integer amount = JavaUtils.tryParseInt(args[3]);

                // If amount is null, return.
                if (amount == null) {
                    CC.sender(player, "&cAmount must be a number.");
                    return;
                }

                // If provided amount is less than 0, return.
                if (amount <= 0) {
                    CC.sender(player, "&cAmount must be positive.");
                    return;
                }

                plugin.getAbilityManager().getAbilities().forEach(ability -> {
                    String displayName = WandAbilities.getWandAbilities().getAbilityConfig().getString(ability + ".ICON.DISPLAYNAME");
                    // If ability exists, give player ability that was specified.
                    if (args[2].equalsIgnoreCase(ability)) {
                        plugin.getAbilityManager().giveAbility(player, target, ability, displayName, amount);
                        return;
                    }
                    // If ability is equaled to "ALL", give all abilities.
                    if (args[2].equals("all")) {
                        plugin.getAbilityManager().giveAbility(player, target, ability, displayName, amount);
                    }
                });
                break;
            case "list": // Display all abilities
                CC.sender(player, "&7&m-----------------------------");
                CC.sender(player, "&3&lWand Abilities List &7(" + Ability.getAbilities().size() + ")");
                CC.sender(player, "");
                Ability.getAbilities().forEach(ability -> CC.sender(player, " &7- &b" + ability.getAbility()));
                CC.sender(player, "&7&m-----------------------------");
                break;
        }
    }

    // Prompt usage of command.
    private void getUsage(CommandSender sender, String label) {
        CC.sender(sender, "&7&m-----------------------------");
        CC.sender(sender, "&3&lWand Ability Help");
        CC.sender(sender, "");
        CC.sender(sender, "&b/" + label + " give <player> <ability|all> <amount>");
        CC.sender(sender, "&b/" + label + " list");
        CC.sender(sender, "&7&m-----------------------------");
    }
}
