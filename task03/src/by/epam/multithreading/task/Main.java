package by.epam.multithreading.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;
import java.util.LinkedList;

import by.epam.multithreading.entity.Stop; 
import by.epam.multithreading.entity.Bus; 

public class Main {
	
	public static void main(String[] args) {
		
		final int NUM_OF_STOPS = 5;
		final int NUM_OF_PEOPLE = 50;
		final int NUM_OF_BUSES = 10;
		
		try {
			List<Stop> slist = new LinkedList<Stop>();
			
			for (int i = 1; i <= NUM_OF_STOPS; ++i) {
				slist.add(new Stop(String.valueOf(i), NUM_OF_PEOPLE));
			}
			
			List<Bus> blist = new LinkedList<>();
			
			for (int i = 1; i <= NUM_OF_BUSES; ++i) {
				blist.add(new Bus(String.valueOf(i), slist));
			}
			/*
			ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
			for (Bus bus : blist) {
				service.scheduleWithFixedDelay(bus, 0, 10, TimeUnit.SECONDS);
			}
			/*/
			ExecutorService service = Executors.newFixedThreadPool(NUM_OF_BUSES);
			//service.invokeAll(blist);
			
			for (Bus bus : blist) {
				service.submit(bus);
			}
			TimeUnit.SECONDS.sleep(5);
			
			for (Bus bus : blist) {
				System.out.println(bus);
			}
			for (Stop stop : slist) {
				System.out.println(stop);
			}
			
			service.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}

