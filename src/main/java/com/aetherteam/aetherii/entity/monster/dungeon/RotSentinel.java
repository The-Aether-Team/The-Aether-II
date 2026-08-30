package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.entity.ai.goal.MostDamageTargetGoal;
import com.aetherteam.aetherii.entity.monster.dungeon.boss.AetherBossMob;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class RotSentinel extends Monster implements AetherBossMob<RotSentinel> {
    private final ServerBossEvent bossFight;

    /**
     * Goal for targeting in groups of entities
     */
    private MostDamageTargetGoal mostDamageTargetGoal;

    public RotSentinel(EntityType<? extends RotSentinel> type, Level level) {
        super(type, level);
        this.bossFight = (ServerBossEvent) new ServerBossEvent(this.getUUID(), this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setPlayBossMusic(true);
        this.setBossFight(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 0.8F));
        //this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 6)
                .add(Attributes.MOVEMENT_SPEED, 0.18)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    /**
     * Handles damaging the Slider.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            super.hurtServer(serverLevel, source, amount);
            if (!this.level().isClientSide() && source.getEntity() instanceof LivingEntity living) {
                this.mostDamageTargetGoal.addAggro(living, amount); // AI goal for being hurt.
            }
        } else {
            if (super.hurtServer(serverLevel, source, amount) && this.getHealth() > 0) {
                if (!this.isBossFight()) {
                    this.start();
                }
                if (!this.level().isClientSide() && source.getEntity() instanceof LivingEntity living) {
                    this.mostDamageTargetGoal.addAggro(living, amount); // AI goal for being hurt.
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable Identifier getBossBarTexture() {
        return null;
    }

    @Override
    public @Nullable Identifier getBossBarBackgroundTexture() {
        return null;
    }

    @Override
    public Component getBossName() {
        return null;
    }

    @Override
    public void setBossName(Component component) {

    }

    /**
     * @return Whether the boss fight is active and the boss bar is visible, as a {@link Boolean}.
     */
    @Override
    public boolean isBossFight() {
        return this.bossFight.isVisible();
    }

    /**
     * Sets whether the boss fight is active and the boss bar is visible.
     *
     * @param isFighting The {@link Boolean} value.
     */
    @Override
    public void setBossFight(boolean isFighting) {
        this.bossFight.setVisible(isFighting);
    }


    @Override
    public @Nullable BossRoomTracker getDungeon() {
        return null;
    }

    @Override
    public void setDungeon(@Nullable BossRoomTracker bossRoomTracker) {

    }

    /**
     * Awakens the boss, starts the boss fight, and closes the boss room.
     */
    private void start() {
        /*if (this.getAwakenSound() != null) {
            this.playSound(this.getAwakenSound(), 2.5F, 1.0F / (this.getRandom().nextFloat() * 0.2F + 0.9F));
        }*/
        this.setHealth(this.getMaxHealth());
        //this.lastHealthStage = this.getMaxHealth();
        //this.setAwake(true);
        this.setBossFight(true);
        if (this.getDungeon() != null) {
            this.closeRoom();
        }
    }

    @Override
    public void reset() {
        this.setBossFight(false);
        this.setTarget(null);
    }

    @Override
    public @Nullable BlockState convertBlock(Level level, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Override
    protected boolean considersEntityAsAlly(Entity other) {
        if (super.considersEntityAsAlly(other)) {
            return true;
        } else {
            return !other.is(AetherIITags.EntityTypes.GUARDIAN_TREE_MOBS) ? false : this.getTeam() == null && other.getTeam() == null;
        }
    }

}
