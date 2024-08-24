package com.aetherteam.aetherii.item.equipment.weapons;

import com.aetherteam.aetherii.item.AetherIIItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TieredCrossbowItem extends CrossbowItem {
    public static final Predicate<ItemStack> BOLT_ONLY = stack -> stack.is(AetherIIItems.SCATTERGLASS_BOLT);
    private final Tier tier;
    private final int chargeTime;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    private static final CrossbowItem.ChargingSounds DEFAULT_SOUNDS = new CrossbowItem.ChargingSounds(Optional.of(SoundEvents.CROSSBOW_LOADING_START), Optional.of(SoundEvents.CROSSBOW_LOADING_MIDDLE), Optional.of(SoundEvents.CROSSBOW_LOADING_END));

    public TieredCrossbowItem(Tier tier, Properties properties) {
        this(tier, 25, properties);
    }

    public TieredCrossbowItem(Tier tier, int chargeTime, Properties properties) {
        super(properties.durability(tier.getUses()));
        this.tier = tier;
        this.chargeTime = chargeTime;
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return BOLT_ONLY;
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return BOLT_ONLY;
    }

    @Override
    public ItemStack getDefaultCreativeAmmo(@Nullable Player player, ItemStack projectileWeaponItem) {
        return AetherIIItems.SCATTERGLASS_BOLT.get().getDefaultInstance();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            this.performShooting(level, player, hand, stack, this.getCrossbowShootingPower(chargedProjectiles), 1.0F, null);
            return InteractionResultHolder.consume(stack);
        } else if (!player.getProjectile(stack).isEmpty()) {
            this.startSoundPlayed = false;
            this.midLoadSoundPlayed = false;
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int count) {
        if (!level.isClientSide()) {
            CrossbowItem.ChargingSounds chargeSounds = this.getChargingSounds(stack);
            float duration = (float) (stack.getUseDuration(livingEntity) - count) / (float) this.getCrossbowChargeDuration(stack, livingEntity);
            if (duration < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (duration >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                chargeSounds.start().ifPresent(p_352849_ -> level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), p_352849_.value(), SoundSource.PLAYERS, 0.5F, 1.0F));
            }

            if (duration >= 0.5F && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                chargeSounds.mid().ifPresent(p_352855_ -> level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), p_352855_.value(), SoundSource.PLAYERS, 0.5F, 1.0F));
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        int duration = this.getUseDuration(stack, entityLiving) - timeLeft;
        float power = this.getPowerForTime(duration, stack, entityLiving);
        if (power >= 1.0F && !isCharged(stack) && this.tryLoadProjectiles(entityLiving, stack)) {
            CrossbowItem.ChargingSounds sounds = this.getChargingSounds(stack);
            sounds.end().ifPresent(sound -> level.playSound(
                null,
                entityLiving.getX(),
                entityLiving.getY(),
                entityLiving.getZ(),
                sound.value(),
                entityLiving.getSoundSource(),
                1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F));
        }
    }

    public boolean tryLoadProjectiles(LivingEntity shooter, ItemStack crossbowStack) {
        List<ItemStack> list = drawProjectile(crossbowStack, shooter.getProjectile(crossbowStack), shooter);
        if (!list.isEmpty()) {
            crossbowStack.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.of(list));
            return true;
        } else {
            return false;
        }
    }

    public List<ItemStack> drawProjectile(ItemStack weapon, ItemStack ammo, LivingEntity shooter) {
        if (ammo.isEmpty()) {
            return List.of();
        } else {
            int i = shooter.level() instanceof ServerLevel serverlevel ? this.getProjectileCount(serverlevel, weapon, shooter, 1) : 1;
            List<ItemStack> list = new ArrayList<>(i);
            ItemStack ammoCopy = ammo.copy();

            for (int j = 0; j < i; j++) {
                ItemStack afterUse = useAmmo(weapon, j == 0 ? ammo : ammoCopy, shooter, j > 0);
                if (!afterUse.isEmpty()) {
                    list.add(afterUse);
                }
            }

            return list;
        }
    }

    @Override
    public void performShooting(Level level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, float velocity, float inaccuracy, @Nullable LivingEntity target) {
        if (level instanceof ServerLevel serverlevel) {
            if (shooter instanceof Player player && EventHooks.onArrowLoose(weapon, shooter.level(), player, 1, true) < 0) return;
            ChargedProjectiles chargedProjectiles = weapon.set(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY);
            if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
                this.shoot(serverlevel, shooter, hand, weapon, chargedProjectiles.getItems(), velocity, inaccuracy, shooter instanceof Player, target);
                if (shooter instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, weapon);
                    serverPlayer.awardStat(Stats.ITEM_USED.get(weapon.getItem()));
                }
            }
        }
    }

    protected void shoot(ServerLevel level, LivingEntity shooter, InteractionHand hand, ItemStack weapon, List<ItemStack> projectileItems, float velocity, float inaccuracy, boolean isCrit, @Nullable LivingEntity target) {
        float f = this.getProjectileSpread(level, weapon, shooter, 0.0F);
        float f1 = projectileItems.size() == 1 ? 0.0F : 2.0F * f / (float)(projectileItems.size() - 1);
        float f2 = (float) ((projectileItems.size() - 1) % 2) * f1 / 2.0F;
        float f3 = 1.0F;

        for (int i = 0; i < projectileItems.size(); i++) {
            ItemStack itemStack = projectileItems.get(i);
            if (!itemStack.isEmpty()) {
                float f4 = f2 + f3 * (float)((i + 1) / 2) * f1;
                f3 = -f3;
                Projectile projectile = this.createProjectile(level, shooter, weapon, itemStack, isCrit);
                this.shootProjectile(shooter, projectile, i, velocity, inaccuracy, f4, target);
                level.addFreshEntity(projectile);
                weapon.hurtAndBreak(this.getDurabilityUse(itemStack), shooter, LivingEntity.getSlotForHand(hand));
                if (weapon.isEmpty()) {
                    break;
                }
            }
        }
    }

    @Override
    protected Projectile createProjectile(Level level, LivingEntity shooter, ItemStack weapon, ItemStack ammo, boolean isCrit) {
        Projectile projectile = super.createProjectile(level, shooter, weapon, ammo, isCrit);
        if (projectile instanceof AbstractArrow abstractarrow) {
            abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        }
        return projectile;
    }

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity, float inaccuracy, float angle, @Nullable LivingEntity target) {
        Vector3f shotVector;
        if (target != null) {
            double x = target.getX() - shooter.getX();
            double z = target.getZ() - shooter.getZ();
            double horizontal = Math.sqrt(x * x + z * z);
            double vertical = target.getY(0.3333333333333333) - projectile.getY() + horizontal * 0.2F;
            shotVector = this.getProjectileShotVector(shooter, new Vec3(x, vertical, z), angle);
        } else {
            Vec3 upVector = shooter.getUpVector(1.0F);
            Quaternionf upQuaternion = new Quaternionf().setAngleAxis(angle * (float) (Math.PI / 180.0), upVector.x, upVector.y, upVector.z);
            Vec3 viewVector = shooter.getViewVector(1.0F);
            shotVector = viewVector.toVector3f().rotate(upQuaternion);
        }

        projectile.shoot(shotVector.x(), shotVector.y(), shotVector.z(), velocity, inaccuracy);
        float shotPitch = this.getShotPitch(shooter.getRandom(), index);
        shooter.level().playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.CROSSBOW_SHOOT, shooter.getSoundSource(), 1.0F, shotPitch);
    }

    public Vector3f getProjectileShotVector(LivingEntity shooter, Vec3 distance, float angle) {
        Vector3f vector3f = distance.toVector3f().normalize();
        Vector3f vector3f1 = vector3f.cross(new Vector3f(0.0F, 1.0F, 0.0F));
        if ((double) vector3f1.lengthSquared() <= 1.0E-7) {
            Vec3 vec3 = shooter.getUpVector(1.0F);
            vector3f1 = vector3f.cross(vec3.toVector3f());
        }
        Vector3f vector3f2 = vector3f.rotateAxis((float) (Math.PI / 2), vector3f1.x, vector3f1.y, vector3f1.z);
        return vector3f.rotateAxis(angle * (float) (Math.PI / 180.0), vector3f2.x, vector3f2.y, vector3f2.z);
    }

    public float getShotPitch(RandomSource random, int index) {
        return index == 0 ? 1.0F : this.getRandomShotPitch((index & 1) == 1, random);
    }

    public float getRandomShotPitch(boolean isHighPitched, RandomSource random) {
        float f = isHighPitched ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    public int getProjectileCount(ServerLevel level, ItemStack tool, Entity entity, int projectileCount) {
        return projectileCount;
    }

    public float getProjectileSpread(ServerLevel level, ItemStack tool, Entity entity, float projectileSpread) {
        return projectileSpread;
    }

    public float getPowerForTime(int timeLeft, ItemStack stack, LivingEntity shooter) {
        float f = (float) timeLeft / (float) this.getCrossbowChargeDuration(stack, shooter);
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public float getCrossbowShootingPower(ChargedProjectiles projectile) {
        return 3.15F;
    }

    @Override
    protected int getDurabilityUse(ItemStack stack) {
        return 1;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return this.getCrossbowChargeDuration(stack, entity) + 3;
    }

    public int getCrossbowChargeDuration(ItemStack stack, LivingEntity shooter) {
        return ((TieredCrossbowItem) stack.getItem()).chargeTime;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW;
    }

    private CrossbowItem.ChargingSounds getChargingSounds(ItemStack stack) {
        return EnchantmentHelper.pickHighestLevel(stack, EnchantmentEffectComponents.CROSSBOW_CHARGING_SOUNDS).orElse(DEFAULT_SOUNDS);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        ChargedProjectiles chargedProjectiles = stack.get(DataComponents.CHARGED_PROJECTILES);
        if (chargedProjectiles != null && !chargedProjectiles.isEmpty()) {
            ItemStack projectile = chargedProjectiles.getItems().get(0);
            tooltipComponents.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append(projectile.getDisplayName()));
        }
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
