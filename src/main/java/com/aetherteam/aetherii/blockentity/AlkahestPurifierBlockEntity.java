package com.aetherteam.aetherii.blockentity;

import com.aetherteam.aetherii.AetherII;
import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.block.utility.AlkahestPurifierBlock;
import com.aetherteam.aetherii.block.utility.AltarBlock;
import com.aetherteam.aetherii.inventory.menu.AlkahestPurifierMenu;
import com.aetherteam.aetherii.item.AetherIIItems;
import com.aetherteam.aetherii.recipe.input.SingleRecipeInputWithRandom;
import com.aetherteam.aetherii.recipe.recipes.AetherIIRecipeTypes;
import com.aetherteam.aetherii.recipe.recipes.item.AltarEnchantingRecipe;
import com.aetherteam.aetherii.recipe.recipes.item.AlkahestPurificationRecipe;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlkahestPurifierBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible, LidBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{5, 6};
    private static final int[] SLOTS_FOR_SIDES = new int[]{1, 2, 3, 4};
    protected static final int MAX_LEVELS = 12;
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
//                ChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_OPEN);
            }

            protected void onClose(Level level, BlockPos pos, BlockState state) {
//                ChestBlockEntity.playSound(level, pos, state, SoundEvents.CHEST_CLOSE);
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
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registry) {
        super.loadAdditional(tag, registry);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items, registry);
        this.processingProgress = tag.getInt("ProcessingTime");
        this.processingTotalTime = tag.getInt("ProcessingTimeTotal");
        this.alkahestLevels = tag.getInt("AlkahestLevels");
        CompoundTag recipesUsedTag = tag.getCompound("RecipesUsed");
        for (String key : recipesUsedTag.getAllKeys()) {
            this.recipesUsed.put(ResourceKey.create(Registries.RECIPE, ResourceLocation.parse(key)), recipesUsedTag.getInt(key));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registry) {
        super.saveAdditional(tag, registry);
        tag.putInt("ProcessingTime", this.processingProgress);
        tag.putInt("ProcessingTimeTotal", this.processingTotalTime);
        tag.putInt("AlkahestLevels", this.alkahestLevels);
        ContainerHelper.saveAllItems(tag, this.items, registry);
        CompoundTag recipesUsedTag = new CompoundTag();
        this.recipesUsed.forEach((location, integer) -> recipesUsedTag.putInt(location.toString(), integer));
        tag.put("RecipesUsed", recipesUsedTag);
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
        if (levels < MAX_LEVELS) {
            for (int i = 1; i < 5; i++) {
                ItemStack stack = blockEntity.getItem(i);
                if (blockEntity.isFuel(stack)) {
                    blockEntity.alkahestLevels += 3;
                    stack.shrink(1);
                    blockEntity.setItem(i, stack.getCraftingRemainder());
                }
            }
        }

        RecipeHolder<AlkahestPurificationRecipe> recipeHolder = blockEntity.quickCheck.getRecipeFor(new SingleRecipeInputWithRandom(blockEntity.getItem(0), level.getRandom()), level).orElse(null);




        int roundedLevels = Mth.floor(levels / 3.0);
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

    private boolean isFuel(ItemStack stack) {
        return stack.is(AetherIIItems.ARKENIUM_ACID_CANISTER.get());
    }

    @Override //todo
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
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
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void setRecipeUsed(@Nullable RecipeHolder<?> recipeHolder) {

    }

    @Override
    public @Nullable RecipeHolder<?> getRecipeUsed() {
        return null;
    }

//    @Override
//    public void awardUsedRecipes(Player player, List<ItemStack> items) {
//    }

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
        for (Object2IntMap.Entry<ResourceKey<Recipe<?>>> entry : this.recipesUsed.object2IntEntrySet()) {
            level.recipeAccess().byKey(entry.getKey()).ifPresent(recipeHolder -> {
                list.add(recipeHolder);
                createExperience(level, popVec, entry.getIntValue(), ((AltarEnchantingRecipe) recipeHolder.value()).experience());
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
    public void fillStackedContents(StackedItemContents stackedItemContents) {

    }
}
