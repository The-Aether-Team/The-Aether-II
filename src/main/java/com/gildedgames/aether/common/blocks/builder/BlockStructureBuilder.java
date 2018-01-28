package com.gildedgames.aether.common.blocks.builder;

import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketUpdateStructure;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockStructureBuilder extends Block implements ITileEntityProvider
{
	public BlockStructureBuilder()
	{
		super(Material.ROCK);

		this.setBlockUnbreakable();
		this.setResistance(100000.0F);

		this.disableStats();
	}

	@Override
	public boolean onBlockActivated(
			final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side,
			final float hitX, final float hitY, final float hitZ)
	{
		if (!world.isRemote)
		{
			if (!player.canUseCommand(2, ""))
			{
				player.sendMessage(new TextComponentString("You need to be an operator to use this block."));

				return false;
			}

			final TileEntityStructureBuilder tile = (TileEntityStructureBuilder) world.getTileEntity(pos);

			if (tile == null)
			{
				return false;
			}

			final PacketUpdateStructure packet = new PacketUpdateStructure(pos, tile.getStructureData());

			NetworkingAether.sendPacketToPlayer(packet, (EntityPlayerMP) player);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final ItemStack stack, @Nullable final World player, final List<String> tooltip, final ITooltipFlag advanced)
	{
		tooltip.add(I18n.format("aether.warning.operator_item"));
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta)
	{
		return new TileEntityStructureBuilder();
	}
}
