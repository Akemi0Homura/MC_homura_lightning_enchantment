package com.homura.lightning.core;


import net.minecraft.world.damagesource.DamageSource;
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
public class Attack {
    /**
     * 闪电是视觉闪电，这样可以避免生物死亡后，掉落物被摧毁<br>
     * 给目标进行点燃1秒，这样可以保证获得熟食，也能避免战斗时候，火焰传染到自己身上<br>
     * 伤害是截取目标的伤害，再加一起，这样可以避免游戏的受伤冷却问题，保证能造成伤害。
     */
    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();  // 直接获取，无需 instanceof 判断
        DamageSource source = event.getSource();

        // 检查攻击者是否为活的生物（包括玩家、狼、铁傀儡等）
        LivingEntity attacker = (LivingEntity) source.getEntity();
        if (attacker == null) return;

        // 检查攻击者主手是否有附魔（生物装备武器时生效）
        ItemStack stack = attacker.getMainHandItem();
        // 替换为你的实际附魔注册名（之前有拼写错误 LIGHHTNING）
        if (stack.getEnchantmentLevel(Enroll.LIGHHTNING.get()) <= 0) return;

        Level level = target.level();

        // 生成纯视觉闪电
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        lightning.moveTo(target.getX(), target.getY() + (target.getBbHeight() / 2.0), target.getZ());
        lightning.setVisualOnly(true);
        level.addFreshEntity(lightning);

        // 直接增加最终伤害：原伤害（已计算护甲等） + 闪电额外伤害
        event.setAmount(event.getAmount() + 3.0F);

        // 点燃目标
        target.setSecondsOnFire(1);
    }
}
