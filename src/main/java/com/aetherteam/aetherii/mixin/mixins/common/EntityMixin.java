package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.client.particle.AetherIIParticleTypes;
import com.aetherteam.aetherii.client.renderer.level.HighlandsSpecialEffects;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.network.packet.clientbound.SetVehiclePacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyArg(method = "doWaterSplashEffect()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), index = 0)
    private ParticleOptions doWaterSplashEffect(ParticleOptions particleOptions) {
        Entity entity = (Entity) (Object) this;
        if (entity.level() instanceof ClientLevel clientLevel && clientLevel.effects() instanceof HighlandsSpecialEffects) {
            if (particleOptions == ParticleTypes.SPLASH) {
                return AetherIIParticleTypes.SPLASH.get();
            }
        }
        return particleOptions;
    }

    /**
     * Handles entities falling out of the Aether. If an entity is not a player, vehicle, or tracked item, it is removed.
     *
     * @param ci The {@link CallbackInfo} for the void method return.
     * @see PhoenixArmor#boostVerticalLavaSwimming(LivingEntity)
     */
    @Inject(at = @At(value = "TAIL"), method = "tick()V")
    private void travel(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        Level level = entity.level();
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.dimension() == AetherIIDimensions.AETHER_HIGHLANDS_LEVEL) {
                if (entity.getY() <= serverLevel.getMinBuildHeight() && !entity.isPassenger()) {
                    if (entity instanceof Player || entity.isVehicle() || (entity instanceof Saddleable) && ((Saddleable) entity).isSaddled()) { // Checks if an entity is a player or a vehicle of a player.
                        entityFell(entity);
                    } else if (entity instanceof Projectile projectile && projectile.getOwner() instanceof Player) {
                        entityFell(projectile);
                    } else if (entity instanceof ItemEntity itemEntity) {
                        if (itemEntity.hasData(AetherIIDataAttachments.DROPPED_ITEM)) {
                            if (itemEntity.getOwner() instanceof Player || itemEntity.getData(AetherIIDataAttachments.DROPPED_ITEM).getOwner(level) instanceof Player) { // Checks if an entity is an item that was dropped by a player.
                                entityFell(entity);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Code to handle falling out of the Aether with all passengers intact.
     *
     * @param entity The {@link Entity}
     */
    @Nullable
    private static Entity entityFell(Entity entity) {
        Level serverLevel = entity.level();
        MinecraftServer minecraftserver = serverLevel.getServer();
        if (minecraftserver != null) {
            ServerLevel destination = minecraftserver.getLevel(Level.OVERWORLD);
            if (destination != null) {
                List<Entity> passengers = entity.getPassengers();
                serverLevel.getProfiler().push("aether_fall");
                entity.setPortalCooldown();
                DimensionTransition transition = new DimensionTransition(destination, new Vec3(entity.getX(), destination.getMaxBuildHeight(), entity.getZ()), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), false, DimensionTransition.DO_NOTHING);
                Entity target = entity.changeDimension(transition);
                serverLevel.getProfiler().pop();
                // Check for passengers.
                if (target != null) {
                    for (Entity passenger : passengers) {
                        passenger.stopRiding();
                        Entity nextPassenger = entityFell(passenger);
                        if (nextPassenger != null) {
                            nextPassenger.startRiding(target);
                            if (target instanceof ServerPlayer serverPlayer) { // Fixes a desync between the server and client.
                                PacketDistributor.sendToPlayer(serverPlayer, new SetVehiclePacket(nextPassenger.getId(), target.getId()));
                            }
                        }
                    }
                }
                return target;
            }
        }
        return null;
    }
}
