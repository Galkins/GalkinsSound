public class Sound {

	String sound;
	float volume;
	
	Clip clip;
	FloatControl control;
	
	public Sound(URL sound, int volumeValue) {
		this.sound = sound.toString();
		this.volume = (float)volumeValue;
	}
	
	public Sound(File sound, int volumeValue) {
		this.sound = sound.toString();
		this.volume = (float)volumeValue;
	}

	public void play()
			throws MalformedURLException, UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(new URL(sound));
		clip = AudioSystem.getClip();

		clip.open(ais);
		control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(getVolume());
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();
	}

	public void play(File sound)
			throws MalformedURLException, UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
		clip = AudioSystem.getClip();

		clip.open(ais);
		control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(getVolume());
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();
	}

	public void stop() {
		clip.stop();
	}

	public void pause() {
		clip.stop();
	}

	public void resume() {
		clip.setMicrosecondPosition(clip.getMicrosecondPosition());
		clip.start();
	}

	public void mute() {
		control.setValue(-80f);
	}

	public void updateVolume() {
		try {
			Thread.sleep(1);
			control.setValue(getVolume());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public float getVolume() {
		float volumeValue = (float) (20 * (Math.log10(volume * 0.01)));
		return volumeValue;
	}

	public long getMicrosecondPosition() {
		long timepos = clip.getMicrosecondPosition();
		return timepos;
	}

	public long getMicrosecondLength() {
		long timelenght = clip.getMicrosecondLength();
		return timelenght;
	}

	public String getTimePosition() {
		long milliseconds = (getMicrosecondPosition() / 1000) % 1000;
		long seconds = (((getMicrosecondPosition() / 1000) - milliseconds) / 1000) % 60;
		long minutes = (((((getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60;
		long hours = ((((((getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60;
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

	public String getTimeLength() {
		long milliseconds = (getMicrosecondLength() / 1000) % 1000;
		long seconds = (((getMicrosecondLength() / 1000) - milliseconds) / 1000) % 60;
		long minutes = (((((getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60;
		long hours = ((((((getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60;
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

	public String getTimeRemaining() {
		long milliseconds = ((getMicrosecondLength() / 1000) % 1000)
				- ((getMicrosecondPosition() / 1000) % 1000);
		long seconds = ((((getMicrosecondLength() / 1000) - milliseconds) / 1000) % 60)
				- ((((getMicrosecondPosition() / 1000) - milliseconds) / 1000) % 60);
		long minutes = ((((((getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60)
				- ((((((getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) % 60);
		long hours = (((((((getMicrosecondLength() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60)
				- (((((((getMicrosecondPosition() / 1000) - milliseconds) / 1000) - seconds) / 60) - minutes) / 60);
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
