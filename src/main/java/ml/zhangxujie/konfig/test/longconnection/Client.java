/**
 * FileName: Client
 * Author:   jason
 * Date:     2021/5/27 16:32
 * Description:
 */
package ml.zhangxujie.konfig.test.longconnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private String serverIp;
    private int port;
    private Socket socket;
    private boolean running = false; //连接状态
    private long lastSendTime; //最后一次发送数据的时间

    public Client(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public static void main(String[] args) throws IOException {
        new Client("127.0.0.1", 65432).go();
    }

    public void go() throws IOException {
        if (running) return;
        socket = new Socket(serverIp, port);
        System.out.println("本地端口号：" + socket.getLocalPort());
        lastSendTime = System.currentTimeMillis();
        running = true;
        new Thread(new KeepAlive()).start(); //保持长连接的线程，每隔2秒发送一次消息
        new Thread(new Receive()).start(); //接受消息的线程
        new Thread(new TrySendSomethings()).start();//10秒后发送一条业务消息
    }

    public void stop() {
        if (running) running = false;
    }

    public void sendObject(Yell yell) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(yell);
        if (yell.type != 0) {
            System.out.println("大喊 ：" + yell.getMsg());
        }
        oos.flush();
    }

    /**
     * 处理方法
     * @param yell
     * @return
     */
    public void dispose(Yell yell) throws IOException {
        if (yell.getType() == 1) {
            System.out.println("寨子里面有人喊 :" + yell.getMsg());
            System.out.println("呵呵 有戏!");
            this.sendObject(new Yell(2, "嘛哈嘛哈"));
        } else if (yell.getType() == 2) {
            System.out.println("寨门开了,但是听到了可怕的事 :" + yell.getMsg());
            System.out.println("穿帮了!快跑!");
        } else {
            System.out.println(yell.waitAndSee());
        }
    }

    class KeepAlive implements Runnable {
        long keepAliveDelay = 2000;

        @Override
        public void run() {
            while (running) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        Client.this.sendObject(new Yell(0, ""));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Client.this.stop();
                    }
                }
            }
        }
    }

    class Receive implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    InputStream in = socket.getInputStream();
                    if (in.available() > 0) {
                        ObjectInputStream ois = new ObjectInputStream(in);
                        //服务器返回的内容
                        dispose((Yell) ois.readObject());
                    } else {
                        Thread.sleep(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }
        }
    }

    class TrySendSomethings implements Runnable {
        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(10000);
                    Client.this.sendObject(new Yell(1, "天王盖地虎"));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Client.this.stop();
                }
            }
        }
    }
}
