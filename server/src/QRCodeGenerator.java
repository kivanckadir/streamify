
import javax.swing.ImageIcon;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;


public class QRCodeGenerator {
	
	String mMessage;
	ImageIcon qr;
	
	public QRCodeGenerator(String message) {
		mMessage = message;
	}
	
	public ImageIcon getQRAsImageIcon() {
		return new ImageIcon(QRCode.from(mMessage).to(ImageType.JPG).stream().toByteArray());
	}
}
