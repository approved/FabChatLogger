package dev.odd.fabchatlogger.mixin;

import dev.odd.fabchatlogger.FabChatLogger;
import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/client/gui/screen/ConnectScreen$1")
public class ServerConnectionMixin {
    @Shadow
    @Final
    private ServerAddress field_33737;

    @Inject(
            method = "run()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/ClientConnection;send(Lnet/minecraft/network/Packet;)V",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    public void onServerConnected(final CallbackInfo ci) {
        String address = field_33737.getAddress();
        FabChatLogger.FCLLogger.info(String.format("Connected to %s:%s", address, field_33737.getPort()));
    }
}
