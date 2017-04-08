package com.gildedgames.aether.common.entities.living.dungeon.util;

import com.gildedgames.aether.common.items.ItemsAether;
import com.gildedgames.aether.common.registry.content.SoundsAether;
import com.gildedgames.aether.common.world.dungeon.DungeonDefinitions;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityGenerator extends EntityCreature
{

	private boolean activated, hasInit;

	public EntityGenerator(World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 1.0F);
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if (stack.getItem() == ItemsAether.aether_developer_wand)
		{
			this.world.playSound(player, player.getPosition(), SoundsAether.tempest_electric_shock, SoundCategory.NEUTRAL, 1.0F,
					0.8F + (this.rand.nextFloat() * 0.5F));

			this.setDead();

			return EnumActionResult.SUCCESS;
		}

		this.world.playSound(player, player.getPosition(), SoundsAether.chest_mimic_awake, SoundCategory.NEUTRAL, 1.0F,
				0.8F + (this.rand.nextFloat() * 0.5F));

		this.activated = !this.activated;

		return EnumActionResult.SUCCESS;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	public boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);

		tag.setBoolean("activated", this.activated);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);

		this.activated = tag.getBoolean("activated");
	}

	@Override
	public void onUpdate()
	{
		if (!this.hasInit && !this.world.isRemote && !this.isDead)
		{
			if (this.activated)
			{
				Entity entity = DungeonDefinitions.SLIDERS_LABYRINTH.createRandomMob(this.world, this.world.rand);

				entity.setPosition(this.posX, this.posY, this.posZ);

				this.world.spawnEntity(entity);

				this.setDead();

				this.activated = false;
			}

			this.hasInit = true;
		}

		super.onUpdate();

		this.motionX = 0.0F;
		this.motionZ = 0.0F;

		if (this.activated)
		{
			for (int k = 0; k < 2; ++k)
			{
				double motionX = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();
				double motionY = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();
				double motionZ = (this.getRNG().nextBoolean() ? 1.0D : -1.0D) * this.getRNG().nextFloat();

				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB,
						this.posX + motionX, this.posY + 0.5D + motionY, this.posZ + motionZ, 0.1D, 0.1D, 0.1D);
			}
		}
	}

	@Override
	public boolean canDespawn()
	{
		return false;
	}

}
