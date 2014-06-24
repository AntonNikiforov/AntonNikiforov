package by.epam.multithreading.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.List;
import java.util.LinkedList;

import by.epam.multithreading.entity.Stop; 
import by.epam.multithreading.entity.Bus; 

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		try {
			List<Stop> slist = new LinkedList<Stop>();
			
			for (int i = 1; i <= 5; ++i) {
				slist.add(new Stop(String.valueOf(i), 50));
			}
			
			List<Bus> blist = new LinkedList<>();
			
			for (int i = 1; i <= 10; ++i) {
				blist.add(new Bus(String.valueOf(i), slist));
			}
			/*
			ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
			for (Bus bus : blist) {
				service.scheduleWithFixedDelay(bus, 0, 10, TimeUnit.SECONDS);
			}
			/*/
			ExecutorService service = Executors.newFixedThreadPool(10);
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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}

