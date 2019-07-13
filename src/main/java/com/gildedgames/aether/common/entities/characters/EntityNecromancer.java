package com.gildedgames.aether.common.entities.characters;

import com.gildedgames.aether.api.entity.EntityCharacter;
import com.gildedgames.aether.api.player.IPlayerAether;
import com.gildedgames.aether.api.shop.IShopInstanceGroup;
import com.gildedgames.aether.common.AetherCore;
import com.gildedgames.aether.common.capabilities.entity.player.PlayerAether;
import com.gildedgames.aether.common.capabilities.entity.player.modules.PlayerDialogModule;
import com.gildedgames.orbis.lib.util.mc.NBTHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityNecromancer extends EntityCharacter
{

	private BlockPos spawned;

	public EntityNecromancer(final World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 2.5F);

		this.rotationYaw = 0.3F;
	}

	@Override
	public IShopInstanceGroup createShopInstanceGroup()
	{
		return null;
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

		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);
		this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(250);
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
	public void registerData()
	{
		super.registerData();
	}

	@Override
	public void livingTick()
	{
		this.setHomePosAndDistance(this.getPosition(), 500);

		if (this.spawned == null)
		{
			this.spawned = this.getPosition();
		}

		super.livingTick();
	}

	@Override
	public boolean processInteract(final PlayerEntity player, final Hand hand)
	{
		if (!super.processInteract(player, hand))
		{
			if (!player.world.isRemote())
			{
				final IPlayerAether aePlayer = PlayerAether.getPlayer(player);
				final PlayerDialogModule dialogModule = aePlayer.getModule(PlayerDialogModule.class);

				dialogModule.setTalkingEntity(this);
				dialogModule.openScene(AetherCore.getResource("necromancer/start"), "#start");
			}
		}

		return true;
	}

}
