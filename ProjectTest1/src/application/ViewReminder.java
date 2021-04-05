package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ViewReminder {
	private final SimpleStringProperty reminder;
	
	public ViewReminder() {
		this.reminder = new SimpleStringProperty();
	}
	
		public final StringProperty reminderProperty() {
			return reminder;
		}
		
		public final String getReminder() {
			return reminder.get();
		}
		
		public final void setReminder(String value) {
			reminder.set(value);
		}
}
