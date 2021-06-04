/**
 * FileName: Server
 * Author:   jason
 * Date:     2021/5/27 16:29
 * Description:
 */
package ml.zhangxujie.konfig.test.longconnection;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server {
    private int port;
    private volatile boolean running = false;
    private Thread connWatchDog;

    public Server(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new Server(65432).start();
    }

    public void start() {
        if (running) return;
        running = true;
        connWatchDog = new Thread(new ConnWatchDog());
        connWatchDog.start();
    }

    @SuppressWarnings("deprecation")
    public void stop() {
        if (running) running = false;
        if (connWatchDog != null) connWatchDog.stop();
    }

    /**
     * 处理方法
     * @param yell
     * @return
     */
    public static Yell dispose(Yell yell) {
        if(yell.getType() == 1){
            System.out.println("大哥,门口的人喊了句 :"+yell.getMsg());
            System.out.println("哦? 难道是自己人? 回暗号!");
            return new Yell(1,"宝塔镇河妖");
        }else if(yell.getType() == 2){
            System.out.println("大哥,门口的人喊了句 :"+yell.getMsg());
            System.out.println("呵呵,这是个细作,让他进来,做掉他.");
            return new Yell(2,"...让他进来,做掉他...");
        }else{
            System.out.println(yell.waitAndSee());
            return yell;
        }
    }

    class ConnWatchDog implements Runnable {
        @Override
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(port, 5);
                while (running) {
                    Socket s = ss.accept();
                    new Thread(new SocketAction(s)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.this.stop();
            }
        }
    }

    class SocketAction implements Runnable {
        Socket s;
        boolean run = true;
        long lastReceiveTime = System.currentTimeMillis();
        public SocketAction(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            while (running && run) {
                long receiveTimeDelay = 5000;
                //超过5秒中断连接
                if (System.currentTimeMillis() - lastReceiveTime > receiveTimeDelay) {
                    close();
                } else {
                    try {
                        InputStream in = s.getInputStream();
                        if (in.available() > 0) {
                            ObjectInputStream ois = new ObjectInputStream(in);
                            lastReceiveTime = System.currentTimeMillis();
                            Object out = dispose((Yell)ois.readObject());//处理
                            log.info(out.toString());
                            //答复
                            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                            oos.writeObject(out);
                            oos.flush();
                        } else {
                            Thread.sleep(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        close();
                    }
                }
            }
        }

        //关闭连接
        private void close() {
            if (run) run = false;
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("关闭：" + s.getRemoteSocketAddress());
        }
    }
}
