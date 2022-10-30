import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

import java.util.*;

public class Contects<ContectsInfo> extends JFrame{
    ArrayList<ContectsInfo> list = new ArrayList<ContectsInfo>();
    JButton create, exit, view, ok, search,delete,change1;
    int index = 0;
    int len = list.size();

    JTextField jf1 = new JFormattedTextField();
    JTextField jf2 = new JFormattedTextField();
    JTextField jf3 = new JFormattedTextField();
    JTextField jf4 = new JFormattedTextField();
    public void CreateJFrame()           //构建一个窗体并在窗体里面添加按钮
    {
        Container c = getContentPane();

        setLayout(null);
        setTitle("个人通讯录系统");



        create = new JButton("添加");
        exit = new JButton("退出");
        view = new JButton("显示");
        search = new JButton("搜索");
        delete = new JButton("删除");
        change1 = new JButton("修改");
        setBounds(500,100,500,500);
        create.setBounds(100,80,100,50);
        view.setBounds(300,80,100,50);
        exit.setBounds(200,390,100,50);
        search.setBounds(100,180,100,50);
        delete.setBounds(300,180,100,50);
        change1.setBounds(200,280,100,50);
        c.add(create);
        c.add(exit);
        c.add(view);
        c.add(search);
        c.add(delete);
        c.add(change1);
        Add();
        view();
        exit();
        search1();
        delete();
        change();

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void Add()      //通讯录的增加功能
    {
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jf = new JFrame("新增联系人");
                JLabel j1 = new JLabel("姓名");
                JLabel j2 = new JLabel("电话");
                JLabel j3 = new JLabel("邮箱");
                JLabel j4 = new JLabel("地址");
                jf.setBounds(400, 400, 800, 500);
                JPanel jp = new JPanel();
                jp.setLayout(null);
                jf.setContentPane(jp);
                ok = new JButton("SAVE");
                jp.add(ok);
                jp.add(j1);
                jp.add(j2);
                jp.add(j3);
                jp.add(j4);
                j1.setBounds(50, 50, 80, 50);
                j2.setBounds(50, 100, 80, 50);
                j3.setBounds(50, 150, 80, 50);
                j4.setBounds(50, 200, 80, 50);
                ok.setBounds(200, 400, 100, 50);
                jp.add(jf1);
                jp.add(jf2);
                jp.add(jf3);
                jp.add(jf4);
                jf1.setBounds(150, 50, 300, 50);
                jf2.setBounds(150, 100, 300, 50);
                jf3.setBounds(150, 150, 300, 50);
                jf4.setBounds(150, 200, 300, 50);
                save();
                jf.setVisible(true);

            }
        });
    }
        public void exit()
        {
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }
        public void save()   //添加功能中的保存功能
        {

            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connection temp  = getConnect();
                    String addSQL = "INSERT INTO student VALUES(?,?,?,?)";
                    try
                    {
                        PreparedStatement ps = temp.prepareStatement(addSQL);
                        ps.setString(1,jf1.getText());
                        ps.setString(2,jf2.getText());
                        ps.setString(3,jf3.getText());
                        ps.setString(4,jf4.getText());
                        ps.execute();
                        temp.close();
                    }
                    catch(SQLException a)
                    {
                        a.printStackTrace();
                    }


                }
            });


        }
        public void view()     //把已存的联系人显示出来
        {


            view.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame show = new JFrame("联系人信息");
                    JTable table = null;
                    JScrollPane jsp = null;
                    show.setBounds(400,300,800,400);
                    Vector columNames = new Vector();
                    columNames.add("姓名");
                    columNames.add("电话");
                    columNames.add("邮箱");
                    columNames.add("地址");

                    Vector rowData = new Vector();
                    Connection conn = getConnect();
                    String sql = "SELECT * FROM student;";
                    try
                    {
                        PreparedStatement ps =  conn.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        while(rs.next())
                        {
                            Vector temp = new Vector();
                            temp.add(rs.getString(1));
                            temp.add(rs.getString(2));
                            temp.add(rs.getString(3));
                            temp.add(rs.getString(4));
                            rowData.add(temp);
                        }

                    }
                    catch(SQLException t)
                    {
                        t.printStackTrace();
                    }
                    table = new JTable(rowData,columNames);
                    jsp = new JScrollPane(table);
                    show.getContentPane().add(jsp, BorderLayout.CENTER);
                    show.setVisible(true);



                }
            });
        }
        public void search1()  //通讯录的查找功能
        {
            JButton jb6 = new JButton("查询");
            search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton jb6 = new JButton("查询");
                    String str[] = {"姓名","电话","邮箱","地址"};
                    JFrame search = new JFrame("查询联系人信息");
                    JPanel jp1 = new JPanel();
                    JTextField jf6 = new JFormattedTextField();
                    search.setBounds(500,300,400,400);
                    jp1.setLayout(null);
                    search.setContentPane(jp1);
                    JComboBox jcb = new JComboBox(str);
                    jb6.setBounds(150,300,100,50);
                    jcb.setBounds(30,50,100,30);
                    jf6.setBounds(150,50,200,30);
                    jp1.add(jb6);
                    jp1.add(jcb);
                    jp1.add(jf6);
                    search.setVisible(true);
                    jb6.addActionListener(new ActionListener() {   //可以按多种类型进行查找
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Connection temp = getConnect();
                            Vector rowData = new Vector();
                            JFrame jf = new JFrame("查询信息");
                            JTable jt = null;
                            JScrollPane jsp = null;
                            jf.setBounds(400,300,400,400);
                            Vector columNames = new Vector();
                            columNames.add("姓名");
                            columNames.add("电话");
                            columNames.add("邮箱");
                            columNames.add("地址");

                            if(jcb.getSelectedItem() == "姓名")
                            {

                                String s1 = "SELECT * FROM student WHERE 姓名=?";
                                try{
                                    PreparedStatement ps1 = temp.prepareStatement(s1);
                                    ps1.setString(1,jf6.getText());
                                    ResultSet rs = ps1.executeQuery();
                                    while(rs.next())
                                    {
                                        Vector t = new Vector();
                                        t.add(rs.getString(1));
                                        t.add(rs.getString(2));
                                        t.add(rs.getString(3));
                                        t.add(rs.getString(4));
                                        rowData.add(t);
                                        jt = new JTable(rowData, columNames);
                                        jsp = new JScrollPane((jt));
                                        jf.getContentPane().add(jsp, BorderLayout.CENTER);
                                        jf.setVisible(true);

                                    }
                                }
                                catch(SQLException e1)
                                {
                                    e1.printStackTrace();
                                }

                            }
                            if(jcb.getSelectedItem() == "电话")
                            {
                                String s2 = "SELECT * FROM student WHERE 电话=?";
                                try {
                                    PreparedStatement ps2 = temp.prepareStatement(s2);
                                    ps2.setString(1, jf6.getText());
                                    ResultSet rs = ps2.executeQuery();
                                    while (rs.next()) {
                                        Vector t = new Vector();
                                        t.add(rs.getString(1));
                                        t.add(rs.getString(2));
                                        t.add(rs.getString(3));
                                        t.add(rs.getString(4));
                                        rowData.add(t);
                                        jt = new JTable(rowData, columNames);
                                        jsp = new JScrollPane((jt));
                                        jf.getContentPane().add(jsp, BorderLayout.CENTER);
                                        jf.setVisible(true);

                                    }
                                }
                                catch(SQLException e2)
                                {
                                    e2.printStackTrace();
                                }
                            }


                            if(jcb.getSelectedItem() == "邮箱")
                            {
                                String s5 = "SELECT * FROM student WHERE 邮箱=?";
                                try
                                {
                                    PreparedStatement ps5 = temp.prepareStatement(s5);
                                    ps5.setString(1,jf6.getText());
                                    ResultSet rs = ps5.executeQuery();
                                    while(rs.next())
                                    {
                                        Vector t = new Vector();
                                        t.add(rs.getString(1));
                                        t.add(rs.getString(2));
                                        t.add(rs.getString(3));
                                        t.add(rs.getString(4));
                                        t.add(rs.getString(5));
                                        rowData.add(t);
                                        jt = new JTable(rowData, columNames);
                                        jsp = new JScrollPane((jt));
                                        jf.getContentPane().add(jsp, BorderLayout.CENTER);
                                        jf.setVisible(true);


                                    }
                                }
                                catch(SQLException e5)
                                {
                                    e5.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });

        }
        public Connection getConnect()   //连接数据库
        {
            Connection con = null;
            String url = "jdbc:mysql://localhost:3306/contact?useUnicode=true&characterEncoding=utf-8&useSSL=false";
            String user = "root";
            String password = "roots123";
            String drive = "com.mysql.cj.jdbc.Driver";

            try
            {
                Class.forName(drive);
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            try
            {
                con = DriverManager.getConnection(url, user, password);

            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            return con;


        }

        public void delete()    //按照指定条件删除联系人
        {
            JButton jp1 = new JButton("删除");
            //JButton change1 = new JButton("更改");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame jframe = new JFrame("删除联系人");
                    JPanel jp1 = new JPanel();
                    jframe.setContentPane(jp1);
                    jp1.setLayout(null);
                    String str[] = {"姓名","电话","邮箱"};
                    JComboBox jcb = new JComboBox(str);
                    JTextField jt = new JFormattedTextField();
                    jframe.setBounds(500,300,400,400);
                    jcb.setBounds(30,50,100,30);
                    jt.setBounds(180,50,200,30);
                    delete.setBounds(50,300,100,50);
                    //change1.setBounds(250,300,100,50);
                    jp1.add(jcb);
                    jp1.add(jt);
                    jp1.add(delete);
                    //jp1.add(change1);
                    jframe.setVisible(true);
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Connection con = getConnect();
                            PreparedStatement ps = null;
                            if(jcb.getSelectedItem() == "姓名")
                            {

                                String sql = "DELETE FROM student WHERE 姓名=?;";
                                try
                                {
                                    ps = con.prepareStatement(sql);
                                    ps.setString(1,jt.getText());
                                    ps.execute();
                                    con.close();
                                }
                                catch(SQLException e1)
                                {
                                    e1.printStackTrace();
                                }
                            }
                            if(jcb.getSelectedItem() == "电话")
                            {
                                String sql = "DELETE FROM student WHERE 电话=?;";
                                try
                                {
                                    ps = con.prepareStatement(sql);
                                    ps.setString(1,jt.getText());
                                    ps.execute();
                                    con.close();
                                }
                                catch(SQLException e2)
                                {
                                    e2.printStackTrace();
                                }
                            }
                            if(jcb.getSelectedItem() == "邮箱")
                            {
                                String sql = "DELETE FROM student WHERE 邮箱=?;";
                                try
                                {
                                    ps = con.prepareStatement(sql);
                                    ps.setString(1,jt.getText());
                                    ps.execute();
                                    con.close();
                                }
                                catch(SQLException e5)
                                {
                                    e5.printStackTrace();
                                }
                            }
                        }
                    });

                }
            });
        }
        public void change()       //按照指定的条件查找联系人并更改你想更改的信息
        {
            change1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame jf = new JFrame("更改联系人信息");
                    JButton jb = new JButton("更改");
                    JPanel jp = new JPanel();
                    String str[] = {"姓名","电话","邮箱","地址"};
                    JComboBox jc = new JComboBox(str);
                    jf.setContentPane(jp);
                    jf.setBounds(500,300,400,400);
                    JLabel jl1 = new JLabel("要更改的联系人:");
                    JLabel jl2 = new JLabel("需要更改的类别:");
                    JLabel jl3 = new JLabel("更改后的信息:");
                    JTextField jd1 = new JFormattedTextField();
                    JTextField jd2 = new JFormattedTextField();
                    jd1.setBounds(150,30,200,50);
                    jd2.setBounds(150,170,200,50);
                    jc.setBounds(150,100,100,50);
                    jb.setBounds(150,300,100,50);
                    jp.add(jd1);
                    jp.add(jd2);
                    jp.add(jc);
                    jp.add(jb);
                    jp.setLayout(null);
                    jp.add(jl1);
                    jp.add(jl2);
                    jp.add(jl3);
                    jl1.setBounds(30,30,100,50);
                    jl2.setBounds(30,100,100,50);
                    jl3.setBounds(30,170,100,50);
                    jf.setVisible(true);
                    jb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Connection con = getConnect();
                            if(jc.getSelectedItem() == "姓名")
                            {
                                String sql = "UPDATE student SET 姓名=? WHERE 姓名=?;";
                                try
                                {
                                    PreparedStatement ps = con.prepareStatement(sql);
                                    ps.setString(2,jd1.getText());
                                    ps.setString(1,jd2.getText());
                                    ps.execute();
                                }
                                catch(SQLException q)
                                {
                                    q.printStackTrace();
                                }
                            }
                            if(jc.getSelectedItem() == "电话")
                            {
                                String sql = "UPDATE student SET 电话=? WHERE 姓名=?;";
                                try
                                {
                                    PreparedStatement ps = con.prepareStatement(sql);
                                    ps.setString(2,jd1.getText());
                                    ps.setString(1,jd2.getText());
                                    ps.execute();
                                }
                                catch(SQLException q)
                                {
                                    q.printStackTrace();
                                }
                            }
                            if(jc.getSelectedItem() == "邮箱")
                            {
                                String sql = "UPDATE student SET 邮箱=? WHERE 姓名=?;";
                                try
                                {
                                    PreparedStatement ps = con.prepareStatement(sql);
                                    ps.setString(2,jd1.getText());
                                    ps.setString(1,jd2.getText());
                                    ps.execute();
                                }
                                catch(SQLException q)
                                {
                                    q.printStackTrace();
                                }
                            }
                        }
                    });

                }
            });
        }



}