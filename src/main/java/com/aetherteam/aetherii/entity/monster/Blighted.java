package com.aetherteam.aetherii.entity.monster;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.world.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * This interface has several methods for handling monsters related to the Blight.
 */
public interface Blighted {
    String SLASH_WEAKNESS_NAME = AetherII.MODID + ":blight_entity.burning.slash_weakness";
    String IMPACT_WEAKNESS_NAME = AetherII.MODID + ":blight_entity.burning.impact_weakness";
    String PIERCE_WEAKNESS_NAME = AetherII.MODID + ":blight_entity.burning.pierce_weakness";
    UUID SLASH_WEAKNESS = UUID.nameUUIDFromBytes(SLASH_WEAKNESS_NAME.getBytes(StandardCharsets.UTF_8));
    UUID IMPACT_WEAKNESS = UUID.nameUUIDFromBytes(IMPACT_WEAKNESS_NAME.getBytes(StandardCharsets.UTF_8));
    UUID PIERCE_WEAKNESS = UUID.nameUUIDFromBytes(PIERCE_WEAKNESS_NAME.getBytes(StandardCharsets.UTF_8));

    /**
     * Call this from your entity's aiStep method.
     */
    default void weaken(LivingEntity livingEntity, RandomSource random, int tickCount, float particleRadius) {
        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            AttributeInstance slashResistance = livingEntity.getAttribute(AetherIIAttributes.SLASH_RESISTANCE.get());
            AttributeInstance impactResistance = livingEntity.getAttribute(AetherIIAttributes.IMPACT_RESISTANCE.get());
            AttributeInstance pierceResistance = livingEntity.getAttribute(AetherIIAttributes.PIERCE_RESISTANCE.get());

            if (slashResistance != null && slashResistance.getValue() < 0 && slashResistance.getModifier(SLASH_WEAKNESS) == null) {
                slashResistance.addTransientModifier(new AttributeModifier(SLASH_WEAKNESS, SLASH_WEAKNESS_NAME, 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
            }
            if (impactResistance != null && impactResistance.getValue() < 0 && impactResistance.getModifier(IMPACT_WEAKNESS) == null) {
                impactResistance.addTransientModifier(new AttributeModifier(IMPACT_WEAKNESS, IMPACT_WEAKNESS_NAME, 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
            }
            if (pierceResistance != null && pierceResistance.getValue() < 0 && pierceResistance.getModifier(PIERCE_WEAKNESS) == null) {
                pierceResistance.addTransientModifier(new AttributeModifier(PIERCE_WEAKNESS, PIERCE_WEAKNESS_NAME, 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
            }

            if (tickCount % 5 == 0) {
                for (int i = 0; i < 5; ++i) {
                    serverLevel.sendParticles(ParticleTypes.SMOKE, livingEntity.getRandomX(particleRadius), livingEntity.getRandomY(), livingEntity.getRandomZ(particleRadius), 1, 0, 0, 0, random.nextGaussian() * 0.02);
                }
            }
            if (tickCount % 20 == 0) {
                livingEntity.level().playSound(livingEntity, livingEntity.getOnPos(), AetherIISoundEvents.ENTITY_BLIGHTED_BURN.get(), SoundSource.HOSTILE, 0.5F, 1.0F);
            }
        }
    }

    default boolean inSunlight(Mob mob) {
        if (LevelUtil.isBrightOutside(mob.level()) && !mob.level().isClientSide()) {
            float f = mob.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(mob.getX(), mob.getEyeY(), mob.getZ());
            boolean flag = mob.isInWaterOrRain() || mob.isInPowderSnow || mob.wasInPowderSnow;
            return f > 0.5F && !flag && mob.level().canSeeSky(blockpos);
        }
        return false;
    }

    int getHideTime();

    void setHideTime(int hideTime);
}
