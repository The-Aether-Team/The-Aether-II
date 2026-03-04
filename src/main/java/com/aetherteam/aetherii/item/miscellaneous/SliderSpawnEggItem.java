package com.aetherteam.aetherii.item.miscellaneous;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class SliderSpawnEggItem extends SpawnEggItem {

    public SliderSpawnEggItem(Item.Properties properties) {
        super(properties);
    }

    /**
     * [CODE COPY] - {@link net.minecraft.world.item.SpawnEggItem#useOn(UseOnContext)}.<br><br>
     * Changed to round the X/Z values based on the click location.
     * This makes it so the slider will always spawn in the center of a 2x2 region based on what corner of a block the player is aiming.<br><br>
     * The position still needs to be aligned with Math.floor once the entity is spawned, so the Slider rounds it's X and Z coordinates down after spawning.
     *
     * @param context The {@link UseOnContext} of the usage interaction.
     */

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level instanceof ServerLevel) {
            ItemStack itemStack = context.getItemInHand();
            BlockPos pos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState state = level.getBlockState(pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof Spawner spawner) {
                EntityType<?> entityType = this.getType(itemStack);
                spawner.setEntityId(entityType, level.getRandom());
                level.sendBlockUpdated(pos, state, state, 3);
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, pos);
                itemStack.shrink(1);
            } else {
                BlockPos relativePos;
                if (state.getCollisionShape(level, pos).isEmpty()) {
                    relativePos = pos;
                } else {
                    relativePos = pos.relative(direction);
                }

                Vec3 clickLoc = context.getClickLocation();
                BlockPos roundedPos = new BlockPos((int) Math.round(clickLoc.x()), relativePos.getY(), (int) Math.round(clickLoc.z()));
                EntityType<?> entitytype = this.getType(itemStack);
                if (entitytype.spawn((ServerLevel) level, itemStack, context.getPlayer(), roundedPos, EntitySpawnReason.SPAWN_ITEM_USE, true, !Objects.equals(pos, relativePos) && direction == Direction.UP) != null) {
                    itemStack.shrink(1);
                    level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
                }

            }
        }
        return InteractionResult.SUCCESS;
    }
}
