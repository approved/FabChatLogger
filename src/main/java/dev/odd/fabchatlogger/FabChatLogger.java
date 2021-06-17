package dev.odd.fabchatlogger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class FabChatLogger implements ModInitializer {
	public static final String ModId = "fabchatlog";
	public static Path FCLogPath = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), ModId);
	public static FileOutputStream FOSLog;
	public static Logger FCLLogger = LogManager.getFormatterLogger("FabChatLog");

	@Override
	public void onInitialize() {
		try {
			File logFile = new File(FCLogPath.toString());
			logFile.mkdirs();
			LocalDateTime time = LocalDateTime.now();
			FOSLog = new FileOutputStream(FCLogPath + String.format("/chat_%s_%s_%s.log", time.getDayOfMonth(), time.getMonth().getValue(), time.getYear()));
		} catch (FileNotFoundException e) {
			FCLLogger.error(e);
		}

		FCLLogger.info("Initialized");

		//ServerWorldEvents.LOAD.register(this::onWorldLoaded);
	}

//	public void onWorldLoaded(MinecraftServer server, World world) {
//		FCLLogger.info("Loaded from Server");
//	}
}
