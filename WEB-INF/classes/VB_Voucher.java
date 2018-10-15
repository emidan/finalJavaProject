package beans;

public class VB_Voucher implements java.io.Serializable {

	private int voucherID;
	private String effectiveDate;
	private String expirationDate;
	private String details1;
	private String details2;
	private String businessName;
	private int businessID;

	public VB_Voucher() {

	}

	public void setVoucherID(int num) {

		voucherID = num;
	}

	public int getVoucherID() {

		return voucherID;
	}

	public void setEffectiveDate(String str) {

		effectiveDate = str;
	}

	public String getEffectiveDate() {

		return effectiveDate;
	}

	public void setExpirationDate(String str) {

		expirationDate = str;
	}

	public String getExpirationDate() {

		return expirationDate;
	}

	public void setDetails1(String str) {

		details1 = str;
	}

	public String getDetails1() {

		return details1;
	}

	public void setDetails2(String str) {

		details2 = str;
	}

	public String getDetails2() {

		return details2;
	}

	public void setBusinessName(String str) {

		businessName = str;
	}

	public String getBusinessName() {

		return businessName;
	}

	public void setBusinessID(int num) {
		
		businessID = num;
	}

	public int getBusinessID() {

		return businessID;
	}
}