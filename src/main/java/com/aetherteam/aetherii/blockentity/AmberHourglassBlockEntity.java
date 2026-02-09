package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AmberHourglassBlock;
import com.aetherteam.aetherii.data.resources.maps.AmberHourglassFuel;
import com.aetherteam.aetherii.data.resources.registries.AetherIIDataMaps;
import com.aetherteam.aetherii.inventory.menu.AmberHourglassMenu;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.HourglassRestoringRecipe;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class AmberHourglassBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{2, 3, 4};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1};

    protected NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
    protected int powerTimeRemaining;
    protected int powerTotalTime;
    protected int processingProgress;
    protected int processingTotalTime;
    protected boolean open;
    protected final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int id) {
            return switch (id) {
                case 0 -> {
                    if (AmberHourglassBlockEntity.this.powerTotalTime > Short.MAX_VALUE) {
                        yield Mth.floor(((double) AmberHourglassBlockEntity.this.powerTimeRemaining / AmberHourglassBlockEntity.this.powerTotalTime) * Short.MAX_VALUE);
                    }
                    yield AmberHourglassBlockEntity.this.powerTimeRemaining;
                }
                case 1 -> Math.min(AmberHourglassBlockEntity.this.powerTotalTime, Short.MAX_VALUE);
                case 2 -> AmberHourglassBlockEntity.this.processingProgress;
                case 3 -> AmberHourglassBlockEntity.this.processingTotalTime;
                default -> 0;
            };
        }

        @Override
        public void set(int id, int value) {
            switch (id) {
                case 0:
                    AmberHourglassBlockEntity.this.powerTimeRemaining = value;
                    break;
                case 1:
                    AmberHourglassBlockEntity.this.powerTotalTime = value;
                    break;
                case 2:
                    AmberHourglassBlockEntity.this.processingProgress = value;
                    break;
                case 3:
                    AmberHourglassBlockEntity.this.processingTotalTime = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<ResourceKey<Recipe<?>>> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.CachedCheck<SingleRecipeInputWithRandom, HourglassRestoringRecipe> quickCheck;

    public AmberHourglassBlockEntity() {
        this(AetherIIBlockEntityTypes.AMBER_HOURGLASS.get(), BlockPos.ZERO, AetherIIBlocks.AMBER_HOURGLASS.get().defaultBlockState());
    }

    public AmberHourglassBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(AetherIIBlockEntityTypes.AMBER_HOURGLASS.get(), pPos, pBlockState);
    }

    public AmberHourglassBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.quickCheck = RecipeManager.createCheck(AetherIIRecipeTypes.HOURGLASS_RESTORING.get());
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("menu." + AetherII.MODID + ".amber_hourglass");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AmberHourglassMenu(containerId, inventory, this, this.dataAccess);
    }

    @Override
    public void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(input, this.items);
        this.processingProgress = input.getIntOr("ProcessingTime", (short) 0);
        this.processingTotalTime = input.getIntOr("ProcessingTimeTotal", (short) 0);
        this.powerTimeRemaining = input.getIntOr("PowerTimeRemaining", (short) 0);
        this.powerTotalTime = input.getIntOr("PowerTimeTotal", (short) 0);
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
        output.putInt("PowerTimeRemaining", this.powerTimeRemaining);
        output.putInt("PowerTimeTotal", this.powerTotalTime);
        ContainerHelper.saveAllItems(output, this.items);
        CompoundTag recipesUsedTag = new CompoundTag();
        this.recipesUsed.forEach((key, integer) -> recipesUsedTag.putInt(key.location().toString(), integer));
        output.store("RecipesUsed", CompoundTag.CODEC, recipesUsedTag);
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        this.loadAdditional(input);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), AetherII.LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
            this.saveAdditional(output);
            tag = output.buildResult();
        }
        return tag;
    }

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, AmberHourglassBlockEntity blockEntity) {
        boolean changed = false;
        if (blockEntity.isPowered()) {
            blockEntity.powerTimeRemaining--;
        }

        ItemStack fuelStack = blockEntity.items.get(1);
        ItemStack inputStack = blockEntity.items.get(0);
        boolean noInput = !inputStack.isEmpty();
        boolean noFuel = !fuelStack.isEmpty();
        if (blockEntity.isPowered() || noFuel && noInput) {
            SingleRecipeInputWithRandom input = new SingleRecipeInputWithRandom(inputStack, level.getRandom());
            RecipeHolder<HourglassRestoringRecipe> recipe;
            if (noInput) {
                recipe = blockEntity.quickCheck.getRecipeFor(input, level).orElse(null);
            } else {
                recipe = null;
            }

            int i = blockEntity.getMaxStackSize();
            if (!blockEntity.isPowered() && canProcess(level.registryAccess(), recipe, input, blockEntity.items, i)) {
                blockEntity.powerTimeRemaining = blockEntity.getFuelDuration(fuelStack);
                blockEntity.powerTotalTime = blockEntity.powerTimeRemaining;
                if (blockEntity.isPowered()) {
                    changed = true;
                    var remainder = fuelStack.getCraftingRemainder();
                    if (!remainder.isEmpty())
                        blockEntity.items.set(1, remainder);
                    else
                    if (noFuel) {
                        Item item = fuelStack.getItem();
                        fuelStack.shrink(1);
                        if (fuelStack.isEmpty()) {
                            blockEntity.items.set(1, item.getCraftingRemainder(fuelStack));
                        }
                    }
                }
            }

            if (blockEntity.isPowered() && canProcess(level.registryAccess(), recipe, input, blockEntity.items, i)) {
                blockEntity.processingProgress++;
                if (blockEntity.processingProgress == blockEntity.processingTotalTime) {
                    blockEntity.processingProgress = 0;
                    blockEntity.processingTotalTime = getTotalProcessingTime(level, blockEntity);
                    if (process(level.registryAccess(), recipe, input, blockEntity.items, i)) {
                        blockEntity.setRecipeUsed(recipe);
                    }

                    changed = true;
                }
            } else {
                blockEntity.processingProgress = 0;
            }
        } else if (!blockEntity.isPowered() && blockEntity.processingProgress > 0) {
            blockEntity.processingProgress = Mth.clamp(blockEntity.processingProgress - 2, 0, blockEntity.processingTotalTime);
        }

        boolean open = blockEntity.open || blockEntity.isOutputFull();
        if (open != state.getValue(AmberHourglassBlock.OPEN)) {
            changed = true;
            state = state.setValue(AmberHourglassBlock.OPEN, open);
            level.setBlock(pos, state, 1 | 2);
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private static boolean canProcess(RegistryAccess registryAccess, @Nullable RecipeHolder<HourglassRestoringRecipe> recipe, SingleRecipeInputWithRandom recipeInput, NonNullList<ItemStack> items, int maxStackSize) {
        if (!items.get(0).isEmpty() && recipe != null) {
            List<ItemStack> results = recipe.value().assembleOutputs(recipeInput, registryAccess);
            if (results.isEmpty()) {
                return false;
            } else {
                boolean flag = false;
                for (int i = 0; i < 3; i++) { //todo this could maybe be improved to be more lenient?
                    int slot = i + 2;
                    ItemStack output = items.get(slot);
                    ItemStack result = results.get(i);
                    if (!result.isEmpty()) {
                        if (output.isEmpty()) {
                            flag = true;
                            break;
                        } else if (ItemStack.isSameItemSameComponents(output, result)) {
                            flag = output.getCount() + result.getCount() <= maxStackSize && output.getCount() + result.getCount() <= output.getMaxStackSize() || output.getCount() + result.getCount() <= result.getMaxStackSize();
                            if (flag) {
                                break;
                            }
                        }
                    }
                }
                return flag;
            }
        } else {
            return false;
        }
    }

    private static boolean process(RegistryAccess registryAccess, @Nullable RecipeHolder<HourglassRestoringRecipe> recipe, SingleRecipeInputWithRandom recipeInput, NonNullList<ItemStack> items, int maxStackSize) {
        if (recipe != null && canProcess(registryAccess, recipe, recipeInput, items, maxStackSize)) {
            ItemStack input = items.get(0);
            List<ItemStack> results = recipe.value().assembleOutputs(recipeInput, registryAccess);
            for (int i = 0; i < 3; i++) {
                int slot = i + 2;
                ItemStack output = items.get(slot);
                ItemStack result = results.get(i);
                if (output.isEmpty()) {
                    items.set(slot, result.copy());
                } else if (ItemStack.isSameItemSameComponents(output, result)) {
                    output.grow(result.getCount());
                }
            }
            input.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    protected int getFuelDuration(ItemStack stack) {
        AmberHourglassFuel fuel = BuiltInRegistries.ITEM.wrapAsHolder(stack.getItem()).getData(AetherIIDataMaps.AMBER_HOURGLASS_FUELS);
        if (fuel != null) {
            return fuel.powerTime();
        }
        return 0;
    }

    private static int getTotalProcessingTime(ServerLevel level, AmberHourglassBlockEntity blockEntity) {
        SingleRecipeInputWithRandom input = new SingleRecipeInputWithRandom(blockEntity.getItem(0), level.getRandom());
        return blockEntity.quickCheck.getRecipeFor(input, level).map(holder -> holder.value().processingTime()).orElse(200);
    }

    private boolean isPowered() {
        return this.powerTimeRemaining > 0;
    }

    private boolean isOutputFull() {
        boolean full = false;
        for (int i = 2; i <= 4; i++) {
            if (!this.items.get(i).isEmpty()) {
                full = true;
                break;
            }
        }
        return full;
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
    public boolean canTakeItemThroughFace(int index, ItemStack itemStack, Direction direction) {
        return direction != Direction.DOWN || index >= 2;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index >= 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack fuelStack = this.items.get(1);
            return this.getFuelDuration(fuelStack) > 0;
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator() && this.getLevel() != null) {
            this.open = true;
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator() && this.getLevel() != null) {
            this.open = false;
        }
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
        stack.limitSize(this.getMaxStackSize(stack));
        if (index == 0 && !flag && this.level instanceof ServerLevel serverlevel) {
            this.processingTotalTime = getTotalProcessingTime(serverlevel, this);
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
            ResourceKey<Recipe<?>> location = recipeHolder.id();
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
                createExperience(level, popVec, entry.getIntValue(), ((HourglassRestoringRecipe) recipeHolder.value()).experience());
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

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
