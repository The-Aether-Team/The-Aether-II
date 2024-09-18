package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.inventory.menu.AltarMenu;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AltarBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{9};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1, 2, 3, 4, 5, 6, 7, 8};

    protected NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY);
    int processingProgress;
    int processingTotalTime;
    boolean blasting;
    int blastingDuration;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int id) {
            return switch (id) {
                case 0 -> AltarBlockEntity.this.processingProgress;
                case 1 -> AltarBlockEntity.this.processingTotalTime;
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
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInput, AltarEnchantingRecipe> quickCheck;

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
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new AltarMenu(pContainerId, pInventory, this, this.dataAccess);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registry) {
        super.loadAdditional(tag, registry);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registry);
        this.processingProgress = tag.getInt("ProcessingTime");
        this.processingTotalTime = tag.getInt("ProcessingTimeTotal");
        CompoundTag recipesUsedTag = tag.getCompound("RecipesUsed");
        for (String key : recipesUsedTag.getAllKeys()) {
            this.recipesUsed.put(ResourceLocation.tryParse(key), recipesUsedTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registry) {
        super.saveAdditional(tag, registry);
        tag.putInt("ProcessingTime", this.processingProgress);
        tag.putInt("ProcessingTimeTotal", this.processingTotalTime);
        ContainerHelper.saveAllItems(tag, this.items, registry);
        CompoundTag recipesUsedTag = new CompoundTag();
        this.recipesUsed.forEach((location, integer) -> recipesUsedTag.putInt(location.toString(), integer));
        tag.put("RecipesUsed", recipesUsedTag);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AltarBlockEntity blockEntity) {
        boolean changed = false;

        RecipeHolder<AltarEnchantingRecipe> recipeHolder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), level).orElse(null);
        boolean hasFuel = hasFuel(blockEntity);
        int i = blockEntity.getMaxStackSize();
        boolean isCharging = false;

        if (hasFuel) {
            if (blockEntity.canProcess(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                changed = true;
                ++blockEntity.processingProgress;
                isCharging = true;
                if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                    useFuel(blockEntity);
                    blockEntity.processingProgress = 0;
                    blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                    if (blockEntity.process(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipeHolder);
                    }
                    isCharging = false;
                    blockEntity.blasting = true;
                    blockEntity.blastingDuration = 15;
                }
            } else {
                blockEntity.processingProgress = 0;
            }
        } else if (blockEntity.processingProgress > 0) {
            blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingProgress);
        }

        if (blockEntity.blastingDuration-- <= 0) {
            blockEntity.blasting = false;
        }

        if (state.getValue(AltarBlock.CHARGING) != isCharging) {
            changed = true;
            state = state.setValue(AltarBlock.CHARGING, isCharging);
            level.setBlock(pos, state, 1 | 2);
        }

        if (state.getValue(AltarBlock.BLASTING) != blockEntity.blasting) {
            changed = true;
            state = state.setValue(AltarBlock.BLASTING, blockEntity.blasting);
            level.setBlock(pos, state, 1 | 2);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private boolean canProcess(RegistryAccess registryAccess, @Nullable RecipeHolder<AltarEnchantingRecipe> recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        ItemStack input = stacks.get(0);
        if (!input.isEmpty() && recipeHolder != null) {
            ItemStack result = recipeHolder.value().assemble(new SingleRecipeInput(this.getItem(0)), registryAccess);
            if (result.isEmpty()) {
                return false;
            } else {
                ItemStack inResultSlot = stacks.get(9);
                if (inResultSlot.isEmpty()) {
                    if (ItemStack.isSameItem(input, result)) {
                        return !input.has(DataComponents.MAX_DAMAGE) || input.getDamageValue() > 0;
                    } else {
                        return true;
                    }
                } else if (!ItemStack.isSameItem(inResultSlot, result)) {
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

    private boolean process(RegistryAccess registryAccess, @Nullable RecipeHolder<AltarEnchantingRecipe> recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        if (recipeHolder != null && this.canProcess(registryAccess, recipeHolder, stacks, maxStackSize)) {
            ItemStack input = stacks.get(0);
            ItemStack result = recipeHolder.value().assemble(new SingleRecipeInput(this.getItem(0)), registryAccess);
            ItemStack output = stacks.get(9);
            if (output.isEmpty()) {
                if (ItemStack.isSameItem(input, result) && input.has(DataComponents.MAX_DAMAGE) && input.getDamageValue() > 0) {
                    ItemStack copy = input.copy();
                    copy.setDamageValue(0);
                    stacks.set(9, copy);
                } else {
                    stacks.set(9, result.copy());
                }
            } else if (output.is(result.getItem())) {
                output.grow(result.getCount());
            }
            input.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private static boolean hasFuel(AltarBlockEntity blockEntity) {
        boolean flag = true;
        for (int i = 1; i <= getRecipeFuelCount(blockEntity); i++) {
            if (!blockEntity.isFuel(blockEntity.getItem(i))) {
                flag = false;
            }
        }
        return flag;
    }

    private static void useFuel(AltarBlockEntity blockEntity) {
        for (int i = 1; i <= getRecipeFuelCount(blockEntity); i++) {
            blockEntity.getItem(i).shrink(1);
        }
    }

    private static int getTotalProcessingTime(Level level, AltarBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), level).map(recipeHolder -> recipeHolder.value().getProcessingTime()).orElse(200);
    }

    public static int getRecipeFuelCount(AltarBlockEntity blockEntity) {
        Optional<RecipeHolder<AltarEnchantingRecipe>> recipeHolderOptional = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInput(blockEntity.getItem(0)), blockEntity.level);
        if (recipeHolderOptional.isPresent()) {
            AltarEnchantingRecipe recipe = recipeHolderOptional.get().value();
            return recipe.getFuelCount();
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

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
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
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
        this.items.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (index == 0 && !flag) {
            this.processingTotalTime = getTotalProcessingTime(this.level, this);
            this.processingProgress = 0;
            this.setChanged();
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
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {
        if (recipeHolder != null) {
            ResourceLocation location = recipeHolder.id();
            this.recipesUsed.addTo(location, 1);
        }
    }

    @Nullable
    @Override
    public RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
        player.awardRecipes(list);
        for (RecipeHolder<?> recipeholder : list) {
            if (recipeholder != null) {
                player.triggerRecipeCrafted(recipeholder, this.items);
            }
        }
        this.recipesUsed.clear();
    }

    public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 popVec) {
        List<RecipeHolder<?>> list = Lists.newArrayList();
        for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                list.add(recipeHolder);
                createExperience(level, popVec, entry.getIntValue(), ((AltarEnchantingRecipe) recipeHolder.value()).getExperience());
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
}
