package com.escel.view;


import com.escel.init.CreateExcel;
import com.escel.tool.PropertiesTool;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * Created by ab1324ab on 2017/5/7.
 */
public class WindowView extends JFrame{


    //测试方法
    public static void main(String[] args) {
        WindowView  sd=new WindowView();
    }
    private CardLayout cardLayout=null;
    private JPanel pagePanel=new JPanel();
    private JPanel controlPanel=new JPanel();
    private JPanel jPanelBar=null;
    private static Map<String,String> contentMap=new HashMap<String, String>();
    private GridBagLayout gridBagLayout=new GridBagLayout();
    private JButton save=null;
    //private JButton create=null;
    private List<JCheckBox> jCheckBoxs=new ArrayList<JCheckBox>();//选择框
    private List<JCheckBox> jCheckBoxsPage1=new ArrayList<JCheckBox>();//选择框1
    private List<JCheckBox> jCheckBoxsPage3=new ArrayList<JCheckBox>();//选择框3
    private List<List<JTextField>> jTextFieldList1=new ArrayList<List<JTextField>>();//第一页文本
    private List<JTextField> jTextFields1=null;
    private List<List<JTextField>> jTextFieldList2=new ArrayList<List<JTextField>>();//第一页文本
    private List<JTextField> jTextFields2=null;
    private List<List<JTextField>> jTextFieldList3=new ArrayList<List<JTextField>>();//第三页文本
    private JFrame jFrame=null;//进度条窗口
    private List<JTextField> jTextFields3=null;
    private Timer timer=null;//进度条时间计数器
    private JProgressBar bar=null;//进度条
    private JTextField fileName=null;//文件名
    private JTextField department=null;//部门
    private JTextField name=null;//名字
    public WindowView (){
        contentMap=PropertiesTool.redConfigFile("config.properties");
        try{
            System.out.println("开始启动");
            init();
        }catch (Exception e){
            //启动预警
            e.printStackTrace();
            jFrame=new JFrame("自动修复");
            repair(jFrame);
        }
    }
    //初始化窗体
    public void init(){
        this.setSize(990,600);
        conter(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        controlCenter(controlPanel);//写入控制面板

        cardLayout=new CardLayout();
        pagePanel.setLayout(gridBagLayout);
        page1(pagePanel);
        page2(pagePanel);
        page3(pagePanel);
        pagePanel.setBackground(Color.CYAN);

        JButton button2=new JButton("关于我们");
        this.add(controlPanel,BorderLayout.NORTH);//添加控制面板入窗体
        this.add(button2,BorderLayout.SOUTH);
        this.add(pagePanel);
        controlPanel.setVisible(true);
        pagePanel.setVisible(true);
        registerListener();
        this.setVisible(true);
    }
    public  void controlCenter(JPanel controlPanel){
        JLabel titleFileName=new JLabel("文件名：");
        fileName=new JTextField(contentMap.get("fileName"));
        fileName.setColumns(20);
         save=new JButton("保存并写出Escel");
        controlPanel.add(titleFileName);
        controlPanel.add(fileName);
        controlPanel.add(save);
    }
    //第一页
    public void page1(JPanel pagePanel1){
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//控制按钮显示组件
        JLabel titleDepartment=new JLabel(contentMap.get("titleDepartment"));
        JLabel titleName=new JLabel(contentMap.get("titleName"));
        department=new JTextField(contentMap.get("department"));
        name=new JTextField(contentMap.get("name"));
        gridBagConstraints.insets=new Insets(5,5,5,5);
        gridBagConstraints.anchor=GridBagConstraints.EAST;
        gridBagLayout.setConstraints(titleDepartment,gridBagConstraints);
        pagePanel1.add(titleDepartment);//部门
        gridBagLayout.setConstraints(department,gridBagConstraints);
        department.setColumns(8);
        pagePanel1.add(department);//创建文本框
        //titleName.setBorder(BorderFactory.createLineBorder(Color.black));//边框
        gridBagLayout.setConstraints(titleName,gridBagConstraints);
        pagePanel1.add(titleName);
        gridBagConstraints.anchor=GridBagConstraints.WEST;
        gridBagLayout.setConstraints(name,gridBagConstraints);
        name.setColumns(8);
        pagePanel1.add(name);//创建文本框
        gridBagConstraints.anchor=GridBagConstraints.CENTER;
        String[] title1=contentMap.get("title1").split(PropertiesTool.SGMTA_SPLIT);
        for(int j=0;j<=9;j++){
            JLabel renwuContents=new JLabel(title1[j]);
            if(j==2||j==3||j==4||j==6||j==7||j==8){
                gridBagConstraints.gridwidth=1;
            }else if(j==0||j==5){
                gridBagConstraints.gridwidth=1;
            }else{
                gridBagConstraints.gridwidth=3;
            }
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridy=1;
            gridBagLayout.setConstraints(renwuContents,gridBagConstraints);
            pagePanel1.add(renwuContents);
        }
         String[] pageContents1=contentMap.get("pageContents1").split(PropertiesTool.SGMTA_SPLIT_LATER);
        for(int i=2;i<2+pageContents1.length;i++){
            jTextFields1=new ArrayList<JTextField>();
            for(int j=0;j<9;j++){
               String sdf=pageContents1[i-2];
                String[] fhl=sdf.split(PropertiesTool.SGMTA_SPLIT);
                JTextField textField=new JTextField(fhl[j]);
                if(j==2||j==3||j==4||j==6||j==7||j==8){
                    textField.setHorizontalAlignment(JTextField.CENTER );
                    textField.setColumns(3);
                    gridBagConstraints.gridwidth=1;
                }else if(j==0||j==5){
                    textField.setHorizontalAlignment(JTextField.CENTER );
                    textField.setColumns(8);
                    gridBagConstraints.gridwidth=1;
                }else{
                    textField.setHorizontalAlignment(JTextField.LEFT );
                    textField.setColumns(27);
                    gridBagConstraints.gridwidth=3;
                }
                jTextFields1.add(textField);//添加文本对象到list
                gridBagConstraints.weightx = 0;
                gridBagConstraints.weighty = 0;
                gridBagConstraints.gridy=i;
                gridBagLayout.setConstraints(textField,gridBagConstraints);
                pagePanel1.add(textField);//完成比例
                    if(j==8){
                        if(i==2){
                            jCheckBoxsPage1.add(new JCheckBox("",true));
                        }else{
                            jCheckBoxsPage1.add(new JCheckBox("",false));
                        }
                        gridBagLayout.setConstraints(jCheckBoxsPage1.get(i-2),gridBagConstraints);
                        pagePanel1.add(jCheckBoxsPage1.get(i-2));
                    }
                }
            jTextFieldList1.add(jTextFields1);//添加文本对象list
        }
    }
    //第二页
    public void page2(JPanel pagePanel1){
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//控制按钮显示组件
        gridBagConstraints.insets=new Insets(5,5,5,5);
        String[] title2=contentMap.get("title2").split(PropertiesTool.SGMTA_SPLIT);
        for(int j=0;j<=6;j++){
            JLabel renwuContents=new JLabel(title2[j]);
            if(j==0){
                gridBagConstraints.gridwidth=1;
            }else if (j==1){
                gridBagConstraints.gridwidth=1;
            }else if(j==3||j==4||j==5){
                gridBagConstraints.gridwidth=1;
            }else{
                gridBagConstraints.gridwidth=6;
            }
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridy=4;
            gridBagLayout.setConstraints(renwuContents,gridBagConstraints);
            pagePanel1.add(renwuContents);
        }
        String[] com=new String[7];
        for (int i=0;i<7;i++){
            com[i]=contentMap.get("content"+i);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        Calendar rightNow = Calendar.getInstance();
        String[] weeks=new String[]{"七","一","二","三","四","五","六",};
        rightNow.setTime(new Date());
        int week=rightNow.get(Calendar.DAY_OF_WEEK )-1;
        int difference=1-(week==0?7:week);
        rightNow.add(Calendar.DAY_OF_WEEK,difference);
        for(int i=5;i<12;i++){
            jTextFields2=new ArrayList<JTextField>();
            for(int j=0;j<=5;j++){
                JTextField weelText=null;
                if(j==0){
                    weelText=new JTextField(weeks[rightNow.get(Calendar.DAY_OF_WEEK )-1]+"("+sdf.format(rightNow.getTime())+")");
                    weelText.setColumns(8);
                    weelText.setHorizontalAlignment(JTextField.CENTER );
                    gridBagConstraints.gridwidth=1;
                }else if (j==1){
                    weelText=new JTextField(com[i-5].split(PropertiesTool.SGMTA_SPLIT)[j]);
                    weelText.setColumns(8);
                    weelText.setHorizontalAlignment(JTextField.CENTER );
                    gridBagConstraints.gridwidth=1;
                }else if(j==3||j==4||j==5){
                    weelText=new JTextField(com[i-5].split(PropertiesTool.SGMTA_SPLIT)[j]);
                    weelText.setColumns(3);
                    weelText.setHorizontalAlignment(JTextField.CENTER );
                    gridBagConstraints.gridwidth=1;
                }else{
                     weelText=new JTextField(com[i-5].split(PropertiesTool.SGMTA_SPLIT)[j]);
                    weelText.setColumns(42);
                    weelText.setHorizontalAlignment(JTextField.LEFT );
                    gridBagConstraints.gridwidth=6;
                }
                jTextFields2.add(weelText);

                gridBagConstraints.weightx = 0;
                gridBagConstraints.weighty = 0;
                gridBagConstraints.gridy=i;
                gridBagLayout.setConstraints(weelText,gridBagConstraints);
                pagePanel1.add(weelText);//完成比例
                if(j==5){
                    if(i==10||i==11){
                         jCheckBoxs.add(new JCheckBox("",false));
                    }else{
                        jCheckBoxs.add(new JCheckBox("",true));
                    }
                    gridBagConstraints.gridy=i;
                    gridBagLayout.setConstraints(jCheckBoxs.get(i-5),gridBagConstraints);
                    pagePanel1.add(jCheckBoxs.get(i-5));
                }
            }
            rightNow.add(Calendar.DAY_OF_WEEK,1);
            jTextFieldList2.add(jTextFields2);
        }

    }
    //第三页
    public void page3(JPanel pagePanel1){
        GridBagConstraints gridBagConstraints=new GridBagConstraints();//控制按钮显示组件
        gridBagConstraints.insets=new Insets(5,5,5,5);
        String[] title3=contentMap.get("title3").split(PropertiesTool.SGMTA_SPLIT);
        for(int j=0;j<=5;j++){
            JLabel renwuContents=new JLabel(title3[j]);
            gridBagConstraints.weightx = 0;
            gridBagConstraints.weighty = 0;
            gridBagConstraints.gridy=12;
            if(j==1){
                gridBagConstraints.gridwidth=3;
            }else{
                gridBagConstraints.gridwidth=1;
            }
            if(j==5){
                gridBagConstraints.anchor=GridBagConstraints.WEST;
            }else{
                gridBagConstraints.anchor=GridBagConstraints.CENTER;
            }
            gridBagLayout.setConstraints(renwuContents,gridBagConstraints);
            pagePanel1.add(renwuContents);
        }
        String[] pageContents3=contentMap.get("pageContents3").split(PropertiesTool.SGMTA_SPLIT_LATER);
        for(int i=13;i<13+pageContents3.length;i++){
            jTextFields3=new ArrayList<JTextField>();
            for(int j=0;j<5;j++) {
                JTextField textField = new JTextField(pageContents3[i - 13].split(PropertiesTool.SGMTA_SPLIT)[j]);
                if(j==0){
                    gridBagConstraints.gridwidth=1;
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setColumns(8);
                }else if(j==1){
                    gridBagConstraints.gridwidth=3;
                    textField.setHorizontalAlignment(JTextField.LEFT);
                    textField.setColumns(27);
                }else{
                    gridBagConstraints.gridwidth=1;
                    textField.setHorizontalAlignment(JTextField.CENTER);
                    textField.setColumns(3);
                }
                jTextFields3.add(textField);
                gridBagConstraints.weightx = 0;
                gridBagConstraints.weighty = 0;
                gridBagConstraints.gridy=i;
                gridBagConstraints.anchor=GridBagConstraints.CENTER;
                gridBagLayout.setConstraints(textField,gridBagConstraints);
                pagePanel1.add(textField);//完成比例
                if(j==4){
                    if(i==13){
                        jCheckBoxsPage3.add(new JCheckBox("",true));
                    }else{
                        jCheckBoxsPage3.add(new JCheckBox("",false));
                    }
                    gridBagConstraints.anchor=GridBagConstraints.WEST;
                    gridBagLayout.setConstraints(jCheckBoxsPage3.get(i-13),gridBagConstraints);
                    pagePanel1.add(jCheckBoxsPage3.get(i-13));
                }
            }
            jTextFieldList3.add(jTextFields3);
        }
    }

    /**
     * 保存按钮事件
     */
    public void registerListener(){
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writePage1();//写入第一页配置文件
                writePage2();//写入第二页配置文件
                writePage3();//写入第三页配置文件
                Map<String,Object> controlMap=new HashMap<String, Object>();
                boolean[] hiddenPage1=new boolean[jCheckBoxsPage1.size()];
                for(int i=0;i<jCheckBoxsPage1.size();i++){
                    hiddenPage1[i]=jCheckBoxsPage1.get(i).isSelected()? false:true;
                }
                controlMap.put("hiddenPage1",hiddenPage1);
                boolean[] hidden=new boolean[jCheckBoxs.size()];
                for(int i=0;i<jCheckBoxs.size();i++){
                    hidden[i]=jCheckBoxs.get(i).isSelected()? false:true;
                }
                controlMap.put("jCheckBoxs",hidden);
                boolean[] hiddenPage3=new boolean[jCheckBoxsPage3.size()];
                for(int i=0;i<jCheckBoxsPage3.size();i++){
                    hiddenPage3[i]=jCheckBoxsPage3.get(i).isSelected()? false:true;
                }
                controlMap.put("hiddenPage3",hiddenPage3);
                //创建文档
                String msg= CreateExcel.mainCreate(controlMap);
                JOptionPane.showMessageDialog(pagePanel, msg, "提示",JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * 写入配置文件第一页内容
     */
    public String writePage1(){
        String fileNameText=fileName.getText();
        PropertiesTool.writeSet("config.properties","fileName",fileNameText);
        String departmentText=department.getText();
        PropertiesTool.writeSet("config.properties","department",departmentText);
        String nameText=name.getText();
        PropertiesTool.writeSet("config.properties","name",nameText);
        String pageTem1="";
        String pageContents1="";
        int indexCell=2;
        for(int i=0;i<indexCell;i++){
            pageTem1="";
            for(int j=0;j<9;j++){
                if(j==8){
                    pageTem1+=jTextFieldList1.get(i).get(j).getText();
                }else{
                    pageTem1+=jTextFieldList1.get(i).get(j).getText()+PropertiesTool.READ_SGMTA_SPLIT;
                }
            }
            if(i==1){
                pageContents1+=pageTem1;
            }else{
                pageContents1+=pageTem1+PropertiesTool.READ_SPLIT_LATER;
            }
        }
        PropertiesTool.writeSet("config.properties","pageContents1",pageContents1);
        return "10";
    }

    /**
     * 写入第二页配置内容
     * @return
     */
    public String writePage2(){
        String pageTem2="";
        int indexCell=7;
        for(int i=0;i<indexCell;i++){
            pageTem2="";
            for(int j=0;j<6;j++){
                if(j==5){
                    pageTem2+=jTextFieldList2.get(i).get(j).getText();
                }else{
                    pageTem2+=jTextFieldList2.get(i).get(j).getText()+PropertiesTool.READ_SGMTA_SPLIT;
                }
            }
            PropertiesTool.writeSet("config.properties","content"+i,pageTem2);
        }
        return "10";
    }

    /**
     * 写入第三页配置内容
     * @return
     */
    public String writePage3(){
        String pageTem3="";
        String pageContents3="";
        int indexCell=2;
        for(int i=0;i<indexCell;i++){
            pageTem3="";
            for(int j=0;j<5;j++){
                if(j==4){
                    pageTem3+=jTextFieldList3.get(i).get(j).getText();
                }else{
                    pageTem3+=jTextFieldList3.get(i).get(j).getText()+PropertiesTool.READ_SGMTA_SPLIT;
                }
            }
            if(i==1){
                pageContents3+=pageTem3;
            }else{
                pageContents3+=pageTem3+PropertiesTool.READ_SPLIT_LATER;
            }
        }
        PropertiesTool.writeSet("config.properties","pageContents3",pageContents3);
        return "10";
    }

    public void repair(final JFrame jFrame){
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600,100);
        jPanelBar=new JPanel();
        bar = new JProgressBar();
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setValue(0);
        bar.setStringPainted(true);
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = bar.getValue();
                if(value < 100) {
                    value++;
                    bar.setValue(value);
                }else{
                    timer.stop();
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    PropertiesTool.initialization();
                    WindowView  sd=new WindowView();
                }
            }
        });
        bar.setPreferredSize(new Dimension(500, 20));
        jPanelBar.add(new JLabel("程序受损,正在自动修复......"));
        jPanelBar.add(bar);
        jPanelBar.setVisible(true);
        jFrame.add(jPanelBar,BorderLayout.CENTER);
        Toolkit kit =Toolkit.getDefaultToolkit();
       // jFrame.setIconImage(kit.getImage(WindowView.class.getResource("/icon/restore.jpg").getPath().substring(1)));
        conter(jFrame);
       // Image image=kit.createImage(PropertiesTool.class.getClass().getResourceAsStream("/icon/restore.ico"));
        System.out.println(new ImageIcon(WindowView.class.getResource("/icon/restore.ico").getPath().substring(1)).getImage());

        jFrame.setVisible(true);
        timer.start();
    }

    /**
     * 窗口居中
     * @param frame
     */
    public static void conter(Frame frame){
        //当前窗口属性
        Toolkit kit =Toolkit.getDefaultToolkit();
        //Image image=kit.createImage();//("C:\\Users\\ab1324ab\\Desktop\\notefile\\view_note\\target\\classes\\com\\myigou\\window\\title.PNG");
        try {
            frame.setIconImage(kit.getImage(new URL("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3371215617,2221790797&fm=21&gp=0.jpg")));
            //this.setIconImage(new ImageIcon("C:\\Users\\ab1324ab\\Desktop\\notefile\\view_note\\target\\classes\\com\\myigou\\window\\title.PNG").getImage());
            Dimension screenSize= kit.getScreenSize();//获取屏幕封装对象
            int screenSizeWidth=screenSize.width;//获取屏幕高度
            int screenSizeheight=screenSize.height;//获取屏幕高度
            int windownWidth=frame.getWidth();//获取当前窗口宽度
            int windownHeight=frame.getHeight();//获取当前窗口高度
            frame.setLocation((screenSizeWidth-windownWidth)/2,(screenSizeheight-windownHeight)/2);//保持屏幕居中
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
