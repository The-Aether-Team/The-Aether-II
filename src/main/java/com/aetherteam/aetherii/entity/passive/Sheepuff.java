package com.aetherteam.aetherii.entity.passive;

import com.aetherteam.aetherii.AetherIITags;
import com.aetherteam.aetherii.block.AetherIIBlocks;
import com.aetherteam.aetherii.client.AetherIISoundEvents;
import com.aetherteam.aetherii.entity.AetherIIDataSerializers;
import com.aetherteam.aetherii.entity.AetherIIEntityTypes;
import com.aetherteam.aetherii.entity.ai.controller.FallingMoveControl;
import com.aetherteam.aetherii.entity.ai.goal.EatAetherGrassGoal;
import com.aetherteam.aetherii.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aetherii.entity.ai.navigator.FallPathNavigation;
import com.aetherteam.aetherii.loot.AetherIILoot;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.common.IShearable;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * [CODE COPY] - {@link net.minecraft.world.entity.animal.Sheep}.<br><br>
 * Cleaned up and added additional behavior for puff behavior and slow-falling.<br><br>
 * Warning for "deprecation" is suppressed because we still need to use vanilla shearing behavior from {@link Shearable}.
 */
@SuppressWarnings("deprecation")
public class Sheepuff extends AetherAnimal implements Shearable, IShearable {
    private static final EntityDataAccessor<SheepuffColor> DATA_WOOL_COLOR_ID = SynchedEntityData.defineId(Sheepuff.class, AetherIIDataSerializers.SHEEPUFF_COLOR.get());
    private static final EntityDataAccessor<Boolean> DATA_SHEARED_ID = SynchedEntityData.defineId(Sheepuff.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_PUFFED_ID = SynchedEntityData.defineId(Sheepuff.class, EntityDataSerializers.BOOLEAN);

    private int eatAnimationTick, amountEaten;
    private EatAetherGrassGoal eatBlockGoal;

    private final FallPathNavigation fallNavigation;
    private final GroundPathNavigation groundNavigation;

    public static int getDecimalColor(SheepuffColor color) {
        return SheepuffColor.DECIMAL_COLOR_BY_SHEEPUFF_COLOR.get(color);
    }

    public Sheepuff(EntityType<? extends Sheepuff> type, Level level) {
        super(type, level);
        this.moveControl = new FallingMoveControl(this);
        this.fallNavigation = new FallPathNavigation(this, level);
        this.groundNavigation = new GroundPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.eatBlockGoal = new EatAetherGrassGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, Ingredient.of(AetherIITags.Items.SHEEPUFF_FOOD), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatBlockGoal);
        this.goalSelector.addGoal(6, new FallingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_WOOL_COLOR_ID, SheepuffColor.WHITE);
        builder.define(DATA_SHEARED_ID, false);
        builder.define(DATA_PUFFED_ID, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        this.setColor(getRandomSheepuffColor(this, level, level.getRandom()));
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    protected void customServerAiStep() {
        this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide()) {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        }
        super.aiStep();
    }

    /**
     * Makes this entity fall slowly when puffed up.
     */
    @Override
    public void tick() {
        super.tick();
        if (this.getPuffed()) {
            this.checkSlowFallDistance();
            AttributeInstance gravity = this.getAttribute(Attributes.GRAVITY);
            if (gravity != null) {
                double fallSpeed = Math.max(gravity.getValue() * -0.625, -0.05);
                if (this.getDeltaMovement().y() < fallSpeed) {
                    this.setDeltaMovement(this.getDeltaMovement().x, fallSpeed, this.getDeltaMovement().z);
                }
            }
            this.navigation = this.fallNavigation;
        } else {
            this.navigation = this.groundNavigation;
        }
    }

    /**
     * Makes this entity jump much higher when puffed up.
     */
    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        if (this.getPuffed()) {
            this.push(0.0, 1.8, 0.0);
        }
    }

