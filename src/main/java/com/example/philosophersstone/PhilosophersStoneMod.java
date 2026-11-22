package com.example.philosophersstone;

import com.example.philosophersstone.block.ModBlocks;
import com.example.philosophersstone.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PhilosophersStoneMod.MODID)
public class PhilosophersStoneMod {
    public static final String MODID = "philosophersstone";

    public PhilosophersStoneMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
