package com.ebook.backend.searching;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class searchfile {
    public static List<Integer> searchByKeyword(String keyword){
        List<Integer> bookIdList = new ArrayList<>();
        try {
            FSDirectory indexDir = FSDirectory.open(Paths.get("./src/main/resources/index/"));
            IndexReader reader = DirectoryReader.open(indexDir);
            IndexSearcher indexSearcher = new IndexSearcher(reader);

            Term term = new Term("description", keyword);
            Query query = new TermQuery(term);
            //符合查找的前n=18项记录
            TopDocs topDocs = indexSearcher.search(query,18);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                Document document = reader.document(scoreDoc.doc);
                bookIdList.add(Integer.valueOf(document.get("id")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookIdList;

    }
}
