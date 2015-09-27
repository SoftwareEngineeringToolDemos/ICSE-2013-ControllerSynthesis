package ar.uba.dc.lafhis.enactment.robot;

import ar.uba.dc.lafhis.enactment.TransitionEvent;
import ar.uba.dc.lafhis.enactment.robot.ARDrone.V1.DroneAPI;
import ar.uba.dc.lafhis.enactment.robot.ARDrone.V1.PicWindow;
import ar.uba.dc.lafhis.enactment.robot.ARDrone.V1.TextWindow;

public class ARDroneUpdatingControllers<State, Action> extends
		RobotAdapter<State, Action>
/* implements IMessageListener */{
	DroneAPI drone;

	private PicWindow frontWin;
	// private PicWindow bottomWin;

	private TextWindow tWin;

	protected Action takeoff;
	protected Action land;
	protected Action blink;
	protected Action read;
	protected Action readValue0;
	protected Action readValue1;
	protected Action readValue2;
	protected Action lowBattery;
	protected Action charge;

	// protected RobotProtocolSession session;

	public ARDroneUpdatingControllers(String name, Action success,
			Action failure, Action lost, Action takeoff, Action land,
			Action blink, Action read, Action readValue0, Action readValue1,
			Action readValue2, Action lowBattery, Action charge) {
		super(name, success, failure, lost);
		this.takeoff = takeoff;
		this.land = land;
		this.blink = blink;
		this.read = read;
		this.readValue0 = readValue0;
		this.readValue1 = readValue1;
		this.readValue2 = readValue2;
		this.lowBattery = lowBattery;
		this.charge = charge;
	}

	@Override
	protected void primitiveHandleTransitionEvent(
			TransitionEvent<Action> transitionEvent) throws Exception {
		if (transitionEvent.getAction().equals(takeoff)) {
			System.out.println("TAKE OFF");
			if (drone.takeoff()) {
				System.out.println("SuccessTakeOff");
				// Success
			} else {
				System.out.println("FailTakeOff");
				// Failure
			}
			drone.hover(5000);
		} else if (transitionEvent.getAction().equals(land)) {
			System.out.println("LAND");
			drone.landing();
			drone.wait(10000);
		} else if (transitionEvent.getAction().equals(blink)) {
			System.out.println("BLINK");
			if (drone.blinkLED("ORANGE")) {
				System.out.println("SuccessBlink");
			} else {
				System.out.println("FailBlink");
			}
			drone.hover(500);
		} else if (transitionEvent.getAction().equals(read)) {
			System.out.println("READ");
			int times = 200;
			tWin.text = "";
			while (times > 0 && isLabel(tWin.text)) {
				// bottomWin.image = drone.takeBottomPicture();
				// bottomWin.repaint();
				frontWin.image = drone.takeFrontPicture();
				frontWin.repaint();

				tWin.text = drone.readFront();
				tWin.repaint();
				drone.hover(500);
				// if(!isLabel(tWin.text)){
				// System.out.println("BOTTOM");
				// tWin.text = drone.readBottom();
				// tWin.repaint();
				// drone.hover(500);
				// }
				times--;
			}
			interpretate(tWin.text);

		} else if (transitionEvent.getAction().equals(readValue0)) {
			System.out.println("READ VALUE 0");
		} else if (transitionEvent.getAction().equals(readValue1)) {
			System.out.println("READ VALUE 1");
		} else if (transitionEvent.getAction().equals(readValue2)) {
			System.out.println("READ VALUE 2");
		} else if (transitionEvent.getAction().equals(lowBattery)) {
			System.out.println("LOW BATTERY");
			drone.blinkLED("RED");
		} else if (transitionEvent.getAction().equals(charge)) {
			System.out.println("CHARGE");
			drone.blinkLED("GREEN");
		}
	}

	private boolean isLabel(String text) {
		return !(tWin.text.equalsIgnoreCase("Tag 2")
				|| tWin.text.equalsIgnoreCase("Tag 1") || tWin.text
					.equalsIgnoreCase("Tag 0"));
	}

	private void interpretate(String text) throws Exception {
		if (text.equalsIgnoreCase("Tag 0")) {
			fireTransitionEvent(readValue0);
		} else if (text.equalsIgnoreCase("Tag 1")) {
			fireTransitionEvent(readValue1);
		} else if (text.equalsIgnoreCase("Tag 2")) {
			fireTransitionEvent(readValue2);
		} else {
			fireTransitionEvent(failure);
		}
	}

	@Override
	public void setUp() {
		System.out.println("SET UP");
		if (frontWin == null) {
			frontWin = new PicWindow();
		} else {
			frontWin.setVisible(true);
		}
		// if(bottomWin==null){
		// bottomWin = new PicWindow();
		// }else{
		// bottomWin.setVisible(true);
		// }
		if (tWin == null) {
			tWin = new TextWindow();
		} else {
			tWin.setVisible(true);
		}
		drone = new DroneAPI();
		drone.start();
	}

	@Override
	public void tearDown() {
		System.out.println("TEAR DOWN");
		drone.stop();
		frontWin.setVisible(false);
		// bottomWin.setVisible(false);
		tWin.setVisible(false);
	}

}
