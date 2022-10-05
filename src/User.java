
public class User {
	private String id;
	private String name;
	private String residingCountry;
	private String residingLocality;

	public User(String id, String name, String country, String locality) {
		this.id = id;
		this.name = name;
		this.residingCountry = country;
		this.residingLocality = locality;
	}

	public String getName() {
		return this.name;
	}

	public String getId() {
		return this.id;
	}

	public String getResidingCountry() {
		return this.residingCountry;
	}

	public String getResidingLocality() {
		return this.residingLocality;
	}
}