    @Override
    public void ate() {
        ++this.amountEaten;
        if (!this.isSheared()) {
            if (this.amountEaten >= 2) { // Sheepuffs only puff up after eating twice.
                this.setPuffed(true);
                this.amountEaten = 0;
            }
        } else {
            if (this.amountEaten == 1) {
                this.setSheared(false);
                this.amountEaten = 0;
            }
        }
        if (this.isBaby()) {
            this.ageUp(60);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof DyeItem dyeItem) {
            DyeColor dyeColor = dyeItem.getDyeColor();
            SheepuffColor sheepuffColor = SheepuffColor.SHEEPUFF_COLOR_BY_DYE.get(dyeColor);
            if (this.getColor() != sheepuffColor) {
                player.swing(hand);
                if (!player.level().isClientSide()) {
                    this.setColor(sheepuffColor);
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    /**
     * Forge shearing method.
     */
    @Override
    public List<ItemStack> onSheared(@Nullable Player player, ItemStack item, Level level, BlockPos pos) {
        level.playSound(null, this, AetherIISoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), player == null ? SoundSource.BLOCKS : SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!level.isClientSide()) {
            int i;
            this.amountEaten = 0;
            if (this.getPuffed()) {
                this.setPuffed(false);
                i = 2;
            } else {
                this.setSheared(true);
                i = 1;
            }
            i += this.getRandom().nextInt(3);
            List<ItemStack> items = new ArrayList<>();
            for (int j = 0; j < i; ++j) {
                items.add(new ItemStack(SheepuffColor.CLOUDWOOL_BY_SHEEPUFF_COLOR.get(this.getColor())));
            }
            return items;
        }
        return Collections.emptyList();
    }

    /**
     * Vanilla shearing method (needed for dispenser behavior).
     */
    @Override
    public void shear(SoundSource source) {
        this.level().playSound(null, this, AetherIISoundEvents.ENTITY_SHEEPUFF_SHEAR.get(), source, 1.0F, 1.0F);
        int i;
        this.amountEaten = 0;
        if (this.getPuffed()) {
            this.setPuffed(false);
            i = 2;
        } else {
            this.setSheared(true);
            i = 1;
        }
        i += this.getRandom().nextInt(3);

        for (int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation(SheepuffColor.CLOUDWOOL_BY_SHEEPUFF_COLOR.get(this.getColor()), 1);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F, this.getRandom().nextFloat() * 0.05F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F));
            }
        }
    }

    @Override
    public boolean isShearable(Player player, ItemStack item, Level world, BlockPos pos) {
        return this.readyForShearing();
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isSheared() && !this.isBaby();
    }

    public boolean isSheared() {
        return this.getEntityData().get(DATA_SHEARED_ID);
    }

    public void setSheared(boolean sheared) {
        this.getEntityData().set(DATA_SHEARED_ID, sheared);
    }

    /**
     * @return Whether the Sheepuff is puffed up, as a {@link Boolean}.
     */
    public boolean getPuffed() {
        return this.getEntityData().get(DATA_PUFFED_ID);
    }

    /**
     * Sets whether the Sheepuff is puffed up.
     *
     * @param flag Whether to set the Sheepuff as puffed, as a {@link Boolean}.
     */
    public void setPuffed(boolean flag) {
        this.getEntityData().set(DATA_PUFFED_ID, flag);
    }

    public SheepuffColor getColor() {
        return this.getEntityData().get(DATA_WOOL_COLOR_ID);
    }

    public void setColor(SheepuffColor color) {
        this.getEntityData().set(DATA_WOOL_COLOR_ID, color);
    }

