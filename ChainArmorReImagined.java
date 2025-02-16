package me.leventezg.chainarmorreimagined;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public final class ChainArmorReImagined extends JavaPlugin implements Listener {
    public static ChainArmorReImagined getInstance() {
        return getPlugin(ChainArmorReImagined.class);
    }
    @Override
    public void onEnable() {
        //Crafting recept : sisak
        ItemStack chainhelmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
        ShapedRecipe helmet = new ShapedRecipe(new NamespacedKey(ChainArmorReImagined.getInstance(),"chainhelmet"), chainhelmet);
        helmet.shape("***","*.*","...");
        helmet.setIngredient('*', Material.CHAIN);
        helmet.setIngredient('.', Material.AIR);
        Bukkit.getServer().addRecipe(helmet);

        //Crafting recept : mellvert

        ItemStack chainchestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
        ShapedRecipe chestplate = new ShapedRecipe(new NamespacedKey(ChainArmorReImagined.getInstance(),"chainchestplate"), chainchestplate);
        chestplate.shape("*.*","***","***");
        chestplate.setIngredient('*', Material.CHAIN);
        chestplate.setIngredient('.', Material.AIR);
        getServer().addRecipe(chestplate);

        //Crafting recept : labszarvedo

        ItemStack chainleggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
        ShapedRecipe leggings = new ShapedRecipe(new NamespacedKey(ChainArmorReImagined.getInstance(),"chainleggings"), chainleggings);
        leggings.shape("***","*.*","*.*");
        leggings.setIngredient('*', Material.CHAIN);
        leggings.setIngredient('.', Material.AIR);
        getServer().addRecipe(leggings);

        //Crafting recept : csizma

        ItemStack chainboots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
        ShapedRecipe boots = new ShapedRecipe(new NamespacedKey(ChainArmorReImagined.getInstance(),"chainboots"), chainboots);
        boots.shape("*.*","***");
        boots.setIngredient('*', Material.CHAIN);
        boots.setIngredient('.', Material.AIR);
        getServer().addRecipe(boots);

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getServer().getLogger().warning("Plugin has been loaded perfectly :D.");
    }

    @Override
    public void onDisable() {
        // Szex?
        getServer().getLogger().warning("Plugin has been shut down sadly :<.");
    }

    @EventHandler
    public void ProjectileHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager().getType().equals(EntityType.ARROW)) {
            Player player = (Player) e.getEntity();
            Arrow arrow = (Arrow) e.getDamager();
            try {
                LivingEntity shooter = (LivingEntity) arrow.getShooter();
                if (shooter == player) {
                    return;
                }
                //Deklarálja a szett változókat.
                ItemStack helmet = player.getInventory().getHelmet();
                ItemStack chestplate = player.getInventory().getChestplate();
                ItemStack leggings = player.getInventory().getLeggings();
                ItemStack boots = player.getInventory().getBoots();
                if (helmet == null || chestplate == null || leggings == null || boots == null) {
                    return;
                }
                if (helmet.getType() == Material.CHAINMAIL_HELMET && chestplate.getType() == Material.CHAINMAIL_CHESTPLATE && leggings.getType() == Material.CHAINMAIL_LEGGINGS && boots.getType() == Material.CHAINMAIL_BOOTS) {
                    Vector direction = shooter.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(-1);
                    e.getDamager().setVelocity(direction.multiply(1.5));
                    double enchants = 1;
                    //Megnézi egyes szettek thorns enchant szintjét és elosztja 8-val majd hozzáadja a változóhoz.
                    enchants += ((double) helmet.getEnchantmentLevel(Enchantment.THORNS) / 8);
                    enchants += ((double) chestplate.getEnchantmentLevel(Enchantment.THORNS) / 8);
                    enchants += ((double) leggings.getEnchantmentLevel(Enchantment.THORNS) / 8);
                    enchants += ((double) boots.getEnchantmentLevel(Enchantment.THORNS) / 8);
                    shooter.damage(e.getDamage()*enchants);
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND,1,4);
                }
            } catch(ClassCastException ignored){}
            e.setCancelled(true);
        }
    }

}
