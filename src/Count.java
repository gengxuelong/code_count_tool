import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author gengxuelong
 * @date 2021-11-17 16:07
 */
public class Count extends JFrame {
    private JTextField textField = new JTextField(100);
    private JTextField textField2 = new JTextField(100);
    private JTextArea textArea = new JTextArea();
    private JButton button = new JButton("开始统计");
    private JLabel label_1 = new JLabel("目标文件或文件夹：");
    private JLabel label_2 = new JLabel("所指位置代码数量:");
    private JLabel label_3 = new JLabel("所统计代码语言：");
    private JLabel label_4 = new JLabel("注释: 可查询主流语言:java,c,c++,python,kotlin(大小写均可),其他语言请填写后缀名(带点),形如：.java");
    private JLabel label_5 = new JLabel("注释: 语言为空默认为java语言；盘幅不是文件夹或文件，只输入盘幅将拒绝统计，结果返回0");
    public Count(){
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        this.setContentPane(contentPane);
        this.setSize(700,380);
        this.setTitle("雪龙代码统计器");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);

        label_1.setBounds(10,10,360,40);
        label_3.setBounds(10,70,360,40);
        label_2.setBounds(10,130,360,40);
        label_4.setBounds(10,240,700,20);
        label_5.setBounds(10,270,700,40);
        label_1.setFont(new Font("微软雅黑",Font.BOLD,30));
        label_3.setFont(new Font("微软雅黑",Font.BOLD,30));
        label_2.setFont(new Font("微软雅黑",Font.BOLD,30));
        label_4.setFont(new Font("微软雅黑", Font.ITALIC,14));
        label_5.setFont(new Font("微软雅黑", Font.ITALIC,14));
        textArea.setBounds(280,130,300,40);
        textField.setBounds(280,10,300,40);
        textField2.setBounds(280,70,300,40);
        button .setBounds(280,180,110,50);
        button.setBackground(Color.ORANGE);
        contentPane.add(label_1);
        contentPane.add(label_2);
        contentPane.add(label_3);
        contentPane.add(label_4);
        contentPane.add(label_5);
        contentPane.add(textArea);
        contentPane.add(textField2);
        contentPane.add(textField);
        contentPane.add(button);
        contentPane.setBackground(Color.CYAN);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    dou_button_actionPerformed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void dou_button_actionPerformed() throws Exception {
        String file = textField.getText().trim();
        String language = textField2.getText().trim();
        if(file.endsWith("://")||file.endsWith(":/")||file.endsWith(":")){
            textArea.setText("0");
            return ;
        }
        String suffix = getSuffix(language);
        int count = getTheCodeNum(new File(file),suffix);
        textArea.setText(String.valueOf(count));

    }

    private String getSuffix(String l) {
        System.out.println(l);
        if(l == null||"".equals(l)||" ".equals(l)){
            return ".java";
        }
        switch (l) {
            case "java":
            case "JAVA":
                return ".java";
            case "c":
            case "C":
                return ".c";
            case "c++":
            case "C++":
                return ".cpp";
            case "python":
            case "PYTHON":
                return ".py";
            case "kotlin":
            case "KOTLIN":
                return ".kt";
            default:
                return l;
        }
    }

    private  int getTheCodeNum(File file,String suffix) throws Exception {
        int count = 0;
        if(file.isFile()){
            if(file.getName().endsWith(suffix)){
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = null;
                while((line=br.readLine())!=null){
                    count ++;
                }
                br.close();
            }
            return count;
        }
        if(file.listFiles()==null) return 0;

        File[] files = file.listFiles();
        assert files != null;
        for(File f:files){
             count += getTheCodeNum(f,suffix);
        }
        return count;
    }

    public static void main(String[] args) {
        new Count();
    }
}
