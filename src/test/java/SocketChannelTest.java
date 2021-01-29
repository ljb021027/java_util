import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class SocketChannelTest {
	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 9999));
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = socketChannel.read(buf);
		System.out.println(bytesRead);
	
	}
}
