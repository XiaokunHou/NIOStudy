/**
 * �ļ����Ϳͻ��ˣ�ͨ���ֽ����������ļ�����ʵ���ļ����䲿�֣�<br>
 * û�ж��ļ�����������д���<br>
 * Ӧ�÷����ļ����ͽ�����ʶ�������ܶ˹ر�����
 * 
 */
public class FileClientHandler extends SimpleChannelHandler {

        // ÿ�δ�����ֽ���
	private int readLength = 8;

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// �����ļ�
		sendFile(e.getChannel());
	}

	private void sendFile(Channel channel) throws IOException {
		File file = new File("E:/1.txt");
		FileInputStream fis = new FileInputStream(file);
		int count = 0;
                BufferedInputStream bis = new BufferedInputStream(fis);
		for (;;) {
			byte[] bytes = new byte[readLength];
			int readNum = bis.read(bytes, 0, readLength);
			if (readNum == -1) {
				return;
			}
			sendToServer(bytes, channel, readNum);
			System.out.println("Send count: " + ++count);
		}

	}

	private void sendToServer(byte[] bytes, Channel channel, int length)
			throws IOException {
		ChannelBuffer buffer = ChannelBuffers.copiedBuffer(bytes, 0, length);
		channel.write(buffer);
	}

}