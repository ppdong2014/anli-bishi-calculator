import java.awt.*;
import java.awt.event.*;

/**
 * 图形界面类
 */
public class CalculatorFrame
{
    //定义该图形中所需的组件的引用
    private Frame f;
    private TextField tf;

    private boolean clearTfNextType = false;

    //方法
    CalculatorFrame()//构造方法
    {
        madeFrame();
    }

    public void madeFrame()
    {
        f = new Frame("计算器");

        //对Frame进行基本设置。
        f.setBounds(300,100,600,500);//对框架的位置和大小进行设置
        f.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));//设计布局

        tf = new TextField(20);
        Label label = new Label("1.仅支持两个数字的加减乘除；2.输入算式时不需要输入'='；"
                + "3.按回车键执行计算；4.按上/下键浏览历史输入记录；5.浏览时按回车可再次执行计算；");

        //将组件添加到Frame中
        f.add(tf);
        f.add(label);

        //加载一下窗体上的事件
        myEvent();

        //显示窗体
        f.setVisible(true);
    }

    private void myEvent()
    {
        f.addWindowListener(new WindowAdapter()//窗口监听
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        tf.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (clearTfNextType) {
                    tf.setText("");
                    clearTfNextType = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    String nowInput = tf.getText();

                    String r = Calculator.processUndo(nowInput);
                    if (r != null) {
                        tf.setText(r);
                    }

                    e.consume();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    String r = Calculator.processRedo();
                    if (r != null) {
                        tf.setText(r);
                    }

                    e.consume();
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String r = Calculator.processInput(tf.getText());
                    tf.setText(r);
                    clearTfNextType = true;
                    e.consume();
                }
            }


        });
    }

    public static void main(String[] agrs)
    {
        new CalculatorFrame();
    }
}