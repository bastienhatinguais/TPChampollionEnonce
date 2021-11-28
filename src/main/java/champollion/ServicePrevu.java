package champollion;

public class ServicePrevu {

	private int volumeCM;
	private int volumeTD;
	private int volumeTP;

	private final Enseignant enseignant;
	private final UE ue;

	public ServicePrevu(Enseignant e, UE ue, int volumeCM, int volumeTD, int volumeTP) {
		if (null == e || null == ue)
			throw new NullPointerException();
		this.enseignant = e;
		this.ue = ue;

		this.setVolumeCM(volumeCM);
		this.setVolumeTD(volumeTD);
		this.setVolumeTP(volumeTP);
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public UE getUe() {
		return ue;
	}

	public int getVolumeCM() {
		return volumeCM;
	}

	public int getVolumeTD() {
		return volumeTD;
	}

	public int getVolumeTP() {
		return volumeTP;
	}

	public void setVolumeCM(int volumeCM) {
		this.volumeCM = volumeCM;
	}

	public void setVolumeTD(int volumeTD) {
		this.volumeTD = volumeTD;
	}

	public void setVolumeTP(int volumeTP) {
		this.volumeTP = volumeTP;
	}

}
