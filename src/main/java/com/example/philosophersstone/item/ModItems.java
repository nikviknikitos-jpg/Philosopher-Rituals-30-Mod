package com.example.philosophersstone.item;

import com.example.philosophersstone.PhilosophersStoneMod;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PhilosophersStoneMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhilosophersStoneMod.MODID);

    // Философский камень — главный предмет мода
    public static final RegistryObject<Item> PHILOSOPHERS_STONE =
            ITEMS.register("philosophers_stone",
                    () -> new PhilosopherStoneItem(new Item.Properties().stacksTo(1)));

    // Эссенция трансмутации — крафтовый материал
    public static final RegistryObject<Item> TRANSMUTATION_ESSENCE =
            ITEMS.register("transmutation_essence",
                    () -> new Item(new Item.Properties()));

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(PHILOSOPHERS_STONE);
            event.accept(TRANSMUTATION_ESSENCE);
        }
    }

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
