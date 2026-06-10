```
Multithreading in JAVA	

-Threads share heap memory.
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

- Lock is an interface. we create an object of this by creating object of ReentrantLock() as in Lock lock = new ReentrantLock(). Now this has many methods:
	lock.tryLock() - acquires lock if available returns true and returns false if not.
	lock.tryLock(time ms, TimeUnit.MILLISECONDS) - waits till this time to acquire lock.
	Thread.currentThread().interrupt() - If an InterruptedException is not handled properly the info that the thread was interrupted will be lost. Only logging is not enough so It 	restores the shutdown signal of a thread. Why do we need it? Methods like Thread.sleep() or wait() throw an InterruptedException when interrupted. Java automatically clears the 	thread's "interrupted flag" to false the moment that exception is thrown. If you catch the exception and do nothing, the shutdown signal is permanently lost (swallowed).What does 	this line do?It manually sets the thread's interrupted flag back to true. It acts like a sticky note saying: We were told to stop. Finish up and exit.
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

IMP - Callable interface can return output a value whereas Runnable cannot. In callable we can catch an exception whereas in runnable we cannot.

ExecutorService methods - 
create thread pool -> ExecutorService executor = Executors.newFixedThreadPool(3); ->
1.executor.execute(Runnable interface with method run()) - perform any task of runnable object.

2.Future<T> f1 = executor.submit(Callable interface with method call()) - same thing just we can return future type output as well. It is async means it can return value after sometime and 		not immediate so we use Future. Future is nothing but a datatype in java that expects output asynchronously. We can see this output using f1.get() -> this method makes the 		thread to wait until value is not obtained.
		
	$ 5 core methods of Future Interface
		1. get() - It puts the running thread(mostly main) on hold until it gets the output from the future.
		2. get(long timeout, TimeUnit unit) - will wait for certain time for output else will throw TimeoutException so that program continues.
		3. cancel(boolean mayInterruptIfRunning) - If task has not started then it will cancel it. If it has started executing then if argument is true then it will try to 				interrupt it but if false then it will wait for it to finish.
		4. isDone() - returns true if task has finished processing. commonly used as a non blocking check before calling get() as get causes blocking.
		5. isCancelled() - Returns true if the task was cancelled before completing normally.
	
	$ Completable Future - because Future had limitations like its blocking nature like get() affecting parallel execution. It uses Fork-join pool executor
		CompletableFuture<T> f1 = CompletableFuture.supplyAsync(); -> returns value
							   .runAsync(); -> No return value
		We can chain this with many other methods like .thenApply() - returns a value and can access the result of previous task by chaining, .thenAccept() - consumes a value also 		can access result of prev task by chaining, .thenRun() -same as apply but cannot access result of prev task.


3. Shutdown() - whenever we use executor its necessary to end it using shutdown. This method stops any new tasks but allows the current executing tasks to complete. 
4. shutdownNow() - suggests to stop everything even the currently running tasks.
5. invokeAll() - take List<callables> and returns List<Futures>
6. invokeAny() -

Videos
 1. https://www.geeksforgeeks.org/java/what-is-java-executor-framework/
 2. https://youtu.be/ZUWs2U71vvk?si=7ZsqMDQjcqPYjUx8 //best video
 3. Futures and Callable - https://www.youtube.com/watch?v=VPtaTUSaBOM&list=PLQEaRBV9gAFsR15tNo2QLF9d2qc-c018p&index=56
 4. Completable Future - https://www.youtube.com/watch?v=FGN225TiXaE&list=PLQEaRBV9gAFsR15tNo2QLF9d2qc-c018p&index=58


ThreadPoolExecutor

How to create Thread Pools??
 1.main method => ExecutorService executor = Executors.newFixedThreadPool(No.ofThreads); - this configures everything of its own so we dont have to provide everything like min max queue     
		 size etc. It uses fixed no. of threads and infinite queue.
 2.Another method => ExecutorService executor = Executors.CachedThreadPool() - unlimited threads + No queue. keep alive time for idle queues is 60 sec.
 3.Another method => ExecutorService executor = Executors.SingleThreadPool() - 1 thread + queue

Types of Queues - ArrayBlockingQueue(limited size), LinkedBlockingQueue(unlimited size)

Rejection Policy(when thread pool has max number of threads that can be created and the queue is also full) - 
		Abort Policy (throws rejected execution exception), 
		Discard Policy(silently discard), 
		DiscarOldestPolicy(remove the oldest task from the queue). //MOSTLY USED

ScheduledThreadPoolExecutor - Runs task in future

methods - scheculer object 
	scheduler.schedule(() -> {}, time to wait, timeunit);
	scheduler.scheduleAtFixedRate(() -> {}, time to start, interval after which to repeat everytime, timeunit) 
					eg. ( , 0 , 2, TimeUnit.SECONDS); - task to repeat after every 2 seconds.

Fork-Join Pool Executor - Divide and Conquer 

Each thread in the pool has its own queue called work stealing queue apart from the general queue used for queuing tasks called submission/blocking queue. Remember that the first priority of a thread is to check its own work stealing queue then general queue then other threads work stealing queue(hence called work stealing).

ThreadLocal

ThreadLocal is a special class in Java that provides thread-local variables. It creates an isolated copy of that variable shared for every single thread that accesses it.
	Core Methods: The ThreadLocal API is straightforward and consists of 4 main methods:
		withInitial(Supplier<? extends S> supplier): Creates a thread-local variable and defines its starting value using a lambda expression.
		set(T value): Sets the current thread's localized copy to a specific value.
		get(): Returns the localized value belonging strictly to the calling thread.
		remove(): Deletes the current thread's localized value. This is critical to prevent memory leaks.

Virtual Thread

Traditionally, every Java thread (java.lang.Thread) was a platform thread, which acts as a thin wrapper around a heavy, 1:1 OS thread. Virtual threads decouple this relationship, allowing millions of threads to run concurrently on just a few underlying OS threads M:N (Many virtual threads to few OS threads). How this helps is that say one thread has gone into wait state for like some IO operation then y keep the os idle so it demounts that vthread and links another vthread to that os thread.

	Thread t1 = Thread.startVirtualThread(Runnable)

java.util.concurrent package contains 2 classes both used to sync execution of multiple threads.

1.CountDownLatch - helps threads to wait until certain tasks in other threads are finished.
	method - countDown() - method called by threads to signal that a certain operation or task has been completed. It decreases internal count by 1. latch is released once count becomes 0 then all waiting threads are released. used when a main thread needs to wait for all worker threads to finish using countDown method.

2.CyclicBarrier - allows a set of threads to wait for each other to reach a common barrier point before continuing execution. unlike countDownLatch it can be reset and used again. each thread calls await method which causes it to wait until all parties have arrived at the barrier. 

Semaphore - controls how many threads can access a resource simultaneously.
	acquire() — take a permit
	release() — return a permit
	Binary semaphore (1 permit) = basically a mutex/lock

```
