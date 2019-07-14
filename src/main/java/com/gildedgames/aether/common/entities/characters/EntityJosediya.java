package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.entity.IEntityEyesComponent;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerProgressModule;
import com.gildedgames.aether.common.entities.util.eyes.EntityEyesComponent;
import com.gildedgames.aether.common.entities.util.eyes.IEntityEyesComponentProvider;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityJosediya extends EntityCharacter implements IEntityEyesComponentProvider
{
	public static final ResourceLocation SPEAKER = AetherCore.getResource("josediya");

	private final IEntityEyesComponent eyes = new EntityEyesComponent(this);

	private BlockPos spawned;

	public EntityJosediya(EntityType<? extends EntityCharacter> type, World worldIn)
	{
		super(type, worldIn);
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		return null;
	}

	@Override
	protected void registerGoals()
	{
		this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
		//this.goalSelector.addGoal(2, new EntityAIWatchClosest(this, EntityPlayer.class, 50.0F, 1.0F));
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

		this.eyes.update();

		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;
	}

	@Override
	public boolean processInteract(final PlayerEntity player, final Hand hand)
	{
		if (!super.processInteract(player, hand))
		{
			if (!player.world.isRemote())
			{
				final PlayerAether playerAether = PlayerAether.getPlayer(player);
				final PlayerDialogModule dialogModule = playerAether.getModule(PlayerDialogModule.class);
				final PlayerProgressModule progressModule = playerAether.getModule(PlayerProgressModule.class);

				boolean talkedBefore = progressModule.hasTalkedTo(EntityJosediya.SPEAKER);

				String node = talkedBefore ? "start" : "start_not_introduced";

				dialogModule.openScene(AetherCore.getResource("josediya/outpost_greet"), node);

				if (!talkedBefore)
				{
					progressModule.setHasTalkedTo(EntityJosediya.SPEAKER, true);
				}
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

	@Override
	public IEntityEyesComponent getEyes()
	{
		return this.eyes;
	}
}
