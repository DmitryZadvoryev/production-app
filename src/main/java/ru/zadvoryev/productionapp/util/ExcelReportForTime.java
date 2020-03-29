package ru.zadvoryev.productionapp.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ru.zadvoryev.productionapp.dto.ReportForTimeDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class ExcelReportForTime {

    public static ByteArrayInputStream toExcelFile(List<List<ReportForTimeDto>> list,
                                                   LocalDate startDate,
                                                   LocalDate endDate) throws IOException {
        String start = startDate.toString();
        String end = endDate.toString();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Продукция" + start + "-" + end);

            Row row = sheet.createRow(0);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


            Cell cell = row.createCell(0);
            cell.setCellValue("Заказчик");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(1);
            cell.setCellValue("Наименование изделия");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(2);
            cell.setCellValue("Вариант");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(3);
            cell.setCellValue("Количество");
            cell.setCellStyle(headerCellStyle);

            cell = row.createCell(4);
            cell.setCellValue("Сторона");
            cell.setCellStyle(headerCellStyle);

            int numRow = 1;
            for (int i = 0; i < list.size(); i++) {
                List<ReportForTimeDto> reportForTimeDtos = list.get(i);
                Row title = sheet.createRow(numRow++);
                title.createCell(0).setCellValue(reportForTimeDtos.get(0).getLineName());
                for (int j = 0; j < reportForTimeDtos.size(); j++) {
                    Row dataRow = sheet.createRow(numRow++);
                    ReportForTimeDto reportForTimeDto = reportForTimeDtos.get(j);
                    dataRow.createCell(0).setCellValue(reportForTimeDto.getNameOrg());
                    dataRow.createCell(1).setCellValue(reportForTimeDto.getNamePr());
                    dataRow.createCell(2).setCellValue(reportForTimeDto.getVar());
                    dataRow.createCell(3).setCellValue(reportForTimeDto.getQuantity());
                    dataRow.createCell(4).setCellValue(reportForTimeDto.getSide());
                }
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
