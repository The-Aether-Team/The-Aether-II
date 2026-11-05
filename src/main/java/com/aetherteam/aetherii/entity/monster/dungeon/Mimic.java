package com.aetherteam.aetherii.entity.monster.dungeon;

import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Optional;

public class Mimic extends Monster {
    private Optional<ResourceKey<LootTable>> lootTableFromChest = Optional.empty();

    public Mimic(EntityType<? extends Mimic> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.5)
                .add(Attributes.MOVEMENT_SPEED, 0.33)
                .add(Attributes.FOLLOW_RANGE, 12.0);
    }

    /**
     * Prevents Mimics from hurting each other and spawns particles when one is hurt by any other entity, and sets the entity as a target.
     *
     * @param source The {@link DamageSource}.
     * @param amount The {@link Float} amount of damage.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (!(source.getDirectEntity() instanceof Mimic)) {
            if (source.getDirectEntity() instanceof LivingEntity livingEntity && this.hurtTime == 0) {
                if (!(livingEntity instanceof Player player) || !player.isCreative()) {
                    this.setTarget(livingEntity);
                }
            }
            return super.hurtServer(level, source, amount);
        } else {
            return false;
        }
    }

    /**
     * Handle sounds when a target is hurt.
     *
     * @param entity The hurt {@link Entity}.
     * @return Whether the entity was hurt, as a {@link Boolean}.
     */
    @Override
    public boolean doHurtTarget(ServerLevel level, Entity entity) {
        boolean result = super.doHurtTarget(level, entity);
        if (entity instanceof LivingEntity livingEntity) { // Choose between attack or kill sound depending on remaining target health.
            SoundEvent sound = livingEntity.getHealth() <= 0.0 ? AetherIISoundEvents.ENTITY_MIMIC_KILL.get() : AetherIISoundEvents.ENTITY_MIMIC_ATTACK.get();
            this.playSound(sound, 1.0F, this.getVoicePitch());
        }
        return result;
    }

    @Override
    protected void dropFromLootTable(ServerLevel level, DamageSource source, boolean playerKilled) {
        super.dropFromLootTable(level, source, playerKilled);
        Optional<ResourceKey<LootTable>> lootTable = this.getLootTableFromChest();
        lootTable.ifPresent(lootTableResourceKey -> this.dropFromLootTable(level, source, playerKilled, lootTableResourceKey));
    }

    public void setLootTableFromChest(Optional<ResourceKey<LootTable>> lootTableFromChest) {
        this.lootTableFromChest = lootTableFromChest;
    }

    public Optional<ResourceKey<LootTable>> getLootTableFromChest() {
        return this.lootTableFromChest;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_MIMIC_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_MIMIC_DEATH.get();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        this.lootTableFromChest.ifPresent((lootTable) -> output.store("LootTableFromChest", LootTable.KEY_CODEC, lootTable));
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.lootTableFromChest = input.read("LootTableFromChest", LootTable.KEY_CODEC);
    }
}

