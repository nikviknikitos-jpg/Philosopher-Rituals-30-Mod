package com.example.philosophersstone.block;

import com.example.philosophersstone.PhilosophersStoneMod;
import com.example.philosophersstone.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PhilosophersStoneMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PhilosophersStoneMod.MODID);

    // Алтарь трансмутации — блок для крафта философского камня
    public static final RegistryObject<Block> TRANSMUTATION_ALTAR =
            BLOCKS.register("transmutation_altar",
                    () -> new Block(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.GOLD)
                            .strength(50.0F, 1200.0F)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.ANVIL)
                            .lightLevel(state -> 8)));

    public static final RegistryObject<Item> TRANSMUTATION_ALTAR_ITEM =
            ModItems.ITEMS.register("transmutation_altar",
                    () -> new BlockItem(TRANSMUTATION_ALTAR.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SEARCH || event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(TRANSMUTATION_ALTAR_ITEM);
        }
    }
}
