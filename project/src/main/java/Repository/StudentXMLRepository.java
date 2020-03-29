package Repository;

import Domain.Student;
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
import java.util.Optional;


public class StudentXMLRepository extends InMemoryRepository<Integer, Student> {

    private String filePath;

    public StudentXMLRepository(String filePath) {
        super();
        this.filePath = filePath;
        this.readFromFile();
    }


    @Override
    public Optional<Student> save(Student entity) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            Element student = document.createElement("student");
            student.setAttribute("id", entity.getId().toString());
            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(entity.getFirstName()));
            student.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(entity.getLastName()));
            student.appendChild(lastName);
            document.getDocumentElement().appendChild(student);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(this.filePath));
            transformer.transform(source, result);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return super.save(entity);

    }

    @Override
    public Optional<Student> delete(Integer integer) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            document.getDocumentElement().normalize();
            NodeList students = document.getElementsByTagName("student");
            for (int i = 0; i < students.getLength(); i++) {
                if (students.item(i).getAttributes().item(0).getTextContent().equals(integer.toString())) {
                    students.item(i).getParentNode().removeChild(students.item(i));
                    break;
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
    public Optional<Student> update(Student entity) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(this.filePath));
            document.getDocumentElement().normalize();

            NodeList students = document.getElementsByTagName("student");
            for(int i=0; i<students.getLength(); i++){
                if(students.item(i).getAttributes().item(0).getTextContent().equals(entity.getId().toString())){
                    Element student = (Element)students.item(i);
                    Node firstName = student.getElementsByTagName("firstName").item(0).getFirstChild();
                    firstName.setNodeValue(entity.getFirstName());
                    Node lastName = student.getElementsByTagName("lastName").item(0).getFirstChild();
                    lastName.setNodeValue(entity.getLastName());
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

    private void readFromFile() {
        try {
            File file = new File(this.filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("student");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getAttribute("id"));
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();

                    Student student = new Student(firstName, lastName);
                    student.setId(id);

                    super.save(student);
                }
            }
        }  catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
