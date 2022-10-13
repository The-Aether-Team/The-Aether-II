package com.gildedgames.aetherii.entity;

import com.gildedgames.aetherii.AetherII;
import com.gildedgames.aetherii.api.TalkableController;
import com.gildedgames.aetherii.api.entity.TalkableMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.UUID;

public class TestEntity extends PathfinderMob implements TalkableMob {
	private static final EntityDataAccessor<Optional<UUID>> DATA_TALKED_FLAGS_ID = SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private final TalkableController talkableController;

	public TestEntity(EntityType<? extends TestEntity> p_21683_, Level p_21684_) {
		super(p_21683_, p_21684_);
		this.talkableController = new TalkableController(this);
		this.talkableController.setLevel(level);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TALKED_FLAGS_ID, Optional.empty());
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes();
	}

	@Override
	public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
		ItemStack itemstack = p_27584_.getItemInHand(p_27585_);
		boolean flag = super.mobInteract(p_27584_, p_27585_) == InteractionResult.PASS;
		if (flag) {
			this.setTalkingEntity(p_27584_);
			String node = "start";

			this.getTalkableController().openScene(p_27584_, this, new ResourceLocation(AetherII.MODID, "test/new_comer"), node);

			this.gameEvent(GameEvent.ENTITY_INTERACT, this);

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.FAIL;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		//this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	@Override
	public ResourceLocation getTalker() {
		return new ResourceLocation(AetherII.MODID, "test");
	}

	@Override
	public TalkableController getTalkableController() {
		return talkableController;
	}

	@Override
	public void setTalkingEntity(Player player) {
		this.entityData.set(DATA_TALKED_FLAGS_ID, this.getTalkingUUID());
	}

	@Override
	public Optional<UUID> getTalkingUUID() {
		return this.entityData.get(DATA_TALKED_FLAGS_ID);
	}
}
