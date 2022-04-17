package com.adb.assignment.adbassignment3graph.service;

import com.adb.assignment.adbassignment3graph.domain.*;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPInputStream;

import static org.neo4j.driver.Values.parameters;

/**
 * @Author Vaibhav Kale
 * This Service class parse the pubmed xml.gz file using JAXB library.
 */
@Service
public class ReadPubMedFileService {


    @Autowired
    Driver driver;

    public void loadFile() throws Exception {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(PubmedArticleSet.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            //Unmarshall the XML file in java Object
            PubmedArticleSet pubmedArticleSet = (PubmedArticleSet) jaxbUnmarshaller.unmarshal(new GZIPInputStream(new FileInputStream("/Users/vaibhavkale/Documents/KSU studies MS /Spring 2022/ADB/assignments/assignment4/pubmed22n1081.xml.gz")));


            List<PubMed> pubMeds = new ArrayList<>();

            //Go through all the citations and insert in pubmed table
            pubmedArticleSet.getPubmedArticle().forEach(pubMedArticle -> {
                try{
                    PubMed pubMed = PubMed.builder()
                            .pmid(pubMedArticle.getMedCitation().getPmid())
                            .article_title( pubMedArticle.getMedCitation().getArticle().getArticleTitle().length() > 255 ?
                                    pubMedArticle.getMedCitation().getArticle().getArticleTitle().substring(0,254) : pubMedArticle.getMedCitation().getArticle().getArticleTitle())
                            .first_author( pubMedArticle.getMedCitation().getArticle().getAuthorList() != null ?
                                    (getAuthor(pubMedArticle) !=null && getAuthor(pubMedArticle).length() > 63 ? getAuthor(pubMedArticle).substring(0,62) : getAuthor(pubMedArticle)) : null)
                            .publisher( pubMedArticle.getMedCitation().getArticle().getJournal().getPublisher().length() > 127 ?
                                    pubMedArticle.getMedCitation().getArticle().getJournal().getPublisher().substring(0,126) : pubMedArticle.getMedCitation().getArticle().getJournal().getPublisher())
                            .publishedDate(getDate(pubMedArticle.getMedCitation().getDateCompleted()))
                            .uploader("Vaibhav Kale")
                            .journal(pubMedArticle.getMedCitation().getArticle().getJournal())
                            .authors(pubMedArticle.getMedCitation().getArticle().getAuthorList() !=null ? pubMedArticle.getMedCitation().getArticle().getAuthorList() : null )
                            .anabstract(pubMedArticle.getMedCitation().getArticle().getAnAbstract() != null ? pubMedArticle.getMedCitation().getArticle().getAnAbstract().getAbstractText() : "na")
                            .keywords(pubMedArticle.getMedCitation().getKeywordList() != null ? pubMedArticle.getMedCitation().getKeywordList().getKeywords() : null)
                            .meshHeadingList(pubMedArticle.getMedCitation().getMeshHeadingList() != null ? pubMedArticle.getMedCitation().getMeshHeadingList() : null)
                            .build();
                    pubMeds.add(pubMed);
                } catch(Exception e){
                    System.out.println("Error in : " + pubMedArticle.getMedCitation().getPmid());
                    e.printStackTrace();
                }

            });

            System.out.println("Loading complete");

//            List<PubMed> pubMeds1 = pubMeds.subList(1, 10);

            pubMeds.forEach(pubMed -> {
                    createCitationNode(pubMed);
                    if(pubMed.getAuthors() != null){
                        if(pubMed.getAuthors().getAuthorList() != null){
                            pubMed.getAuthors().getAuthorList().forEach(author -> {
                                createAuthorNode(pubMed, author);
                                createCitationAuthorRelationshipNode(pubMed, author);
                            });
                        }

                    }
                    createJournalNode(pubMed);
                    createJournalAndCitationRelationshipNode(pubMed);
                    if(pubMed.getKeywords() != null){
                        pubMed.getKeywords().forEach(keyword -> {
                            createKeywordNode(keyword);
                            createKeywordAndCitationRelationshipNode(pubMed,keyword);
                        });
                    }
                    if(pubMed.getMeshHeadingList() != null){
                        if(pubMed.getMeshHeadingList().getMeshHeadings() != null){
                            pubMed.getMeshHeadingList().getMeshHeadings().forEach(meshHeading -> {
                                createMeshNode(meshHeading.getTerm());
                                createMeshAndCitationRelationshipNode(pubMed, meshHeading.getTerm());
                            });
                        }
                    }
            });

            System.out.println("Citation Created");

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String getAuthor(PubMedArticle pubMedArticle) {
        return (pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getForeName() != null) ?
                (pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getForeName().concat(" ")
                .concat(pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getLastName())) :
                (pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getCollectiveName() != null ?
                        pubMedArticle.getMedCitation().getArticle().getAuthorList().getAuthorList().get(0).getCollectiveName() : null);
    }


    public Date getDate(DateCompleted pubDate) {
        if(pubDate == null || pubDate.getYear() == null || pubDate.getMonth() == null || pubDate.getDay() == null ){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, pubDate.getYear());
        cal.set(Calendar.MONTH, pubDate.getMonth() -1);
        cal.set(Calendar.DATE, pubDate.getDay());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return new Date(cal.getTimeInMillis());
    }


    public void createCitationNode( final PubMed pubMed)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction( tx ->
            {
                return tx.run( "CREATE (n:Citation {pmid:" + pubMed.getPmid() + ", articletitle:'" +
                        pubMed.getArticle_title().replace("\'", "") + "',abstractText:'" +
                        pubMed.getAnabstract().replace("\'", "")  + "'})");
            } );

        } catch (Exception e){
            System.out.println("error in the pubmed" + pubMed.getPmid().toString());
        }
    }

