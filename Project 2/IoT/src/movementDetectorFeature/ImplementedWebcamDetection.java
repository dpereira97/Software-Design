package movementDetectorFeature;

import com.bezirk.middleware.Bezirk;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionEvent;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamMotionListener;

/**
 * Uses webcam motion detection and launches a MovementUpdateEvent to Bezirk
 * @author https://github.com/sarxos/webcam-capture
 */
public class ImplementedWebcamDetection implements WebcamMotionListener {

	private Bezirk bezirk;
	
	public ImplementedWebcamDetection(Bezirk bezirk) {
		this.bezirk = bezirk;
		WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
		detector.setInterval(1250); // one check per 1250 ms
		detector.addMotionListener(this);
		detector.start();
	}

	@Override
	public void motionDetected(WebcamMotionEvent wme) {
		
		bezirk.sendEvent(new MovementUpdateEvent());
	}
}