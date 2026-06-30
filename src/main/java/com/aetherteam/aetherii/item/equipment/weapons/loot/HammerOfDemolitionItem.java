package com.aetherteam.aetherii.item.equipment.weapons.loot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.projectile.DemolitionProjectile;
import com.aetherteam.aetherii.item.SpecialAttackStrengthScale;
import com.aetherteam.aetherii.item.equipment.AetherIIToolMaterials;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class HammerOfDemolitionItem extends TieredHammerItem implements SpecialAttackStrengthScale {
    public HammerOfDemolitionItem(Properties properties) {
        super(AetherIIToolMaterials.HAMMER_OF_DEMOLITION, 3, -2.4F, AetherIIStats.HAMMER_OF_DEMOLITION, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            if (level instanceof ServerLevel serverlevel) {
                if (!player.getAbilities().instabuild) {
                    player.getCooldowns().addCooldown(heldStack.getItem(), 100);
                    heldStack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(hand));
                }
                DemolitionProjectile projectile = new DemolitionProjectile(player, serverlevel);
                projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 1.0F);
                serverlevel.addFreshEntity(projectile);
            }
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.ITEM_HAMMER_OF_DEMOLITION_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F), false);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide());
        }
        return super.use(level, player, hand);
    }

    public static void disableAttacks(AttackEntityEvent event) {
        Player entity = event.getEntity();
        ItemStack stack = entity.getMainHandItem();
        if (entity.getCooldowns().getCooldownPercent(stack.getItem(), 0.0F) != 0) {
            event.setCanceled(true);
        }
    }

    @Override
    public float getAttackStrengthScale(Level level, Player player, ItemStack stack, float adjustTicks, int attackStrengthTicker) {
        if (player.getCooldowns().isOnCooldown(stack.getItem())) {
            float percent = player.getCooldowns().getCooldownPercent(stack.getItem(), 1.0F);
            return 1.0F - percent;
        }
        return SpecialAttackStrengthScale.super.getAttackStrengthScale(level, player, stack, adjustTicks, attackStrengthTicker);
    }
}
