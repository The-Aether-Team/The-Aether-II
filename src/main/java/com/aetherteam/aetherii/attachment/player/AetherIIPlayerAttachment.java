package com.aetherteam.aetherii.attachment.player;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIIConfig;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.attachment.AetherIIDataAttachments;
import com.aetherteam.aetherii.block.portal.AetherPortalForcer;
import com.aetherteam.aetherii.block.portal.AetherPortalBlock;
import com.aetherteam.aetherii.block.portal.PortalClientUtil;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDimensions;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.item.miscellaneous.ToggleItem;
import com.aetherteam.aetherii.network.packet.serverbound.MovementDataPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.player.Input;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.*;
import com.aetherteam.aetherii.network.codec.ByteBufCodecs;
import com.aetherteam.aetherii.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.BlockUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.ClientPacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AetherIIPlayerAttachment {
    private static final ResourceLocation LOGOMARKS = new ResourceLocation(AetherII.MODID, "logomarks");
    private static final Style INFO = Style.EMPTY.withColor(0x56C1EF).withUnderlined(true).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Open Info Screen")));
    private static final Style PATREON = Style.EMPTY.withColor(16728653).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/TheAetherTeam")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("https://www.patreon.com/TheAetherTeam")));
    private static final Style MAKESHIP = Style.EMPTY.withColor(0x506AB9).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.makeship.com/products/aerwhale-jumbo-plushie")).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("https://www.makeship.com/products/aerwhale-jumbo-plushie")));

    private boolean isMovingHorizontally;
    private boolean isMovingOverall;
    private boolean isJumping;
    private boolean useToggled = false;

    private boolean canGetPortal = true;
    private boolean canSpawnInAether = true;

    public float portalIntensity;
    public float oPortalIntensity;
    private int aetherPortalTime;
    private boolean insideAetherPortal;

    public List<EntityType<?>> stuckProjectiles = new ArrayList<>();
    public int removeStuckProjectileTime = 0;

    public static final MapCodec<AetherIIPlayerAttachment> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.BOOL.fieldOf("is_moving_horizontally").forGetter(AetherIIPlayerAttachment::isMovingHorizontally),
            Codec.BOOL.fieldOf("is_moving_overall").forGetter(AetherIIPlayerAttachment::isMovingOverall),
            Codec.BOOL.fieldOf("is_jumping").forGetter(AetherIIPlayerAttachment::isJumping),
            Codec.BOOL.fieldOf("can_get_portal").forGetter(AetherIIPlayerAttachment::canGetPortal),
            Codec.BOOL.fieldOf("can_spawn_in_aether").forGetter(AetherIIPlayerAttachment::canSpawnInAether)
    ).apply(instance, AetherIIPlayerAttachment::new));
    public static final StreamCodec<FriendlyByteBuf, AetherIIPlayerAttachment> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::isMovingHorizontally,
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::isMovingOverall,
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::isJumping,
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::canGetPortal,
            ByteBufCodecs.BOOL, AetherIIPlayerAttachment::canSpawnInAether,
            ByteBufCodecs.registry(Registries.ENTITY_TYPE).apply(ByteBufCodecs.list()), AetherIIPlayerAttachment::getStuckProjectiles,
            AetherIIPlayerAttachment::new);

    private boolean shouldSyncBetweenClients;

    protected AetherIIPlayerAttachment(boolean isMovingHorizontally, boolean isMovingOverall, boolean isJumping, boolean canGetPortal, boolean canSpawnInAether) {
        this.isMovingHorizontally = isMovingHorizontally;
        this.isMovingOverall = isMovingOverall;
        this.isJumping = isJumping;
        this.canGetPortal = canGetPortal;
        this.canSpawnInAether = canSpawnInAether;
    }

    protected AetherIIPlayerAttachment(boolean isMovingHorizontally, boolean isMovingOverall, boolean isJumping, boolean canGetPortal, boolean canSpawnInAether, List<EntityType<?>> stuckProjectiles) {
        this.isMovingHorizontally = isMovingHorizontally;
        this.isMovingOverall = isMovingOverall;
        this.isJumping = isJumping;
        this.canGetPortal = canGetPortal;
        this.canSpawnInAether = canSpawnInAether;
        this.stuckProjectiles = new ArrayList<>(stuckProjectiles);
    }

    public AetherIIPlayerAttachment() { }

    /**
     * Handles functions when the player logs out of a world from {@link net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent}.
     */
    public void logout(Player player) {

    }

    /**
     * Handles functions when the player logs in to a world from {@link net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent}.
     */
    public void login(Player player) {
        this.handleGivePortal(player);
        this.startInAether(player);
    }

    public void onJoinLevel(Player player) {

    }

    public void changeDimension(Player player, ResourceKey<Level> to) {
        if (to == AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
            if (player instanceof ServerPlayer serverPlayer && AetherIIConfig.COMMON.show_alpha_message.get()) {
                MutableComponent thanksMessage = colored("Thank you for checking out ", 0xE5E5FF);
                thanksMessage.append(colored("The Aether II's public alpha test", 0x56C1EF));
                thanksMessage.append(colored("!", 0xE5E5FF));
                serverPlayer.sendSystemMessage(thanksMessage.append(CommonComponents.NEW_LINE));

                serverPlayer.sendSystemMessage(colored("The mod is incomplete and in active development, so some features are missing or unfinished.", 0xE5E5FF).append(CommonComponents.NEW_LINE));

                MutableComponent hereMessage = colored("Check ", 0xE5E5FF);
                hereMessage.append(Component.literal("* ").setStyle(INFO.withFont(LOGOMARKS))).append(Component.literal("here").setStyle(INFO));
                hereMessage.append(colored(" for an overview of the state of the mod and what to expect from future updates.", 0xE5E5FF));
                serverPlayer.sendSystemMessage(hereMessage.append(CommonComponents.NEW_LINE));

                MutableComponent linkMessage = colored("You can support our ongoing development on ", 0xE5E5FF);
                linkMessage.append(Component.literal(", ").setStyle(PATREON.withFont(LOGOMARKS))).append(Component.literal("Patreon").setStyle(PATREON));
                linkMessage.append(colored(".", 0xE5E5FF));
                serverPlayer.sendSystemMessage(linkMessage.append(CommonComponents.NEW_LINE));

                AetherIIConfig.COMMON.show_alpha_message.set(false);
            }
        }
    }

    /**
     * Handles functions when the player ticks from {@link net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent}
     */
    public void postTickUpdate(Player player) {
        this.handleAetherPortal(player);
        this.removeStuckProjectiles(player);
    }

    /**
     * Increments or decrements the Aether portal timer depending on if the player is inside an Aether portal.
     * On the client, this will also help to set the portal overlay.
     */
    private void handleAetherPortal(Player player) {
        if (player.level().isClientSide()) {
            PortalClientUtil.handleAetherPortal(player, this);
        } else if (player instanceof ServerPlayer serverPlayer) {
            if (this.insideAetherPortal) {
                int transitionTime = getPortalTransitionTime(serverPlayer);
                if (this.aetherPortalTime++ >= transitionTime) {
                    this.aetherPortalTime = 0;
                    this.insideAetherPortal = false;
                    AetherPortalBlock.handleTeleportation(serverPlayer);
                    return;
                }
            } else if (this.aetherPortalTime > 0) {
                this.aetherPortalTime = Math.max(0, this.aetherPortalTime - 4);
            }
            this.insideAetherPortal = false;
        }
    }

    private static int getPortalTransitionTime(ServerPlayer player) {
        return player.getAbilities().invulnerable ? 1 : 80;
    }

    public void setInsideAetherPortal() {
        this.insideAetherPortal = true;
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
                    this.getStuckProjectiles().remove(i - 1);
                    AetherIIDataAttachments.sync(player, AetherIIDataAttachments.PLAYER);
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

    public void movementInput(Player player, Input input) {
        boolean sync = false;
        boolean isJumping = input.jumping;
        if (isJumping != this.isJumping()) {
            this.setJumping(isJumping);
            sync = true;
        }
        boolean isMovingHorizontally = input.up || input.down || input.left || input.right;
        if (isMovingHorizontally != this.isMovingHorizontally()) {
            this.setMovingHorizontally(isMovingHorizontally);
            sync = true;
        }
        boolean isMovingOverall = isJumping || isMovingHorizontally || player.isFallFlying();
        if (isMovingOverall != this.isMovingOverall()) {
            this.setMovingOverall(isMovingOverall);
            sync = true;
        }
        if (sync) {
            ClientPacketDistributor.sendToServer(new MovementDataPacket(isJumping, isMovingHorizontally, isMovingOverall));
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

    public void startInAether(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            var aetherIIPlayer = AetherIIDataAttachments.get(player, AetherIIDataAttachments.PLAYER);
            if (AetherIIConfig.COMMON.spawn_in_aether.get()) {
                if (aetherIIPlayer.canSpawnInAether()) { // Checks if the player has been set to spawn in the Aether.
                    MinecraftServer server = serverPlayer.level().getServer();
                    ServerLevel aetherLevel = server.getLevel(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL);
                    if (aetherLevel != null && serverPlayer.level().dimension() != AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL) {
                        AetherPortalForcer portal = new AetherPortalForcer(aetherLevel, true);
                        double scale = DimensionType.getTeleportationScale(aetherLevel.dimensionType(), aetherLevel.dimensionType());
                        BlockPos startPos = aetherLevel.getWorldBorder().clampToBounds(serverPlayer.getX() * scale, serverPlayer.getY() + 64, serverPlayer.getZ() * scale);
                        Optional<BlockUtil.FoundRectangle> portalDestination = portal.createPortal(startPos, Direction.Axis.X, false);
                        if (portalDestination.isPresent()) {
                            BlockPos finalPos = portalDestination.get().minCorner;
                            Vec3 destination = Vec3.atBottomCenterOf(finalPos);
                            serverPlayer.teleportTo(aetherLevel, destination.x(), destination.y(), destination.z(), 0.0F, 0.0F);
                            serverPlayer.setRespawnPosition(AetherIIDimensions.AETHER_HOLY_ISLES_LEVEL, finalPos, 0.0F, true, false);
                            aetherIIPlayer.setCanSpawnInAether(false); // Sets that the player has already spawned in the Aether.
                        } else {
                            AetherII.LOGGER.error("Unable to create a portal, likely target out of worldborder");
                        }
                    }
                }
            } else {
                aetherIIPlayer.setCanSpawnInAether(false);
            }
        }
    }

    public void stickProjectile(Projectile projectile, Player player) {
        EntityType<?> entityType = projectile.getType();
        if (projectile.getType().is(AetherIITags.EntityTypes.STICKABLE_PROJECTILES)) {
            this.stuckProjectiles.add(entityType);
            AetherIIDataAttachments.sync(player, AetherIIDataAttachments.PLAYER);
        }
    }

    private static MutableComponent colored(String text, int color) {
        return Component.literal(text).withStyle(Style.EMPTY.withColor(color));
    }

    public void setMovingHorizontally(boolean isMovingHorizontally) {
        this.isMovingHorizontally = isMovingHorizontally;
    }

    /**
     * @return Whether the player is moving, as a {@link Boolean}.
     */
    public boolean isMovingHorizontally() {
        return this.isMovingHorizontally;
    }

    public void setMovingOverall(boolean isMoving) {
        this.isMovingOverall = isMoving;
    }

    /**
     * @return Whether the player is moving, as a {@link Boolean}.
     */
    public boolean isMovingOverall() {
        return this.isMovingOverall;
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
