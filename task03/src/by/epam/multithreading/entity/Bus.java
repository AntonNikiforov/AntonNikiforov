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
	
	public static final Logger log = Logger.getLogger(Bus.class);
	
	public static final int MAX_PLACES = 30;
	
	private String name;
	private List<Stop> route = new LinkedList<>();
	private AtomicInteger numOfPassengers;
	
	private final Lock lock = new ReentrantLock();
	
	public Bus(String name, List<Stop> list) {
		this.name = name;
		route = new LinkedList<Stop>(list);
		numOfPassengers = new AtomicInteger(0);
	}
	
	public int pickUpPassenger() throws Exception {
		lock.lock();
		try {
			if (numOfPassengers.get() >= MAX_PLACES) {
				String msg = "no free places at " + toString();
				log.log(Level.ERROR, msg);
				throw new Exception(msg);
			}
			return numOfPassengers.incrementAndGet();
		} finally {
			lock.unlock();
		}
	}
	
	public int dropOffPassenger() throws Exception {
		lock.lock();
		try {
			if (numOfPassengers.get() <= 0) {
				String msg = "no passengers in " + toString();
				log.log(Level.ERROR, msg);
				throw new Exception(msg);
			}
			return numOfPassengers.decrementAndGet();
		} finally {
			lock.unlock();
		}
	}
	
	public int getNumOfPassengers() {
		return numOfPassengers.get();
	}
	
	public boolean hasPlaces() {
		lock.lock();
		try {
			return numOfPassengers.get() < MAX_PLACES;
		} finally {
			lock.unlock();
		}
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
					dropOffPassenger();
				}
				//System.out.println(toString() + "\t=>\t" + stop);
				log.log(Level.INFO, toString() + "\t=>\t" + stop);
				
				// если на остановке есть несколько автобусов
				// и текущий автобус не первый,
				// будет осуществляться пересадка
				// с текущего автобуса на первый
				if (stop.getCurrentBuses().size() > 1) {
					
					//Bus firstBus = stop.getCurrentBuses().iterator().next();
					Bus firstBus = stop.getCurrentBuses().peek();
					
					if (firstBus != null && firstBus != this) {
						
						number = rand.nextInt(getNumOfPassengers() + 1);
						
						for (int i = 0; i < number; ++i) {
							//if (getNumOfPassengers() <= 0 || !firstBus.hasPlaces()) {
							if (!firstBus.hasPlaces()) {
								break;
							}
							firstBus.pickUpPassenger();
							dropOffPassenger();
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
	
	private void comeToStop(Stop stop) throws InterruptedException {
		
		stop.parkBus(this);
		
		String msg = toString() + " arrived " + stop;
		log.log(Level.INFO, msg);
		//System.out.println(msg);
	}
	
	private void comeFromStop(Stop stop) {

		stop.unparkBus(this);
		
		String msg = toString() + " left " + stop;
		log.log(Level.INFO, msg);
		//System.out.println(msg);
	}
	
	@Override
	public String toString() {
		return "Bus #" + name + ": " + numOfPassengers;
	}
}
