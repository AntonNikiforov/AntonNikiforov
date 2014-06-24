package by.epam.multithreading.entity;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import org.apache.log4j.Logger; 
import org.apache.log4j.Level; 

public class Bus implements Runnable {
	
	public static Logger log = Logger.getLogger(Bus.class);
	
	public static int MAX_PLACES = 30;
	
	private String name;
	private List<Stop> route = new LinkedList<>();
	private AtomicInteger numOfPassengers;
	
	final Lock lock = new ReentrantLock();
	
	public Bus(String name, List<Stop> list) {
		this.name = name;
		route = new LinkedList<Stop>(list);
		numOfPassengers = new AtomicInteger(0);
	}
	
	public int pickUpPassenger() throws Exception {
		if (numOfPassengers.get() >= MAX_PLACES) {
			String msg = "no free places at " + toString();
			log.log(Level.ERROR, msg);
			throw new Exception(msg);
		}
		return numOfPassengers.incrementAndGet();
	}
	
	public int debarkPassenger() throws Exception {
		if (numOfPassengers.get() <= 0) {
			String msg = "no passengers in " + toString();
			log.log(Level.ERROR, msg);
			throw new Exception(msg);
		}
		return numOfPassengers.decrementAndGet();
	}
	
	public int getNumOfPassengers() {
		return numOfPassengers.get();
	}
	
	public boolean hasPlaces() {
		return numOfPassengers.get() < MAX_PLACES;
	}
	
	@Override
	public void run() {
		
		Random rand = new Random();
		int number;
		try {
			for (Stop stop : route) {				
				// автобус занимет место
				comeToStop(stop);
				
				// высадка пассажиров
				number = rand.nextInt(getNumOfPassengers() + 1);
				
				for (int i = 0; i < number; ++i) {
					if (getNumOfPassengers() <= 0) {
						break;
					}
					stop.addPerson();
					debarkPassenger();
				}
				//System.out.println(toString() + "\t=>\t" + stop);
				log.log(Level.INFO, toString() + "\t=>\t" + stop);
				
				// если на остановке есть несколько автобусов
				// и текущий автобус не первый,
				// будет осуществляться пересадка
				// с текущего автобуса на первый
				if (stop.getCurrentBuses().size() > 1) {
					
					Bus firstBus = stop.getCurrentBuses().iterator().next();
					
					if (firstBus != this) {
						
						number = rand.nextInt(getNumOfPassengers() + 1);
						
						for (int i = 0; i < number; ++i) {
							//if (getNumOfPassengers() <= 0 || !firstBus.hasPlaces()) {
							if (!firstBus.hasPlaces()) {
								break;
							}
							firstBus.pickUpPassenger();
							debarkPassenger();
						}
						//System.out.println(stop + ": " + toString() + "\t=>\t" + firstBus.toString());
						log.log(Level.INFO, stop + ": " + toString() + "\t=>\t" + firstBus.toString());
					}
				}
				
				// посадка пассажиров
				number = rand.nextInt( ((MAX_PLACES - getNumOfPassengers()) % stop.getNumOfPeople()) + 1);
				
				for (int i = 0; i < number; ++i) {
					if (stop.getNumOfPeople() <=0 || !hasPlaces()) {
						break;
					}
					stop.removePerson();
					pickUpPassenger();
				}
				//System.out.println(toString() + "\t<=\t" + stop);
				log.log(Level.INFO, toString() + "\t<=\t" + stop);
				
				// автобус освобождает место
				comeFromStop(stop);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void comeToStop(Stop stop) throws InterruptedException {
		lock.lock();
		try {
			stop.getCurrentBuses().put(this);
			
			String msg = toString() + " arrived " + stop;
			log.log(Level.INFO, msg);
			//System.out.println(msg);
		} finally {
			lock.unlock();
		}
	}
	
	public void comeFromStop(Stop stop) {
		lock.lock();
		try {
			stop.getCurrentBuses().remove(this);
			
			String msg = toString() + " left " + stop;
			log.log(Level.INFO, msg);
			//System.out.println(msg);
		} finally {
			lock.unlock();
		}
	}
	
	public Lock getLock() {
		return lock;
	}
	
	@Override
	public String toString() {
		return "Bus #" + name + ": " + numOfPassengers;
	}
}
