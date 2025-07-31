package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.idev.excel.ExcelReader;
import cn.idev.excel.FastExcel;
import cn.idev.excel.metadata.csv.CsvConstant;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.holder.ReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;

@SpringBootApplication
public class DemoApplication {
	/**
	 * 模拟数据
	 * @return List<DemoData>
	 */
	private static List<DemoData> data() {
		List<DemoData> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			DemoData data = new DemoData();
			data.setString("字符串" + i);
			data.setDate(new Date());
			data.setDoubleData(0.56);
			list.add(data);
		}
		return list;
	}

	public static void main(String[] args) {
		/* 
		String fileName = "demo.xlsx";
		// 创建一个名为“模板”的 sheet 页，并写入数据
		FastExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
		String fileName = "demo2.csv";
		FastExcel.write(fileName, DemoData.class)
				// 设置 CSV 文件的分隔符和引号模式
				.csv()
				.quote(CsvConstant.DOUBLE_QUOTE, QuoteMode.ALL)
				.recordSeparator(CsvConstant.LF)
				.nullString(CsvConstant.UNICODE_EMPTY)
				.escape(null)
				// 写入数据
				.doWrite(data());
		*/

		/*
		String fileName = "demo.xlsx";
		// 读取 Excel 文件
		FastExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
		*/
		
		String fileName = "demo.csv";
		/* 
		List<DemoData> tmpList = FastExcel.read(fileName, DemoData.class, new DemoDataListener())
			.csv()
			//.delimiter(CsvConstant.AT)
			//.quote('"',QuoteMode.ALL_NON_NULL)
			//.recordSeparator(CsvConstant.CRLF)
			//.nullString("")
			//.escape(null)
			.doReadSync();
		for (DemoData demoData : tmpList) {
			System.out.println(demoData.getString() + " | " +
					demoData.getDate() + " | " +
					demoData.getDoubleData() + " | " +
					demoData.getIgnore());
		}
					*/
		
		CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setDelimiter(CsvConstant.AT).build();
		//File csvFile = TestFileUtil.readFile(fileName);
		DemoDataListener listener = new DemoDataListener();
		try (ExcelReader excelReader = FastExcel.read(fileName, DemoData.class, listener).build()) {
			ReadWorkbookHolder readWorkbookHolder = excelReader.analysisContext().readWorkbookHolder();
			if (readWorkbookHolder instanceof CsvReadWorkbookHolder) {
				CsvReadWorkbookHolder csvReadWorkbookHolder = (CsvReadWorkbookHolder) readWorkbookHolder;
				csvReadWorkbookHolder.setCsvFormat(csvFormat);
			}
			ReadSheet readSheet = FastExcel.readSheet(0).build();
			excelReader.read(readSheet);
		}

		List<DemoData> tmpList = listener.getDataList();
		for (DemoData demoData : tmpList) {
			System.out.println(demoData.getString() + " | " +
					demoData.getDate() + " | " +
					demoData.getDoubleData() + " | " +
					demoData.getIgnore());
		}

	}

}
