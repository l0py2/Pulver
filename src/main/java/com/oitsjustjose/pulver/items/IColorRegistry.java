package com.oitsjustjose.pulver.items;

import com.oitsjustjose.pulver.Lib;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class IColorRegistry implements IItemColor
{
    IDustRegistry registry;

    public IColorRegistry(ItemDust itemDust)
    {
        this.registry = itemDust.registry;
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int renderPass)
    {
        try
        {
            if (stack.getItemDamage() < registry.getVariants().length && renderPass == 1)
                return registry.getColorFromMeta(stack.getItemDamage());
        }
        catch (IOException e)
        {
            LogManager.getLogger(Lib.MODID).info(e.getMessage());
        }
        return 10724259;
    }
}
