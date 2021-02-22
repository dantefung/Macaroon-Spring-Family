package com.dantefung.lucene.bootstrap;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class IndexManager {

	public static final String BASE_DIR = System.getProperty("user.dir");
	public static final String LUCENE_INDEX_DIR =  BASE_DIR + File.separator + "spring-boot-lucene/target/Lucene_index";
	public static final String LUCENE_DOCUMENT_DIR = BASE_DIR + File.separator + "spring-boot-lucene/src/main/resources/Lucene_Document";

    private IndexWriter indexWriter;

    @Before
    public void init() throws Exception {
        //创建一个IndexWriter对象，需要使用SmartChineseAnalyzer作为分析器
        indexWriter =
                new IndexWriter(FSDirectory.open(new File(LUCENE_INDEX_DIR).toPath()),
                        new IndexWriterConfig(new SmartChineseAnalyzer()));
    }

    @Test
    public void addDocument() throws Exception {
        //创建一个IndexWriter对象，需要使用IKAnalyzer作为分析器
        IndexWriter indexWriter =
                new IndexWriter(FSDirectory.open(new File(LUCENE_INDEX_DIR).toPath()),
                new IndexWriterConfig(new SmartChineseAnalyzer()));
        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域
        document.add(new TextField("name", "新添加的文件", Field.Store.YES));
        document.add(new TextField("content", "新添加的文件内容", Field.Store.NO));
        document.add(new StoredField("path",LUCENE_DOCUMENT_DIR));
        // 把文档写入索引库
        indexWriter.addDocument(document);
        //关闭索引库
        indexWriter.close();
    }

    @Test
    public void deleteAllDocument() throws Exception {
        //删除全部文档
        indexWriter.deleteAll();
        //关闭索引库
        indexWriter.close();
    }

    @Test
    public void deleteDocumentByQuery() throws Exception {
        indexWriter.deleteDocuments(new Term("name", "apache"));
        indexWriter.close();
    }

    @Test
    public void updateDocument() throws Exception {
        //创建一个新的文档对象
        Document document = new Document();
        //向文档对象中添加域
        document.add(new TextField("name", "更新之后的文档", Field.Store.YES));
        document.add(new TextField("name1", "更新之后的文档2", Field.Store.YES));
        document.add(new TextField("name2", "更新之后的文档3", Field.Store.YES));
        //更新操作
        indexWriter.updateDocument(new Term("name", "spring"), document);
        //关闭索引库
        indexWriter.close();
    }

}
