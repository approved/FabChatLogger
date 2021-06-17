package dev.odd.fabchatlogger.mixin;

import dev.odd.fabchatlogger.FabChatLogger;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Mixin(ChatHudListener.class)
public abstract class ChatMixin {
	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss a");

	@Inject(at = @At("HEAD"), method = "onChatMessage", cancellable = true)
	public void onChatMessageReceived(MessageType messageType, Text text, UUID sender, CallbackInfo ci) {
		String message = text.getString();
		if (message == null || message.isEmpty()) {
			return;
		}

		try {
			String fom = String.format("%s: %s\n", dtFormatter.format(LocalDateTime.now()), message);
			FabChatLogger.FOSLog.write(fom.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			FabChatLogger.FCLLogger.error(e);
		}
	}
}
