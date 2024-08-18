package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.effect.AetherIIEffects;
import com.aetherteam.aetherii.entity.ai.goal.CockatriceMeleeAttackGoal;
import com.aetherteam.aetherii.entity.ai.goal.CockatriceRangedAttackGoal;
import com.aetherteam.aetherii.entity.projectile.ToxicDart;
import com.aetherteam.aetherii.entity.projectile.VenomousDart;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Cockatrice extends Monster implements RangedAttackMob {

    private int attackTick;
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();

    public Cockatrice(EntityType<? extends Cockatrice> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            //handle the attack animation
            if (this.attackAnimationState.isStarted()) {
                if (this.attackTick >= 40) {
                    this.attackAnimationState.stop();
                } else {
                    ++this.attackTick;
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(4, new CockatriceMeleeAttackGoal(this, 1.1F, true));
        this.goalSelector.addGoal(5, new CockatriceRangedAttackGoal(this, 0.8F, 20 * 10, 20 * 15, 10.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 61) {
            this.attackAnimationState.start(this.tickCount);
            this.attackTick = 0;
            this.shootAnimationState.stop();
        } else if (pId == 62) {
            this.shootAnimationState.start(this.tickCount);
            this.attackAnimationState.stop();
        } else if (pId == 63) {
            this.shootAnimationState.stop();
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    @Override
    public void aiStep() {
        boolean flag = this.isSunBurnTick();
        if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            if (!itemstack.isEmpty()) {
                if (itemstack.isDamageableItem()) {
                    Item item = itemstack.getItem();
                    itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                    if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                        this.onEquippedItemBroken(item, EquipmentSlot.HEAD);
                        this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }

                flag = false;
            }

            if (flag) {
                this.igniteForSeconds(8.0F);
            }
        }
        super.aiStep();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        VenomousDart dart = new VenomousDart(this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        dart.shoot(d1, d0 + d4, d3, 0.8F, 6.0F);
        this.playSound(AetherIISoundEvents.COCKATRICE_SHOOT.value(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(dart);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != AetherIIEffects.VENOM.get() && super.canBeAffected(effect);
    }
}
