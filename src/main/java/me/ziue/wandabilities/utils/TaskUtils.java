package me.ziue.wandabilities.utils;

import me.ziue.wandabilities.WandAbilities;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtils {

    /*
        Utils from open src project.
     */

    public static void run(Runnable runnable) {
        WandAbilities.getWandAbilities().getServer().getScheduler().runTask(WandAbilities.getWandAbilities(), runnable);
    }

    public static void runTimer(Runnable runnable, long delay, long timer) {
        WandAbilities.getWandAbilities().getServer().getScheduler().runTaskTimer(WandAbilities.getWandAbilities(), runnable, delay, timer);
    }

    public static void runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(WandAbilities.getWandAbilities(), delay, timer);
    }

    public static void runTimerAsync(Runnable runnable, long delay, long timer) {
        WandAbilities.getWandAbilities().getServer().getScheduler().runTaskTimerAsynchronously(WandAbilities.getWandAbilities(), runnable, delay, timer);
    }

    public static void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimerAsynchronously(WandAbilities.getWandAbilities(), delay, timer);
    }

    public static void runLater(Runnable runnable, long delay) {
        WandAbilities.getWandAbilities().getServer().getScheduler().runTaskLater(WandAbilities.getWandAbilities(), runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        try {
            WandAbilities.getWandAbilities().getServer().getScheduler().runTaskLaterAsynchronously(WandAbilities.getWandAbilities(), runnable, delay);
        }
        catch (IllegalStateException e) {
            WandAbilities.getWandAbilities().getServer().getScheduler().runTaskLater(WandAbilities.getWandAbilities(), runnable, delay);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTaskTimerAsynchronously(Runnable runnable, int delay) {
        try {
            WandAbilities.getWandAbilities().getServer().getScheduler().runTaskTimerAsynchronously(WandAbilities.getWandAbilities(), runnable, 20L * delay, 20L * delay);
        }
        catch (IllegalStateException e) {
            WandAbilities.getWandAbilities().getServer().getScheduler().runTaskTimer(WandAbilities.getWandAbilities(), runnable, 20L * delay, 20L * delay);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runAsync(Runnable runnable) {
        try {
            WandAbilities.getWandAbilities().getServer().getScheduler().runTaskAsynchronously(WandAbilities.getWandAbilities(), runnable);
        }
        catch (IllegalStateException e) {
            WandAbilities.getWandAbilities().getServer().getScheduler().runTask(WandAbilities.getWandAbilities(), runnable);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}