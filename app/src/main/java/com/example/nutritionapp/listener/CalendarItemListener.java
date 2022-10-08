package com.example.nutritionapp.listener;

import java.time.LocalDate;

public interface CalendarItemListener {

    void onCalendarCellClicked(int position, LocalDate date);
}
