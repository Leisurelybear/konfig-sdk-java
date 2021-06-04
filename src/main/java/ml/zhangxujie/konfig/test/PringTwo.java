/**
 * FileName: PringTwo
 * Author:   jason
 * Date:     2021/6/4 16:32
 * Description:
 */
package ml.zhangxujie.konfig.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PringTwo {
    private static Lock lock = new ReentrantLock();//通过JDK5中的锁来保证线程的访问的互斥
    private static int state = 0;
    static class First extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println(1);
                lock.lock();
                if (state % 4 == 0) {
                    System.out.println("1");
                    state++;
                }
                lock.unlock();
            }
        }
    }

    static class Second extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                if (state % 4 == 1) {
                    System.out.println("2");
                    state++;
                }
                lock.unlock();
            }
        }
    }

    static class Third extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                if (state % 4 == 2) {
                    System.out.println("3");
                    state++;
                }
                lock.unlock();
            }
        }
    }

    static class Forth extends Thread {
        @Override
        public void run() {
            while (true) {
                lock.lock();
                if (state % 4 == 3) {
                    System.out.println("4");
                    state++;
                }
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        First first = new First();
        Second second = new Second();
        Third third = new Third();
        Forth forth = new Forth();
        first.start();
        second.start();
        third.start();
        forth.start();
    }
}
