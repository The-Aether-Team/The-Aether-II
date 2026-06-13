package com.aetherteam.aetherii.item.miscellaneous;

import com.aetherteam.aetherii.AetherII;
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
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class CompanionItem extends Item {
    private final Holder<EntityType<?>> companionType;
    private final Holder<SoundEvent> sound;

    public CompanionItem(Holder<EntityType<?>> companionType, Holder<SoundEvent> sound, Item.Properties properties) {
        super(properties.stacksTo(1));
        this.companionType = companionType;
        this.sound = sound;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget.getType() == this.getCompanionType()
                && interactionTarget instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && owner.getUUID().equals(player.getUUID())
                && ((!interactionTarget.getData(AetherIIDataAttachments.COMPANION) && stack.get(AetherIIDataComponents.COMPANION_UUID) == null) || UUIDsMatch(stack, interactionTarget))) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
            CompoundTag tag = removeCompanion(interactionTarget, player);
            stack.set(AetherIIDataComponents.COMPANION_UUID, interactionTarget.getUUID());
            stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
            player.setItemInHand(usedHand, stack);
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        UUID companionUUID = stack.get(AetherIIDataComponents.COMPANION_UUID);
        CompoundTag companionNBT = stack.get(AetherIIDataComponents.COMPANION_NBT);

        if (player != null && companionUUID != null) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (companionNBT != null) {
                Vec3 pos = player.position();
                for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(player.getRandom())) {
                    if (level.getBlockState(BlockPos.containing(pos.relative(direction, 1.0F))).isAir()) {
                        pos = pos.relative(direction, 0.5F).relative(Direction.UP, 0.25F);
                        break;
                    }
                }
                spawnCompanion(player, pos, companionUUID, companionNBT);
                stack.remove(AetherIIDataComponents.COMPANION_NBT);
                player.setItemInHand(hand, stack);
                return InteractionResult.SUCCESS_SERVER;
            } else {
                Entity companion = level.getEntity(companionUUID);
                if (companion instanceof LivingEntity living) {
                    CompoundTag tag = removeCompanion(living, player);
                    stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
                    player.setItemInHand(hand, stack);
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        ItemStack stack = context.getItemInHand();
        Direction face = context.getClickedFace();
        Vec3 pos = context.getClickLocation();

        UUID companionUUID = stack.get(AetherIIDataComponents.COMPANION_UUID);
        CompoundTag companionNBT = stack.get(AetherIIDataComponents.COMPANION_NBT);

        if (player != null && companionUUID != null) {
            if (companionNBT != null) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 1.0F, 1.0F);
                spawnCompanion(player, pos.relative(face, 0.25F), companionUUID, companionNBT);
                stack.remove(AetherIIDataComponents.COMPANION_NBT);
                player.setItemInHand(hand, stack);
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        Player player = context.player();
        MutableComponent status = Component.translatable("aether_ii.tooltip.item.companion.status.empty");
        if (itemStack.has(AetherIIDataComponents.COMPANION_NBT)) {
            if (player != null && player.getCooldowns().isOnCooldown(itemStack)) {
                status = Component.translatable("aether_ii.tooltip.item.companion.status.recovering");
            } else {
                status = Component.translatable("aether_ii.tooltip.item.companion.status.stored");
            }
        } else if (itemStack.has(AetherIIDataComponents.COMPANION_UUID)) {
            status = Component.translatable("aether_ii.tooltip.item.companion.status.active");
        }
        MutableComponent combined = Component.translatable("aether_ii.tooltip.item.companion.status", status).withStyle(ChatFormatting.GRAY);
        builder.accept(combined);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return itemStack.has(AetherIIDataComponents.COMPANION_UUID) && !itemStack.has(AetherIIDataComponents.COMPANION_NBT) || super.isFoil(itemStack);
    }

    public static void companionPostTick(EntityTickEvent.Post event) {
        Entity entity = event.getEntity();
        if (entity instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && entity.getData(AetherIIDataAttachments.COMPANION)) {
            if (owner.level().isClientSide()) {
                if (getMatchingStack(owner, entity).isEmpty()) {
                    ClientPacketDistributor.sendToServer(new StoreCompanionItemEntityPacket(entity.getId()));
                }
            }
        }
    }

    public static void entityChangeDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity && entity instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && entity.getData(AetherIIDataAttachments.COMPANION)) {
            InventoryMenu menu = owner.inventoryMenu;
            ItemStack stack = ItemStack.EMPTY;
            for (ItemStack inventoryStack : menu.getItems()) {
                if (UUIDsMatch(inventoryStack, entity)) {
                    stack = inventoryStack;
                }
            }
            if (!stack.isEmpty()) {
                CompoundTag tag = removeCompanion(livingEntity, owner);
                stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
            } else {
                if (owner.level().isClientSide()) {
                    ItemStack carriedStack = menu.getCarried();
                    if (UUIDsMatch(carriedStack, entity)) {
                        CompoundTag tag = removeCompanion(livingEntity, owner);
                        stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
                        ClientPacketDistributor.sendToServer(new DiscardCompanionPacket(entity.getId()));
                    }
                }
            }
            event.setCanceled(true);
        } else if (entity instanceof Player player) {
            findAndRetrieveCompanion(player);
        }
    }

    public static void companionDeath(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof OwnableEntity owned && owned.getOwner() instanceof Player owner && living.getData(AetherIIDataAttachments.COMPANION)) {
            InventoryMenu menu = owner.inventoryMenu;
            ItemStack stack = ItemStack.EMPTY;
            for (ItemStack inventoryStack : menu.getItems()) {
                if (UUIDsMatch(inventoryStack, living)) {
                    stack = inventoryStack;
                }
            }
            if (!stack.isEmpty()) {
                CompoundTag tag = removeCompanion(living, owner);
                stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
                owner.getCooldowns().addCooldown(stack, 1000);
                if (living.level() instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.SHOW_DEATH_MESSAGES) && owner instanceof ServerPlayer serverPlayer) {
                    serverPlayer.sendSystemMessage(Component.translatable("death.attack.aether_ii.retreat", living.getDisplayName()));
                }
            } else {
                if (owner.level().isClientSide()) {
                    ItemStack carriedStack = menu.getCarried();
                    if (UUIDsMatch(carriedStack, living)) {
                        CompoundTag tag = removeCompanion(living, owner);
                        stack.set(AetherIIDataComponents.COMPANION_NBT, tag);
                        ClientPacketDistributor.sendToServer(new DiscardCompanionDeathPacket(living.getId(), stack));
                    }
                }
            }
            event.setCanceled(true);
        }
    }

    public static void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        findAndRetrieveCompanion(player);
    }

    public static void findAndRetrieveCompanion(Player player) {
        InventoryMenu menu = player.inventoryMenu;
        for (ItemStack inventoryStack : menu.getItems()) {
            UUID thisUUID = inventoryStack.get(AetherIIDataComponents.COMPANION_UUID);
            if (thisUUID != null && player.level().getEntity(thisUUID) instanceof LivingEntity companion && companion.getData(AetherIIDataAttachments.COMPANION)) {
                CompoundTag tag = removeCompanion(companion, player);
                inventoryStack.set(AetherIIDataComponents.COMPANION_NBT, tag);
            }
        }
    }

    public static void spawnCompanion(Player owner, Vec3 pos, UUID companionUUID, CompoundTag companionNBT) {
        if (owner.level().getEntity(companionUUID) == null) {
            try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(owner.problemPath(), AetherII.LOGGER)) {
                ValueInput value = TagValueInput.create(reporter, owner.registryAccess(), companionNBT);
                EntityType.create(value, owner.level(), EntitySpawnReason.MOB_SUMMONED).ifPresent((entity) -> {
                    if (entity instanceof LivingEntity living && living.getHealth() <= 0) {
                        living.setHealth(living.getMaxHealth());
                        living.removeAllEffects();
                        living.clearFire();
                        living.clearFreeze();
                    }
                    entity.setDeltaMovement(Vec3.ZERO);
                    entity.snapTo(pos.x(), pos.y(), pos.z(), 0.0F, 0.0F); //todo this rotation isnt always consistent
                    owner.level().addFreshEntity(entity);
                    entity.setYRot(owner.getViewYRot(1.0F));
                    entity.setData(AetherIIDataAttachments.COMPANION, true);
                });
            }
        }
    }

    public static CompoundTag removeCompanion(LivingEntity companion, Player owner) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(companion.problemPath(), AetherII.LOGGER)) {
            TagValueOutput value = TagValueOutput.createWithContext(reporter, companion.registryAccess());
            String id = companion.getEncodeId();
            if (id != null) {
                value.putString("id", id);
            }
            companion.saveWithoutId(value);
            CompoundTag tag = value.buildResult();
            companion.discard();
            return tag;
        }
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
        UUID thisUUID = stack.get(AetherIIDataComponents.COMPANION_UUID);
        if (thisUUID != null) {
            return thisUUID.equals(entity.getUUID());
        }
        return false;
    }

    public EntityType<?> getCompanionType() {
        return this.companionType.value();
    }
}
