package com.aetherteam.aetherii.item.miscellaneous;

import java.util.List;

import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.item.components.AetherIIDataComponents;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardCompanionDeathPacket;
import com.aetherteam.aetherii.network.packet.serverbound.DiscardCompanionPacket;
import com.aetherteam.aetherii.network.packet.serverbound.StoreCompanionItemEntityPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.aetherteam.aetherii.network.ClientPacketDistributor;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CompanionItem extends Item {
    private final Supplier<? extends EntityType<?>> companionType;
    private final Holder<SoundEvent> sound;

    public CompanionItem(Supplier<? extends EntityType<?>> companionType, Holder<SoundEvent> sound, Item.Properties properties) {
        super(properties.stacksTo(1));
        this.companionType = companionType;
        this.sound = sound;
    }

    public CompanionItem(Holder<EntityType<?>> companionType, Holder<SoundEvent> sound, Item.Properties properties) {
        this(companionType::value, sound, properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget.getType() == this.getCompanionType()
                && interactionTarget instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && owner.getUUID().equals(player.getUUID())
                && ((!AetherIIDataAttachments.get(interactionTarget, AetherIIDataAttachments.COMPANION) && AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_UUID) == null) || UUIDsMatch(stack, interactionTarget))) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound.value(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            CompoundTag tag = removeCompanion(interactionTarget, player);
            AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_UUID, interactionTarget.getUUID());
            AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_NBT, tag);
            player.setItemInHand(usedHand, stack);
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        UUID companionUUID = AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_UUID);
        CompoundTag companionNBT = AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_NBT);

        if (player != null && companionUUID != null) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound.value(), SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (companionNBT != null) {
                Vec3 pos = player.position();
                for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(player.getRandom())) {
                    if (level.getBlockState(BlockPos.containing(pos.relative(direction, 1.0F))).isAir()) {
                        pos = pos.relative(direction, 0.5F);
                        break;
                    }
                }
                spawnCompanion(player, pos, companionUUID, companionNBT);
                AetherIIDataComponents.remove(stack, AetherIIDataComponents.COMPANION_NBT);
                player.setItemInHand(hand, stack);
                return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
            } else {
                Entity companion = level instanceof ServerLevel serverLevel ? serverLevel.getEntity(companionUUID) : null;
                if (companion instanceof LivingEntity living) {
                    CompoundTag tag = removeCompanion(living, player);
                    AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_NBT, tag);
                    player.setItemInHand(hand, stack);
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                }
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        ItemStack stack = context.getItemInHand();
        Direction face = context.getClickedFace();
        BlockPos pos = context.getClickedPos();

        UUID companionUUID = AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_UUID);
        CompoundTag companionNBT = AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_NBT);

        if (player != null && companionUUID != null) {
            if (companionNBT != null) {
                Vec3 spawnPos = Vec3.atBottomCenterOf(pos.relative(face));
                if (face.getAxis().isVertical()) {
                    BlockState blockState = level.getBlockState(pos);
                    VoxelShape shape = blockState.getCollisionShape(level, pos);
                    spawnPos = Vec3.atBottomCenterOf(pos).relative(face, shape.isEmpty() ? 0 : shape.bounds().getYsize());
                }
                if (!level.getBlockState(BlockPos.containing(spawnPos)).isSolid()) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound.value(), SoundSource.NEUTRAL, 1.0F, 1.0F);
                    spawnCompanion(player, spawnPos, companionUUID, companionNBT);
                    AetherIIDataComponents.remove(stack, AetherIIDataComponents.COMPANION_NBT);
                    player.setItemInHand(hand, stack);
                    return InteractionResult.sidedSuccess(level.isClientSide());
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, net.minecraft.world.level.Level level, List<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, builder, tooltipFlag);
        Player player = null;
        MutableComponent status = Component.translatable("aether_ii.tooltip.item.companion.status.empty");
        if (AetherIIDataComponents.has(itemStack, AetherIIDataComponents.COMPANION_NBT)) {
            if (player != null && player.getCooldowns().isOnCooldown(itemStack.getItem())) {
                status = Component.translatable("aether_ii.tooltip.item.companion.status.recovering");
            } else {
                status = Component.translatable("aether_ii.tooltip.item.companion.status.stored");
            }
        } else if (AetherIIDataComponents.has(itemStack, AetherIIDataComponents.COMPANION_UUID)) {
            status = Component.translatable("aether_ii.tooltip.item.companion.status.active");
        }
        MutableComponent combined = Component.translatable("aether_ii.tooltip.item.companion.status", status).withStyle(ChatFormatting.GRAY);
        builder.add(combined);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return AetherIIDataComponents.has(itemStack, AetherIIDataComponents.COMPANION_UUID) && !AetherIIDataComponents.has(itemStack, AetherIIDataComponents.COMPANION_NBT) || super.isFoil(itemStack);
    }

    public static void companionPostTick(LivingEvent.LivingTickEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && AetherIIDataAttachments.get(entity, AetherIIDataAttachments.COMPANION)) {
            if (owner.level().isClientSide()) {
                if (getMatchingStack(owner, entity).isEmpty()) {
                    ClientPacketDistributor.sendToServer(new StoreCompanionItemEntityPacket(entity.getId()));
                }
            }
        }
    }

    public static void entityChangeDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity companion && companion instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && AetherIIDataAttachments.get(entity, AetherIIDataAttachments.COMPANION)) {
            if (isCompanionSeparateFromPlayer(owner, companion)) {
                InventoryMenu menu = owner.inventoryMenu;
                ItemStack stack = ItemStack.EMPTY;
                for (ItemStack inventoryStack : menu.getItems()) {
                    if (UUIDsMatch(inventoryStack, entity)) {
                        stack = inventoryStack;
                    }
                }
                if (!stack.isEmpty()) {
                    CompoundTag tag = removeCompanion(companion, owner);
                    AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_NBT, tag);
                } else {
                    if (owner.level().isClientSide()) {
                        ItemStack carriedStack = menu.getCarried();
                        if (UUIDsMatch(carriedStack, entity)) {
                            CompoundTag tag = removeCompanion(companion, owner);
                            AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_NBT, tag);
                            ClientPacketDistributor.sendToServer(new DiscardCompanionPacket(entity.getId()));
                        }
                    }
                }
                event.setCanceled(true);
            }
        } else if (entity instanceof Player owner) {
            InventoryMenu menu = owner.inventoryMenu;
            for (ItemStack inventoryStack : menu.getItems()) {
                UUID thisUUID = AetherIIDataComponents.get(inventoryStack, AetherIIDataComponents.COMPANION_UUID);
                Entity companionEntity = owner.level() instanceof ServerLevel serverLevel ? serverLevel.getEntity(thisUUID) : null;
                if (thisUUID != null && companionEntity instanceof LivingEntity companion && AetherIIDataAttachments.get(companion, AetherIIDataAttachments.COMPANION)) {
                    if (isCompanionSeparateFromPlayer(owner, companion)) {
                        CompoundTag tag = removeCompanion(companion, owner);
                        AetherIIDataComponents.set(inventoryStack, AetherIIDataComponents.COMPANION_NBT, tag);
                    }
                }
            }
        }
    }

    public static void companionDeath(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && AetherIIDataAttachments.get(living, AetherIIDataAttachments.COMPANION)) {
            InventoryMenu menu = owner.inventoryMenu;
            ItemStack stack = ItemStack.EMPTY;
            for (ItemStack inventoryStack : menu.getItems()) {
                if (UUIDsMatch(inventoryStack, living)) {
                    stack = inventoryStack;
                }
            }
            if (!stack.isEmpty()) {
                CompoundTag tag = removeCompanion(living, owner);
                AetherIIDataComponents.set(stack, AetherIIDataComponents.COMPANION_NBT, tag);
                owner.getCooldowns().addCooldown(stack.getItem(), 1000);
                if (living.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && owner instanceof ServerPlayer serverPlayer) {
                    serverPlayer.sendSystemMessage(Component.translatable("death.attack.aether_ii.retreat", living.getDisplayName()));
                }
            } else {
                if (owner.level().isClientSide()) {
                    ItemStack carriedStack = menu.getCarried();
                    if (UUIDsMatch(carriedStack, living)) {
                        CompoundTag tag = removeCompanion(living, owner);
                        AetherIIDataComponents.set(carriedStack, AetherIIDataComponents.COMPANION_NBT, tag);
                        ClientPacketDistributor.sendToServer(new DiscardCompanionDeathPacket(living.getId(), stack));
                    }
                }
            }
            event.setCanceled(true);
        }
    }

    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        InventoryMenu menu = player.inventoryMenu;
        for (ItemStack inventoryStack : menu.getItems()) {
            UUID thisUUID = AetherIIDataComponents.get(inventoryStack, AetherIIDataComponents.COMPANION_UUID);
            Entity entity = player.level() instanceof ServerLevel serverLevel ? serverLevel.getEntity(thisUUID) : null;
            if (thisUUID != null && entity instanceof LivingEntity companion && AetherIIDataAttachments.get(companion, AetherIIDataAttachments.COMPANION)) {
                CompoundTag tag = removeCompanion(companion, player);
                AetherIIDataComponents.set(inventoryStack, AetherIIDataComponents.COMPANION_NBT, tag);
            }
        }
    }

    public static void spawnCompanion(Player owner, Vec3 pos, UUID companionUUID, CompoundTag companionNBT) {
        if (!(owner.level() instanceof ServerLevel serverLevel) || serverLevel.getEntity(companionUUID) == null) {
            EntityType.create(companionNBT, owner.level()).ifPresent((entity) -> {
                if (entity instanceof LivingEntity living && living.getHealth() <= 0) {
                    living.setHealth(living.getMaxHealth());
                    living.removeAllEffects();
                    living.clearFire();
                    living.setTicksFrozen(0);
                }
                entity.setDeltaMovement(Vec3.ZERO);
                entity.moveTo(pos.x(), pos.y(), pos.z(), 0.0F, 0.0F); //todo this rotation isnt always consistent
                owner.level().addFreshEntity(entity);
                entity.setYRot(owner.getViewYRot(1.0F));
                AetherIIDataAttachments.set(entity, AetherIIDataAttachments.COMPANION, true);
            });
        }
    }

    public static CompoundTag removeCompanion(LivingEntity companion, Player owner) {
        CompoundTag tag = new CompoundTag();
        String id = companion.getEncodeId();
        if (id != null) {
            tag.putString("id", id);
        }
        companion.saveWithoutId(tag);
        companion.discard();
        return tag;
    }

    public static boolean isCompanionSeparateFromPlayer(Player owner, Entity companion) {
        return (companion.getFirstPassenger() == null || !companion.getFirstPassenger().getUUID().equals(owner.getUUID()))
                && (companion.getVehicle() == null || !companion.getVehicle().getUUID().equals(owner.getUUID()))
                && (owner.getFirstPassenger() == null || !owner.getFirstPassenger().getUUID().equals(companion.getUUID()))
                && (owner.getVehicle() == null || !owner.getVehicle().getUUID().equals(companion.getUUID()));
    }

    public static ItemStack getMatchingStack(Player player, Entity entity) {
        InventoryMenu menu = player.inventoryMenu;
        ItemStack carriedStack = menu.getCarried();
        if (UUIDsMatch(carriedStack, entity)) {
            return carriedStack;
        }
        for (ItemStack inventoryStack : menu.getItems()) {
            if (UUIDsMatch(inventoryStack, entity)) {
                return inventoryStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean UUIDsMatch(ItemStack stack, Entity entity) {
        UUID thisUUID = AetherIIDataComponents.get(stack, AetherIIDataComponents.COMPANION_UUID);
        if (thisUUID != null) {
            return thisUUID.equals(entity.getUUID());
        }
        return false;
    }

    public EntityType<?> getCompanionType() {
        return this.companionType.get();
    }
}
