package mpp.socket.common.Repository;


import mpp.socket.common.Domain.Problem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Optional;

public class ProblemXMLRepository extends InMemoryRepository<Integer, Problem> {
    private String filePath;
    public ProblemXMLRepository(String filePath) {
        super();
        this.filePath = filePath;
        this.readFromFile();
    }

    @Override
    public Optional<Problem> findOne(Integer integer) {
        return super.findOne(integer);
    }

    @Override
    public Iterable<Problem> findAll() {
        return super.findAll();
    }

    @Override
    public Optional<Problem> save(Problem entity) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            Element problem = document.createElement("problem");
            problem.setAttribute("id", entity.getId().toString());
            Element description = document.createElement("description");
            description.appendChild(document.createTextNode(entity.getDescription()));
            problem.appendChild(description);
            Element difficulty = document.createElement("difficulty");
            difficulty.appendChild(document.createTextNode(entity.getDifficulty()));
            problem.appendChild(difficulty);
            document.getDocumentElement().appendChild(problem);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.filePath));
            transformer.transform(source, result);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return super.save(entity);
    }

    @Override
    public Optional<Problem> delete(Integer entity) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            document.getDocumentElement().normalize();
            NodeList problems = document.getElementsByTagName("problem");
            for (int i = 0; i < problems.getLength(); i++) {
                if (problems.item(i).getAttributes().item(0).getTextContent().equals(entity.toString())) {
                    problems.item(i).getParentNode().removeChild(problems.item(i));
                    break;
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.filePath));
            transformer.transform(source, result);
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return super.delete(entity);
    }

    @Override
    public Optional<Problem> update(Problem entity) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            document.getDocumentElement().normalize();

            NodeList problems = document.getElementsByTagName("problem");
            for(int i=0; i<problems.getLength(); i++){
                if(problems.item(i).getAttributes().item(0).getTextContent().equals(entity.getId().toString())){
                    Element problem = (Element)problems.item(i);
                    Node description = problem.getElementsByTagName("description").item(0).getFirstChild();
                    description.setNodeValue(entity.getDescription());
                    Node difficulty = problem.getElementsByTagName("difficulty").item(0).getFirstChild();
                    difficulty.setNodeValue(entity.getDifficulty());
                    break;
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.filePath));
            transformer.transform(source, result);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return super.update(entity);
    }

    protected void readFromFile(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("problem");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element element = (Element) node;
                int id = Integer.parseInt(element.getAttribute("id"));
                String description = element.getElementsByTagName("description").item(0).getTextContent();
                String difficulty = element.getElementsByTagName("difficulty").item(0).getTextContent();
                Problem problem = new Problem(description, difficulty);
                problem.setId(id);
                super.save(problem);
            }
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
