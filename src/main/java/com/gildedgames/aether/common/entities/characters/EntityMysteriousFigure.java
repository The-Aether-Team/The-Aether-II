package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.AetherAPI;
import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.shop.IShopDefinition;
import com.gildedgames.aether.api.shop.IShopInstance;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.AetherCelebrations;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.shop.ShopInstanceGroup;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
import java.util.Random;

public class EntityMysteriousFigure extends EntityCharacter
{
	public static final ResourceLocation SPEAKER = AetherCore.getResource("mysterious_figure");

	private BlockPos spawned;

	public EntityMysteriousFigure(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 2.0F);
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
	public void applyEntityCollision(Entity entityIn)
	{
		super.applyEntityCollision(entityIn);
	}

	@Override
	protected EntityBodyHelper createBodyHelper()
	{
		return new EntityBodyHelper(this);
	}

	@Override
	protected void registerGoals()
	{
		super.registerGoals();

		this.goalSelector.addGoal(3, new EntityAILookIdle(this));
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
	public void writeEntityToNBT(final CompoundNBT compound)
	{
		super.writeEntityToNBT(compound);

		compound.put("spawned", NBTHelper.writeBlockPos(this.spawned));
	}

	@Override
	public void readEntityFromNBT(final CompoundNBT compound)
	{
		super.readEntityFromNBT(compound);

		this.spawned = NBTHelper.readBlockPos(compound.getCompound("spawned"));

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
	@OnlyIn(Dist.CLIENT)
	public void turn(final float yaw, final float pitch)
	{
		//super.turn(yaw, pitch);
	}

	@Override
	public void livingTick()
	{
		if (!AetherCelebrations.isHalloweenEvent())
		{
			if (this.width > 0 && this.height > 0)
			{
				this.setSize(0, 0);
			}
		}
		else
		{
			if (this.width == 0 && this.height == 0)
			{
				this.setSize(1.0F, 2.0F);
			}
		}

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
			this.setHomePosAndDistance(this.spawned, 3);
		}

		super.livingTick();

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

			if (!player.world.isRemote)
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
	protected void playStepSound(final BlockPos pos, final Block blockIn)
	{

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

}
