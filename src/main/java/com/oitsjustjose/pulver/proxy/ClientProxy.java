package com.oitsjustjose.pulver.proxy;

import com.oitsjustjose.pulver.Lib;
import com.oitsjustjose.pulver.items.IColorRegistry;
import com.oitsjustjose.pulver.items.ItemDust;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy
{
	CreativeTabs tab;
	String MODID = Lib.MODID;

	/**
	 * @param item
	 *            The Item to register a model registry for. You still have to make the model file, but now MC will know where to look
	 */
	@SideOnly(Side.CLIENT)
	public void register(Item item)
	{
		tab = item.getCreativeTab();
		int meta = 0;

		NonNullList<ItemStack> subItems = NonNullList.create();
		item.getSubItems(tab, subItems);
		for (ItemStack sub : subItems)
		{
			String name = item.getUnlocalizedName(sub).toLowerCase().replace(MODID + ".", "").replace("item.", "");
			ModelBakery.registerItemVariants(item, new ResourceLocation(MODID, name));
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(MODID + ":" + name, "inventory"));
			meta++;
		}
	}

	/**
	 * @param block
	 *            The Item to register a model registry for. You still have to make the model file, but now MC will know where to look
	 */
	@SideOnly(Side.CLIENT)
	public void register(Block block)
	{
		tab = block.getCreativeTabToDisplayOn();
		int meta = 0;
		ItemBlock itemBlock = new ItemBlock(block);
		// Checks if the block has metadata / subtypes
		if (itemBlock.getHasSubtypes())
		{
			NonNullList<ItemStack> subItems = NonNullList.create();
			itemBlock.getSubItems(tab, subItems);
			for (ItemStack sub : subItems)
			{
				String name = itemBlock.getUnlocalizedName(sub).toLowerCase().replace(MODID + ".", "").replace("tile.", "");
				ModelBakery.registerItemVariants(itemBlock, new ResourceLocation(MODID, name));
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, meta, getLocation(block));
				meta++;
			}
		}
		else
		{
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, getLocation(block));
		}
	}
	
	private ModelResourceLocation getLocation(Block block)
	{
		return new ModelResourceLocation(MODID + ":" + block.getUnlocalizedName().substring(6).toLowerCase(), "inventory");
	}

	@SideOnly(Side.CLIENT)
	public void registerColorizers(ItemDust dust)
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IColorRegistry(dust), dust);
	}
}