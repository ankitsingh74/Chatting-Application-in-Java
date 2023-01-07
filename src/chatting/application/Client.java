
package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

public class Client implements ActionListener {

    JTextField  text;
   static JPanel a1;
   static Box vertical = Box.createVerticalBox();//messages align in vertical form
   static DataOutputStream dout;

   static  JFrame f = new JFrame();

    Client(){

        f.setLayout(null);

        JPanel p1 = new JPanel();//JPanel is used do something over frame(design extra block of frame which is smaller than actual frame)............
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(285,20,35,35);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,35, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(345,20,35,35);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15,35, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(425,20,15,35);
        p1.add(morevert);

        JLabel  name = new JLabel("Bunty");  //Using JLable you can write anything on the Frame
        name.setForeground(Color.WHITE);
        name.setBounds(105,20,100,18);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel  status = new JLabel("Active Now");  //Using JLable you can write anything on the Frame
        status.setForeground(Color.WHITE);
        status.setBounds(105,40,100,18);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);

        a1 = new JPanel();//JPanel is used do something over frame(design extra block of frame which is smaller than actual frame)............
        //   a1.setBackground(new Color(211,211,211));//for gray color (mark for removal)
        a1.setBounds(5,75,440,570);
        a1.setLayout(null);
        f.add(a1);



        text = new JTextField();//JTextField is used for user to pass or fill any messages
        text.setBounds(5,650,310,35);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,650,124,35);
        send.setBackground(new Color(7,96,84));
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        send.addActionListener(this);
        send.setForeground(Color.WHITE);
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(900, 50);
        f.getContentPane().setBackground(Color.WHITE);//getContentPane() is used for take whole frame
        f.setUndecorated(true);
        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {

        String out = text.getText();//getText always return string
        if (out.equals("")) {
            JOptionPane.showMessageDialog(null, "Bunty Text is Required before Send");
        } else {
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());//messages align right side
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);//messages align in the vertical form
            vertical.add(Box.createVerticalStrut(15));//space between each vertical message (15)
            a1.add(vertical, BorderLayout.PAGE_START);

            try {
                dout.writeUTF(out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            text.setText("");//Empty the JTextfield after sending the messages

            f.repaint();
            f.invalidate();
            f.validate();

        }
    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 18));
        output.setBackground(new Color(144,225,144));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 15, 10, 15));//green color box rectangle from center of send messages
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        time.setBorder(new EmptyBorder(0, 200, 0, 0));
        time.setOpaque(true);
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();
        try {
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());//we can read message by the help of din
            dout = new DataOutputStream(s.getOutputStream()); //we can SEND  message by the help of dout(main method static this class should also b

            while(true){
                a1.setLayout(new BorderLayout());//main fn is static then JPanel a1 obj should be static
                String msg = din.readUTF();//message read here and store in msg
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);//main fn is static then Box class should be static
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f.validate();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
