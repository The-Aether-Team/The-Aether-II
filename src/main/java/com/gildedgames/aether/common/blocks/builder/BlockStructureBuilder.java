package com.gildedgames.aether.common.blocks.builder;

import com.gildedgames.aether.common.entities.tiles.builder.TileEntityStructureBuilder;
import com.gildedgames.aether.common.network.NetworkingAether;
import com.gildedgames.aether.common.network.packets.PacketUpdateStructure;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			if (!player.canUseCommand(2, ""))
			{
				player.sendMessage(new TextComponentString("You need to be an operator to use this block."));

				return false;
			}

			TileEntityStructureBuilder tile = (TileEntityStructureBuilder) world.getTileEntity(pos);

			if (tile == null)
			{
				return false;
			}

			PacketUpdateStructure packet = new PacketUpdateStructure(pos, tile.getStructureData());

			NetworkingAether.sendPacketToPlayer(packet, (EntityPlayerMP) player);
		}

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		tooltip.add(I18n.format("aether.warning.operator_item"));
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityStructureBuilder();
	}
}
