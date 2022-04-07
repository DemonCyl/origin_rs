package zero.test;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import zero.dao.impl.testDao;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Description:
 * @Author: DarkFuneral
 * @CreateDate: 2018/3/30.11:41
 * @Project: WS_KAIJIA
 */
public class Client {

    public static void main(String[] args) {


        read();


    }

    private static void read() {
        String filePath = "/home/zero/Documents/Book1.xlsx";
        testDao dao = new testDao();
        ArrayList<String[]> data = new ArrayList<>();
        File file = new File(filePath);
        data = openExcel(file);
        int count = 0;
        for (String[] str : data) {
//            dao.updateData(str[0]);
//            dao.updateTax(str);  // 税率修改
//            dao.updatePmk(str[0]);
            //dao.update(str); //233
//            dao.updateFaj(str);
            System.out.println(str[0]);
            count += 1;
            // 组织更新 BY CHENYL
/*            switch (str[2]){
                case "A":  // ADD
                    dao.addOrg(str);
                    System.out.println(str[0]);
                    break;
                case "U":  // UPDATE
                    dao.updateOrg(str);
                    System.out.println(str[0]);
                    break;
                case "D":  // DELETE
                    dao.deleteOrg(str);
                    System.out.println(str[0]);
                    break;
            }*/
/*            dao.updateOrg(str);
            System.out.println(str[0]);*/
        }
        System.out.println(count);
//        System.out.println(data.size());
//        dao.updatePmk("221-180400001");
    }

    public static Workbook getWorkBook(File file) {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = new FileInputStream(file);

            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith("xls")) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    private static ArrayList<String[]> openExcel(File file ) {
        ArrayList<String[]> datas = new ArrayList<>();
        Workbook workbook = getWorkBook(file);
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                //获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    //获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
//                        System.out.println(cells[cellNum]);
                    }
                    datas.add(cells);
                }
            }

        }


        return datas;

    }
    public static String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        /*if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }*/
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    DateFormat formater = new SimpleDateFormat(
                            "yyyy/MM/dd");
                    cellValue = formater.format(date);
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}




