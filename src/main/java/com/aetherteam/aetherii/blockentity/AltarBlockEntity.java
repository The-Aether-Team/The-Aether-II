package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.network.packet.clientbound.AltarParticlesPacket;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import net.minecraft.world.item.crafting.RecipeManager;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInput;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import com.aetherteam.aetherii.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AltarBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeHolder, StackedContentsCompatible {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{9};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

    protected NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    protected int processingProgress;
    protected int processingTotalTime;
    protected int fuelCount;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int id) {
            return switch (id) {
                case 0 -> AltarBlockEntity.this.processingProgress;
                case 1 -> AltarBlockEntity.this.processingTotalTime;
                case 2 -> AltarBlockEntity.this.fuelCount;
                default -> 0;
            };
        }

        @Override
        public void set(int id, int value) {
            switch (id) {
                case 0:
                    AltarBlockEntity.this.processingProgress = value;
                    break;
                case 1:
                    AltarBlockEntity.this.processingTotalTime = value;
                    break;
                case 2:
                    AltarBlockEntity.this.fuelCount = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, AltarEnchantingRecipe> quickCheck;
    private float ambSpinningSpeed = 0.0F;
    private float ambrosiumFinalRotation = 0.0F;
    private float bobOffs = -1.0F;
    private float inputItemRotation = 0.0F;

    public AltarBlockEntity() {
        this(AetherIIBlockEntityTypes.ALTAR.get(), BlockPos.ZERO, AetherIIBlocks.ALTAR.get().defaultBlockState());
    }

    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(AetherIIBlockEntityTypes.ALTAR.get(), pPos, pBlockState);
    }

    public AltarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.quickCheck = RecipeManager.createCheck(AetherIIRecipeTypes.ALTAR_ENCHANTING.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + AetherII.MODID + ".altar");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AltarMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.processingProgress = tag.getInt("ProcessingTime");
        this.processingTotalTime = tag.contains("ProcessingTimeTotal") ? tag.getInt("ProcessingTimeTotal") : 200;
        CompoundTag recipesUsedTag = tag.getCompound("RecipesUsed");
        for (String key : recipesUsedTag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(key), recipesUsedTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("ProcessingTime", this.processingProgress);
        tag.putInt("ProcessingTimeTotal", this.processingTotalTime);
        ContainerHelper.saveAllItems(tag, this.items);
        CompoundTag recipesUsedTag = new CompoundTag();
        this.recipesUsed.forEach((key, integer) -> recipesUsedTag.putInt(key.toString(), integer));
        tag.put("RecipesUsed", recipesUsedTag);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, AltarBlockEntity blockEntity) {
        boolean changed = false;
        int oldProcessingProgress = blockEntity.processingProgress;

        AltarEnchantingRecipe recipeHolder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), level).orElse(null);
        if (recipeHolder != null) {
            blockEntity.fuelCount = recipeHolder.fuelCount();
        } else {
            blockEntity.fuelCount = 0;
        }
        boolean hasFuel = hasFuel(level, blockEntity);
        int i = blockEntity.getMaxStackSize();

        if (hasFuel) {
            if (blockEntity.canProcess(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                changed = true;
                ++blockEntity.processingProgress;
                if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                    useFuel(level, blockEntity);
                    blockEntity.processingProgress = 0;
                    blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                    if (blockEntity.process(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipeHolder);
                        PacketDistributor.sendToAllPlayers(new AltarParticlesPacket(pos));
                    }
                }
            } else {
                blockEntity.processingProgress = 0;
            }
        } else if (blockEntity.processingProgress > 0) {
            blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingProgress);
        }

        if (changed) {
            setChanged(level, pos, state);
        }

        if (oldProcessingProgress != blockEntity.processingProgress) {
            if (blockEntity.getLevel() != null) {
                blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), 1 | 2);
            }
        }
    }

    private boolean canProcess(RegistryAccess registryAccess, @Nullable AltarEnchantingRecipe recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        ItemStack input = stacks.get(0);
        if (!input.isEmpty() && recipeHolder != null) {
            ItemStack result = recipeHolder.assemble(new SingleRecipeInput(this.getItem(0)));
            if (result.isEmpty()) {
                return false;
            } else {
                ItemStack inResultSlot = stacks.get(9);
                if (inResultSlot.isEmpty()) {
                    if (ItemStack.isSameItemSameTags(input, result)) {
                        return !input.isDamageableItem() || input.getDamageValue() > 0;
                    } else {
                        return true;
                    }
                } else if (!ItemStack.isSameItem(inResultSlot, result)) {
                    return false;
                } else if (!ItemStack.isSameItemSameTags(inResultSlot, result)) {
                    return false;
                } else if (inResultSlot.getCount() + result.getCount() <= maxStackSize && inResultSlot.getCount() + result.getCount() <= inResultSlot.getMaxStackSize()) {
                    return true;
                } else {
                    return inResultSlot.getCount() + result.getCount() <= result.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean process(RegistryAccess registryAccess, @Nullable AltarEnchantingRecipe recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        if (recipeHolder != null && this.canProcess(registryAccess, recipeHolder, stacks, maxStackSize)) {
            ItemStack input = stacks.get(0);
            ItemStack result = recipeHolder.assemble(new SingleRecipeInput(this.getItem(0)));
            ItemStack output = stacks.get(9);
            if (output.isEmpty()) {
                if (ItemStack.isSameItemSameTags(input, result) && input.isDamageableItem() && input.getDamageValue() > 0) {
                    ItemStack copy = input.copy();
                    copy.setDamageValue(0);
                    stacks.set(9, copy);
                } else {
                    stacks.set(9, result.copy());
                }
            } else if (ItemStack.isSameItemSameTags(output, result)) {
                output.grow(result.getCount());
            }
            input.shrink(1);
            if (this.getLevel() != null) {
                this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 1 | 2);
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean hasFuel(ServerLevel serverLevel, AltarBlockEntity blockEntity) {
        boolean flag = true;
        for (int i = 1; i <= getRecipeFuelCount(serverLevel, blockEntity); i++) {
            if (!blockEntity.isFuel(blockEntity.getItem(i))) {
                flag = false;
            }
        }
        return flag;
    }

    private static void useFuel(ServerLevel serverLevel, AltarBlockEntity blockEntity) {
        for (int i = 1; i <= getRecipeFuelCount(serverLevel, blockEntity); i++) {
            blockEntity.getItem(i).shrink(1);
        }
    }

    private static int getTotalProcessingTime(ServerLevel level, AltarBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), level).map(recipeHolder -> recipeHolder.processingTime()).orElse(200);
    }

    public static int getRecipeFuelCount(ServerLevel serverLevel, AltarBlockEntity blockEntity) {
        Optional<AltarEnchantingRecipe> recipeHolderOptional = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), serverLevel);
        if (recipeHolderOptional.isPresent()) {
            AltarEnchantingRecipe recipe = recipeHolderOptional.get();
            return recipe.fuelCount();
        }
        return 0;
    }

    private boolean isFuel(ItemStack stack) {
        return stack.is(AetherIITags.Items.ALTAR_FUEL);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack itemStack) {
        int smallestStackIndex = 0;
        int smallestStackSize = 64;
        for (int i = 1; i < 9; i++) {
            int stackSize = this.getItem(i).getCount();
            if (stackSize < smallestStackSize) {
                smallestStackIndex = i;
                smallestStackSize = stackSize;
            }
        }
        if (index == 9) {
            return false;
        } else if (index == 0) {
            return true;
        } else {
            return this.isFuel(itemStack) && index == smallestStackIndex;
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN || index < 1 || index > 8;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.items.get(index);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            if (this.level instanceof ServerLevel serverLevel) {
                this.processingTotalTime = getTotalProcessingTime(serverLevel, this);
                this.processingProgress = 0;
                this.setChanged();
            }
        }

        if (this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 1 | 2);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipeHolder) {
        if (recipeHolder != null) {
            ResourceLocation location = recipeHolder.getId();
            this.recipesUsed.addTo(location, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        if (player.level() instanceof ServerLevel serverLevel) {
            List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(serverLevel, player.position());
            player.awardRecipes(list);
            for (Recipe<?> recipeholder : list) {
                if (recipeholder != null) {
                    player.triggerRecipeCrafted(recipeholder, this.items);
                }
            }
        }
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<Recipe<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                list.add(recipeHolder);
                createExperience(level, popVec, entry.getIntValue(), ((AltarEnchantingRecipe) recipeHolder).experience());
            });
        }
        return list;
    }

    private static void createExperience(ServerLevel level, Vec3 popVec, int recipeIndex, float experience) {
        int i = Mth.floor((float) recipeIndex * experience);
        float f = Mth.frac((float) recipeIndex * experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrb.award(level, popVec, i);
    }

    @Override
    public void fillStackedContents(StackedContents stackedContents) {
        for (ItemStack itemstack : this.items) {
            stackedContents.accountStack(itemstack);
        }
    }

    public int getProcessingProgress() {
        return processingProgress;
    }

    public float getAmbSpinningSpeed() {
        return this.ambSpinningSpeed;
    }

    public void setAmbSpinningSpeed(float ambSpinningSpeed) {
        this.ambSpinningSpeed = ambSpinningSpeed;
    }

    public float getAmbrosiumFinalRotation() {
        return this.ambrosiumFinalRotation;
    }

    public void setAmbrosiumFinalRotation(float ambrosiumFinalRotation) {
        this.ambrosiumFinalRotation = ambrosiumFinalRotation;
    }

    public float getBobOffs() {
        return this.bobOffs;
    }

    public void setBobOffs(float bobOffs) {
        this.bobOffs = bobOffs;
    }

    public float getInputItemRotation() {
        return this.inputItemRotation;
    }

    public void setInputItemRotation(float inputItemRotation) {
        this.inputItemRotation = inputItemRotation;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
