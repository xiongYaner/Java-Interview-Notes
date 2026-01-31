# 多线程

Java多线程是Java提供的一种并发编程机制，它允许程序同时执行多个任务。Java多线程是Java的核心特性之一，广泛应用于各种应用程序中。

## 线程的创建和启动

Java有两种创建线程的方法：
- 继承Thread类
- 实现Runnable接口

### 继承Thread类

```java
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        thread1.setName("Thread 1");
        thread1.start();

        MyThread thread2 = new MyThread();
        thread2.setName("Thread 2");
        thread2.start();
    }
}
```

### 实现Runnable接口

```java
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread thread1 = new Thread(runnable, "Thread 1");
        thread1.start();

        Thread thread2 = new Thread(runnable, "Thread 2");
        thread2.start();
    }
}
```

### 使用Callable和Future

```java
public class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Thread running: " + Thread.currentThread().getName());
        return 10;
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = executor.submit(new MyCallable());
        Future<Integer> future2 = executor.submit(new MyCallable());

        try {
            Integer result1 = future1.get();
            Integer result2 = future2.get();
            System.out.println("Result 1: " + result1);
            System.out.println("Result 2: " + result2);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
```

## 线程生命周期

Java线程有5个生命周期状态：
- 新建状态（New）：线程刚被创建，尚未启动
- 就绪状态（Runnable）：线程已准备好运行，等待CPU调度
- 运行状态（Running）：线程正在CPU上运行
- 阻塞状态（Blocked）：线程等待某些资源或条件
- 死亡状态（Terminated）：线程已完成执行或异常终止

## 线程同步

Java提供了多种线程同步机制，包括synchronized关键字、Lock接口和volatile关键字。

### synchronized关键字

```java
public class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
    }

    public int getCount() {
        return count;
    }
}

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Count: " + counter.getCount());
    }
}
```

### Lock接口

```java
public class Counter {
    private int count = 0;
    private Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            count--;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.decrement();
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Count: " + counter.getCount());
    }
}
```

### volatile关键字

```java
public class Flag {
    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

public class Main {
    public static void main(String[] args) {
        Flag flag = new Flag();
        Thread thread1 = new Thread(() -> {
            while (!flag.isFlag()) {
                // 等待
            }
            System.out.println("Thread 1: Flag is true");
        });
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag.setFlag(true);
            System.out.println("Thread 2: Flag is set to true");
        });
        thread1.start();
        thread2.start();
    }
}
```

## 线程通信

Java线程通信是指线程之间的信息传递和协调。Java提供了多种线程通信机制，包括wait()、notify()和notifyAll()方法，以及Condition接口。

### wait()、notify()和notifyAll()方法

```java
public class SharedResource {
    private int data = 0;
    private boolean available = false;

    public synchronized int getData() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false;
        notify();
        return data;
    }

    public synchronized void setData(int data) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.data = data;
        available = true;
        notify();
    }
}

public class Producer implements Runnable {
    private SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedResource.setData(i);
            System.out.println("Producer: " + i);
        }
    }
}

public class Consumer implements Runnable {
    private SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            int data = sharedResource.getData();
            System.out.println("Consumer: " + data);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread producer = new Thread(new Producer(sharedResource));
        Thread consumer = new Thread(new Consumer(sharedResource));
        producer.start();
        consumer.start();
    }
}
```

### Condition接口

```java
public class SharedResource {
    private int data = 0;
    private boolean available = false;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public int getData() {
        lock.lock();
        try {
            while (!available) {
                condition.await();
            }
            available = false;
            condition.signal();
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return -1;
    }

    public void setData(int data) {
        lock.lock();
        try {
            while (available) {
                condition.await();
            }
            this.data = data;
            available = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class Producer implements Runnable {
    private SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedResource.setData(i);
            System.out.println("Producer: " + i);
        }
    }
}

public class Consumer implements Runnable {
    private SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            int data = sharedResource.getData();
            System.out.println("Consumer: " + data);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();
        Thread producer = new Thread(new Producer(sharedResource));
        Thread consumer = new Thread(new Consumer(sharedResource));
        producer.start();
        consumer.start();
    }
}
```

## 线程池

Java线程池是Java提供的一种管理线程的机制，它可以减少线程创建和销毁的开销，提高程序的性能。

### 线程池的创建

```java
// 创建固定大小的线程池
ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

// 创建缓存线程池
ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

// 创建单线程线程池
ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

// 创建定时线程池
ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

// 自定义线程池
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    5, // 核心线程数
    10, // 最大线程数
    60, // 线程空闲时间
    TimeUnit.SECONDS, // 时间单位
    new LinkedBlockingQueue<>(), // 任务队列
    Executors.defaultThreadFactory(), // 线程工厂
    new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
);
```

### 线程池的使用

```java
ExecutorService executor = Executors.newFixedThreadPool(5);

for (int i = 0; i < 10; i++) {
    final int task = i;
    executor.execute(() -> {
        System.out.println("Task " + task + " is running by " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
}

executor.shutdown();
```

## 总结

Java多线程是Java提供的一种并发编程机制，它允许程序同时执行多个任务。Java多线程是Java的核心特性之一，广泛应用于各种应用程序中。本文介绍了Java多线程的基本概念，包括线程的创建和启动、线程生命周期、线程同步、线程通信和线程池等内容，希望对大家有所帮助。