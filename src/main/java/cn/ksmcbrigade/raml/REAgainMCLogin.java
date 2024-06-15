package cn.ksmcbrigade.raml;

import cn.ksmcbrigade.raml.commands.LoginCommand;
import cn.ksmcbrigade.raml.commands.RegisterCommand;
import cn.ksmcbrigade.raml.commands.RetrievePasswordCommand;
import com.google.gson.JsonArray;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static cn.ksmcbrigade.raml.client.REAgainMCLoginClient.channel;

public class REAgainMCLogin implements ModInitializer {

    public static GetPlayer getPlayer = new GetPlayer();

    @Override
    public void onInitialize() {

        ServerPlayNetworking.registerGlobalReceiver(channel,((server, player, handler, buf, responseSender) -> {
            JsonArray array = new JsonArray();
            FabricLoader.getInstance().getAllMods().forEach(e->{
                if(!e.getMetadata().getId().equalsIgnoreCase("minecraft") && !e.getMetadata().getId().equalsIgnoreCase("java") && !e.getMetadata().getId().equalsIgnoreCase("mixinextras") && !e.getMetadata().getId().equalsIgnoreCase("fabricloader") && !e.getMetadata().getId().toLowerCase().contains("fabric-")){
                    array.add(e.getMetadata().getId().toLowerCase());
                    //System.out.println("server: "+e.getMetadata().getId().toLowerCase());
                }
            });
            if(!array.toString().equalsIgnoreCase(buf.readString())){
                player.networkHandler.disconnect(Text.of("The client mods does not match the server mods."));
            }
        }));

        RegisteredPlayersJson.read();
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LoginCommand.register(dispatcher);
            RegisterCommand.register(dispatcher);
            RetrievePasswordCommand.register(dispatcher);
        });
    }

    public static PlayerLogin getPlayer(ServerPlayerEntity player) {
        return getPlayer.get(player);
    }
}
