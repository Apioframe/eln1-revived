package mods.eln.eventhandlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import mods.eln.Eln;
import mods.eln.packets.AchievePacket;

public class ElnFMLEventsHandler {

    // won't these break the hell out of the script? I hope they won't.
    private final static AchievePacket macerator_packet = new AchievePacket("craft50VMacerator");
    private final static AchievePacket socket_packet = new AchievePacket("craft50VSocket");

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onCraft(ItemCraftedEvent e) {
        if (e.crafting.getUnlocalizedName().toLowerCase().equals("50v_macerator")) {
            Eln.elnNetwork.sendToServer(macerator_packet);
        } else if (e.crafting.getUnlocalizedName().toLowerCase().equals("50v_power_socket")) {
            Eln.elnNetwork.sendToServer(socket_packet);
        }
    }
}
