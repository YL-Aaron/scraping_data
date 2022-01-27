package com.data.scrapingdata.core.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * NamedEntityExtractor
 */
public class HANLPExtractor implements NLPExtractor {
    private final static Logger LOG = LogManager.getLogger(HANLPExtractor.class);
    private static final Segment segment = HanLP.newSegment().enableOrganizationRecognize(true).enablePlaceRecognize(true);

    /**
     * 抽取命名实体
     *
     * @param content 文章正文
     * @return map的key是一下三种nr, ns, nt  其value就是对应的词表
     */
    @Override
    public Map<String, Set<String>> extractNamedEntity(String content) {
        List<Term> termList = segment.seg(content);
        Set<String> nrList = termList.stream().filter(term -> term.nature.startsWith("nr"))
                .map(term -> term.word).collect(Collectors.toSet());
        Set<String> nsList = termList.stream().filter(term -> term.nature.startsWith("ns"))
                .map(term -> term.word).collect(Collectors.toSet());
        Set<String> ntList = termList.stream().filter(term -> term.nature.startsWith("nt"))
                .map(term -> term.word).collect(Collectors.toSet());
        Map<String, Set<String>> namedEntity = new HashMap<>();
        namedEntity.put("nr", nrList);
        namedEntity.put("ns", nsList);
        namedEntity.put("nt", ntList);
        return namedEntity;
    }

    /**
     * 抽取摘要
     *
     * @param content 文章正文
     * @return 摘要句子列表
     */
    @Override
    public List<String> extractSummary(String content) {
        return HanLP.extractSummary(content, 5);
    }

    /**
     * 抽取关键词
     *
     * @param content 文章正文
     * @return 关键词列表
     */
    @Override
    public List<String> extractKeywords(String content) {
        return HanLP.extractKeyword(content, 10);
    }

}