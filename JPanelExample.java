public class JPanelExample extends JPanel implements ActionListener {

	public static final long serialVersionUID = 1L;
	public static JButton play = new JButton("Play");
	public static JButton stop = new JButton("Stop");
	public static JButton pause = new JButton("Pause");
	public static JButton mute = new JButton("Mute");
	public static JButton demute = new JButton("Demute");
	public static JFrame frame;
	public static JSlider volume = new JSlider(0, 100);
	public static JLabel timeLength = new JLabel(), timePos = new JLabel(),
			timeRemaining = new JLabel();
	Thread updateTimePos, updateTimeRemaining;
	Sound sound = new Sound(new File(
			"C:/Users/Sioxox/Desktop/The EnderScrolls Minecraft/music.wav"),
			volume);

	public static void main(String[] args) throws Exception {
		frame = new JFrame("Galkins - Sound");
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(new JPanelExample());
		frame.setVisible(true);
	}

	public JPanelExample() throws FontFormatException, IOException {
		setLayout(null);

		add(play);
		add(stop);
		add(pause);
		add(mute);
		add(demute);
		play.addActionListener(this);
		stop.addActionListener(this);
		pause.addActionListener(this);
		mute.addActionListener(this);
		demute.addActionListener(this);

		play.setBounds(100, 60, 100, 35);
		stop.setBounds(155, 60, 100, 35);
		pause.setBounds(45, 60, 100, 35);
		mute.setBounds(100, 105, 100, 35);
		demute.setBounds(100, 105, 100, 35);

		frame.add(timeLength);
		timeLength.setText("00:00:00");
		timeLength.setForeground(Color.RED);
		timeLength.setBounds(157, 100, 300, 100);

		frame.add(timePos);
		timePos.setText("00:00:00 / ");
		timePos.setForeground(Color.RED);
		timePos.setBounds(100, 100, 300, 100);

		frame.add(timeRemaining);
		timeRemaining.setText("00:00:00");
		timeRemaining.setForeground(Color.RED);
		timeRemaining.setBounds(125, -35, 300, 100);

		updateTimePos = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timePos.setText(sound.getTimePosition() + " / ");
				}
			}
		};

		updateTimeRemaining = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timeRemaining.setText(sound.getTimeRemaining());
				}
			}
		};

		frame.add(volume);
		volume.setBounds(75, 25, 150, 25);
		volume.setValue(50);

		pause.setVisible(false);
		mute.setVisible(false);
		demute.setVisible(false);
		stop.setVisible(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			try {
				sound.playFile();
			} catch (UnsupportedAudioFileException | IOException
					| LineUnavailableException e1) {
				e1.printStackTrace();
			}
			play.setBounds(45, 60, 100, 35);
			play.removeActionListener(this);

			play.setVisible(false);
			pause.setVisible(true);
			mute.setVisible(true);
			stop.setVisible(true);

			timeLength.setText(sound.getTimeLength());
			updateTimePos.start();
			updateTimeRemaining.start();
		} else if (e.getSource() == stop) {
			sound.stop();
			play.setBounds(100, 60, 100, 35);
			pause.setVisible(false);
			mute.setVisible(false);
			demute.setVisible(false);
			stop.setVisible(false);
			timePos.setVisible(false);
			timeLength.setVisible(false);
			timeRemaining.setVisible(false);
			play.setVisible(true);
			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						sound.playFile();
					} catch (UnsupportedAudioFileException | IOException
							| LineUnavailableException e1) {
						e1.printStackTrace();
					}
					play.setBounds(45, 60, 100, 35);
					play.removeActionListener(this);

					play.setVisible(false);
					pause.setVisible(true);
					mute.setVisible(true);
					stop.setVisible(true);
					timeLength.setText(sound.getTimeLength());
					/*
					 * updateTimePos.resume(); updateTimeRemaining.resume();
					 */
				}
			});
		} else if (e.getSource() == pause) {
			sound.pause();
			pause.setVisible(false);
			play.setVisible(true);
			play.setEnabled(true);
			play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					play.removeActionListener(this);
					sound.resume();
					play.removeActionListener(this);
					play.setVisible(false);
					pause.setVisible(true);
				}
			});
		} else if (e.getSource() == mute) {
			sound.mute();
			mute.setVisible(false);
			demute.setVisible(true);
		} else if (e.getSource() == demute) {
			sound.demute();
			mute.setVisible(true);
			demute.setVisible(false);
		}
	}
}
