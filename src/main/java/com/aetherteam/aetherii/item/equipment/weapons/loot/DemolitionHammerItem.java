package com.aetherteam.aetherii.item.equipment.weapons.loot;

import com.aetherteam.aetherii.AetherIIStats;
import com.aetherteam.aetherii.entity.projectile.DetonationProjectile;
import com.aetherteam.aetherii.item.equipment.AetherIIItemTiers;
import com.aetherteam.aetherii.item.equipment.weapons.TieredHammerItem;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class DemolitionHammerItem extends TieredHammerItem implements ProjectileItem {
    public DemolitionHammerItem(Properties properties) {
        super(applyWeaponProperties(properties, AetherIIItemTiers.DEMOLITION, 3, -2.4F, AetherIIStats.DEMOLITION_HAMMER));
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
//        level.playLocalSound(player.getX(), player.getY(), player.getZ(), (SoundEvent)AetherSoundEvents.ITEM_HAMMER_OF_KINGBDOGZ_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F), false);
        if (level instanceof ServerLevel serverlevel) {
            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(heldStack, 100);
                heldStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            }
            Projectile.spawnProjectileFromRotation((l, e, s) -> new DetonationProjectile(e, l), serverlevel, heldStack, player, 0.0F, 1.0F, 1.0F);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new DetonationProjectile(pos.x(), pos.y(), pos.z(), level);
    }

    public ProjectileItem.DispenseConfig createDispenseConfig() {
        return DispenseConfig.builder().uncertainty(1.0F).build();
    }
}
