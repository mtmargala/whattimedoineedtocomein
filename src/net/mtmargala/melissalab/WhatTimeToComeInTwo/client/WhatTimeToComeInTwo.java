package net.mtmargala.melissalab.WhatTimeToComeInTwo.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.Format;
import com.google.gwt.user.datepicker.client.DatePicker;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WhatTimeToComeInTwo implements EntryPoint {

	private HorizontalPanel rootPanel = new HorizontalPanel();
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel mainPanel2 = new VerticalPanel();
	private Button addRow = new Button("Add Row");
	private Button addRow2 = new Button("Add Row");
	private Button refreshFinalTime = new Button("Refresh Final Time");
	private Button refreshFinalTime2 = new Button("Refresh Final Time");
	private TextBox hour = new TextBox();
	private TextBox hour2 = new TextBox();
	private HorizontalPanel finalPanel = new HorizontalPanel();
	private HorizontalPanel finalPanel2 = new HorizontalPanel();
	private FlexTable flexTable = new FlexTable();
	private FlexTable flexTable2 = new FlexTable();
	private DateBox dateBox = new DateBox();
	private DateBox dateBox2 = new DateBox();
	private Label finalDateText = new Label();
	private Label finalDateText2 = new Label();
	private ArrayList<Date> dates = new ArrayList<Date>();
	private ArrayList<Date> dates2 = new ArrayList<Date>();
	private ArrayList<TextBox> hours = new ArrayList<TextBox>();
	private ArrayList<TextBox> hours2 = new ArrayList<TextBox>();
	DateTimeFormat dtf = DateTimeFormat.getFormat("EEE, MMM d, h:mm a");
	

	@Override
	public void onModuleLoad() {
		flexTable.setText(0, 0, "Starting Date");
	    flexTable.setText(0, 1, "Hours(HH:MM:SS)");
	    flexTable2.setText(0, 0, "Ending Date");
	    flexTable2.setText(0, 1, "Hours(HH:MM:SS)");
		
		// Set the value in the text box when the user selects a date
		DatePicker datePicker = dateBox.getDatePicker();
		DatePicker datePicker2 = dateBox2.getDatePicker();
		
		dateBox.setFormat(new DateBox.DefaultFormat(dtf));
		dateBox2.setFormat(new DateBox.DefaultFormat(dtf));
		
		dates.add(new Date());
		dates2.add(new Date());
		
	    datePicker.setValue(dates.get(0), true);
	    datePicker2.setValue(dates2.get(0), true);
		
		hour.setText("20:00:00");
		hours.add(hour);
		
		hour2.setText("20:00:00");
		hours2.add(hour2);
		
		flexTable.setWidget(1, 0, dateBox);
		flexTable.setWidget(1, 1, hours.get(0));
		flexTable2.setWidget(1, 0, dateBox2);
		flexTable2.setWidget(1, 1, hours2.get(0));
		
		recalculateAllValues(dates, hours, finalDateText);
		recalculateAllValues(dates2, hours2, finalDateText2);
		
		finalPanel.add(finalDateText);
		finalPanel.add(refreshFinalTime);
		finalPanel2.add(finalDateText2);
		finalPanel2.add(refreshFinalTime2);
		
		mainPanel.add(flexTable);
		mainPanel.add(addRow);
		mainPanel.add(finalPanel);
		
		mainPanel2.add(flexTable2);
		mainPanel2.add(addRow2);
		mainPanel2.add(finalPanel2);
		
		rootPanel.add(mainPanel);
		rootPanel.add(mainPanel2);
		
		//Link rootPanel to the 'mainContent' in the HTML
		RootPanel.get("mainContent").add(rootPanel);
		
	    addRow.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	         addRow(dates, hours, flexTable, finalDateText);
	      }
	    });
	    
	    addRow2.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  addRow(dates2, hours2, flexTable2, finalDateText2);
	      }
	    });
	    
	    refreshFinalTime.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  recalculateAllValues(dates, hours, finalDateText);
	      }
	    });
	    
	    refreshFinalTime2.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	    	  recalculateAllValues(dates2, hours2, finalDateText2);
	      }
	    });
	    
	    dateBox.addValueChangeHandler(new ValueChangeHandler<Date>() {
	    	public void onValueChange(ValueChangeEvent<Date> event) {
	    		dates.get(0).setTime(event.getValue().getTime());
	    		recalculateAllValues(dates, hours, finalDateText);
	    	}
	    });
	    
	    dateBox2.addValueChangeHandler(new ValueChangeHandler<Date>() {
	    	public void onValueChange(ValueChangeEvent<Date> event) {
	    		dates2.get(0).setTime(event.getValue().getTime());
	    		recalculateAllValues(dates2, hours2, finalDateText2);
	    	}
	    });
	}
	
	private void recalculateAllValues(ArrayList<Date> dates, ArrayList<TextBox> hours, Label finalDateText)
	{
		for (int i = 1; i < dates.size(); i++)
		{
			Date date = dates.get(i-1);
			String text = hours.get(i-1).getText();
			dates.set(i, getTimeFromDate(date, text, dates == this.dates));
		}
		Date lastDate = dates.get(dates.size()-1);
		String lastText = hours.get(hours.size()-1).getText();
		Date finalDate = getTimeFromDate(lastDate, lastText, dates == this.dates);
		finalDateText.setText(dtf.format(finalDate));
	}
	
	private void addRow(final ArrayList<Date> dates, final ArrayList<TextBox> hours, FlexTable flexTable, final Label finalDateText)
	{
		//create two new text boxes, 1st one not editable, 
		TextBox dateBox = new TextBox();

		//Create new Date object
		Date date = getTimeFromDate(dates.get(dates.size()-1),hours.get(hours.size()-1).getText(), dates == this.dates);

		dateBox.setText(dtf.format(date));
		TextBox hourBox = new TextBox();
		hourBox.setText("20:00:00");
		//add to each array list
		dates.add(date);
		hours.add(hourBox);
		
		// Listen for keyboard events in the input box.
		hourBox.addKeyPressHandler(new KeyPressHandler() {
	      public void onKeyPress(KeyPressEvent event) {
	        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
	          recalculateAllValues(dates, hours, finalDateText);
	        }
	      }
	    });
	    
		hourBox.addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				recalculateAllValues(dates, hours, finalDateText);
			}
		});
		
		int row = flexTable.getRowCount();
		flexTable.setText(row, 0, dtf.format(date));
		flexTable.setWidget(row, 1, hourBox);
		
		recalculateAllValues(dates, hours, finalDateText);
	}
	
	private Date getTimeFromDate(Date startingDate, String text, boolean add)
	{
		long timeToAdd = 0;
		text = text.trim();
		int indexOfFirst = text.indexOf(':');
		int indexOfLast = text.indexOf(':', indexOfFirst+1);
		long hoursToAdd = Integer.parseInt(text.substring(0, indexOfFirst));
		long minToAdd = Integer.parseInt(text.substring(indexOfFirst+1, indexOfLast));
		long secondsToAdd = Integer.parseInt(text.substring(indexOfLast+1, text.length()));
		
		timeToAdd = hoursToAdd * 60L * 60L * 1000L +
					minToAdd * 60 * 1000L +
					secondsToAdd * 1000L;
		
		long currentTime = startingDate.getTime();
		
		
		
		long finalTime = add ? currentTime + timeToAdd : currentTime - timeToAdd;
		
		return new Date(finalTime);
	}
}
