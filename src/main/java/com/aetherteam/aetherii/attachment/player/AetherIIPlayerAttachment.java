package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.portal.PortalClientUtil;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.ToggleItem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.player.ClientInput;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dialog.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AetherIIPlayerAttachment {
    private boolean isMoving;
    private boolean isJumping;
    private boolean useToggled = false;

    private boolean canGetPortal = true;
    private boolean canSpawnInAether = true;

    public float portalIntensity;
    public float oPortalIntensity;

    public List<EntityType<?>> stuckProjectiles = new ArrayList<>();
    public int removeStuckProjectileTime = 0;

    private boolean sentChatMessage = false;

//    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
//            Map.entry("setMoving", Triple.of(Type.BOOLEAN, (object) -> this.setMoving((boolean) object), this::isMoving)),
//            Map.entry("setJumping", Triple.of(Type.BOOLEAN, (object) -> this.setJumping((boolean) object), this::isJumping)),
//            Map.entry("setShouldSyncBetweenClients", Triple.of(Type.BOOLEAN, (object) -> this.setShouldSyncBetweenClients((boolean) object), this::shouldSyncBetweenClients)) //todo ?
//    );

    public static final MapCodec<AetherIIPlayerAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.fieldOf("is_moving").forGetter(AetherIIPlayerAttachment::isMoving),
            Codec.BOOL.fieldOf("is_jumping").forGetter(AetherIIPlayerAttachment::isJumping),
            Codec.BOOL.fieldOf("can_get_portal").forGetter(AetherIIPlayerAttachment::canGetPortal),
            Codec.BOOL.fieldOf("can_spawn_in_aether").forGetter(AetherIIPlayerAttachment::canSpawnInAether),
            Codec.BOOL.fieldOf("sent_chat_message").forGetter((attachment) -> attachment.sentChatMessage)
    ).apply(instance, AetherIIPlayerAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AetherIIPlayerAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::isMoving,
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::isJumping,
            ByteBufCodecs.registry(Registries.ENTITY_TYPE).apply(ByteBufCodecs.list()), AetherIIPlayerAttachment::getStuckProjectiles,
            AetherIIPlayerAttachment::new);

    private boolean shouldSyncBetweenClients;

    protected AetherIIPlayerAttachment(boolean isMoving, boolean isJumping, boolean canGetPortal, boolean canSpawnInAether, boolean sentChatMessage) {
        this.isMoving = isMoving;
        this.isJumping = isJumping;
        this.canGetPortal = canGetPortal;
        this.canSpawnInAether = canSpawnInAether;
        this.sentChatMessage = sentChatMessage;
    }

    protected AetherIIPlayerAttachment(boolean canGetPortal, boolean canSpawnInAether, List<EntityType<?>> stuckProjectiles) {
        this.canGetPortal = canGetPortal;
        this.canSpawnInAether = canSpawnInAether;
        this.stuckProjectiles = new ArrayList<>(stuckProjectiles);
    }

    public AetherIIPlayerAttachment() { }

    /**
     * Handles functions when the player logs out of a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent}.
     */
    public void logout(Player player) {

    }

    /**
     * Handles functions when the player logs in to a world from {@link net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent}.
     */
    public void login(Player player) {
        this.startInAether(player);

    }

    public void onJoinLevel(Player player) {
//        if (player.level().isClientSide() && player.isLocalPlayer()) {
//            this.setSynched(player.getId(), Direction.SERVER, "setShouldSyncBetweenClients", true);
//        }
    }

    public void changeDimension(Player player, ResourceKey<Level> to) {
        if (to == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
            if (player instanceof ServerPlayer serverPlayer && !this.sentChatMessage) {

                serverPlayer.sendSystemMessage(Component.literal("test").withStyle(Style.EMPTY.withClickEvent(new ClickEvent.ShowDialog(Holder.direct(getDialog())))));
                this.sentChatMessage = true;
            }
        }
    }

    public static NoticeDialog getDialog() {
        CommonDialogData dialogData = new CommonDialogData(Component.literal("test"), Optional.empty(), true, true, DialogAction.CLOSE, List.of(), List.of());
        CommonButtonData buttonData = new CommonButtonData(Component.literal("button"), Optional.empty(), 200);

        ActionButton actionButton = new ActionButton(buttonData, Optional.empty());

        NoticeDialog dialog = new NoticeDialog(dialogData, actionButton);

        return dialog;
    }

    /**
     * Handles functions when the player ticks from {@link net.neoforged.neoforge.event.entity.living.LivingEvent.LivingTickEvent}
     */
    public void postTickUpdate(Player player) {
        this.syncClients(player);
        this.handleAetherPortal(player);
        this.removeStuckProjectiles(player);
    }

    private void syncClients(Player player) {
        if (this.shouldSyncBetweenClients()) {
            if (!player.level().isClientSide()) {
                MinecraftServer server = player.level().getServer();
                if (server != null) {
                    PlayerList playerList = server.getPlayerList();
                    for (ServerPlayer serverPlayer : playerList.getPlayers()) {
                        if (!serverPlayer.getUUID().equals(player.getUUID())) {
//                            this.forceSync(player.getId(), Direction.CLIENT);
                        }
                    }
                }
            }
            this.setShouldSyncBetweenClients(false);
        }
    }

    /**
     * Increments or decrements the Aether portal timer depending on if the player is inside an Aether portal.
     * On the client, this will also help to set the portal overlay.
     */
    private void handleAetherPortal(Player player) {
        if (player.level().isClientSide()) {
            PortalClientUtil.handleAetherPortal(player, this);
        }
    }

    public void removeStuckProjectiles(Player player) {
        if (!player.level().isClientSide()) {
            int i = this.getStuckProjectiles().size();
            if (i > 0) {
                if (this.removeStuckProjectileTime <= 0) {
                    this.removeStuckProjectileTime = 20 * (30 - i);
                }
                this.removeStuckProjectileTime--;
                if (this.removeStuckProjectileTime <= 0) {
                    this.getStuckProjectiles().removeLast();
                    player.syncData(AetherIIDataAttachments.PLAYER);
                }
            }
        }
    }

    public void mouseInput(Player player, boolean isUseItem, int action) {
        if (isUseItem && action == 1) {
            if (!player.isUsingItem() && player.isHolding((stack) -> stack.getItem() instanceof ToggleItem)) {
                this.useToggled = true;
            } else if (player.isUsingItem() && player.getUseItem().getItem() instanceof ToggleItem) {
                this.useToggled = false;
            }
        }
    }

    public void movementInput(Player player, ClientInput input) {
        boolean isJumping = input.keyPresses.jump();
        if (isJumping != this.isJumping()) {
            this.setJumping(isJumping);
        }
        boolean isMoving = isJumping || input.keyPresses.forward() || input.keyPresses.backward() || input.keyPresses.left() || input.keyPresses.right() || player.isFallFlying();
        if (isMoving != this.isMoving()) {
            this.setMoving(isMoving);
        }
    }

    /**
     * Gives the player an Aether Portal Frame item on login if the {@link AetherIIConfig.Common#start_with_portal} config is enabled.
     */
    private void handleGivePortal(Player player) {
        if (AetherIIConfig.COMMON.start_with_portal.get()) {
            this.givePortalItem(player);
        } else {
            this.setCanGetPortal(false);
        }
    }

    /**
     * Gives the player an Aether Portal Frame item.
     */
    private void givePortalItem(Player player) {
        if (this.canGetPortal()) {
            player.addItem(new ItemStack(AetherIIItems.AETHER_PORTAL_FRAME.get()));
            this.setCanGetPortal(false);
        }
    }

    public void startInAether(Player player) { //todo: port to new 1.21 portal system
//        var aetherIIPlayer = player.getData(AetherIIDataAttachments.PORTAL_TELEPORTATION);
//        if (AetherIIConfig.SERVER.spawn_in_aether.get()) {
//            if (aetherIIPlayer.canSpawnInAether()) { // Checks if the player has been set to spawn in the Aether.
//                if (player instanceof ServerPlayer serverPlayer) {
//                    MinecraftServer server = serverPlayer.level().getServer();
//                    if (server != null) {
//                        ServerLevel aetherLevel = server.getLevel(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL);
//                        if (aetherLevel != null && serverPlayer.level().dimension() != AetherIIDimensions.AETHER_HIGHLANDS_LEVEL) {
//                            if (player.changeDimension(aetherLevel, new AetherPortalForcer(aetherLevel, false, true)) != null) {
//                                serverPlayer.setRespawnPosition(AetherIIDimensions.AETHER_HIGHLANDS_LEVEL, serverPlayer.blockPosition(), serverPlayer.getYRot(), true, false);
//                                aetherIIPlayer.setCanSpawnInAether(false); // Sets that the player has already spawned in the Aether.
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            aetherIIPlayer.setCanSpawnInAether(false);
//        }
    }

    public void stickProjectile(Projectile projectile, Player player) {
        EntityType<?> entityType = projectile.getType();
        if (entityType.is(AetherIITags.Entities.STICKABLE_PROJECTILES)) {
            this.stuckProjectiles.addLast(entityType);
            player.syncData(AetherIIDataAttachments.PLAYER);
        }
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    /**
     * @return Whether the player is moving, as a {@link Boolean}.
     */
    public boolean isMoving() {
        return this.isMoving;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    /**
     * @return Whether the player is jumping, as a {@link Boolean}.
     */
    public boolean isJumping() {
        return this.isJumping;
    }

    public boolean isUseToggled() {
        return this.useToggled;
    }

    public void setCanGetPortal(boolean canGetPortal) {
        this.canGetPortal = canGetPortal;
    }

    /**
     * @return Whether the player can get the Aether Portal Frame item, as a {@link Boolean}.
     */
    public boolean canGetPortal() {
        return this.canGetPortal;
    }

    public void setCanSpawnInAether(boolean canSpawnInAether) {
        this.canSpawnInAether = canSpawnInAether;
    }

    /**
     * @return Whether the player will spawn in the Aether dimension on first join, as a {@link Boolean}.
     */
    public boolean canSpawnInAether() {
        return this.canSpawnInAether;
    }

    public float getPortalIntensity() {
        return this.portalIntensity;
    }

    public float getOldPortalIntensity() {
        return this.oPortalIntensity;
    }

    public List<EntityType<?>> getStuckProjectiles() {
        return this.stuckProjectiles;
    }

    /**
     * @return Whether the capability should sync server values to nearby clients.
     */
    private boolean shouldSyncBetweenClients() {
        return this.shouldSyncBetweenClients;
    }

    private void setShouldSyncBetweenClients(boolean shouldSyncBetweenClients) {
        this.shouldSyncBetweenClients = shouldSyncBetweenClients;
    }
}
