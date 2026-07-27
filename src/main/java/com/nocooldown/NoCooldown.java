package com.nocooldown;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.Set;

public class NoCooldown extends JavaPlugin implements Listener {

    private static final Set<Material> SWORDS = EnumSet.of(
            Material.WOODEN_SWORD,
            Material.STONE_SWORD,
            Material.IRON_SWORD,
            Material.GOLDEN_SWORD,
            Material.DIAMOND_SWORD,
            Material.NETHERITE_SWORD
    );

    private static final double NO_COOLDOWN_SPEED = 1024.0;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        // Apply to all currently online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            applyNoCooldown(player);
        }

        // Reapply every tick for all online players to ensure it sticks
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                applyNoCooldown(player);
            }
        }, 1L, 1L);

        getLogger().info("NoCooldown enabled! Sword cooldowns removed for all players.");
    }

    @Override
    public void onDisable() {
        // Reset attack speed to defaults when plugin disables
        for (Player player : Bukkit.getOnlinePlayers()) {
            resetCooldown(player);
        }
        getLogger().info("NoCooldown disabled.");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        applyNoCooldown(event.getPlayer());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            // Reapply immediately after attack
            Bukkit.getScheduler().runTaskLater(this, () -> applyNoCooldown(player), 1L);
        }
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Bukkit.getScheduler().runTaskLater(this, () -> applyNoCooldown(event.getPlayer()), 1L);
    }

    private void applyNoCooldown(Player player) {
        // 1. Set ATTACK_SPEED attribute to a very high value
        //    This makes the attack strength meter recharge instantly,
        //    so every attack deals full damage with zero cooldown.
        AttributeInstance attackSpeed = player.getAttribute(Attribute.ATTACK_SPEED);
        if (attackSpeed != null) {
            attackSpeed.setBaseValue(NO_COOLDOWN_SPEED);
        }

        // 2. Clear any item cooldowns (ender pearls, shields, etc.)
        for (Material mat : SWORDS) {
            player.setCooldown(mat, 0);
        }

        // 3. Also clear all non-sword item cooldowns
        for (Material mat : Material.values()) {
            if (mat.isItem() && player.hasCooldown(mat)) {
                player.setCooldown(mat, 0);
            }
        }
    }

    private void resetCooldown(Player player) {
        AttributeInstance attackSpeed = player.getAttribute(Attribute.ATTACK_SPEED);
        if (attackSpeed != null) {
            attackSpeed.setBaseValue(attackSpeed.getDefaultValue());
        }
    }
}
