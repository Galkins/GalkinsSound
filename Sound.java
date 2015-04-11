public class Sound {

	static Clip clip;
	static FloatControl control;
	static String soundStr;

	public static void play(URL sound, final int volumeValue)
			throws MalformedURLException, UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
		clip = AudioSystem.getClip();

		clip.open(ais);
		control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(getVolume(volumeValue));
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();

		soundStr = sound.toString();
	}

	public static void play(File sound, final int volumeValue)
			throws MalformedURLException, UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
		clip = AudioSystem.getClip();

		clip.open(ais);
		control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(getVolume(volumeValue));
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();

		soundStr = sound.toString();
	}

	public static void stop() {
		clip.stop();
	}

	public static void pause() {
		clip.stop();
	}

	public static void resume(int volumeValue) {
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();
	}

	public static void mute() {
		control.setValue(-80f);
	}

	public static void updateVolume(final int volumeValue) {
		try {
			Thread.sleep(1);
			control.setValue(getVolume(volumeValue));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static float getVolume(int volumeValue) {
		float volume = (float) (20 * (Math.log10(volumeValue * 0.01)));
		return volume;
	}

	public static String getSound() {
		return soundStr;
	}

	public static long getMicrosecondPosition() {
		long timepos = clip.getMicrosecondPosition();
		return timepos;
	}

	public static long getMicrosecondLength() {
		long timelenght = clip.getMicrosecondLength();
		return timelenght;
	}

	public static String getTimePosition() {
		long milliseconds = (Sound.getMicrosecondPosition() / 1000) % 1000;
		long seconds = (((Sound.getMicrosecondPosition() / 1000) - milliseconds) / 1000) % 60;
		long minutes = (((((Sound.getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60;
		long hours = ((((((Sound.getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60;
		String timePosH = null, timePosM = null, timePosS = null;

		if (hours < 10)
			timePosH = "0" + Objects.toString(hours);
		else
			timePosH = Objects.toString(hours);

		if (minutes < 10)
			timePosM = "0" + Objects.toString(minutes);
		else
			timePosM = Objects.toString(minutes);

		if (seconds < 10)
			timePosS = "0" + Objects.toString(seconds);
		else
			timePosS = Objects.toString(seconds);

		return timePosH + ":" + timePosM + ":" + timePosS;
	}

	public static String getTimeLength() {
		long milliseconds = (Sound.getMicrosecondLength() / 1000) % 1000;
		long seconds = (((Sound.getMicrosecondLength() / 1000) - milliseconds) / 1000) % 60;
		long minutes = (((((Sound.getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60;
		long hours = ((((((Sound.getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60;
		String timeLengthH = null, timeLengthM = null, timeLengthS = null;

		if (hours < 10)
			timeLengthH = "0" + Objects.toString(hours);
		else
			timeLengthH = Objects.toString(hours);

		if (minutes < 10)
			timeLengthM = "0" + Objects.toString(minutes);
		else
			timeLengthM = Objects.toString(minutes);

		if (seconds < 10)
			timeLengthS = "0" + Objects.toString(seconds);
		else
			timeLengthS = Objects.toString(seconds);

		return timeLengthH + ":" + timeLengthM + ":" + timeLengthS;
	}

	public static String getTimeRemaining() {
		long milliseconds = ((Sound.getMicrosecondLength() / 1000) % 1000)
				- ((Sound.getMicrosecondPosition() / 1000) % 1000);
		long seconds = ((((Sound.getMicrosecondLength() / 1000) - milliseconds) / 1000) % 60)
				- ((((Sound.getMicrosecondPosition() / 1000) - milliseconds) / 1000) % 60);
		long minutes = ((((((Sound.getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60)
				- ((((((Sound.getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60);
		long hours = (((((((Sound.getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60)
				- (((((((Sound.getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60);
		String timeRemainingH = null, timeRemainingM = null, timeRemainingS = null;

		if (hours < 10)
			timeRemainingH = "0" + Objects.toString(hours);
		else
			timeRemainingH = Objects.toString(hours);

		if (minutes < 10)
			timeRemainingM = "0" + Objects.toString(minutes);
		else
			timeRemainingM = Objects.toString(minutes);

		if (seconds < 10)
			timeRemainingS = "0" + Objects.toString(seconds);
		else
			timeRemainingS = Objects.toString(seconds);

		return timeRemainingH + ":" + timeRemainingM + ":" + timeRemainingS;
	}
}
