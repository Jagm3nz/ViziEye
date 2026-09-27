package com.example.myplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;

/**
 * Copy-paste helper for plugin developers: let ViziEye ignore a player for a moment.
 * <p>
 * Use it when your plugin moves players in ways a normal player can't - jump pads, launchers,
 * grappling hooks, custom knockback, dashes, minigame mechanics - so ViziEye doesn't flag them.
 * <p>
 * ViziEye needs no API dependency: players with the bypass permission are never checked, so the
 * helper gives that permission for a number of ticks and Bukkit takes it away again automatically.
 * Add {@code softdepend: [ViziEye]} to your plugin.yml so ViziEye loads first.
 *
 * <pre>{@code
 * player.setVelocity(new Vector(0, 2.5, 0));      // your launch pad
 * ViziEyeHook.exempt(this, player, 60);           // 3 seconds - long enough to land again
 * }</pre>
 */
public final class ViziEyeHook {

    /** ViziEye's default bypass permission ("permissions.bypass" in its config.yml). */
    public static final String BYPASS = "vizieye.bypass";

    private ViziEyeHook() {
    }

    /** Whether ViziEye is installed and enabled on this server. */
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("ViziEye");
    }

    /**
     * ViziEye ignores the player for {@code ticks} ticks (20 ticks = 1 second).
     * Returns false when ViziEye is not installed - your plugin keeps working either way.
     */
    public static boolean exempt(Plugin plugin, Player player, int ticks) {
        return exempt(plugin, player, ticks, BYPASS);
    }

    /** Same as {@link #exempt(Plugin, Player, int)}, for servers that renamed the bypass permission. */
    public static boolean exempt(Plugin plugin, Player player, int ticks, String bypassPermission) {
        if (!isAvailable() || ticks <= 0) {
            return false;
        }
        PermissionAttachment attachment = player.addAttachment(plugin, bypassPermission, true, ticks);
        return attachment != null;
    }
}
