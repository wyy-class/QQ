
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.util.ArrayList;

public class FaceGUI extends JFrame {

    private JPanel contentPane;
    private JButton btnNewButton;
    private JButton btnNewButton_1;
    private JButton btnNewButton_2;
    private JButton btnNewButton_3;
    private JButton btnNewButton_4;
    private JButton btnNewButton_5;
    private JButton btnNewButton_6;
    private JButton btnNewButton_7;
    private JButton btnNewButton_8;
    private JButton btnNewButton_9;
    private JButton btnNewButton_10;
    private JButton btnNewButton_11;
    private JFrame jf;

    private JTextArea textArea;

    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private ArrayList<String> contents=new ArrayList<String>();
    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public FaceGUI(final JTextArea textArea) {
        jf=this;
        this.textArea=textArea;
        init();
        for (int i=0;i<buttons.size();i++) {
            final JButton button=buttons.get(i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.append(button.getText());
                    jf.setVisible(false);
                }
            });
            final int finalI = i;
            button.addMouseListener(new MouseAdapter() {
                @Override
                //鼠标移入
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    button.setToolTipText(contents.get(finalI));
                }

                @Override
                //鼠标移出
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                }
            });
        }

    }

    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(280, 360, 386, 260);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        btnNewButton = new JButton(" (\uFE36\uFE3F\uFE36)");
        btnNewButton.setBounds(14, 22, 113, 27);
        contentPane.add(btnNewButton);
        buttons.add(btnNewButton);
        contents.add("不满");

        btnNewButton_1 = new JButton(" \u56E7");
        btnNewButton_1.setBounds(130, 22, 113, 27);
        contentPane.add(btnNewButton_1);
        buttons.add(btnNewButton_1);
        contents.add("囧");

        btnNewButton_2 = new JButton(" ^(oo)^");
        btnNewButton_2.setBounds(245, 22, 113, 27);
        contentPane.add(btnNewButton_2);
        buttons.add(btnNewButton_2);
        contents.add("猪头");

        btnNewButton_3 = new JButton(" Orz ");
        btnNewButton_3.setBounds(14, 69, 113, 27);
        contentPane.add(btnNewButton_3);
        buttons.add(btnNewButton_3);
        contents.add("我服了你");

        btnNewButton_4 = new JButton(" (T_T)");
        btnNewButton_4.setBounds(130, 69, 113, 27);
        contentPane.add(btnNewButton_4);
        buttons.add(btnNewButton_4);
        contents.add("哭涕");

        btnNewButton_5 = new JButton(" (=^_^=)");
        btnNewButton_5.setBounds(245, 69, 113, 27);
        contentPane.add(btnNewButton_5);
        buttons.add(btnNewButton_5);
        contents.add("喵喵");

        btnNewButton_6 = new JButton("\uFF08\u2299o\u2299\uFF09");
        btnNewButton_6.setBounds(14, 117, 113, 27);
        contentPane.add(btnNewButton_6);
        buttons.add(btnNewButton_6);
        contents.add("目瞪口呆");

        btnNewButton_7 = new JButton("\uFF08*^\u3014^*\u3015");
        btnNewButton_7.setBounds(130, 117, 113, 27);
        contentPane.add(btnNewButton_7);
        buttons.add(btnNewButton_7);
        contents.add("害羞");

        btnNewButton_8 = new JButton(" (\u2027_\u2027\uFF1F)");
        btnNewButton_8.setBounds(245, 117, 113, 27);
        contentPane.add(btnNewButton_8);
        buttons.add(btnNewButton_8);
        contents.add("什么意思？");

        btnNewButton_9 = new JButton(" (\u02CB\uFF3E\u02CA)");
        btnNewButton_9.setBounds(14, 167, 113, 27);
        contentPane.add(btnNewButton_9);
        buttons.add(btnNewButton_9);
        contents.add("不以为然");

        btnNewButton_10 = new JButton(" (-_-)ZZZ");
        btnNewButton_10.setBounds(130, 167, 113, 27);
        contentPane.add(btnNewButton_10);
        buttons.add(btnNewButton_10);
        contents.add("睡觉");

        btnNewButton_11 = new JButton(" P(^_^)q");
        btnNewButton_11.setBounds(245, 167, 113, 27);
        contentPane.add(btnNewButton_11);
        buttons.add(btnNewButton_11);
        contents.add("加油");
    }
}

