package cn.com.karl.apache.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;

public class DOMParser {

	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

	// Load and parse XML file into DOM
	public Document parse( String filePath ) {
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an XML file into a DOM tree
			document = builder.parse( new File( filePath ) );
		} catch( ParserConfigurationException e ) {
			e.printStackTrace();
		} catch( SAXException e ) {
			e.printStackTrace();
		} catch( IOException e ) {
			e.printStackTrace();
		}
		return document;
	}

	public static void main( String[] args ) throws Exception{
		NodeList triggerList;
		Map<String, Node> nodesMap = Maps.newHashMap();
		Map<String, TaskInfo> triggerInfoMap = Maps.newHashMap();
		Map<String, TaskInfo> taskInfoMap = Maps.newHashMap();
		DOMParser parser = new DOMParser();

		Document document = parser.parse( "D:\\dev\\source\\git-new\\ocean-task\\src\\main\\resources\\config\\task-service.xml" );
		// get root element
		Element rootElement = document.getDocumentElement();

		// traverse child elements
		NodeList nodes = rootElement.getChildNodes();
		for( int i = 0; i < nodes.getLength(); i++ ) {
			NodeList nodeList;
			Node node = nodes.item( i );
			if( node.getAttributes() != null && node.getAttributes().getNamedItem( "class" ) != null &&
					StringUtils.equals( node.getAttributes().getNamedItem( "class" ).getNodeValue(),
							"org.springframework.scheduling.quartz.SchedulerFactoryBean" ) ) {
				System.out.println( node.getAttributes().getNamedItem( "class" ).getNodeValue() );
				nodeList = node.getChildNodes();
				for( int j = 0; j < nodeList.getLength(); ++j ) {
					if( nodeList.item( j ).getNodeType() == Node.ELEMENT_NODE ) {
						nodeList = nodeList.item( j ).getChildNodes();
						for( int k = 0; k < nodeList.getLength(); ++k ) {
							if( nodeList.item( k ).getNodeType() == Node.ELEMENT_NODE ) {
								triggerList = nodeList.item( k ).getChildNodes();
								for( int f = 0; f < triggerList.getLength(); ++f ) {
									if( triggerList.item( f ) != null && triggerList.item( f ).getNodeType() == Node.ELEMENT_NODE
											&& triggerList.item( f ).getAttributes() != null ) {
										triggerInfoMap.put( triggerList.item( f ).getAttributes().getNamedItem( "local" ).getNodeValue(), new TaskInfo() );
									}
								}
							}
						}
						break;
					}
				}
			}
			if( node.getAttributes() != null && node.getAttributes().getNamedItem( "id" ) != null ) {
				nodesMap.put( node.getAttributes().getNamedItem( "id" ).getNodeValue(), node );
			}
		}
		//initial task-info 's detail
		for( String key : triggerInfoMap.keySet() ) {
			Node triggerNode = nodesMap.get( key );
			NodeList nl = triggerNode.getChildNodes();
			Node n;
			TaskInfo info = triggerInfoMap.get( key );
			for( int i = 0; i < nl.getLength(); ++i ) {
				if( ( n = nl.item( i ) ).getNodeType() == Node.ELEMENT_NODE ) {
					String name = n.getAttributes().getNamedItem( "name" ).getNodeValue();
					if( "jobDetail".equals( name ) ) {
						Node jobNode = nodesMap.get( n.getAttributes().getNamedItem( "ref" ).getNodeValue() );
						NodeList jobNl = jobNode.getChildNodes();
						Node jn;
						for( int j = 0; j < jobNl.getLength(); ++j ) {
							if( ( jn = jobNl.item( j ) ).getNodeType() == Node.ELEMENT_NODE ) {
								String jpname = jn.getAttributes().getNamedItem( "name" ).getNodeValue();
								if( "targetObject".equals( jpname ) ) {
									info.setClassName( jn.getAttributes().getNamedItem( "ref" ).getNodeValue() );
									taskInfoMap.put( info.getClassName(), info );
								}
							}
						}
					}
					if( "cronExpression".equals( name ) )
						info.setSchedule( n.getAttributes().getNamedItem( "value" ).getNodeValue() );
				}
			}
		}
		Workbook workbook= new XSSFWorkbook(  new FileInputStream(  new File( "C:\\Users\\chenjinlong\\Desktop\\worklog\\task梳理\\task-list-copy.xlsx" ) ) );
		org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt( 0 );
		int idx = 4;
		for( String key : taskInfoMap.keySet() ) {
			Cell classCell,scheduleCell;
			Row row;
			row = ( row = sheet.getRow( idx ) ) == null ? sheet.createRow( idx ) : row;
			TaskInfo info = taskInfoMap.get( key );
			classCell = (classCell = row.getCell( 1 )) == null ? row.createCell( 1 ) : classCell;
			scheduleCell = (scheduleCell = row.getCell( 3 )) == null ? row.createCell( 3 ) : scheduleCell;
			classCell.setCellValue( info.getClassName() );
			scheduleCell.setCellValue( info.getSchedule() );
			++idx;
		}
		workbook.write( new FileOutputStream( new File( "C:\\Users\\chenjinlong\\Desktop\\worklog\\task梳理\\task-list-copy.xlsx" ) ) );
		workbook.close();
	}
}
