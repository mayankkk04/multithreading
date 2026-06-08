```
Multithreading in JAVA	

-Thread can be created using 2 ways , one by extending Thread class and other by implementing Runnable interface. When a class already extends another class then it has to implement Runnable as multiple inheritance is not allowed in java. 
-If a class implements runnable to then it has to implement its run method as well.
-Thread lifecycle is Ready, Runnable, Blocked/Terminated, Waiting, Timed_Waiting.
-Run method is executed when u execute start method in main method.
-While using thread.sleep cover it with try catch block as it may result in Interrupted Exception.
-User threads - threads that we create.
-Deamon threads - threads that run in background eg. garbage collector. But remember that JVM does not stop for deamon threads. It only waits until all user threads have finished executing.

Methods in Thread - 
run() - executes things.
start() - triggers run method.
sleep() - sleeps the thread.
join() - thread calling this method has to end for the further execution to take place.
setPriority() - u can actually set priority of threads from 1 to 10. default being 5. eg t1.setPriority(Thread.MIN_PRIORITY/MAX_PRIORITY/NORM_PRIORITY).
interrupt() - interrupts the thread calling it so that it stops doing whatever it was.
getName() - Thread.currentThread().getName().
Thread.yield() - this method gives a hint to OS that current thread is willing to sacrifice its time for other imp things like other thread execution. Scheduler can still ignore this.
t1.setDeamon() - We can set any user thread as deamon thread so that JVM stops executing it once other user threads have executed.

-Synchronized - as threads share same memory there can be mistakes while accessing things from same place and then performing tasks using it as both of them do not get the most updated value of it. For this a method can be declared as synchronized or we can write that section of code in a specific synchronized block so that only one thread accesses and updates it at a specific time.

-A Race condition is a flaw that occurs in multithreaded applications when two or more threads concurrently access shared data and try to modify it at the same time.

-Mutual exclusion ensures that only one thread can access a specific shared resource or execute a particular block of code at any given time. And that sensitive section of code is often called as critical section.

-Java Locks - Intrinsic(when u use sync keyword ur using these automatic locks) and extrinsic(manual lock using Lock class form java.util.concurrent.locks) i.e u explicitly tell when to lock and unlock giving u control over how and when people can write. This is because some threads may go take large time to execute so there is no way to set timeouts in this so explicit locks are created.

- Lock is an interface. we create an object of this by creating object of ReentrantLock() as in Lock lock = new ReentrantLock(). Now this has many methods
	lock.tryLock() - acquires lock if available returns true and returns false if not.
	lock.tryLock(time ms, TimeUnit.MILLISECONDS) - waits till this time to acquire lock.
	Thread.currentThread().interrupt() - If an InterruptedException is not handled properly the info that the thread was interrupted will be lost. Only logging is not enough so It restores the shutdown signal of a thread. Why do we need it? Methods like Thread.sleep() or wait() throw an InterruptedException when interrupted. Java automatically clears the thread's "interrupted flag" to false the moment that exception is thrown. If you catch the exception and do nothing, the shutdown signal is permanently lost (swallowed).What does this line do?It manually sets the thread's interrupted flag back to true. It acts like a sticky note saying: We were told to stop. Finish up and exit.
	lock.Interruptibly() - If some other thread interrupts curr thread then interruption of lock acquired is supported. 

	Thread.currentThread().isInterrupted() - u cannot use this if u would have not handled the exception as stated above using manual interrupt() as it would have never been catched.
-Reentrant lock class is used as it prevents the threads from going into deadlocks for acquiring the locks which are already acquired by their outer threads.
-we can also ensure fairness in locks by providing ReentrantLock(true) as a parameter. This ensures that locks are acquired in the sequence as which one requests to acquire it first.

-Synchronized does not understand the difference btn read and write operations so we create
	private final ReadWriteLock lock = new ReentrantReadWriteLock(); - this lock allows multiple locks to read resource concurrently as long as no thread is writing to it. It ensures exclusive access to write operations. Imported from - import java.util.concurrent.locks.ReadWriteLock;
-Interface ReadWriteLock has 2 methods readLock() and writeLock() which return locks for reading and writing.

-Inter-Thread communication - 
basically there are 3 main methods which work only inside a synchronized block.
	-wait() - tells current thread to release lock and wait.
	-notify() - wakes up a single thread that is waiting.
	-notifyAll() - wakes up all threads that are waiting.

-Functional Interface - an interface that has only single abstract method(method with no body) eg. Runnable interface has run().
-Lambda Expression - Now this single abstract method of functional interface can be directly implemented using lambda expression just by (parameter) -> {thingtodo};

-Executor Framework - 

A pool is where all the predefined/ready to work threads are kept. Tasks are allocated to them as per sequence. If all threads are busy then tasks are queued up in a queue. Then sequencially the tasks are provided as per the threads finish their tasks. Sometimes when tasks become more than the queue size in such cases new threads are created to improve efficiency.

Executor interface -> Executor Service interface -> 3 classes -> ThreadPoolExecutor, ForkJoinPool, ScheduledThreadPoolExecutor.  	

ExecutorService methods - 
Callable interface can return output a value whereas Runnable cannot.

ExecutorService executor.execute(Runnable interface with method run()) - perform any task of runnable object.

Future<T> f1 = executor.submit(Callable interface with method call()) - same thing just we can return future type output as well. It is async means it can return value after sometime and 		not immediate so we use Future. Future is nothing but a datatype in java that expects output asynchronously. We can see this output using f1.get() -> this method makes the 		thread to wait until value is not obtained.

 1. https://www.geeksforgeeks.org/java/what-is-java-executor-framework/
 2. https://youtu.be/ZUWs2U71vvk?si=7ZsqMDQjcqPYjUx8 //best video

ThreadPoolExecutor
-Futures and Callable - https://www.youtube.com/watch?v=VPtaTUSaBOM&list=PLQEaRBV9gAFsR15tNo2QLF9d2qc-c018p&index=56

 *main method => ExecutorService executor = Executors.newFixedThreadPool(No.ofThreads); - this configures everything of its own so we dont have to provide everything like min max queue     
		 size etc. It uses fixed no. of threads and infinite queue.
 *Another method => ExecutorService executor = Executors.CachedThreadPool() - unlimited threads + No queue. keep alive time for idle queues is 60 sec.
 *Another metho => ExecutorService executor = Executors.SingleThreadPool() - 1 thread + queue

Types of Queues - ArrayBlockingQueue(limited size), LinkedBlockingQueue(unlimited size)

Rejection Policy(when thread pool has max number of threads that can be created and the queue is also full) - 
		Abort Policy (throws rejected execution exception), 
		Discard Policy(silently discard), 
		DiscarOldestPolicy(remove the oldest task from the queue). //MOSTLY USED

ScheduledThreadPoolExecutor - 	Runs task in future
methods - scheculer object 
	scheduler.schedule(() -> {}, time to wait, timeunit);
	scheduler.scheduleAtFixedRate(() -> {}, time to start, interval after which to repeat everytime, timeunit) 
					eg. ( , 0 , 2, TimeUnit.SECONDS); - task to repeat after every 2 seconds.





```
