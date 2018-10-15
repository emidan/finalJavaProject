package beans;

public class VB_Business implements java.io.Serializable {
	
	private int businessID;
	private String businessName;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;

	public VB_Business() {

	}

	public void setBusinessID(int num) {
		businessID = num;
	}

	public int getBusinessID() {

		return businessID;
	}

	public void setBusinessName(String str) {

		businessName = str;
	}

	public String getBusinessName() {

		return businessName;
	}

	public void setAddressLine1(String str) {

		addressLine1 = str;
	}

	public String getAddressLine1() {

		return addressLine1;
	}

	public void setAddressLine2(String str) {

		addressLine2 = str;
	}

	public String getAddressLine2() {

		return addressLine2;
	}

	public void setCity(String str) {

		city = str;
	}

	public String getCity() {

		return city;
	}

	public void setState(String str) {

		state = str;
	}

	public String getState() {

		return state;
	}

	public void setZip(String str) {

		zip = str;
	}

	public String getZip() {

		return zip;
	}

}