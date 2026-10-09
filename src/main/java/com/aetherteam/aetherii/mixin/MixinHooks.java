package com.aetherteam.aetherii.mixin;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.natural.AetherGrassBlock;
import com.aetherteam.aetherii.block.natural.Snowable;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDamageTypes;
import com.aetherteam.aetherii.entity.attributes.AetherIIAttributes;
import com.aetherteam.aetherii.entity.monster.PlantMob;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.item.components.BrokenStack;
import com.aetherteam.aetherii.network.packet.clientbound.AttackShockParticlePacket;
import com.aetherteam.aetherii.network.packet.clientbound.AttackStabParticlePacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector3f;

public class MixinHooks {
    public static void handleSnowfall(ServerLevel serverLevel, BlockPos heightmapPos, BlockPos belowHeightmapPos, Holder<Biome> biomeHolder) {
        Biome biome = biomeHolder.value();
        BlockState belowState = serverLevel.getBlockState(belowHeightmapPos);

        if (serverLevel.isAreaLoaded(belowHeightmapPos, 1)) {
            if (biome.shouldFreeze(serverLevel, belowHeightmapPos)) {
                serverLevel.setBlockAndUpdate(belowHeightmapPos, AetherIIBlocks.ARCTIC_ICE.get().defaultBlockState());
            }
        }

        Biome.Precipitation precipitation = biome.getPrecipitationAt(belowHeightmapPos, serverLevel.getSeaLevel());
        if (serverLevel.isRaining() && precipitation != Biome.Precipitation.NONE) {
            int i = serverLevel.getGameRules().get(GameRules.MAX_SNOW_ACCUMULATION_HEIGHT);
            if (i > 0 && AetherGrassBlock.shouldSnow(biome, serverLevel, heightmapPos)) {
                boolean hasNeighborSnow = false;
                for (Direction direction : Direction.Plane.HORIZONTAL) {
                    for (int y = -1; y <= 1; y++) {
                        BlockPos relative = heightmapPos.relative(direction).above(y);
                        BlockState relativeState = serverLevel.getBlockState(relative);
                        if (relativeState.is(AetherIIBlocks.ARCTIC_SNOW) || (relativeState.getBlock() instanceof Snowable snowable && snowable.isSnowy(relativeState))) {
                            hasNeighborSnow = true;
                            break;
                        }
                    }
                }
                if (hasNeighborSnow) {
                    BlockState blockState = serverLevel.getBlockState(heightmapPos);
                    if (blockState.is(AetherIIBlocks.ARCTIC_SNOW.get())) {
                        int layers = blockState.getValue(SnowLayerBlock.LAYERS);
                        if (layers < Math.min(i, 8)) {
                            BlockState blockstate1 = blockState.setValue(SnowLayerBlock.LAYERS, layers + 1);
                            Block.pushEntitiesUp(blockState, blockstate1, serverLevel, heightmapPos);
                            serverLevel.setBlockAndUpdate(heightmapPos, blockstate1);
                        }
                    } else if (AetherGrassBlock.plantNotSnowed(blockState) && blockState.getBlock() instanceof Snowable snowable) {
                        serverLevel.setBlockAndUpdate(heightmapPos, snowable.setSnowy(blockState));
                    } else if (!belowState.is(AetherIITags.Blocks.CANNOT_SUPPORT_SNOWFALL)) {
                        serverLevel.setBlockAndUpdate(heightmapPos, AetherIIBlocks.ARCTIC_SNOW.get().defaultBlockState());
                    }
                }
            }

            belowState.getBlock().handlePrecipitation(belowState, serverLevel, belowHeightmapPos, precipitation);
        }
    }

