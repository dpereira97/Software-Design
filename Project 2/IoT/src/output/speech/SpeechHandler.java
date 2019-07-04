package output.speech;

import i18n.I18N;
import i18n.Messages;
import javazoom.jl.player.Player;

import output.OutputZirk;
import output.speech.synthesiser.SynthesiserV2;

/**
 * Realiza a voz sintetizada
 * @author fc47806, fc47878, fc47888
 */
public class SpeechHandler {
	
	// Atributos
	private SynthesiserV2 synthesiser;
	
	/**
	 * Inicializacao do sintetizador de voz
	 */
	public SpeechHandler() {
		this.synthesiser = new SynthesiserV2("YOUR API KEY HERE");
	}
	
	/**
	 * Given a message, it speaks the content of the message
	 * @param message message to be spoken
	 * @requires message != null
	 */
	public void computeMessage(String message) {
		
		synchronized(OutputZirk.LOCKER) {
			try {
				Player player = new Player(synthesiser.getMP3Data(message));
				player.play();
					
			} catch (Exception e) {
				System.err.println(I18N.getString(Messages.ERR_TTS)); 
			}
		}
	}
}
