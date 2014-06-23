package by.epam.multithreading.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.*;

import org.apache.log4j.*; 

public class Stop {
	
	public static Logger log = Logger.getLogger(Stop.class);
	
	public static int MAX_NUM_OF_BUSES = 2;
	
	private String name;
	private BlockingQueue<Bus> currentBuses;
	private AtomicInteger numOfPeople;
	
	Lock lock = new ReentrantLock();
	
	public Stop(String name, int num) {
		if (num < 0) {
			String msg = "int num must not be negative";
			log.log(Level.ERROR, msg);
			throw new IllegalArgumentException(msg);
		}
		
		this.name = name;
		currentBuses = new LinkedBlockingQueue<Bus>(MAX_NUM_OF_BUSES);
		numOfPeople = new AtomicInteger(num);
	}
	
	public int addPerson() {
		return numOfPeople.incrementAndGet();
	}
	
	public int removePerson() throws Exception {
		if (numOfPeople.get() <= 0) {
			String msg = "no people at " + toString();
			log.log(Level.ERROR, msg);
			throw new Exception(msg);
		}
		return numOfPeople.decrementAndGet();
	}
	
	public int getNumOfPeople() {
		return numOfPeople.get();
	}
	
	public BlockingQueue<Bus> getCurrentBuses() {
		return currentBuses;
	}
	
	@Override
	public String toString() {
		return "Stop #" + name + " " + currentBuses.toString() + ": " + numOfPeople;
	}
}
