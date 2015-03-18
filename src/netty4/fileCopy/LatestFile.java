package netty4.fileCopy;

import java.io.File;

public class LatestFile {
	private File file;

	public LatestFile(){
		file=new File("D:\\Automation\\CodeForTheWorld_XIAOKUN\\ZeroMQ\\Code\\testdata2");
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
