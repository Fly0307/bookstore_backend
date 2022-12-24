package com.ebook.backend;

import com.ebook.backend.entity.Book;
import com.ebook.backend.entity.TagNode;
import com.ebook.backend.repository.BookRepository;
import com.ebook.backend.repository.TagNodeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class BookstoreApplication {
    //重定向对8080端口的访问
//    @Bean
//    public Connector connector(){
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(true);
//        connector.setRedirectPort(8443);
//        return connector;
//    }
//
//    //增加对Tomcat访问的控制
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
//        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/*");
////                collection.addPattern("/logout");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(connector);
//        return tomcat;
//    }
    public static void main(String[] args) {SpringApplication.run(BookstoreApplication.class, args);
    }
    @Bean
    CommandLineRunner createTagNodeAndConnect(TagNodeRepository tagNodeRepository, BookRepository bookRepository) {
        return args -> {

            tagNodeRepository.deleteAll();
            List<Book> books = bookRepository.getBooks();
            //从type中读取标签并建立Node关系
            for (Book book :
                    books) {
                String types = book.getType();
                String type1= types.split("、")[0];
                String type2=types.split("、")[1];
                TagNode tagNode1=new TagNode(type1);
                tagNodeRepository.save(tagNode1);
                tagNode1=tagNodeRepository.findByName(type1);
                if(!Objects.equals(type2, "")){
                    TagNode tagNode2=new TagNode(type2);
                    tagNodeRepository.save(tagNode2);
                    tagNode1.relatedTo(tagNode2);
                }
                tagNodeRepository.save(tagNode1);
            }
        };
    }

}
