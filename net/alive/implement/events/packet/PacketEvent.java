package net.alive.implement.events.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.alive.api.event.IEvent;
import net.alive.api.event.cancel.ICancellable;
import net.minecraft.network.Packet;

@Getter @Setter
public class PacketEvent implements IEvent, ICancellable {
    private PacketType packetType;
    private boolean cancelled;
    private Packet packet;

    public PacketEvent(PacketType packetType, Packet packet){
    this.packetType = packetType;
    this.packet = packet;
    }

    public <T extends Packet> T getPacket() {
        return (T) packet;
    }
}
