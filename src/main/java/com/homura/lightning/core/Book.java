package com.homura.lightning.core;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * 附魔书相关数据<br>
 * @author Akemi0Homura
 */
public class Book extends Enchantment {
    protected Book() {
        super(Rarity.UNCOMMON,EnchantmentCategory.WEAPON,new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    //可在附魔台上出现
    @Override
    public boolean isDiscoverable() {
        return true;
    }

    //允许村民交易出现
    @Override
    public boolean isTradeable() {
        return true;
    }

    //最便宜：15-20级
    //中间：20-25级
    //最贵：25-30级

    //附魔台最低等级
    @Override
    public int getMinCost(int level) {
        return switch (level) {
            case 1 -> 15;
            case 2 -> 30;
            case 3 -> 45;
            case 4 -> 60;
            case 5 -> 75;
            default -> 90;
        };
    }

    //附魔台最高等级
    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 15;
    }

    //最高等级
    @Override
    public int getMaxLevel() {
        return 5;
    }
}

