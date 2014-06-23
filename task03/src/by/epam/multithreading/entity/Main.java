package by.epam.multithreading.entity;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;
import java.util.*;

import org.apache.log4j.*; 

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