    public static void shortswordSlashBehavior(Player player, Entity target, boolean canShortswordSlash) {
        if (canShortswordSlash) {
            float sweepRange = (float) player.getAttributeValue(AetherIIAttributes.SWEEP_RANGE);
            if (sweepRange > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(sweepRange, sweepRange, sweepRange))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker()) && !(other instanceof PlantMob)) {
                        float sweepKnockback = (float) player.getAttributeValue(AetherIIAttributes.SWEEP_KNOCKBACK);
                        float sweepDamage = (float) player.getAttributeValue(AetherIIAttributes.SWEEP_DAMAGE);
                        if (sweepKnockback > 0) {
                            other.knockback(sweepKnockback, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * sweepKnockback, -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * sweepKnockback);
                        }
                        if (sweepDamage > 0) {
                            other.hurt(AetherIIDamageTypes.playerAoe(player.level(), player), sweepDamage);
                        }
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
                sweepAttack(player);
            }
        }
    }

    private static void sweepAttack(Player player) {
        double d0 = -Mth.sin(player.getYRot() * (float) (Math.PI / 180.0));
        double d1 = Mth.cos(player.getYRot() * (float) (Math.PI / 180.0));
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(AetherIIParticleTypes.SWEEP_ATTACK.get(), player.getX() + d0, player.getY(0.5), player.getZ() + d1, 0, d0, 0.0, d1, 0.0);
        }
    }

    public static void hammerShockBehavior(Player player, Entity target, boolean canHammerShock) {
        if (canHammerShock) {
            float shockRange = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_RANGE);
            if (shockRange > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(shockRange, shockRange, shockRange))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker()) && !(other instanceof PlantMob)) {
                        double x = target.position().x() - other.position().x();
                        double z = target.position().z() - other.position().z();
                        if (x > 0) {
                            x = 5;
                        } else if (x < 0) {
                            x = -5;
                        }
                        if (z > 0) {
                            z = 5;
                        } else if (z < 0) {
                            z = -5;
                        }

                        float shockKnockback = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_KNOCKBACK);
                        float shockDamage = (float) player.getAttributeValue(AetherIIAttributes.SHOCK_DAMAGE);
                        if (shockKnockback > 0) {
                            other.knockback(shockKnockback, x * shockKnockback, z * shockKnockback);
                        }
                        if (shockDamage > 0) {
                            other.hurt(AetherIIDamageTypes.playerAoe(player.level(), player), shockDamage);
                        }
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.PLAYER_ATTACK_SHOCK.get(), player.getSoundSource(), 1.0F, 1.0F);
                shockAttack(player, target);
            }
        }
    }

    private static void shockAttack(Player player, Entity target) {
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new AttackShockParticlePacket(target.position().toVector3f(), player.getViewYRot(1.0F)));
        }
    }

    public static void pikeStabBehavior(Player player, Entity target, boolean canPikeStab) {
        if (canPikeStab) {
            float stabRadius = (float) player.getAttributeValue(AetherIIAttributes.STAB_RADIUS);
            float stabDistance = (float) player.getAttributeValue(AetherIIAttributes.STAB_DISTANCE);
            if (stabRadius > 0 || stabDistance > 0) {
                for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(stabDistance, stabDistance, stabDistance), (other) -> withinStabDistance(player, target, other, stabRadius, stabDistance))) {
                    if (other != player && other != target && !player.isAlliedTo(other) && (!(other instanceof ArmorStand armorStand) || !armorStand.isMarker()) && !(other instanceof PlantMob)) {
                        float stabKnockback = (float) player.getAttributeValue(AetherIIAttributes.STAB_KNOCKBACK);
                        float stabDamage = (float) player.getAttributeValue(AetherIIAttributes.STAB_DAMAGE);
                        if (stabKnockback > 0) {
                            other.knockback(stabKnockback, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * stabKnockback, -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * 2.5 * stabKnockback);
                        }
                        if (stabDamage > 0) {
                            other.hurt(AetherIIDamageTypes.playerAoeNoKnockback(player.level(), player), stabDamage);
                        }
                    }
                }
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), AetherIISoundEvents.PLAYER_ATTACK_STAB.get(), player.getSoundSource(), 1.0F, 1.0F);
            }
            stabAttack(player, target);
        }
    }

    private static boolean withinStabDistance(Entity player, Entity target, Entity other, double radialBounds, double forwardBounds) {
        Vec3 playerPosition = player.position();
        Vec3 targetPosition = target.position();
        Vec3 otherPosition = other.position();

        Vec3 playerToTarget = targetPosition.subtract(playerPosition);
        Vec3 playerToOther = otherPosition.subtract(playerPosition);

        double betweenAngle = Math.acos((playerToTarget.dot(playerToOther)) / (playerToTarget.length() * playerToOther.length()));
        double radialDistance = playerToOther.length() * Math.sin(betweenAngle);
        double forwardDistance = playerToOther.length() * Math.cos(betweenAngle);

        return radialDistance <= radialBounds && forwardDistance <= forwardBounds;
    }

    private static void stabAttack(Player player, Entity target) {
        if (player instanceof ServerPlayer serverPlayer) {
            Vector3f playerPos = new Vector3f((float) player.position().x(), (float) player.getEyeY(), (float) player.position().z());
            Vector3f targetPos = target.position().toVector3f();
            PacketDistributor.sendToPlayer(serverPlayer, new AttackStabParticlePacket(playerPos, targetPos));
        }
    }

    public static void breakLootItem(ItemStack itemStack, LivingEntity livingEntity) {
        if (itemStack.is(AetherIITags.Items.UNBREAKABLE_LOOT)) {
            EquipmentSlot slot = livingEntity.getEquipmentSlotForItem(itemStack);
            ItemStack brokenItem = getBrokenLootStack(itemStack);
            livingEntity.setItemSlot(slot, brokenItem);
        }
    }

    public static ItemStack getBrokenLootStack(ItemStack itemStack) {
        ItemStack brokenItem = new ItemStack(AetherIIItems.BROKEN_ITEM.get());
        brokenItem.set(AetherIIDataComponents.BROKEN_STACK, new BrokenStack(itemStack.copy()));
        Identifier modelLocation = itemStack.get(DataComponents.ITEM_MODEL);
        if (modelLocation != null) {
            brokenItem.set(DataComponents.ITEM_MODEL, modelLocation.withSuffix("_broken"));
        }
        brokenItem.set(DataComponents.ITEM_NAME, Component.translatable("item.aether_ii.broken_item_template", itemStack.get(DataComponents.ITEM_NAME)));
        Integer maxDamage = itemStack.get(DataComponents.MAX_DAMAGE);
        if (maxDamage != null) {
            brokenItem.set(DataComponents.MAX_DAMAGE, maxDamage);
            brokenItem.set(DataComponents.DAMAGE, maxDamage - 1);
        }
        brokenItem.set(DataComponents.RARITY, itemStack.get(DataComponents.RARITY));
        return brokenItem;
    }

    public static ParticleOptions replaceSplashParticles(Entity entity, BlockPos pos, ParticleOptions particleOptions) {
        if (entity.level() instanceof ClientLevel clientLevel && clientLevel.getBiome(pos).is(AetherIITags.Biomes.THE_AETHER)) {
            if (particleOptions == ParticleTypes.SPLASH) {
                return AetherIIParticleTypes.SPLASH.get();
            }
        }
        return particleOptions;
    }
}
