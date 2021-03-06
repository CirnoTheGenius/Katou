package ga.nurupeaches.kato.network.packets;

import ga.nurupeaches.kato.utils.UnsafeUtils;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

	/**
	 * An ID lookup field; uses bytes.
	 */
	public static final Map<Byte, Class<? extends Packet>> ID_LOOKUP = new HashMap<>();

	private InetSocketAddress origin;

	/**
	 * Register the packets related to Katou. You could register your own packets if you want.
	 */
	static {
		ID_LOOKUP.put((byte) 0x01, PacketMetadata.class);
	}


	/**
	 * Matches an ID with a new, empty packet.
	 * @param id - The byte id from a buffer.
	 * @return A new packet from the buffer. Does not fill the packet with information and increments the buffer.
	 */
	public static Packet convertPacket(byte id){
		Class<? extends Packet> packet = ID_LOOKUP.get(id);
		try{
			return (Packet)UnsafeUtils.getUnsafe().allocateInstance(packet);
		} catch (InstantiationException e){
			// Theoretically, this should only happen if the user is running anything but OpenJDK or Oracle's JRE/JDK.
			// TODO: Support other JVMs.
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the origin of this packet.
	 * @param address - The Original sender of the packet.
	 */
	public void setOrigin(InetSocketAddress address){
		this.origin = address;
	}

	/**
	 * Returns the origin of this packet.
	 */
	public InetSocketAddress getOrigin(){
		return origin;
	}

	/**
	 * Reads data from the given buffer.
	 * @param buffer - Buffer to read from.
	 */
	public abstract void read(ByteBuffer buffer);

	/**
	 * Writes data to the given buffer.
	 * @param buffer - Buffer to write to.
	 */
	public abstract void write(ByteBuffer buffer);

}