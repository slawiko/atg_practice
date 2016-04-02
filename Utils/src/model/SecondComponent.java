package model;

public class SecondComponent {
	private FirstComponent firstComponent;
	private String lastName;

	public SecondComponent() {
	}

	public FirstComponent getFirstComponent() {
		return this.firstComponent;
	}

	public void setFirstComponent(FirstComponent firstComponent) {
		this.firstComponent = firstComponent;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
