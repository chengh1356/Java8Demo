package base.thread;

import javax.tools.Tool;
import java.util.concurrent.Exchanger;

/**
 * @Description 大胖和小白去了创业公司，公司为了节约开支，没有请专门的保洁人员。让员工自己扫地和擦桌。
 *
 *              大胖觉得擦桌轻松，就让小白去扫地。可小白觉得扫地太累，也想擦桌。
 *
 *              为了公平起见，于是决定，每人先干一半，然后交换工具，再接着干对方剩下的那一个半。
 * @Author cgh
 * @Date 2020-09-11 下午 3:01
 */
public class ExchangerTest {
    public static void main(String[] args) {

    }
    static Exchanger<Tool> ex = new Exchanger<>();


}
