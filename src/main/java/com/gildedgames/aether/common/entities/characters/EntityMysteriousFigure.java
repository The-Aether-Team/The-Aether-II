package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.shop.IShopDefinition;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.shop.ShopInstanceGroup;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.Random;

public class EntityMysteriousFigure extends EntityCharacter
{
	public static final ResourceLocation SPEAKER = AetherCore.getResource("mysterious_figure");

	private BlockPos spawned;

	public EntityMysteriousFigure(EntityType<? extends EntityCharacter> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		IShopInstanceGroup group = new ShopInstanceGroup();

		Optional<IShopDefinition> shopDefinition = AetherAPI.content().shop().getShopDefinition(SPEAKER);

		if (shopDefinition.isPresent())
		{
			IShopInstance instance = AetherAPI.content().shop().createInstance(SPEAKER, shopDefinition.get(), new Random(this.getRNG().nextLong()));

			group.setShopInstance(0, instance);
		}

		return group;
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 50.0F, 1.0F));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}

	@Override
	public void writeAdditional(CompoundNBT nbt)
	{
		super.writeAdditional(nbt);

		nbt.put("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readAdditional(CompoundNBT nbt)
	{
		super.readAdditional(nbt);

		this.spawned = NBTHelper.readBlockPos(nbt.getCompound("spawned"));

		if (this.spawned != null)
		{
			this.setHomePosAndDistance(this.spawned, 3);
		}
	}

	@Override
	protected void setRotation(final float yaw, final float pitch)
	{
		//super.setRotation(yaw, pitch);
	}

	@Override
	public void registerData()
	{
		super.registerData();
	}

	@Override
	public void tick()
	{
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.tick();

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;
	}

	@Override
	public boolean processInteract(final PlayerEntity player, final Hand hand)
	{
		LocalDateTime time = LocalDateTime.now();

		if (time.getMonth() != Month.OCTOBER)
		{
			return false;
		}

		if (!super.processInteract(player, hand))
		{
			final PlayerAether playerAether = PlayerAether.getPlayer(player);
			final PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);

			dialogModule.setTalkingEntity(this);

			if (!player.world.isRemote())
			{
				dialogModule.openScene(AetherCore.getResource("mysterious_figure/start"), "start");
			}
		}

		return true;
	}

	@Override
	public boolean isOnLadder()
	{
		return false;
	}

	@Override
	protected void playStepSound(final BlockPos pos, final BlockState blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

}
