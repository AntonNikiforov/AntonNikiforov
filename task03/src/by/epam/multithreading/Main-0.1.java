//package by.epam.multithreading.entity;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.*;
import java.util.*;

import org.apache.log4j.*; 

public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
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
		//es.invokeAll(blist);
		
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
	}
}

class Stop {
	
	public static int MAX_NUM_OF_BUS = 2;
	
	private String name;
	private BlockingQueue<Bus> list;
	private AtomicInteger num;
	
	Lock lock = new ReentrantLock();
	
	public Stop(String name, int num) {
		this.name = name;
		list = new LinkedBlockingQueue<Bus>(MAX_NUM_OF_BUS);
		this.num = new AtomicInteger(num);
	}
	
	public int inc() {
		return num.incrementAndGet();
	}
	
	public int dec() {
		return num.decrementAndGet();
	}
	
	public int getNum() {
		return num.get();
	}
	
	public BlockingQueue<Bus> getList() {
		return list;
	}
	
	public String toString() {
		return "Stop #" + name + " " + list.toString() + ": " + getNum();
	}
}

class Bus implements Runnable {
	
	public static int WAIT_PERSON = 10;
	public static int WAIT_STOP = 1000;
	public static int MAX_PLACES = 30;
	
	public static Logger log = Logger.getLogger(Bus.class);
	
	private String name;
	private Stop stop;
	private List<Stop> slist = new LinkedList<>();
	private AtomicInteger num;
	
	Lock lock = new ReentrantLock();
	
	public Bus(String name, List<Stop> list) {
		this.name = name;
		slist = new LinkedList<Stop>(list);
		num = new AtomicInteger(0);
	}
	
	public int inc() {
		return num.incrementAndGet();
	}
	
	public int dec() {
		return num.decrementAndGet();
	}
	
	public int getNum() {
		return num.get();
	}
	
	public boolean hasPlaces() {
		return num.get() < MAX_PLACES;
	}
	
	public void run() {
		Iterator<Stop> iter = slist.iterator();
		Iterator<Bus> bIter;
		Random rand = new Random();
		int number;
		try {
			//while (iter.hasNext()) {
			//	stop = iter.next();
			for (Stop stop : slist) {
			//for(int k = 0; k < 5; ++k) {
				//TimeUnit.MILLISECONDS.sleep(WAIT_STOP);
				comeToStop(stop);
				
				//synchronized (stop) {
				number = rand.nextInt(getNum()+1);
				
				for (int i = 0; i < number; ++i) {
					//TimeUnit.MILLISECONDS.sleep(WAIT_PERSON);
					//synchronized (stop) {
					if (getNum() <= 0) {
						break;
					}
						
					stop.inc();
					dec();
					//System.out.println(toString() + "\t=>\t" + stop);
					//}
				}
				System.out.println(toString() + "\t=>\t" + stop);
				//}
				
				if (stop.getList().size() > 1) {
					bIter = stop.getList().iterator();
					Bus prevBus = bIter.next();
					if (prevBus != this) {
						//synchronized (stop) {
						number = rand.nextInt(5);
						
						for (int i = 0; i < number; ++i) {
							//TimeUnit.MILLISECONDS.sleep(WAIT_PERSON);
							//synchronized (stop) {
							if (getNum() <= 0 || prevBus.getNum() <= 0) {
								break;
							}
								
							prevBus.inc();
							dec();
							//System.out.println(toString() + "\t=>\t" + stop);
							//}
						}
						System.out.println(stop + ": " + toString() + "\t=>\t" + prevBus.toString());
						//}
					}
				}
				
				//synchronized (stop) {
				number = rand.nextInt( ((MAX_PLACES - getNum()) % stop.getNum()) + 1);
				//number = rand.nextInt(10);
				for (int i = 0; i < number; ++i) {
					//TimeUnit.MILLISECONDS.sleep(WAIT_PERSON);
					TimeUnit.MILLISECONDS.sleep(0);
					if (stop.getNum() <=0 || !hasPlaces()) {
						break;
					}
					stop.dec();
					inc();
					//System.out.println(toString() + "\t<=\t" + stop);
				}
				System.out.println(toString() + "\t<=\t" + stop);
				//}
				
				comeFromStop(stop);
				//stop = null;
			//}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void comeToStop(Stop stop) throws InterruptedException {
		//BasicConfigurator.configure();
		stop.getList().put(this);
		
		String msg = toString() + " arrived " + stop;
		log.log(Level.INFO, msg);
		//System.out.println(msg);
	}
	
	public void comeFromStop(Stop stop) {
		//BasicConfigurator.configure();
		stop.getList().remove(this);
		
		String msg = toString() + " left the " + stop;
		log.log(Level.INFO, msg);
		//System.out.println(msg);
	}
	
	public String toString() {
		return "Bus #" + name + ": " + getNum();
	}
}
