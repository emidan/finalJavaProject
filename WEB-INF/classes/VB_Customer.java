package beans;

public class VB_Customer implements java.io.Serializable {

	private int customerID;
	private String customerName;
	private String customerEmail;	

	public VB_Customer() {

	}

	public void setCustomerID(int num) {

		customerID = num;
	}

	public int getCustomerID() {

		return customerID;
	}

	public void setCustomerName(String str) {

		customerName = str;
	}

	public String getCustomerName() {

		return customerName;
	}

	public void setCustomerEmail(String str) {

		customerEmail = str;
	}

	public String getCustomerEmail() {

		return customerEmail;
	}
}