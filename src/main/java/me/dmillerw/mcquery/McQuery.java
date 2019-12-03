package me.dmillerw.mcquery;

import me.dmillerw.mcquery.queryable.Server;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("mcquery")
public class McQuery {

    public McQuery() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
    }

    public void setup(final FMLCommonSetupEvent event) {
        LogicalSide side = EffectiveSide.get();
        Server.create();
    }
}
