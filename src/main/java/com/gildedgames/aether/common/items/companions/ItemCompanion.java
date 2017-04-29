package com.gildedgames.aether.common.items.companions;

import com.gildedgames.aether.common.items.InformationProvider;
import com.gildedgames.aether.common.registry.content.CreativeTabsAether;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.List;

public class ItemCompanion extends Item
{
	private InformationProvider informationProvider;

	private final DecimalFormat timeFormat = new DecimalFormat("0.0");

	public ItemCompanion()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabsAether.COMPANIONS);
	}

	public ItemCompanion(InformationProvider informationProvider)
	{
		this();

		this.informationProvider = informationProvider;
	}

	public static void setRespawnTimer(ItemStack stack, World world, int timer)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null)
		{
			stack.setTagCompound(compound = new NBTTagCompound());
		}

		compound.setLong("respawnTimer", world.getTotalWorldTime() + timer);
	}

	public static long getTicksUntilRespawn(ItemStack stack, World world)
	{
		NBTTagCompound compound = stack.getTagCompound();

		if (compound == null || !compound.hasKey("respawnTimer"))
		{
			return 0;
		}

		return compound.getLong("respawnTimer") - world.getTotalWorldTime();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		long respawn = ItemCompanion.getTicksUntilRespawn(stack, player.world);

		if (respawn > 0)
		{
			tooltip.add(TextFormatting.RED + "" + TextFormatting.ITALIC + "Disabled! Respawns in " + this.parseTicks(respawn) + ".");
		}

		if (this.informationProvider != null)
		{
			this.informationProvider.addInformation(stack, player, tooltip, advanced);
		}

		super.addInformation(stack, player, tooltip, advanced);
	}

	protected String parseTicks(long ticks)
	{
		boolean useMinutes = ticks > (20 * 60);

		return this.timeFormat.format(ticks / (useMinutes ? 20f * 60 : 20f)) + " " + (useMinutes ? "minutes" : "seconds");
	}
}