    public void createAuthorNode(final PubMed pubMed, Author author)
    {

        int hashcode = author.getLastName() != null ? author.getLastName().hashCode() : (author.getForeName() != null ? author.getForeName().hashCode() : "NA".hashCode());

        String lastname = author.getLastName() != null ? author.getLastName() : " ";
        String forename = author.getForeName() != null ? author.getForeName() : " ";
        String initials = author.getInitials() != null ? author.getInitials() : " ";

        String query = "CREATE (n:Author {lastname:'" + lastname.replace("\'", "")
                + "', forename:'" +  forename.replace("\'", "")
                + "', initials:'" +  initials.replace("\'", "")
                + "', authorid:" +  hashcode + "})";

            try ( Session session = driver.session() )
            {
                session.writeTransaction( tx ->
                {
                    return tx.run(query);
                } );

            } catch (Exception e){

            }

    }

    public void createJournalNode( final PubMed pubMed)
    {
        if (pubMed.getJournal() != null) {
            try ( Session session = driver.session() )
            {
                session.writeTransaction( tx ->
                {
                    return tx.run( "CREATE (n:Journal {issn:" + pubMed.getJournal().getPublisher().hashCode() + ", title:'" +
                            pubMed.getJournal().getPublisher().replace("\'", "") + "'})");
                } );

            }catch (Exception e){

            }
        }
    }

    public void createKeywordNode( final String keyword)
    {
            try ( Session session = driver.session() )
            {
                session.writeTransaction( tx ->
                {
                    return tx.run( "CREATE (n:Keyword {kwid:" + keyword.hashCode() + ", term:'" +
                            keyword.replace("\'", "") + "'})");
                } );
            }catch (Exception e){

            }
    }

    public void createMeshNode( final String term)
    {
            try ( Session session = driver.session() )
            {
                session.writeTransaction( tx ->
                {
                    return tx.run( "CREATE (n:Mesh {meshid:" + term.hashCode() + ", term:'" +
                            term.replace("\'", "") + "'})");
                } );
            }catch (Exception e){

            }
    }

    public void createCitationAuthorRelationshipNode( final PubMed pubMed, Author author)
    {
        int hashcode = author.getLastName() != null ? author.getLastName().hashCode() : (author.getForeName() != null ? author.getForeName().hashCode() : "NA".hashCode());

        try ( Session session = driver.session() )
        {
            session.writeTransaction( tx ->
            {
                return tx.run( "MATCH\n" +
                        "  (a:Citation),\n" +
                        "  (b:Author)\n" +
                        "WHERE a.pmid = " + pubMed.getPmid() + " and b.authorid = " + hashcode + " \n" +
                        "CREATE (a)-[r:WrittenBy]->(b)\n" +
                        "RETURN type(r)");
            } );

        }
    }


    public void createJournalAndCitationRelationshipNode( final PubMed pubMed)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction( tx ->
            {
                return tx.run( "MATCH\n" +
                        "  (a:Citation),\n" +
                        "  (b:Journal)\n" +
                        "WHERE a.pmid = " + pubMed.getPmid() + " and b.issn = " + pubMed.getJournal().getPublisher().hashCode() + " \n" +
                        "CREATE (a)-[r:PublishedBy]->(b)\n" +
                        "RETURN type(r)");
            } );

        }
    }

    public void createKeywordAndCitationRelationshipNode( final PubMed pubMed, final String keyword)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction( tx ->
            {
                return tx.run( "MATCH\n" +
                        "  (a:Citation),\n" +
                        "  (b:Keyword)\n" +
                        "WHERE a.pmid = " + pubMed.getPmid() + " and b.kwid = " + keyword.hashCode() + " \n" +
                        "CREATE (a)-[r:Contains]->(b)\n" +
                        "RETURN type(r)");
            } );

        }
    }

    public void createMeshAndCitationRelationshipNode( final PubMed pubMed, final String term)
    {
        try ( Session session = driver.session() )
        {
            session.writeTransaction( tx ->
            {
                return tx.run( "MATCH\n" +
                        "  (a:Citation),\n" +
                        "  (b:Mesh)\n" +
                        "WHERE a.pmid = " + pubMed.getPmid() + " and b.meshid = " + term.hashCode() + " \n" +
                        "CREATE (a)-[r:Contains]->(b)\n" +
                        "RETURN type(r)");
            } );

        }
    }


}
