package xyz.coolblog.chapter1;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import xyz.coolblog.chapter1.dao.ArticleDao;
import xyz.coolblog.chapter1.model.Article;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * MyBatisTest
 *
 * @author Tian ZhongBo
 * @date 2018-07-01 13:24:55
 */
public class MyBatisTest {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void prepare() throws IOException {
        String resource = "chapter1/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        inputStream.close();
    }


    @Test
    public void testNull(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            ArticleDao articleDao = sqlSession.getMapper(ArticleDao.class);
            List<Article> list = articleDao.findByAuthorAndCreateTime("xxx","2019-10-10");
            System.out.println(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void testUnpooledDataSource(){
        UnpooledDataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        Properties dataSourceProperties = new Properties();
        dataSourceProperties.put("driver", "org.hsqldb.jdbcDriver");
        dataSourceProperties.put("url", "jdbc:hsqldb:mem:xml_references");
        dataSourceProperties.put("username", "sa");
        dataSourceFactory.setProperties(dataSourceProperties);
        DataSource ds = dataSourceFactory.getDataSource();
        System.out.println(ds);
    }

    @Test
    public void testMyBatis() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            ArticleDao articleDao = session.getMapper(ArticleDao.class);
            List<Article> articles = articleDao.
                findByAuthorAndCreateTime("coolblog.xyz", "2018-06-10");

        } finally {
            session.commit();
            session.close();
        }
    }

    @Test
    public void XPathTest() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(false);
        documentBuilderFactory.setCoalescing(false);
        documentBuilderFactory.setExpandEntityReferences(true);
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {

            }

            @Override
            public void error(SAXParseException exception) throws SAXException {

            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {

            }
        });
        String resource = "chapter1/book.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Document document = builder.parse(inputStream);
        XPathFactory factory = XPathFactory.newInstance();
        //创建XPath对象
        XPath xPath = factory.newXPath();
        XPathExpression expr = xPath.compile("//book[author='xuzhiyong']/title/text()");
        Object result = expr.evaluate(document, XPathConstants.NODESET);
        NodeList nodeList = (NodeList) result;
        for(int i = 0 ; i < nodeList.getLength() ; i++){
            System.out.println(nodeList.item(i).getNodeValue());
        }
    }
}