    public static SheepuffColor getRandomSheepuffColor(Sheepuff sheepuff, ServerLevelAccessor level, RandomSource random) {
        Holder<Biome> biome = level.getBiome(sheepuff.blockPosition());
        if (biome.is(AetherIITags.Biomes.HIGHFIELDS)) {
            int i = random.nextInt(100);
            if (i < 5) {
                return SheepuffColor.LIGHT_GRAY;
            } else if (i < 10) {
                return SheepuffColor.GRAY;
            } else if (i < 15) {
                return SheepuffColor.CYAN;
            } else if (i < 18) {
                return SheepuffColor.YELLOW;
            } else {
                return random.nextInt(500) == 0 ? SheepuffColor.MAGENTA : SheepuffColor.WHITE;
            }
        } else if (biome.is(AetherIITags.Biomes.MAGNETIC)) {
            int i = random.nextInt(100);
            if (i < 5) {
                return SheepuffColor.LIGHT_GRAY;
            } else if (i < 10) {
                return SheepuffColor.GRAY;
            } else if (i < 15) {
                return SheepuffColor.CYAN;
            } else if (i < 18) {
                return SheepuffColor.LIGHT_BLUE;
            } else {
                return random.nextInt(500) == 0 ? SheepuffColor.MAGENTA : SheepuffColor.WHITE;
            }
        } else if (biome.is(AetherIITags.Biomes.ARCTIC)) {
            int i = random.nextInt(100);
            if (i < 5) {
                return SheepuffColor.LIGHT_GRAY;
            } else if (i < 10) {
                return SheepuffColor.GRAY;
            } else if (i < 15) {
                return SheepuffColor.CYAN;
            } else if (i < 18) {
                return SheepuffColor.BROWN;
            } else {
                return random.nextInt(500) == 0 ? SheepuffColor.MAGENTA : SheepuffColor.WHITE;
            }
        }
        return SheepuffColor.WHITE;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(AetherIITags.Items.SHEEPUFF_FOOD);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AetherIISoundEvents.ENTITY_SHEEPUFF_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return AetherIISoundEvents.ENTITY_SHEEPUFF_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AetherIISoundEvents.ENTITY_SHEEPUFF_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), AetherIISoundEvents.ENTITY_SHEEPUFF_STEP.get(), SoundSource.NEUTRAL, 0.15F, 1.0F);
    }

    @Override
    public ResourceKey<LootTable> getDefaultLootTable() {
        if (this.isSheared()) {
            return this.getType().getDefaultLootTable();
        } else {
            return switch (this.getColor()) {
                case WHITE -> AetherIILoot.ENTITIES_SHEEPUFF_WHITE;
                case ORANGE -> AetherIILoot.ENTITIES_SHEEPUFF_ORANGE;
                case MAGENTA -> AetherIILoot.ENTITIES_SHEEPUFF_MAGENTA;
                case LIGHT_BLUE -> AetherIILoot.ENTITIES_SHEEPUFF_LIGHT_BLUE;
                case YELLOW -> AetherIILoot.ENTITIES_SHEEPUFF_YELLOW;
                case LIME -> AetherIILoot.ENTITIES_SHEEPUFF_LIME;
                case PINK -> AetherIILoot.ENTITIES_SHEEPUFF_PINK;
                case GRAY -> AetherIILoot.ENTITIES_SHEEPUFF_GRAY;
                case LIGHT_GRAY -> AetherIILoot.ENTITIES_SHEEPUFF_LIGHT_GRAY;
                case CYAN -> AetherIILoot.ENTITIES_SHEEPUFF_CYAN;
                case PURPLE -> AetherIILoot.ENTITIES_SHEEPUFF_PURPLE;
                case BLUE -> AetherIILoot.ENTITIES_SHEEPUFF_BLUE;
                case BROWN -> AetherIILoot.ENTITIES_SHEEPUFF_BROWN;
                case GREEN -> AetherIILoot.ENTITIES_SHEEPUFF_GREEN;
                case RED -> AetherIILoot.ENTITIES_SHEEPUFF_RED;
                case BLACK -> AetherIILoot.ENTITIES_SHEEPUFF_BLACK;
            };
        }
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return this.getPuffed() ? 0 : super.calculateFallDamage(distance, damageMultiplier);
    }

    @Override
    public int getMaxFallDistance() {
        return !this.onGround() && this.getPuffed() ? 20 : super.getMaxFallDistance();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        Sheepuff parent = (Sheepuff) entity;
        Sheepuff baby = AetherIIEntityTypes.SHEEPUFF.get().create(level);
        if (baby != null) {
            baby.setColor(this.getOffspringColor(this, parent));
        }
        return baby;
    }

    private SheepuffColor getOffspringColor(Animal parent1, Animal parent2) {
        SheepuffColor color1 = ((Sheepuff) parent1).getColor();
        SheepuffColor color2 = ((Sheepuff) parent2).getColor();
        CraftingInput craftinginput = makeCraftInput(color1.getDyeColor(), color2.getDyeColor());
        return this.level()
                .getRecipeManager()
                .getRecipeFor(RecipeType.CRAFTING, craftinginput, this.level())
                .map(p_352802_ -> p_352802_.value().assemble(craftinginput, this.level().registryAccess()))
                .map(ItemStack::getItem)
                .filter(DyeItem.class::isInstance)
                .map(DyeItem.class::cast)
                .map(DyeItem::getDyeColor)
                .map(SheepuffColor.SHEEPUFF_COLOR_BY_DYE::get)
                .orElseGet(() -> this.level().random.nextBoolean() ? color1: color2);
    }

    private static CraftingInput makeCraftInput(DyeColor pColor1, DyeColor pColor2) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(pColor1)), new ItemStack(DyeItem.byColor(pColor2))));
    }

    public float getHeadEatPositionScale(float pos) {
        if (this.eatAnimationTick <= 0) {
            return 0.0F;
        } else if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
            return 1.0F;
        } else {
            return this.eatAnimationTick < 4 ? (this.eatAnimationTick - pos) / 4.0F : -(this.eatAnimationTick - 40 - pos) / 4.0F;
        }
    }

    public float getHeadEatAngleScale(float angle) {
        if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
            float f = ((float) (this.eatAnimationTick - 4) - angle) / 32.0F;
            return (Mth.PI / 5.0F) + 0.21991149F * Mth.sin(f * 28.7F);
        } else {
            return this.eatAnimationTick > 0 ? (Mth.PI / 5.0F) : this.getXRot() * Mth.DEG_TO_RAD;
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.eatAnimationTick = 40;
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", this.isSheared());
        tag.putBoolean("Puffed", this.getPuffed());
        tag.putInt("Color", this.getColor().id());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Sheared")) {
            this.setSheared(tag.getBoolean("Sheared"));
        }
        if (tag.contains("Puffed")) {
            this.setPuffed(tag.getBoolean("Puffed"));
        }
        if (tag.contains("Color")) {
            this.setColor(SheepuffColor.BY_ID.apply(tag.getInt("Color")));
        }
    }

    public enum SheepuffColor {
        WHITE(0, 16777215, DyeColor.WHITE, AetherIIBlocks.WHITE_CLOUDWOOL),
        ORANGE(1, 16760199, DyeColor.ORANGE, AetherIIBlocks.ORANGE_CLOUDWOOL),
        MAGENTA(2, 14989818, DyeColor.MAGENTA, AetherIIBlocks.MAGENTA_CLOUDWOOL),
        LIGHT_BLUE(3, 12041207, DyeColor.LIGHT_BLUE, AetherIIBlocks.LIGHT_BLUE_CLOUDWOOL),
        YELLOW(4, 16768903, DyeColor.YELLOW, AetherIIBlocks.YELLOW_CLOUDWOOL),
        LIME(5, 12317344, DyeColor.LIME, AetherIIBlocks.LIME_CLOUDWOOL),
        PINK(6, 16759510, DyeColor.PINK, AetherIIBlocks.PINK_CLOUDWOOL),
        GRAY(7, 8028561, DyeColor.GRAY, AetherIIBlocks.GRAY_CLOUDWOOL),
        LIGHT_GRAY(8, 13947085, DyeColor.LIGHT_GRAY, AetherIIBlocks.LIGHT_GRAY_CLOUDWOOL),
        CYAN(9, 10741468, DyeColor.CYAN, AetherIIBlocks.CYAN_CLOUDWOOL),
        PURPLE(10, 10255815, DyeColor.PURPLE, AetherIIBlocks.PURPLE_CLOUDWOOL),
        BLUE(11, 6846906, DyeColor.BLUE, AetherIIBlocks.BLUE_CLOUDWOOL),
        BROWN(12, 7230555, DyeColor.BROWN, AetherIIBlocks.BROWN_CLOUDWOOL),
        GREEN(13, 8497266, DyeColor.GREEN, AetherIIBlocks.GREEN_CLOUDWOOL),
        RED(14, 11230822, DyeColor.RED, AetherIIBlocks.RED_CLOUDWOOL),
        BLACK(15, 3093053, DyeColor.BLACK, AetherIIBlocks.BLACK_CLOUDWOOL);

        public static final IntFunction<SheepuffColor> BY_ID = ByIdMap.continuous(SheepuffColor::id, SheepuffColor.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, SheepuffColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SheepuffColor::id);

        public static final Map<DyeColor, SheepuffColor> SHEEPUFF_COLOR_BY_DYE = Maps.<DyeColor, SheepuffColor>newEnumMap(Arrays.stream(SheepuffColor.values()).collect(Collectors.toMap(color -> color.dyeColor, color -> color)));

        private static final Map<SheepuffColor, Integer> DECIMAL_COLOR_BY_SHEEPUFF_COLOR = Maps.<SheepuffColor, Integer>newEnumMap(Arrays.stream(SheepuffColor.values()).collect(Collectors.toMap(color -> color, color -> color.color)));
        private static final Map<SheepuffColor, DyeColor> DYE_COLOR_BY_SHEEPUFF_COLOR = Maps.<SheepuffColor, DyeColor>newEnumMap(Arrays.stream(SheepuffColor.values()).collect(Collectors.toMap(color -> color, color -> color.dyeColor)));
        private static final Map<SheepuffColor, ItemLike> CLOUDWOOL_BY_SHEEPUFF_COLOR = Maps.<SheepuffColor, ItemLike>newEnumMap(Arrays.stream(SheepuffColor.values()).collect(Collectors.toMap(color -> color, color -> color.wool)));

        private final int id;
        private final int color;
        private final DyeColor dyeColor;
        private final ItemLike wool;

        SheepuffColor(int id, int color, DyeColor dyeColor, ItemLike wool) {
            this.id = id;
            this.color = color;
            this.dyeColor = dyeColor;
            this.wool = wool;
        }

        public int getColor() {
            return this.color;
        }

        public DyeColor getDyeColor() {
            return this.dyeColor;
        }

        public ItemLike getWool() {
            return this.wool;
        }

        public int id() {
            return id;
        }
    }
}
