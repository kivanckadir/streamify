import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Broadcast {
	
	String ffmpegFilePath = "/FFMPEG/bin/ffmpeg.exe";
	Process pr;
	
	public void start(String targetIp, int streamPort) throws IOException {
		
		String cmd[] = {
				
				ffmpegFilePath,
				
				"-y",
				"-rtbufsize","200M",
				"-probesize","200M",
				
				"-f","dshow",
				"-thread_queue_size","1024",
				"-channel_layout","stereo",
				"-i","audio=Stereo Karisimi (Realtek High Definition Audio)",
				
				"-f","gdigrab",
				"-thread_queue_size","1024",
				"-itsoffset","1.2",
				"-re",
				"-s","1680:1050",
				"-draw_mouse","1",
				"-i","desktop",
				
				"-c:v","libx264",
				"-pix_fmt","yuv420p",
				"-b:v","2M",
				
				"-c:a","aac",
				"-ac","2",
				"-b:a","128k",
				"-strict","-2",

				"-crf","35",
				"-g","23",

				"-minrate","1M",
				"-maxrate","5M",
				"-bufsize","1M",
				
				"-profile:v","baseline",
				"-tune","zerolatency",
				"-preset","ultrafast",

				"-f","mpegts",
				"\"tcp://"+targetIp+":"+streamPort+"\""
		};
		
		ProcessBuilder pb = new ProcessBuilder(cmd);
		pb.redirectErrorStream(true);

		pr = pb.start();

		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		try {
			pr.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("************************************************");

		in.close();
		pr.destroy();

	}
	
	public void stop() {
		pr.destroy();
	}
}
