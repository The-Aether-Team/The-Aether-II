package com.gildedgames.aether.common.entities.projectiles;

import com.gildedgames.aether.api.entity.damage.IDamageLevelsHolder;
import com.gildedgames.aether.api.registrar.ItemsAether;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBolt extends EntityArrow implements IDamageLevelsHolder
{
	private int ticksInAir;

	private float slashDamageLevel = 0, pierceDamageLevel = 0, impactDamageLevel = 0;

	public EntityBolt(final World worldIn)
	{
		super(worldIn);
	}

	public EntityBolt(final World worldIn, final EntityLivingBase shooter)
	{
		super(worldIn, shooter);
	}

	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemsAether.bolt);
	}

	@Override
	protected void arrowHit(final EntityLivingBase living)
	{

	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBrightnessForRender()
	{
		final BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));

		if (this.world.isBlockLoaded(blockpos$mutableblockpos))
		{
			blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + (double) this.getEyeHeight()));
			return this.world.getCombinedLight(blockpos$mutableblockpos, 0);
		}
		else
		{
			return 0;
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		if (this.hasNoGravity())
		{
			if (this.ticksInAir == 500)
			{
				this.setDead();
			}

			if (!this.onGround)
			{
				++this.ticksInAir;
			}
		}
	}

	public void setSlashDamageLevel(float slashDamageLevel)
	{
		this.slashDamageLevel = slashDamageLevel;
	}

	public void setPierceDamageLevel(float pierceDamageLevel)
	{
		this.pierceDamageLevel = pierceDamageLevel;
	}

	public void setImpactDamageLevel(float impactDamageLevel)
	{
		this.impactDamageLevel = impactDamageLevel;
	}

	@Override
	public float getSlashDamageLevel()
	{
		return this.slashDamageLevel;
	}

	@Override
	public float getPierceDamageLevel()
	{
		return this.pierceDamageLevel;
	}

	@Override
	public float getImpactDamageLevel()
	{
		return this.impactDamageLevel;
	}
}
