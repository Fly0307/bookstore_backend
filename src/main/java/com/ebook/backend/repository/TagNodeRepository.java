package com.ebook.backend.repository;

import com.ebook.backend.entity.TagNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Set;

public interface TagNodeRepository extends Neo4jRepository<TagNode,Long> {
    TagNode findByName(String name);

    Set<TagNode> findByRelatedTagNode(String name);
    Set<TagNode> findTagNodesByRelatedTagNodeAndRelatedTagNode(String name);
}
