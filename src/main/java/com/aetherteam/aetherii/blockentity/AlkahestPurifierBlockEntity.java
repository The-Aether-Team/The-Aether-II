package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AlkahestPurifierBlock;
import com.aetherteam.aetherii.client.sound.AetherIISoundEvents;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AlkahestPurifierBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible, LidBlockEntity {
    public static final int MAX_LEVELS = 12;
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{1, 2, 3, 4, 5, 6};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1, 2, 3, 4};
    private final ContainerOpenersCounter openersCounter;
    private final ChestLidController chestLidController;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int id) {
            return switch (id) {
                case 0 -> AlkahestPurifierBlockEntity.this.processingProgress;
                case 1 -> AlkahestPurifierBlockEntity.this.processingTotalTime;
                case 2 -> AlkahestPurifierBlockEntity.this.alkahestLevels;
                default -> 0;
            };
        }

        @Override
        public void set(int id, int value) {
            switch (id) {
                case 0:
                    AlkahestPurifierBlockEntity.this.processingProgress = value;
                    break;
                case 1:
                    AlkahestPurifierBlockEntity.this.processingTotalTime = value;
                    break;
                case 2:
                    AlkahestPurifierBlockEntity.this.alkahestLevels = value;
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };
    private final Object2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInputWithRandom, AlkahestPurificationRecipe> quickCheck;

    protected NonNullList<ItemStack> items = NonNullList.withSize(7, ItemStack.EMPTY);
    protected int alkahestLevels;
    protected int processingProgress;
    protected int processingTotalTime;

    public AlkahestPurifierBlockEntity() {
        this(AetherIIBlockEntityTypes.ALKAHEST_PURIFIER.get(), BlockPos.ZERO, AetherIIBlocks.ALKAHEST_PURIFIER.get().defaultBlockState());
    }

    public AlkahestPurifierBlockEntity(BlockPos pos, BlockState state) {
        this(AetherIIBlockEntityTypes.ALKAHEST_PURIFIER.get(), pos, state);
    }

    public AlkahestPurifierBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos pos, BlockState state) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), AetherIISoundEvents.BLOCK_ALKAHEST_PURIFIER_OPEN, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), AetherIISoundEvents.BLOCK_ALKAHEST_PURIFIER_CLOSE, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            }

            protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155364_, int p_155365_) {
                AlkahestPurifierBlockEntity.this.signalOpenCount(level, pos, state, p_155364_, p_155365_);
            }

            protected boolean isOwnContainer(Player player) {
                if (!(player.containerMenu instanceof AlkahestPurifierMenu alkahestPurifierMenu)) {
                    return false;
                } else {
                    Container container = alkahestPurifierMenu.getContainer();
                    return container == AlkahestPurifierBlockEntity.this || container instanceof CompoundContainer compoundContainer && compoundContainer.contains(AlkahestPurifierBlockEntity.this);
                }
            }
        };
        this.chestLidController = new ChestLidController();
        this.quickCheck = RecipeManager.createCheck(AetherIIRecipeTypes.ALKAHEST_PURIFICATION.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + AetherII.MODID + ".alkahest_purifier");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AlkahestPurifierMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.processingProgress = input.getIntOr("ProcessingTime", 0);
        this.processingTotalTime = input.getIntOr("ProcessingTimeTotal", 200);
        this.alkahestLevels = input.getIntOr("AlkahestLevels", 0);
        Optional<CompoundTag> recipesUsedTag = input.read("RecipesUsed", CompoundTag.CODEC);
        recipesUsedTag.ifPresent(tag -> {
            for (String key : tag.keySet()) {
                this.recipesUsed.put(ResourceKey.create(Registries.RECIPE, ResourceLocation.parse(key)), tag.getIntOr(key, 0));
            }
        });
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("ProcessingTime", this.processingProgress);
        output.putInt("ProcessingTimeTotal", this.processingTotalTime);
        output.putInt("AlkahestLevels", this.alkahestLevels);
        ContainerHelper.saveAllItems(output, this.items);
        CompoundTag recipesUsedTag = new CompoundTag();
        this.recipesUsed.forEach((key, integer) -> recipesUsedTag.putInt(key.location().toString(), integer));
        output.store("RecipesUsed", CompoundTag.CODEC, recipesUsedTag);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, AlkahestPurifierBlockEntity blockEntity) {
        boolean changed = false;

        int levels = blockEntity.alkahestLevels;
        if (levels + 3 <= MAX_LEVELS) {
            for (int i = 1; i < 5; i++) {
                ItemStack stack = blockEntity.getItem(i);
                if (blockEntity.isFuel(stack)) {
                    blockEntity.alkahestLevels = Math.min(blockEntity.alkahestLevels + 3, MAX_LEVELS);
                    blockEntity.setItem(i, stack.getCraftingRemainder());
                    break;
                }
            }
        }

        RecipeHolder<AlkahestPurificationRecipe> recipeHolder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInputWithRandom(blockEntity.getItem(0), level.getRandom()), level).orElse(null);
        int alkahestUsage = 1;
        if (recipeHolder != null) {
            alkahestUsage = recipeHolder.value().alkahestUsage();
        }
        boolean hasFuel = blockEntity.alkahestLevels >= alkahestUsage;
        int i = blockEntity.getMaxStackSize();

        if (hasFuel) {
            if (blockEntity.canProcess(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                changed = true;
                ++blockEntity.processingProgress;
                if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                    blockEntity.processingProgress = 0;
                    blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                    if (blockEntity.process(level.registryAccess(), recipeHolder, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipeHolder);
                        blockEntity.alkahestLevels -= alkahestUsage;
                    }
                }
            } else {
                blockEntity.processingProgress = 0;
            }
        } else if (blockEntity.processingProgress > 0) {
            blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingProgress);
        }

        int roundedLevels = Mth.ceil(levels / 3.0);
        roundedLevels = Math.min(roundedLevels, 4);
        if (state.getValue(AlkahestPurifierBlock.LEVEL) != roundedLevels) {
            changed = true;
            state = state.setValue(AlkahestPurifierBlock.LEVEL, roundedLevels);
            level.setBlock(pos, state, 1 | 2);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    public static void lidAnimateTick(Level level, BlockPos pos, BlockState state, AlkahestPurifierBlockEntity blockEntity) {
        blockEntity.chestLidController.tickLid();
    }

    private boolean canProcess(RegistryAccess registryAccess, @Nullable RecipeHolder<AlkahestPurificationRecipe> recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        ItemStack input = stacks.get(0);
        if (!input.isEmpty() && recipeHolder != null) {
            ItemStack result = recipeHolder.value().assemble(new SingleRecipeInputWithRandom(this.getItem(0), this.getLevel().getRandom()), registryAccess);
            if (result.isEmpty()) {
                return false;
            } else {
                ItemStack inResultSlot = stacks.get(5);
                if (inResultSlot.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItemSameComponents(inResultSlot, result)) {
                    return false;
                } else {
                    return inResultSlot.getCount() + result.getCount() <= maxStackSize && inResultSlot.getCount() + result.getCount() <= inResultSlot.getMaxStackSize() || inResultSlot.getCount() + result.getCount() <= result.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean process(RegistryAccess registryAccess, @Nullable RecipeHolder<AlkahestPurificationRecipe> recipeHolder, NonNullList<ItemStack> stacks, int maxStackSize) {
        if (recipeHolder != null && this.canProcess(registryAccess, recipeHolder, stacks, maxStackSize)) {
            ItemStack inputSlot = stacks.get(0);
            ItemStack result = recipeHolder.value().assemble(new SingleRecipeInputWithRandom(this.getItem(0), this.getLevel().getRandom()), registryAccess);
            ItemStack outputSlot = stacks.get(5);
            if (outputSlot.isEmpty()) {
                stacks.set(5, result.copy());
            } else if (ItemStack.isSameItemSameComponents(outputSlot, result)) {
                outputSlot.grow(result.getCount());
            }
            ItemStack byproducts = recipeHolder.value().byproducts().process(this.getLevel().getRandom());
            ItemStack byproductSlot = stacks.get(6);
            if (byproductSlot.isEmpty()) {
                stacks.set(6, byproducts.copy());
            } else if (ItemStack.isSameItemSameComponents(byproductSlot, byproducts)) {
                byproductSlot.grow(byproducts.getCount());
            }

            inputSlot.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    private static int getTotalProcessingTime(ServerLevel level, AlkahestPurifierBlockEntity blockEntity) {
        return blockEntity.quickCheck.getRecipeFor(new SingleRecipeInputWithRandom(blockEntity.getItem(0), blockEntity.getLevel().getRandom()), level).map(recipeHolder -> recipeHolder.value().processingTime()).orElse(200);
    }

    private boolean isFuel(ItemStack stack) {
        return stack.is(AetherIIItems.ARKENIUM_ALKAHEST_CANISTER.get());
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStack);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 5 || index == 6) {
            return false;
        } else if (index == 0) {
            return true;
        } else {
            return this.isFuel(stack);
        }
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
        return direction != Direction.DOWN || (index != 1 && index != 2 && index != 3 && index != 4) || !this.isFuel(itemStack);
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public float getOpenNess(float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int eventId, int eventParam) {
        Block block = state.getBlock();
        level.blockEvent(pos, block, 1, eventParam);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
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
        stack.limitSize(this.getMaxStackSize(stack));

        if (index == 0 && !flag) {
            if (this.level instanceof ServerLevel serverLevel) {
                this.processingTotalTime = getTotalProcessingTime(serverLevel, this);
                this.processingProgress = 0;
                this.setChanged();
            }
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
            ResourceKey<Recipe<?>> location = recipeHolder.id();
            this.recipesUsed.addTo(location, 1);
        }
    }

    @Override
    public @Nullable RecipeHolder<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player, List<ItemStack> items) {
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<RecipeHolder<?>> list = this.getRecipesToAwardAndPopExperience(player.level(), player.position());
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
        for (Object2IntMap.Entry<ResourceKey<Recipe<?>>> entry : this.recipesUsed.object2IntEntrySet()) {
            level.recipeAccess().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                list.add(recipeHolder);
                createExperience(level, popVec, entry.getIntValue(), ((AlkahestPurificationRecipe) recipeHolder.value()).experience());
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
    public void fillStackedContents(StackedItemContents stackedContents) {
        for (ItemStack itemstack : this.items) {
            stackedContents.accountStack(itemstack);
        }
    }
}
