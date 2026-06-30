package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.SetVehiclePacket;
import com.aetherteam.aetherii.world.LevelUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.PacketDistributor;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

@Mixin(Entity.class)
public class EntityMixin {
    private static final ITeleporter AETHER_FALL_TELEPORTER = new ITeleporter() {
        @Nullable
        @Override
        public PortalInfo getPortalInfo(Entity entity, ServerLevel destinationLevel, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
            return new PortalInfo(new Vec3(entity.getX(), destinationLevel.getMaxBuildHeight(), entity.getZ()), Vec3.ZERO, entity.getYRot(), entity.getXRot());
        }

        @Override
        public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceLevel, ServerLevel destinationLevel) {
            return false;
        }
    };

    @ModifyArg(method = "doWaterSplashEffect()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"), index = 0)
    private ParticleOptions doWaterSplashEffect(ParticleOptions particleOptions) {
        Entity entity = (Entity) (Object) this;
        if (entity.level().isClientSide()) {
            return MixinHooks.replaceSplashParticles(entity, entity.blockPosition(), particleOptions);
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
            if (serverLevel.dimension() == LevelUtil.destinationDimension()) {
                if (entity.getY() <= serverLevel.getMinBuildHeight() && !entity.isPassenger()) {
                    if (entity instanceof Player || entity.isVehicle() || ((entity instanceof OwnableEntity) && ((OwnableEntity) entity).getOwner() != null)) { // Checks if an entity is a player, a vehicle of a player or a pet.
                        entityFell(entity);
                    } else if (entity instanceof Projectile projectile && projectile.getOwner() instanceof Player) {
                        entityFell(projectile);
                    } else if (entity instanceof ItemEntity itemEntity) {
                        if (itemEntity.getOwner() instanceof Player || AetherIIDataAttachments.get(itemEntity, AetherIIDataAttachments.DROPPED_ITEM).getOwner(level) instanceof Player) { // Checks if an entity is an item that was dropped by a player.
                            entityFell(entity);
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
            ServerLevel destination = minecraftserver.getLevel(LevelUtil.returnDimension());
            if (destination != null) {
                List<Entity> passengers = entity.getPassengers();
                ProfilerFiller profiler = serverLevel.getProfiler();
                profiler.push("aether_fall");
                entity.setPortalCooldown();
                Entity target = entity.changeDimension(destination, AETHER_FALL_TELEPORTER);
                profiler.pop();
                // Check for passengers.
                if (target != null) {
                    for (Entity passenger : passengers) {
                        passenger.stopRiding();
                        Entity nextPassenger = entityFell(passenger);
                        if (nextPassenger != null) {
                            nextPassenger.startRiding(target, true);
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

    @ModifyExpressionValue(
            method = "collide",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;onGround()Z")
    )
    private boolean collide(boolean original) {
        Entity entity = (Entity) (Object) this;
        return original || entity instanceof Moa moa && moa.hasControllingPassenger();
    }

    @WrapOperation(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;"))
    private Vec3 move(Entity instance, Vec3 movement, MoverType moverType, Operation<Vec3> original) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity livingEntity) {
            EffectsSystemAttachment attachment = AetherIIDataAttachments.get(livingEntity, AetherIIDataAttachments.EFFECTS_SYSTEM);
            Vec3 multiplier = attachment.getMotionMultiplier();
            if (multiplier.length() != new Vec3(1, 1, 1).length()) {
                movement = movement.multiply(multiplier);
            }
        }
        return original.call(instance, movement, moverType);
    }

    @Inject(method = "checkFallDamage(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V", at = @At("HEAD"), cancellable = true)
    private void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if ((entity.getFirstPassenger() != null && entity.getFirstPassenger().getType() == AetherIIEntityTypes.AERBUNNY.get())
                || (entity instanceof LivingEntity livingEntity && livingEntity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS))
                || (entity instanceof Player player && !player.onGround() && !(player.isInWater()/* || player.isInFluidType()*/) && ((LivingEntityAccessor) player).aether$isJumping() && ((LivingEntityAccessor) player).aether$getNoJumpDelay() == 0 && EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.GRAVITITE_ARMOR) && !AetherIIDataAttachments.get(player, AetherIIDataAttachments.ABILITY_BEHAVIOR).isGravititeJumpUsed())) {
            entity.resetFallDistance();
            ci.cancel();
        }
    }
}
