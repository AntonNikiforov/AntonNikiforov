package by.epam.multithreading.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class Stop {
	
	public static final Logger log = Logger.getLogger(Stop.class);
	
	public static final int MAX_NUM_OF_BUSES = 2;
	
	private String name;
	private BlockingQueue<Bus> currentBuses;
	private AtomicInteger numOfPeople;
	
	private final Lock lock = new ReentrantLock();
	
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
	
	public String getName() {
		return name;
	}
	
	public int addPerson() {
		return numOfPeople.incrementAndGet();
	}
	
	public int removePerson() throws Exception {
		lock.lock();
		try {
			if (numOfPeople.get() <= 0) {
				String msg = "no people at " + toString();
				log.log(Level.ERROR, msg);
				throw new Exception(msg);
			}
			return numOfPeople.decrementAndGet();
		} finally {
			lock.unlock();
		}
	}
	
	public int getNumOfPeople() {
		return numOfPeople.get();
	}
	
	public BlockingQueue<Bus> getCurrentBuses() {
		return currentBuses;
	}
	
	public void parkBus(Bus bus) throws InterruptedException {
		currentBuses.put(bus);
		String msg = toString() + ": " + bus + " arrived";
		log.log(Level.INFO, msg);
	}
	
	public void unparkBus(Bus bus) {
		currentBuses.remove(bus);
		String msg = toString() + ": " + bus + " left";
		log.log(Level.INFO, msg);
	}
	
	@Override
	public String toString() {
		return "Stop #" + name + " " + currentBuses.toString() + ": " + numOfPeople;
	}
}
