package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.item.AetherIIItems;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Predicate;

public class AetherIICrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> BOLT_ONLY = stack -> stack.is(AetherIIItems.SCATTERGLASS_BOLT);
    private final Tier tier;
    private final int chargeTime;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public AetherIICrossbowItem(Tier tier, Properties properties) {
        this(tier, 25, properties);
    }

    public AetherIICrossbowItem(Tier tier, int chargeTime, Properties properties) {
        super(properties.defaultDurability(tier.getUses()));
        this.tier = tier;
        this.chargeTime = chargeTime;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (isCharged(itemStack)) {
            performShooting(level, player, hand, itemStack, this.getShootingPower(itemStack), 1.0F);
            CrossbowItem.setCharged(itemStack, false);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            if (!CrossbowItem.isCharged(itemStack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    public static void performShooting(Level pLevel, LivingEntity pShooter, InteractionHand pUsedHand, ItemStack pCrossbowStack, float pVelocity, float pInaccuracy) {
        if (pShooter instanceof Player player && EventHooks.onArrowLoose(pCrossbowStack, pShooter.level(), player, 1, true) < 0) return;
        List<ItemStack> list = AetherIICrossbowItem.getChargedProjectiles(pCrossbowStack);
        float[] afloat = AetherIICrossbowItem.getShotPitches(pShooter.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = pShooter instanceof Player && ((Player)pShooter).getAbilities().instabuild;
            if (!itemstack.isEmpty()) {
                if (i == 0) {
                    AetherIICrossbowItem.shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, 0.0F);
                } else if (i == 1) {
                    AetherIICrossbowItem.shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, -10.0F);
                } else if (i == 2) {
                    AetherIICrossbowItem.shootProjectile(pLevel, pShooter, pUsedHand, pCrossbowStack, itemstack, afloat[i], flag, pVelocity, pInaccuracy, 10.0F);
                }
            }
        }

        onCrossbowShot(pLevel, pShooter, pCrossbowStack);
    }

    private static float[] getShotPitches(RandomSource random) {
        boolean flag = random.nextBoolean();
        return new float[] {1.0F, AetherIICrossbowItem.getRandomShotPitch(flag, random), AetherIICrossbowItem.getRandomShotPitch(!flag, random)};
    }

    private static float getRandomShotPitch(boolean isHighPitched, RandomSource random) {
        float f = isHighPitched ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void shootProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbow, ItemStack ammo, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        if (!level.isClientSide()) {
            AbstractArrow projectile = AetherIICrossbowItem.getArrow(level, shooter, crossbow, ammo);
            if (isCreativeMode || projectileAngle != 0.0F) {
                projectile.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }
            if (shooter instanceof CrossbowAttackMob crossbowattackmob) {
                crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), crossbow, projectile, projectileAngle);
            } else {
                Vec3 vec3 = shooter.getUpVector(1.0F);
                Quaternionf quaternionf = new Quaternionf().setAngleAxis(projectileAngle * (float) (Math.PI / 180.0), vec3.x, vec3.y, vec3.z);
                Vec3 vec31 = shooter.getViewVector(1.0F);
                Vector3f vector3f = vec31.toVector3f().rotate(quaternionf);
                projectile.shoot(vector3f.x(), vector3f.y(), vector3f.z(), velocity, inaccuracy);
            }
            crossbow.hurtAndBreak(1, shooter, p_40858_ -> p_40858_.broadcastBreakEvent(hand));
            level.addFreshEntity(projectile);
            level.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, soundPitch);
        }
    }

    private static AbstractArrow getArrow(Level level, LivingEntity livingEntity, ItemStack crossbow, ItemStack ammo) {
        ScatterglassBoltItem boltItem = (ScatterglassBoltItem) (ammo.getItem() instanceof ScatterglassBoltItem ? ammo.getItem() : AetherIIItems.SCATTERGLASS_BOLT.get());
        AbstractArrow arrow = boltItem.createArrow(level, ammo, livingEntity);
        if (livingEntity instanceof Player) {
            arrow.setCritArrow(true);
        }
        arrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        arrow.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, crossbow);
        if (i > 0) {
            arrow.setPierceLevel((byte)i);
        }
        return arrow;
    }

    private static void onCrossbowShot(Level level, LivingEntity shooter, ItemStack crossbow) {
        if (shooter instanceof ServerPlayer serverPlayer) {
            if (!level.isClientSide()) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, crossbow);
            }
            serverPlayer.awardStat(Stats.ITEM_USED.get(crossbow.getItem()));
        }
        AetherIICrossbowItem.clearChargedProjectiles(crossbow);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = this.getPowerForChargeTime(i);
        if (f >= 1.0F && !CrossbowItem.isCharged(stack) && this.tryLoadProjectile(livingEntity, stack)) {
            CrossbowItem.setCharged(stack, true);
            SoundSource soundSource = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundSource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }
    }

    public float getPowerForChargeTime(int useTime) {
        float f = (float) useTime / (float) this.chargeTime;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public boolean tryLoadProjectile(LivingEntity shooter, ItemStack crossbow) {
        boolean flag = shooter instanceof Player player && player.getAbilities().instabuild;
        ItemStack projectile = shooter.getProjectile(crossbow);
        if (projectile.isEmpty() && flag) {
            projectile = new ItemStack(AetherIIItems.SCATTERGLASS_BOLT.get());
        }
        return loadProjectile(shooter, crossbow, projectile, false, flag);
    }

    public boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack ammo, boolean hasAmmo, boolean isCreative) {
        if (ammo.isEmpty()) {
            return false;
        } else {
            boolean flag = isCreative && ammo.getItem() instanceof ScatterglassBoltItem;
            ItemStack stack;
            if (!flag && !isCreative && !hasAmmo) {
                stack = ammo.split(1);
                if (ammo.isEmpty() && shooter instanceof Player player) {
                    player.getInventory().removeItem(ammo);
                }
            } else {
                stack = ammo.copy();
            }

            AetherIICrossbowItem.addChargedProjectile(crossbow, stack);
            return true;
        }
    }

    private static void addChargedProjectile(ItemStack crossbow, ItemStack ammo) {
        CompoundTag crossbowTag = crossbow.getOrCreateTag();
        ListTag listTag;
        if (crossbowTag.contains("ChargedProjectiles", 9)) {
            listTag = crossbowTag.getList("ChargedProjectiles", 10);
        } else {
            listTag = new ListTag();
        }
        CompoundTag tag = new CompoundTag();
        ammo.save(tag);
        listTag.add(tag);
        crossbowTag.put("ChargedProjectiles", listTag);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack crossbow) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag crossbowTag = crossbow.getTag();
        if (crossbowTag != null && crossbowTag.contains("ChargedProjectiles", 9)) {
            ListTag listTag = crossbowTag.getList("ChargedProjectiles", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag tag = listTag.getCompound(i);
                list.add(ItemStack.of(tag));
            }
        }
        return list;
    }

    private static void clearChargedProjectiles(ItemStack crossbow) {
        CompoundTag crossbowTag = crossbow.getTag();
        if (crossbowTag != null) {
            ListTag ammo = crossbowTag.getList("ChargedProjectiles", 9);
            ammo.clear();
            crossbowTag.put("ChargedProjectiles", ammo);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide()) {
            SoundEvent startSound = this.getStartSound();
            SoundEvent loadingSound = SoundEvents.CROSSBOW_LOADING_MIDDLE;
            float f = (float) (stack.getUseDuration() - count) / (float) AetherIICrossbowItem.getCrossbowChargeDuration(stack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }
            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), startSound, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
            if (f >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), loadingSound, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
    }

    public static int getCrossbowChargeDuration(ItemStack crossbowStack) {
        return ((AetherIICrossbowItem) crossbowStack.getItem()).chargeTime;
    }

    public SoundEvent getStartSound() {
        return SoundEvents.CROSSBOW_LOADING_START;
    }

    public float getShootingPower(ItemStack crossbow) {
        return 3.15F;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return this.chargeTime + 3;
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return BOLT_ONLY;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return BOLT_ONLY;
    }

    public Tier getTier() {
        return this.tier;
    }

    @Override
    public int getEnchantmentValue() {
        return this.tier.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairItem) {
        return this.tier.getRepairIngredient().test(repairItem);
    }
}
