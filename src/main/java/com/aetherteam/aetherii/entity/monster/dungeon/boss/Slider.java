package com.aetherteam.aetherii.entity.monster.dungeon.boss;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlockStateProperties;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.dungeon.CopyBlock;
import com.aetherteam.aetherii.block.dungeon.GroundTrapBlock;
import com.aetherteam.aetherii.blockentity.CopyBlockEntity;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.ai.controller.BlankMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.MostDamageTargetGoal;
import com.aetherteam.aetherii.entity.ai.goal.boss.*;
import com.aetherteam.aetherii.network.packet.clientbound.BossInfoPacket;
import com.aetherteam.nitrogen.entity.BossRoomTracker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import com.aetherteam.aetherii.network.PacketDistributor;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Slider extends PathfinderMob implements AetherBossMob<Slider>, Enemy, IEntityAdditionalSpawnData {
    private static final EntityDataAccessor<Boolean> DATA_AWAKE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Component> DATA_BOSS_NAME_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.COMPONENT);
    private static final EntityDataAccessor<Float> DATA_HURT_ANGLE_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_HURT_ANGLE_X_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> DATA_HURT_ANGLE_Z_ID = SynchedEntityData.defineId(Slider.class, EntityDataSerializers.FLOAT);
    private static final Music SLIDER_MUSIC = new Music(AetherIISoundEvents.MUSIC_BOSS_SLIDER.getHolder().orElseGet(() -> Holder.direct(AetherIISoundEvents.MUSIC_BOSS_SLIDER.get())), 0, 0, true);

    /**
     * Goal for targeting in groups of entities
     */
    private MostDamageTargetGoal mostDamageTargetGoal;

    /**
     * Boss health bar manager
     */
    private final ServerBossEvent bossFight;
    @Nullable
    private BossRoomTracker<Slider> bronzeDungeon;

    private int chatCooldown;

    private Direction moveDirection = null;
    private int moveDelay = this.calculateMoveDelay();
    private Vec3 targetPoint = null;
    private int attackCooldown = 0;
    public int sliderDeathTime = 0;
    public float lastHealthStage = 0.0F;
    private boolean breakTreasureVault;
    private boolean suppressExperienceDrop;
    private boolean delayedExperienceDropPending;
    private int delayedExperienceReward;

    public Slider(EntityType<? extends Slider> type, Level level) {
        super(type, level);
        this.moveControl = new BlankMoveControl(this);
        this.bossFight = new ServerBossEvent(this.getBossName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        this.bossFight.setPlayBossMusic(true);
        this.setBossFight(false);
        this.xpReward = XP_REWARD_BOSS;
        this.setRot(0, 0);
        this.setPersistenceRequired();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        MutableComponent mk = Component.literal("Mk.E-");
        for (int i = 0; i < 4; i++) {
            if (this.getRandom().nextBoolean()) {
                mk.append(Component.literal(String.valueOf(this.random.nextInt(10))));
            } else {
                mk.append(Component.literal(String.valueOf((char) (this.getRandom().nextInt(26) + 'a')).toUpperCase()));
            }
        }
        MutableComponent name = Component.translatable("gui.aether_ii.slider.title", mk);
        this.setBossName(name);
        this.moveTo(Mth.floor(this.getX()), this.getY(), Mth.floor(this.getZ()), 0.0F, 0.0F); // Aligns the Slider with the blocks below it.
        return spawnData;
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 350.0)
                .add(Attributes.FOLLOW_RANGE, 64.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CollideGoal(this));
        this.goalSelector.addGoal(2, new CrushGoal(this));
        this.goalSelector.addGoal(3, new BackOffAfterAttackGoal(this));
        this.goalSelector.addGoal(4, new SetPathUpOrDownGoal(this));
        this.goalSelector.addGoal(5, new AvoidObstaclesGoal(this));
        this.goalSelector.addGoal(6, new SliderDeathWithBreakDoorGoal(this));
        this.goalSelector.addGoal(7, new SliderMoveGoal(this));

        this.mostDamageTargetGoal = new MostDamageTargetGoal(this);
        this.targetSelector.addGoal(1, this.mostDamageTargetGoal);
        this.targetSelector.addGoal(2, new SliderNearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_AWAKE_ID, false);
        this.entityData.define(DATA_BOSS_NAME_ID, Component.literal("Slider"));
        this.entityData.define(DATA_HURT_ANGLE_ID, 0.0F);
        this.entityData.define(DATA_HURT_ANGLE_X_ID, 0.0F);
        this.entityData.define(DATA_HURT_ANGLE_Z_ID, 0.0F);
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 60) {
            this.explode();
        } else {
            super.handleEntityEvent(p_21375_);
        }
    }

    /**
     * Handles stopping target tracking, liquid evaporation, and chat message cooldown.
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.isAwake() || (this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        if (this.isAwake()) {
            this.evaporate();
        }
        if (this.getChatCooldown() > 0) {
            this.chatCooldown--;
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            float stageIncrement = this.getMaxHealth() / (14 + 2);
            if (this.getHealth() <= this.lastHealthStage - stageIncrement) {
                this.triggerTrap(serverLevel);
                this.lastHealthStage = this.getHealth();
            }
        }
    }


    /**
     * Evaporates liquid blocks.
     *
     * @see AetherBossMob#evaporate(Mob, BlockPos, BlockPos, Predicate)
     */
    private void evaporate() {
        Pair<BlockPos, BlockPos> minMax = this.getDefaultBounds(this);
        AetherBossMob.super.evaporate(this, minMax.getLeft(), minMax.getRight(), (blockState) -> true);
    }

    private void triggerTrap(ServerLevel serverLevel) {
        if (this.getDungeon() != null) {
            AABB bounds = this.getDungeon().roomBounds();
            List<BlockPos> positions = new ArrayList<>();
            BlockPos.betweenClosed((int) bounds.minX, (int) bounds.minY, (int) bounds.minZ, (int) bounds.maxX, (int) bounds.minY + 2, (int) bounds.maxZ).forEach(position -> positions.add(position.immutable()));
            Collections.shuffle(positions);
            for (BlockPos pos : positions) {
                BlockState oldState = serverLevel.getBlockState(pos);
                if (oldState.is(AetherIIBlocks.SENTRY_TRAP.get()) && oldState.getValue(GroundTrapBlock.LOCKED) && oldState.getValue(GroundTrapBlock.TRAP_STATE) == AetherIIBlockStateProperties.TrapState.LOADED) {
                    BlockState newState = oldState.setValue(GroundTrapBlock.TRAP_STATE,  AetherIIBlockStateProperties.TrapState.TRIGGERED);
                    serverLevel.setBlock(pos, newState, 1 | 2);
                    break;
                }
            }
        }
    }

    /**
     * Handles boss fight tracking and dungeon tracking<br><br>
     * Warning for "unchecked" is suppressed because the brain is always a Slider brain.
     */
    @Override
    public void customServerAiStep() {
        ServerLevel serverLevel = (ServerLevel) this.level();
        super.customServerAiStep();
        this.bossFight.setProgress(this.getHealth() / this.getMaxHealth());
        this.trackDungeon();
        if (this.moveDelay > 0) {
            --this.moveDelay;
        }
        if (this.attackCooldown > 0) {
            --this.attackCooldown;
        }
    }

    /**
     * Handles damaging the Slider.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Optional<LivingEntity> damageResult = this.canDamageSlider(source);
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            super.hurt(source, amount);
            if (!this.level().isClientSide() && source.getEntity() instanceof LivingEntity living) {
                this.mostDamageTargetGoal.addAggro(living, amount); // AI goal for being hurt.
            }
        } else if (damageResult.isPresent()) {
            LivingEntity attacker = damageResult.get();
            if (super.hurt(source, amount) && this.getHealth() > 0) {
                if (!this.isBossFight()) {
                    this.start();
                }
                this.setDeltaMovement(this.getDeltaMovement().scale(0.75F));

                // Handle the Slider's model tilt when damaged.
                double a = Math.abs(this.position().x() - attacker.position().x());
                double c = Math.abs(this.position().z() - attacker.position().z());
                if (a > c) {
                    this.setHurtAngleZ(1);
                    this.setHurtAngleX(0);
                    if (this.position().x() > attacker.position().x()) {
                        this.setHurtAngleZ(-1);
                    }
                } else {
                    this.setHurtAngleX(1);
                    this.setHurtAngleZ(0);
                    if (this.position().z() > attacker.position().z()) {
                        this.setHurtAngleX(-1);
                    }
                }
                this.setHurtAngle(0.7F - (this.getHealth() / 875.0F));

                if (!this.level().isClientSide() && source.getEntity() instanceof LivingEntity living) {
                    this.mostDamageTargetGoal.addAggro(living, amount); // AI goal for being hurt.
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the Slider can be damaged, playing a chat message if the player attempts to damage with the wrong tool or is too far away.
     *
     * @param source The {@link DamageSource}.
     * @return An {@link Optional} that contains the attacking {@link LivingEntity} if the damage checks are successful.
     */
    private Optional<LivingEntity> canDamageSlider(DamageSource source) {
        if (this.level().getDifficulty() != Difficulty.PEACEFUL) {
            if (source.getDirectEntity() instanceof LivingEntity attacker) {
                if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) { // Only allow damage within the boss room.
                    if (attacker.getMainHandItem().is(AetherIITags.Items.SLIDER_DAMAGING_ITEMS)
                            || attacker.getMainHandItem().isCorrectToolForDrops(AetherIIBlocks.UNDERSHALE_BRICKS.get().defaultBlockState())) { // Check for correct tool.
                        return Optional.of(attacker);
                    } else {
                        return this.sendInvalidToolMessage(attacker);
                    }
                } else {
                    this.sendTooFarMessage(attacker);
                }
            } else if (source.getDirectEntity() instanceof Projectile projectile) {
                if (projectile.getOwner() instanceof LivingEntity attacker) {
                    if (this.getDungeon() == null || this.getDungeon().isPlayerWithinRoomInterior(attacker)) { // Only allow damage within the boss room.
                        if (projectile.getType().builtInRegistryHolder().is(AetherIITags.EntityTypes.SLIDER_DAMAGING_PROJECTILES)) {
                            return Optional.of(attacker);
                        } else {
                            return this.sendInvalidToolMessage(attacker);
                        }
                    } else {
                        return this.sendTooFarMessage(attacker);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Tells the player that they are using an invalid tool to attack the Slider.
     *
     * @param attacker The attacking {@link LivingEntity}.
     * @return An empty {@link Optional}.
     */
    private Optional<LivingEntity> sendInvalidToolMessage(LivingEntity attacker) {
        if (!this.level().isClientSide() && attacker instanceof Player player && !this.isAwake()) {
            if (this.getChatCooldown() <= 0) {
                player.displayClientMessage(Component.translatable("gui.aether_ii.slider.message.attack.invalid"), true); // Invalid tool.
                this.setChatCooldown(15);
            }
        }
        return Optional.empty();
    }

    /**
     * Tells the player that they are too far away to attack the Slider.
     *
     * @param attacker The attacking {@link LivingEntity}.
     * @return An empty {@link Optional}.
     */
    private Optional<LivingEntity> sendTooFarMessage(LivingEntity attacker) {
        if (!this.level().isClientSide() && attacker instanceof ServerPlayer player) {
            if (this.getChatCooldown() <= 0) {
                this.displayTooFarMessage(player); // Too far from Slider
                this.setChatCooldown(15);
            }
        }
        return Optional.empty();
    }

    /**
     * Awakens the boss, starts the boss fight, and closes the boss room.
     */
    private void start() {
        if (this.getAwakenSound() != null) {
            this.playSound(this.getAwakenSound(), 2.5F, 1.0F / (this.getRandom().nextFloat() * 0.2F + 0.9F));
        }
        this.setHealth(this.getMaxHealth());
        this.lastHealthStage = this.getMaxHealth();
        this.setAwake(true);
        this.setBossFight(true);
        if (this.getDungeon() != null) {
            this.closeRoom();
        }
    }

    /**
     * Resets the boss fight.
     */
    public void reset() {
        this.setDeltaMovement(Vec3.ZERO);
        this.setAwake(false);
        this.setBossFight(false);
        this.setTarget(null);
        if (this.getDungeon() != null) {
            this.setPos(this.getDungeon().originCoordinates());
            this.openRoom();
        }
    }

    /**
     * Ends the boss fight, opens the room, and grants advancements when the boss dies.
     *
     * @param source The {@link DamageSource}.
     */
    @Override
    public void die(DamageSource source) {
        this.setDeltaMovement(Vec3.ZERO);
        if (this.level() instanceof ServerLevel serverLevel) {
            this.bossFight.setProgress(this.getHealth() / this.getMaxHealth()); // Forces an update to the boss health meter.
            if (this.getDungeon() != null) {
                this.getDungeon().grantAdvancements(source);
                //Move TearDown method calling to tickDeath method
            }
            this.delayedExperienceDropPending = this.lastHurtByPlayerTime > 0
                    && this.shouldDropExperience()
                    && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)
                    && !this.wasExperienceConsumed();
            this.delayedExperienceReward = this.delayedExperienceDropPending
                    ? net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.lastHurtByPlayer, this.getExperienceReward())
                    : 0;
        }
        if (this.isDeadOrDying()) {

            this.setAwake(true);
        }
        this.suppressExperienceDrop = true;
        super.die(source);
        this.suppressExperienceDrop = false;
    }

    @Override
    public boolean shouldDropExperience() {
        return !this.suppressExperienceDrop && super.shouldDropExperience();
    }

    @Override
    protected void dropExperience() {
        if (!this.suppressExperienceDrop) {
            super.dropExperience();
        }
    }


    public void setBreakTreasureVault() {
        this.breakTreasureVault = true;

    }

    @Override
    protected void tickDeath() {
        this.sliderDeathTime++;

        //use to slider usable goal while death animation
        if (this.level() instanceof ServerLevel) {
            ProfilerFiller profilerfiller = this.level().getProfiler();
            int i = this.tickCount + this.getId();
            if (i % 2 != 0 && this.tickCount > 1) {
                profilerfiller.push("targetSelector");
                this.targetSelector.tickRunningGoals(false);
                profilerfiller.pop();
                profilerfiller.push("goalSelector");
                this.goalSelector.tickRunningGoals(false);
                profilerfiller.pop();
            } else {
                profilerfiller.push("targetSelector");
                this.targetSelector.tick();
                profilerfiller.pop();
                profilerfiller.push("goalSelector");
                this.goalSelector.tick();
                profilerfiller.pop();
            }
            if (this.moveDelay > 0) {
                --this.moveDelay;
            }
            if (this.attackCooldown > 0) {
                --this.attackCooldown;
            }
        }
        if (this.breakTreasureVault && this.getMoveDelay() < 2) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.playSound(SoundEvents.GENERIC_EXPLODE, 2.5F, 1.0F / (this.getRandom().nextFloat() * 0.2F + 0.9F));

            if (this.getDungeon() != null) {
                this.tearDownRoom();
            }
            this.dropDelayedExperience();
            this.remove(Entity.RemovalReason.KILLED);
            this.gameEvent(GameEvent.ENTITY_DIE);
        }
    }

    private void dropDelayedExperience() {
        if (this.delayedExperienceDropPending && this.delayedExperienceReward > 0 && this.level() instanceof ServerLevel serverLevel) {
            ExperienceOrb.award(serverLevel, this.position(), this.delayedExperienceReward);
        }
        this.delayedExperienceDropPending = false;
        this.delayedExperienceReward = 0;
    }

    /**
     * Explosion particles for the Slider.
     */
    public void explode() {
        for (int i = 0; i < 16; i++) {
            double x = this.getX() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            double y = this.getY() + 1.75 + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            double z = this.getZ() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            this.level().addParticle(ParticleTypes.POOF, x, y, z, 0.0, 0.0, 0.0);
        }
        for (int i = 0; i < 64; i++) {

            double x = this.getX() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            double y = this.getY() + 1.5 + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            double z = this.getZ() + (double) (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 1.5;
            float xMove = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 10;
            float yMove = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 10;
            float zMove = (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 10;

            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, AetherIIBlocks.SENTRY_BASE_BRICKS.get().defaultBlockState()), x, y, z, xMove, yMove, zMove);
        }
    }

    /**
     * Disallows the Slider from receiving knockback.
     *
     * @param strength The {@link Double} for knockback strength.
     * @param x        The {@link Double} for knockback x-direction.
     * @param z        The {@link Double} for knockback z-direction.
     */
    @Override
    public void knockback(double strength, double x, double z) {
    }

    /**
     * Disallows the Slider from being pushed.
     *
     * @param x The {@link Double} for x-motion.
     * @param y The {@link Double} for y-motion.
     * @param z The {@link Double} for z-motion.
     */
    @Override
    public void push(double x, double y, double z) {
    }

    @Override
    public void setDeltaMovement(Vec3 deltaMovement) {
        if (this.isAwake()) {
            super.setDeltaMovement(deltaMovement);
        }
    }

    /**
     * Required despite call to {@link Mob#setPersistenceRequired()} in constructor.
     */
    @Override
    public void checkDespawn() {
    }

    /**
     * Called on every block in the boss room when the boss is defeated.
     *
     * @param oldState The {@link BlockState} to try to convert.
     * @return The converted {@link BlockState}.
     */
    @Nullable
    @Override
    public BlockState convertBlock(BlockState oldState) {
        if (oldState.getBlock() instanceof GroundTrapBlock && oldState.getValue(GroundTrapBlock.LOCKED)) {
            return oldState.setValue(GroundTrapBlock.LOCKED, false);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockState convertBlock(Level level, BlockPos pos, BlockState oldState) {
        if (oldState.getBlock() instanceof CopyBlock && !oldState.getValue(CopyBlock.EMPTY)) {
            if (level.getBlockEntity(pos) instanceof CopyBlockEntity blockEntity) {
                return blockEntity.destroy(level, pos);
            }
        }
        return this.convertBlock(oldState);
    }

    /**
     * Tracks the player as a part of the boss fight when the player is nearby, displaying the boss bar for them.
     *
     * @param player The {@link ServerPlayer}.
     */
    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        PacketDistributor.sendToPlayer(player, new BossInfoPacket.Display(this.bossFight.getId(), this.getId()));
        if (this.getDungeon() == null || this.getDungeon().isPlayerTracked(player)) {
            this.bossFight.addPlayer(player);
        }
    }

    /**
     * Tracks the player as no longer in the boss fight when the player is nearby, removing the boss bar for them.
     *
     * @param player The {@link ServerPlayer}.
     */
    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        PacketDistributor.sendToPlayer(player, new BossInfoPacket.Remove(this.bossFight.getId(), this.getId()));
        this.bossFight.removePlayer(player);
    }

    /**
     * Adds a player to the boss fight when they've entered the dungeon.
     *
     * @param player The {@link Player}.
     */
    @Override
    public void onDungeonPlayerAdded(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.addPlayer(serverPlayer);
        }
    }

    /**
     * Removes a player from the boss fight when they've left the dungeon.
     *
     * @param player The {@link Player}.
     */
    @Override
    public void onDungeonPlayerRemoved(@Nullable Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            this.bossFight.removePlayer(serverPlayer);
        }
    }

    /**
     * @return Whether the entity is awake, as a {@link Boolean}.
     */
    public boolean isAwake() {
        return this.getEntityData().get(DATA_AWAKE_ID);
    }

    /**
     * Sets whether the entity is awake.
     *
     * @param awake The {@link Boolean} value.
     */
    public void setAwake(boolean awake) {
        this.getEntityData().set(DATA_AWAKE_ID, awake);
    }

    /**
     * @return The {@link Component} for the boss name.
     */
    @Override
    public Component getBossName() {
        if (this.hasCustomName()) {
            return this.getCustomName();
        } else {
            return this.getEntityData().get(DATA_BOSS_NAME_ID);
        }
    }

    /**
     * Sets the {@link Component} for the boss name and in the boss fight.
     *
     * @param component The name {@link Component}.
     */
    @Override
    public void setBossName(Component component) {
        this.getEntityData().set(DATA_BOSS_NAME_ID, component);
        this.bossFight.setName(component);
    }

    /**
     * @return The {@link Float} for the x-angle for the Slider to tilt when damaged.
     */
    public float getHurtAngleX() {
        return this.getEntityData().get(DATA_HURT_ANGLE_X_ID);
    }

    /**
     * Sets the x-angle for the Slider to tilt when damaged.
     *
     * @param hurtAngleX The {@link Float} value.
     */
    public void setHurtAngleX(float hurtAngleX) {
        this.getEntityData().set(DATA_HURT_ANGLE_X_ID, hurtAngleX);
    }

    /**
     * @return The {@link Float} for the z-angle for the Slider to tilt when damaged.
     */
    public float getHurtAngleZ() {
        return this.getEntityData().get(DATA_HURT_ANGLE_Z_ID);
    }

    /**
     * Sets the z-angle for the Slider to tilt when damaged.
     *
     * @param hurtAngleZ The {@link Float} value.
     */
    public void setHurtAngleZ(float hurtAngleZ) {
        this.getEntityData().set(DATA_HURT_ANGLE_Z_ID, hurtAngleZ);
    }

    /**
     * @return The {@link Float} for the magnitude of the hurt angle tilt.
     */
    public float getHurtAngle() {
        return this.getEntityData().get(DATA_HURT_ANGLE_ID);
    }

    /**
     * Sets the magnitude of the hurt angle tilt.
     *
     * @param hurtAngle The {@link Float} value.
     */
    public void setHurtAngle(float hurtAngle) {
        this.getEntityData().set(DATA_HURT_ANGLE_ID, hurtAngle);
    }

    /**
     * @return The {@link Slider} {@link BossRoomTracker} for the Bronze Dungeon.
     */
    @Nullable
    @Override
    public BossRoomTracker<Slider> getDungeon() {
        return this.bronzeDungeon;
    }

    /**
     * Sets the tracker for the Bronze Dungeon.
     *
     * @param dungeon The {@link Slider} {@link BossRoomTracker}.
     */
    @Override
    public void setDungeon(@Nullable BossRoomTracker<Slider> dungeon) {
        this.bronzeDungeon = dungeon;
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

    /**
     * @return The {@link } for this boss's health bar.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarTexture() {
        return new ResourceLocation(AetherII.MODID, "boss_bar/slider");
    }

    /**
     * @return The {@link ResourceLocation} for this boss's health bar background.
     */
    @Nullable
    @Override
    public ResourceLocation getBossBarBackgroundTexture() {
        return new ResourceLocation(AetherII.MODID, "boss_bar/slider_background");
    }

    /**
     * @return The {@link Music} for this boss's fight.
     */
    @Nullable
    @Override
    public Music getBossMusic() {
        return SLIDER_MUSIC;
    }

    /**
     * @return The {@link Integer} for the cooldown until another chat message can display.
     */
    public int getChatCooldown() {
        return this.chatCooldown;
    }

    /**
     * Sets the cooldown for when another chat message can display.
     *
     * @param cooldown The {@link Integer} cooldown.
     */
    public void setChatCooldown(int cooldown) {
        this.chatCooldown = cooldown;
    }

    @Nullable
    public Direction getMoveDirection() {
        return this.moveDirection;
    }

    public void setMoveDirection(@Nullable Direction moveDirection) {
        this.moveDirection = moveDirection;
    }

    public int getMoveDelay() {
        return this.moveDelay;
    }

    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }

    @Nullable
    public Vec3 findTargetPoint() {
        Vec3 pos = this.targetPoint;
        if (pos != null) {
            return pos;
        } else {
            LivingEntity target = getTarget();
            return target == null ? null : target.position();
        }
    }

    @Nullable
    public Vec3 getTargetPoint() {
        return this.targetPoint;
    }

    public void setTargetPoint(@Nullable Vec3 targetPoint) {
        this.targetPoint = targetPoint;
    }

    public int attackCooldown() {
        return this.attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    /**
     * @return The {@link Integer} cooldown for when the Slider can move again.
     * Slightly randomized and dependent on whether the Slider is in critical mode.
     */
    public int calculateMoveDelay() {
        return this.isCritical() ? 1 + this.getRandom().nextInt(10) : 2 + this.getRandom().nextInt(14);
    }

    public static Direction calculateDirection(double x, double y, double z) {
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        double absZ = Math.abs(z);
        if (absY > absX && absY > absZ) {
            return y > 0 ? Direction.UP : Direction.DOWN;
        } else if (absX > absZ) {
            return x > 0 ? Direction.EAST : Direction.WEST;
        } else {
            return z > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    /**
     * Calculates a box adjacent to the original, with equal dimensions except for the axis it's translated along.
     *
     * @param box       The {@link AABB} bounding box.
     * @param direction The movement {@link Direction}.
     * @return The adjacent {@link AABB} bounding box.
     */
    public static AABB calculateAdjacentBox(AABB box, Direction direction) {
        double minX = box.minX;
        double minY = box.minY;
        double minZ = box.minZ;
        double maxX = box.maxX;
        double maxY = box.maxY;
        double maxZ = box.maxZ;
        if (direction == Direction.UP) {
            minY = maxY;
            maxY += 1;
        } else if (direction == Direction.DOWN) {
            maxY = minY;
            minY -= 1;
        } else if (direction == Direction.NORTH) {
            maxZ = minZ;
            minZ -= 1;
        } else if (direction == Direction.SOUTH) {
            minZ = maxZ;
            maxZ += 1;
        } else if (direction == Direction.EAST) {
            minX = maxX;
            maxX += 1;
        } else { // West
            maxX = minX;
            minX -= 1;
        }
        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * @return The {@link Float} for how much to add to the Slider's velocity.
     * Dependent on whether the Slider is in critical mode.
     */
    public float getVelocityIncrease() {
        return Math.max(this.isCritical() ? 0.045F - (this.getHealth() / 10000) : 0.035F - (this.getHealth() / 30000), 400.0F / 30000);
    }

    /**
     * @return A {@link Boolean} for whether the Slider is in critical mode.
     * The Slider goes critical when its health is at 1/4th.
     */
    public boolean isCritical() {
        return this.getHealth() <= this.getMaxHealth() / 4.0F;
    }

    /**
     * =
     *
     * @return The {@link Float} for the maximum velocity limit.
     */
    public float getMaxVelocity() {
        return 2.5F;
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.setBossName(name);
    }

    protected SoundEvent getAwakenSound() {
        return AetherIISoundEvents.ENTITY_SLIDER_AWAKEN.get();
    }

    public SoundEvent getCollideSound() {
        return AetherIISoundEvents.ENTITY_SLIDER_COLLIDE.get();
    }

    public SoundEvent getMoveSound() {
        return AetherIISoundEvents.ENTITY_SLIDER_MOVE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_SLIDER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_SLIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_SLIDER_DEATH.get();
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target.canBeSeenAsEnemy();
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being affected by explosions.
     */
    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public float getYRot() {
        return 0;
    }

    /**
     * @return A {@link Boolean} for whether the Slider can be collided with as if it were a block.
     * It can only be collided with when it is asleep.
     */
    public boolean canBeCollidedWith(Entity entity) {
        return !this.isAwake();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isAwake();
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being pushed.
     */
    @Override
    public boolean isPushable() {
        return false;
    }

    /**
     * @return A true {@link Boolean}, preventing the Slider from being affected by gravity.
     */
    @Override
    public boolean isNoGravity() {
        return true;
    }

    /**
     * @return A true {@link Boolean}, preventing the Slider from being affected by friction.
     */
    public boolean shouldDiscardFriction() {
        return true;
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being affected by liquids.
     */
    public boolean isAffectedByFluids() {
        return false;
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being on fire.
     */
    public boolean displayFireAnimation() {
        return false;
    }

    /**
     * @return A false {@link Boolean}, preventing the Slider from being affected by freezing.
     */
    @Override
    public boolean isFullyFrozen() {
        return false;
    }

    /**
     * Disallows the Slider from making footstep noises.
     *
     * @return The type of {@link net.minecraft.world.entity.Entity.MovementEmission}.
     */
    @Override
    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#addBossSaveData(CompoundTag)
     */
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        this.addBossSaveData(tag);
        tag.putBoolean("Awake", this.isAwake());
        tag.putFloat("LastHealthStage", this.lastHealthStage);
    }

    /**
     * @see com.aetherteam.nitrogen.entity.BossMob#readBossSaveData(CompoundTag)
     */
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readBossSaveData(tag);
        this.setAwake(tag.contains("Awake") && tag.getBoolean("Awake"));
        this.lastHealthStage = tag.contains("LastHealthStage") ? tag.getFloat("LastHealthStage") : this.getMaxHealth();
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
    }

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
    }
}
