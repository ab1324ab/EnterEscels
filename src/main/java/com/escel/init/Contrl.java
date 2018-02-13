package com.escel.init;

import java.io.File;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.*;

/**
 * Created by Administrator on 2017-4-23.
 */
public class Contrl {

        public  void main( ) {
            try {
                WritableCellFormat wc = null;
                // 打开文件
                WritableWorkbook book = Workbook.createWorkbook(new File("C:\\Users\\ab1324ab\\Desktop\\test2.xlsx"));
                // 生成名为“第一页”的工作表，参数0表示这是第一页
               WritableSheet sheet = book.createSheet("第一页", 1);
                // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
                // 以及单元格内容为test
                Label label=null;
                int init=50;

                // 将定义合并的单元格添加到工作表中
                for(int i=0;i<init;i+=6){ //行数 i
                    int ss=3;
                    int ssc=4;
                    int ss2=3;
                    int ssc2=4;
                    //标题
                    sheet.mergeCells(0, i, init, i);
                    label = new Label(0, i, "工号：0300009  姓名：狙击手   部门：生产部SMT"+i);
                    sheet.addCell(label);

                for (int j=0;j<=init;j++){
                    wc=new WritableCellFormat();
                    wc.setAlignment(Alignment.CENTRE);
                    wc.setBorder(Border.ALL, BorderLineStyle.THIN);
                    if(j==3 || ss==j ){
                        wc.setBackground(Colour.BRIGHT_GREEN);
                        ss+=7;
                    }else if( j==4|| ssc==j){
                        wc.setBackground(Colour.BRIGHT_GREEN);
                        ssc+=7;
                    }
                    jxl.write.Number number = new jxl.write.Number(j, i+1, j+1,wc);
                    sheet.addCell(number);
                }
                    for (int j=0;j<=init;j++){
                        wc=new WritableCellFormat();
                        wc.setAlignment(Alignment.CENTRE);
                        wc.setWrap(true);
                        wc.setBorder(Border.ALL, BorderLineStyle.THIN);
                        if(j==3 || ss2==j ){
                            label = new Label(j, i+2, "",wc);
                            ss2+=7;
                            sheet.addCell(label);
                        }else if( j==4|| ssc2==j){
                            label = new Label(j, i+2, "",wc);
                            ssc2+=7;
                            sheet.addCell(label);
                        }else {
                            label = new Label(j, i+2, "21:22 21:2"+j+" 21:22 21:6"+i,wc);
                            sheet.addCell(label);
                        }
                    }

                    for(int y=2;y<=init;y+=6){
                        wc=new WritableCellFormat();
                        wc.setBorder(Border.ALL, BorderLineStyle.THIN);
                        wc.setAlignment(Alignment.CENTRE);
                        for(int x=0;x<=init;x++){
                            Cell c2=sheet.getCell(x,y);
                            String stringc2 = c2.getContents();
                            //System.out.println(stringc2+"x="+x+"y="+y);
                            String[] str=stringc2.split(" ");
                            if(stringc2.equals("") || str.length==0){
                               sheet.addCell(new Label(x,y+1,"",wc));
                                continue;
                            }
                            for(int xl=0;xl<str.length;xl++){
                                String[] strings=str[0].split(":");
                                jxl.write.Number number = new jxl.write.Number(x,y+1,Integer.valueOf(strings[1])-Integer.valueOf(strings[0]),wc);
                                //label=new Label(x,y+1,str[0]+str[1],wc);//str[1]
                                sheet.addCell(number);
                            }
                        }
                    }
                }
                /*
                 * 生成一个保存数字的单元格 必须使用Number的完整包路径，否则有语法歧义 单元格位置是第二列，第一行，值为789.123
                 */
                /*jxl.write.Number number = new jxl.write.Number(0, 12, 555.12541);
                sheet.addCell(number);
*/
                // 写入数据并关闭文件
                book.write();
                book.close();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

