package services;

import java.sql.Connection;
import java.sql.ResultSet;

import database.BrokenBusQuery;
import javafx.concurrent.Task;
import models.Bus;

public class SelectABusService extends Task<Bus> {

	int id;
	
	public SelectABusService(int id){
		this.id = id;
	}
	
	@Override
	protected Bus call() throws Exception {
		
		Connection conn = BrokenBusQuery.establishConnection();
		
		if(conn.isValid(0)) {
			ResultSet rs = BrokenBusQuery.executeSelectNotBrokenBusQuery(id);
			
			while(rs.next()) {
				int busId = rs.getInt("BusId");
				String busName = rs.getString("BusName");
				int currentRouteId = rs.getInt("CurrentRouteId");
				int currentTerminalId = rs.getInt("CurrentTerminalId");
				boolean broken = rs.getBoolean("Broken");
				boolean charging = rs.getBoolean("Charging");
				int battery = rs.getInt("Battery");
				int delay = rs.getInt("Delay");

				Bus bus = new Bus(busId, busName, currentRouteId, currentTerminalId, broken, charging, battery, delay);
				
				return bus;
			}
			
		} else {
			System.out.println("connection is down");
		}
		conn.close();
		return null;
	}

}
