package com.aetherteam.aetherii.item.combat;

import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class AetherCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> BOLT_ONLY = stack -> stack.is(AetherIIItems.SCATTERGLASS_BOLT);
    private final Tier tier;
    private final int chargeTime;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;

    public AetherCrossbowItem(Tier tier, Properties properties) {
        this(tier, 25, properties);
    }

    public AetherCrossbowItem(Tier tier, int chargeTime, Properties properties) {
        super(properties.defaultDurability(tier.getUses()));
        this.tier = tier;
        this.chargeTime = chargeTime;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (isCharged(itemStack)) {
            performShooting(level, player, hand, itemStack, this.getShootingPower(itemStack), 1.0F);
            setCharged(itemStack, false);
            return InteractionResultHolder.consume(itemStack);
        } else if (!player.getProjectile(itemStack).isEmpty()) {
            if (!isCharged(itemStack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(hand);
            }
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    public float getShootingPower(ItemStack crossbow) {
        return 3.15F;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = this.getPowerForChargeTime(i);
        if (f >= 1.0F && !isCharged(stack) && this.tryLoadProjectile(livingEntity, stack)) {
            setCharged(stack, true);
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
            boolean flag = isCreative && ammo.getItem() instanceof ArrowItem;
            ItemStack stack;
            if (!flag && !isCreative && !hasAmmo) {
                stack = ammo.split(1);
                if (ammo.isEmpty() && shooter instanceof Player player) {
                    player.getInventory().removeItem(ammo);
                }
            } else {
                stack = ammo.copy();
            }

            addChargedProjectile(crossbow, stack);
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

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide()) {
            SoundEvent startSound = this.getStartSound();
            SoundEvent loadingSound = SoundEvents.CROSSBOW_LOADING_MIDDLE;
            float f = (float) (stack.getUseDuration() - count) / (float) getCrossbowChargeDuration(stack);
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
        return ((AetherCrossbowItem) crossbowStack.getItem()).chargeTime;
    }

    public SoundEvent getStartSound() {
        return SoundEvents.CROSSBOW_LOADING_START;
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
        return this.tier.getRepairIngredient().test(repairItem) || super.isValidRepairItem(stack, repairItem);
    }
}
