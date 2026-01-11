package com.homura.lightning.core;


import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 逻辑模块<br>
 * 附魔后，攻击敌人会释放闪电<br>
 * @author Akemi0Homura
 */
@Mod.EventBusSubscriber(modid = "homura_lightning_enchantment")
public class Effect {
    /**
     * 闪电是视觉闪电，这样可以避免生物死亡后，掉落物被摧毁<br>
     * 给目标进行点燃1秒，这样可以保证获得熟食，也能避免战斗时候，火焰传染到自己身上<br>
     * 伤害是截取目标的伤害，再加一起，这样可以避免游戏的受伤冷却问题，保证能造成伤害。
     */
    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        //只在服务器执行
        if (event.getEntity().level().isClientSide) return;

        DamageSource source = event.getSource();
        //伤害来源
        Entity src = source.getEntity();
        if (!(src instanceof LivingEntity attacker)) return;

        //伤害来源的主兽物品
        ItemStack weapon = attacker.getMainHandItem();
        //获取附魔等级
        int level = weapon.getEnchantmentLevel(Enroll.LIGHTNING.get());
        //检查有没有附魔
        if (level <= 0) return;

        //获取触发目标
        LivingEntity target = event.getEntity();
        Level levelWorld = target.level();

        //生成闪电实体
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(levelWorld);
        if (lightning != null) {
            //生成位置
            lightning.moveTo(target.position());
            //视觉闪电
            lightning.setVisualOnly(true);
            //服务器执行
            levelWorld.addFreshEntity(lightning);
        }
        //附带伤害，受等级影响
        event.setAmount(event.getAmount() + 1.0F * level);
        //点燃目标
        target.setSecondsOnFire(1);
    }
}

