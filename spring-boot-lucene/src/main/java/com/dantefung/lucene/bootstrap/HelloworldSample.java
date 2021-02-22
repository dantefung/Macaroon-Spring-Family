/*
 * Copyright (C), 2015-2020
 * FileName: HelloworldSample
 * Author:   DANTE FUNG
 * Date:     2021/2/20 10:23 下午
 * Description: hello lucene
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/20 10:23 下午   V1.0.0
 */
package com.dantefung.lucene.bootstrap;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @Title: HelloworldSample
 * @Description: hello lucene
 * @author DANTE FUNG
 * @date 2021/02/20 22/23
 * @since JDK1.8
 */
public class HelloworldSample {

	public static final String BASE_DIR = System.getProperty("user.dir");
	public static final String LUCENE_INDEX_DIR =  BASE_DIR + File.separator + "spring-boot-lucene/target/Lucene_index";
	public static final String LUCENE_DOCUMENT_DIR = BASE_DIR + File.separator + "spring-boot-lucene/src/main/resources/Lucene_Document";

	public static void main(String[] args) throws Exception {
		luceneCreateIndex();
		searchIndex();
		testTokenStream();
	}


	//创建索引
	public static void luceneCreateIndex() throws Exception {
		//1、创建一个Director对象，指定索引库保存的位置。
	    // Directory directory = new RAMDirectory();
		//指定索引存放的位置
		Directory directory = FSDirectory.open(Paths.get(new File(LUCENE_INDEX_DIR).getPath()));
		System.out.println("pathname" + Paths.get(new File(LUCENE_INDEX_DIR).getPath()));
		//创建一个分词器
		//        StandardAnalyzer analyzer = new StandardAnalyzer();
		//        CJKAnalyzer cjkAnalyzer = new CJKAnalyzer();
		SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();
		//创建indexwriterConfig(参数分词器)
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(smartChineseAnalyzer);
		//2、基于Directory对象创建indexwrite 对象(文件对象，索引配置对象)
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		//3、读取磁盘上的原始文件,对应每个文件创建一个文档对象
		File file = new File(LUCENE_DOCUMENT_DIR);

		for (File f : file.listFiles()) {
			//文件名
			String fileName = f.getName();
			//文件内容
			String fileContent = FileUtils.readFileToString(f, "UTF-8");
			System.out.println(fileContent);
			//文件路径
			String path = f.getPath();
			//文件大小
			long fileSize = FileUtils.sizeOf(f);
			// 参数1: 域的名称， 参数2: 域的内容， 参数3: 是否存储
			//创建文件域名
			//域的名称 域的内容 是否存储
			Field fileNameField = new TextField("fileName", fileName, Field.Store.YES);
			Field fileContentField = new TextField("fileContent", fileContent, Field.Store.YES);
			Field filePathField = new TextField("filePath", path, Field.Store.YES);
			Field fileSizeField = new TextField("fileSize", fileSize + "", Field.Store.YES);

			//创建Document 对象
			Document indexableFields = new Document();
			indexableFields.add(fileNameField);
			indexableFields.add(fileContentField);
			indexableFields.add(filePathField);
			indexableFields.add(fileSizeField);
			//创建索引，并写入索引库
			indexWriter.addDocument(indexableFields);

		}
   
		//关闭indexWriter
		indexWriter.close();
	}

	public static void searchIndex() throws IOException {
		//指定索引库存放路径
		Directory directory = FSDirectory.open(Paths.get(new File(LUCENE_INDEX_DIR).getPath()));
		//创建indexReader对象
		IndexReader indexReader = DirectoryReader.open(directory);
		//创建indexSearcher对象
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//创建查询
		Query query = new TermQuery(new Term("fileContent", "搜索"));
		//执行查询
		//参数一  查询对象    参数二  查询结果返回的最大值
		TopDocs topDocs = indexSearcher.search(query, 10);
		System.out.println("查询结果的总数" + topDocs.totalHits);
		//遍历查询结果
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			//scoreDoc.doc 属性就是doucumnet对象的id
			Document doc = indexSearcher.doc(scoreDoc.doc);
			System.out.println(doc.getField("fileName"));
			System.out.println(doc.getField("fileContent"));
			System.out.println(doc.getField("filePath"));
			System.out.println(doc.getField("fileSize"));
			System.out.println();
		}
		indexReader.close();
	}

	public static void testTokenStream() throws IOException {
		// 1、创建一个Analyzer对象， StrandardAnalyzer对象
		//StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
		SmartChineseAnalyzer smartChineseAnalyzer = new SmartChineseAnalyzer();
		// 使用分析器对象的tokenStream方法获得一个TokenStream对象
		TokenStream tokenStream = smartChineseAnalyzer.tokenStream("",
				"在本套课程中，我们将全面的讲解Lucene技术，从简单应用到细节使用再到底层原理都有深入讲解。尤其对Lucene底层的存储结构，搜索算法，以及数据结构等晦涩难懂的知识做深入浅出式讲解。学习Lucene对日后学习它的下游技术产品ElasticSearch和Solr将会大有帮助");
		// 3、向TokenStream对象中设置一个引用，相当于数一个指针
		CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
		// 4、使用TokenStream对象的rest方法。如果不调用抛异常
		tokenStream.reset();
		// 5、使用while循环遍历TokenStream对象
		while (tokenStream.incrementToken()) {
			System.out.println(charTermAttribute.toString());
		}
		// 6、关闭TokenStream对象
		tokenStream.close();

	}

}
