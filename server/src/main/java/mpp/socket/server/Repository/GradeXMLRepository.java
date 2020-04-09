package Repository;

import Domain.Grade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GradeXMLRepository extends InMemoryRepository<Integer, Grade> {

    private String filePath;

    public GradeXMLRepository(String filePath) {
        super();
        this.filePath = filePath;
        this.readFromFile();
    }

    @Override
    public Optional<Grade> save(Grade entity) {
        try {
            DocumentBuilderFactory dbf  = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(this.filePath));

            Element element = doc.createElement("grade");
            Element studentID = doc.createElement("studentid");
            Element problemID = doc.createElement("problemid");
            Element actualGrade = doc.createElement("agrade");

            studentID.appendChild(doc.createTextNode(Integer.toString(entity.getStudent())));
            problemID.appendChild(doc.createTextNode(Integer.toString(entity.getProblem())));
            actualGrade.appendChild(doc.createTextNode(Integer.toString(entity.getActualGrade())));

            element.appendChild(studentID);
            element.appendChild(problemID);
            element.appendChild(actualGrade);

            doc.getDocumentElement().appendChild(element);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(this.filePath));
            t.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return super.save(entity);
    }

    @Override
    public Optional<Grade> delete(Integer integer) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            document.getDocumentElement().normalize();
            NodeList gradeNodes = document.getElementsByTagName("grade");

            Iterable<Grade> allGrades = this.findAll();
            Set<Grade> grades = new HashSet<>();
            allGrades.forEach(grades::add);

            List<Grade> filteredGrades = grades.stream().filter(grade -> grade.getId().equals(integer)).collect(Collectors.toList());

            for (int i = 0; i < gradeNodes.getLength(); i++) {
                 if (gradeNodes.item(i).getNodeType() == gradeNodes.item(i).ELEMENT_NODE){
                     Element grade = (Element) gradeNodes.item(i);
                     for (int j = 0; j < filteredGrades.size(); j++) {
                         if (Integer.parseInt(grade.getElementsByTagName("studentid").item(0).getTextContent()) == filteredGrades.get(j).getStudent() &&
                         Integer.parseInt(grade.getElementsByTagName("problemid").item(0).getTextContent()) == filteredGrades.get(j).getProblem()) {
                             gradeNodes.item(i).getParentNode().removeChild(gradeNodes.item(i));
                             break;
                         }
                     }
                 }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.filePath));
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return super.delete(integer);
    }

    @Override
    public Optional<Grade> update(Grade entity) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(this.filePath));

            NodeList nodeList = doc.getElementsByTagName("grade");
            for(int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (
                            entity.getStudent() == Integer.parseInt(element.getElementsByTagName("studentid").item(0).getTextContent()) &&
                                    entity.getProblem() == Integer.parseInt(element.getElementsByTagName("problemid").item(0).getTextContent())
                    ) {
                        Node actualGrade = element.getElementsByTagName("agrade").item(0).getFirstChild();
                        actualGrade.setNodeValue(Integer.toString(entity.getActualGrade()));
                        break;
                    }
                }
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(this.filePath));
            t.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return super.update(entity);
    }

    private void readFromFile(){
        try {
            File file = new File(this.filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("grade");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int studentID = Integer.parseInt(element.getElementsByTagName("studentid").item(0).getTextContent());
                    int problemID = Integer.parseInt(element.getElementsByTagName("problemid").item(0).getTextContent());
                    int actualGrade = Integer.parseInt(element.getElementsByTagName("agrade").item(0).getTextContent());

                    Grade grade = new Grade(studentID, problemID, actualGrade);
                    super.save(grade);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
