package com.ebook.backend.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Date;

public interface StatisticsService {

    JSONArray getBookConsumption(Date start, Date end);

    JSONArray getUserConsumption(Date start, Date end);

    JSONObject userToBook(Date start, Date end);
}
