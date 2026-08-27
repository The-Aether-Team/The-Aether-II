package com.aetherteam.aetherii.mixin.mixins.common;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.attachment.living.EffectsSystemAttachment;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.passive.Moa;
import com.aetherteam.aetherii.item.equipment.EquipmentUtil;
import com.aetherteam.aetherii.mixin.MixinHooks;
import com.aetherteam.aetherii.mixin.mixins.common.accessor.LivingEntityAccessor;
import com.aetherteam.aetherii.network.packet.clientbound.SetVehiclePacket;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Mixin(Entity.class)
public class EntityMixin {
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
            if (serverLevel.dimension() == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
                if (entity.getY() <= serverLevel.getMinY() && !entity.isPassenger()) {
                    if (entity instanceof Player || entity.isVehicle() || (entity instanceof LivingEntity) && !((LivingEntity) entity).getItemBySlot(EquipmentSlot.SADDLE).isEmpty() || ((entity instanceof OwnableEntity) && ((OwnableEntity) entity).getOwner() != null)) { // Checks if an entity is a player, a vehicle of a player or a pet.
                        entityFell(entity);
                    } else if (entity instanceof Projectile projectile && projectile.getOwner() instanceof Player) {
                        entityFell(projectile);
                    } else if (entity instanceof ItemEntity itemEntity) {
                        if (itemEntity.getOwner() instanceof Player || itemEntity.hasData(AetherIIDataAttachments.DROPPED_ITEM)) { // Checks if an entity is an item that was dropped by a player.
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
            ServerLevel destination = minecraftserver.getLevel(Level.OVERWORLD);
            if (destination != null) {
                List<Entity> passengers = entity.getPassengers();
                ProfilerFiller profiler = Profiler.get();
                profiler.push("aether_fall");
                entity.setPortalCooldown();
                TeleportTransition transition = new TeleportTransition(destination, new Vec3(entity.getX(), destination.getMaxY(), entity.getZ()), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), Set.of(), TeleportTransition.DO_NOTHING);
                Entity target = entity.teleport(transition);
                profiler.pop();
                // Check for passengers.
                if (target != null) {
                    for (Entity passenger : passengers) {
                        passenger.stopRiding();
                        Entity nextPassenger = entityFell(passenger);
                        if (nextPassenger != null) {
                            nextPassenger.startRiding(target, true, false);
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

    @ModifyArgs(method = "move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;"))
    private void move(Args args) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity livingEntity) {
            EffectsSystemAttachment attachment = livingEntity.getData(AetherIIDataAttachments.EFFECTS_SYSTEM);
            Vec3 multiplier = attachment.getMotionMultiplier();
            if (multiplier.length() != new Vec3(1, 1, 1).length()) {
                Vec3 movement = args.get(0);
                args.set(0, movement.multiply(multiplier));
            }
        }
    }

    @WrapMethod(method = "checkFallDamage(DZLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V")
    private void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos, Operation<Void> original) {
        Entity entity = (Entity) (Object) this;
        if ((entity.getFirstPassenger() != null && entity.getFirstPassenger().getType() == AetherIIEntityTypes.AERBUNNY.get())
                || (entity instanceof LivingEntity livingEntity && livingEntity.getUseItem().is(AetherIITags.Items.TOOLS_GLIDERS))
                || (entity instanceof Player player && !player.onGround() && !(player.isInWater()/* || player.isInFluidType()*/) && ((LivingEntityAccessor) player).aether$isJumping() && ((LivingEntityAccessor) player).aether$getNoJumpDelay() == 0 && EquipmentUtil.hasArmorAbility(player, AetherIITags.Items.GRAVITITE_ARMOR) && !player.getData(AetherIIDataAttachments.ABILITY_BEHAVIOR).isGravititeJumpUsed())) {
            entity.resetFallDistance();
        } else {
            original.call(y, onGround, state, pos);
        }
    }
}
