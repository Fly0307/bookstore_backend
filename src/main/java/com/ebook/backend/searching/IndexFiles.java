package com.ebook.backend.searching;

import com.alibaba.fastjson.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IndexFiles {


    private static void index(String indexDirPath, String filePath) throws IOException {

        Directory indexDir = FSDirectory.open(Paths.get(indexDirPath));
        Analyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setUseCompoundFile(false);
        IndexWriter writer = new IndexWriter(indexDir, indexWriterConfig);

        BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            JSONObject jsonObj = JSONObject.parseObject(line);
            Document document = new Document();
            document.add(new Field("description", jsonObj.getString("description"), TextField.TYPE_NOT_STORED));
            document.add(new Field("id", jsonObj.getString("book_id"), StringField.TYPE_STORED));
            writer.addDocument(document);
        }
        br.close();
        writer.close();
    }

    public static void main(String[] args) {
        try {

            index("./src/main/resources/index/", "./src/main/resources/books.txt" );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
