package cn.ksmcbrigade.raml.client;

import com.google.gson.JsonArray;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class REAgainMCLoginClient implements ClientModInitializer {

    public static JsonArray mods = new JsonArray();

    public static Identifier channel = new Identifier("raml","mods");

    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getAllMods().forEach(e-> {
            if(!e.getMetadata().getId().equalsIgnoreCase("minecraft") && !e.getMetadata().getId().equalsIgnoreCase("java") && !e.getMetadata().getId().equalsIgnoreCase("mixinextras") && !e.getMetadata().getId().equalsIgnoreCase("fabricloader") && !e.getMetadata().getId().toLowerCase().contains("fabric-")){
                mods.add(e.getMetadata().getId().toLowerCase());
                System.out.println("client: "+e.getMetadata().getId().toLowerCase());
            }
        });
    }
}
