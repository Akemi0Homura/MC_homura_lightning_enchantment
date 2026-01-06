package com.homura.lightning.core;

import com.homura.lightning.EnchantmentsMod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 注册附魔<br>
 * forge自动将附魔加进创造模式的面板
 * @author Akemi0Homura
 */
public class Enroll {
    public static final DeferredRegister<net.minecraft.world.item.enchantment.Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, EnchantmentsMod.MODID);

    public static RegistryObject<net.minecraft.world.item.enchantment.Enchantment> LIGHHTNING=
            ENCHANTMENTS.register("lightning",Book::new);
}
