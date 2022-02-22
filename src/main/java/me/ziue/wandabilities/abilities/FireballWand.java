package me.ziue.wandabilities.abilities;

import me.ziue.wandabilities.WandAbilities;
import me.ziue.wandabilities.listeners.Ability;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.WeakHashMap;

public class FireballWand extends Ability {

    // Explanatory on how it's done is showed in @TeleportWand.java

    public static WeakHashMap<Integer, BukkitTask> comets = new WeakHashMap();

    private final WandAbilities plugin = WandAbilities.getWandAbilities();

    public FireballWand() {
        super("FIREBALL_WAND");
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (!isAbility(event.getItem())) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);

            Player player = event.getPlayer();

            Fireball fb = player.getWorld().spawn(player.getEyeLocation(), Fireball.class);
            fb.setShooter(player);
            fb.setBounce(false);
            fb.setYield(6.0f);
            fb.setVelocity(player.getLocation().getDirection().multiply(2));
            BukkitTask autoCancel = Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

                @Override
                public void run() {
                    if (comets.containsKey(fb.getEntityId())) {
                        comets.get(fb.getEntityId()).cancel();
                        comets.remove(fb.getEntityId());
                    }
                }
            }, 200L);
            comets.put(fb.getEntityId(), Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

                @Override
                public void run() {
                    if (!fb.isDead() && fb.isValid()) {
                        play(fb.getLocation(), FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.RED).withColor(Color.ORANGE).build());
                    } else {
                        if(comets != null) {
                            comets.remove(fb.getEntityId());
                            autoCancel.cancel();
                        }
                    }
                }
            }, 2L, 2L));

            plugin.getAbilityManager().playerMessage(player, this.getAbility());
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        int id = event.getEntity().getEntityId();
        if (comets.containsKey(id)) {
            comets.get(id).cancel();
            comets.remove(id);
        }
    }

    public void play(Location location, FireworkEffect effect) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fMeta = firework.getFireworkMeta();
        fMeta.addEffect(effect);
        firework.setFireworkMeta(fMeta);
        new BukkitRunnable(){

            public void run() {
                firework.detonate();
            }
        }.runTaskLater(plugin, 1L);
    }
}
