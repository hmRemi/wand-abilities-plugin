package me.ziue.wandabilities;

import lombok.Getter;
import lombok.Setter;
import me.ziue.api.command.CommandManager;
import me.ziue.wandabilities.commands.AbilityCommand;
import me.ziue.wandabilities.managers.AbilityManager;
import me.ziue.wandabilities.utils.chat.CC;
import me.ziue.wandabilities.utils.file.type.BasicConfigurationFile;
import org.bukkit.plugin.java.JavaPlugin;

public class WandAbilities extends JavaPlugin {

    @Getter @Setter public AbilityManager abilityManager;
    @Getter @Setter private BasicConfigurationFile abilityConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Initialize configs
        initializeConfigs();

        // Initialize managers
        initializeManagers();

        // Initialize commands
        initializeCommands();

        CC.loadPlugin();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initializeConfigs() {
        this.abilityConfig = new BasicConfigurationFile(this, "config");
    }

    public void initializeManagers() {
        abilityManager = new AbilityManager();
        abilityManager.load();
    }

    public void initializeCommands() {
        new CommandManager(this);
        new AbilityCommand();
    }

    public static WandAbilities getWandAbilities() {
        return getPlugin(WandAbilities.class);
    }
}
