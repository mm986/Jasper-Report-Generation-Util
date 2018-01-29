package com.mm.jasper;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jpatest.Player;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class GenerateJasper {
	
	public static void main(String args[]) throws Exception{
		 EntityManager em;
	        EntityManagerFactory emf;
	        emf=Persistence.createEntityManagerFactory("JPATestPU");
	        em=emf.createEntityManager();
	        em.getTransaction().begin();
	        Connection con=em.unwrap(Connection.class);
	        System.out.println("Connection...."+con);
	        
	        List<Player> list1=em.createNamedQuery("Player.findAll",Player.class).getResultList();
	        for(Player pp:list1)
	        {
	        System.out.println("ID:"+pp.getId());
	         System.out.println("Lastname:"+pp.getLastname());
	         System.out.println("Firstname:"+pp.getFirstname());
	         System.out.println("Jerseynumber:"+pp.getJerseynumber());
	         System.out.println("Lastspokenwords:"+pp.getLastspokenwords()+"\n\n");
	          }
	        //Create DataSource from Java Collection
	        JRBeanCollectionDataSource playerJRB=new JRBeanCollectionDataSource(list1);
	        
	        //Sending Data to Jasper Report
	        JasperDesign jd=JRXmlLoader.load(new File("").getAbsolutePath()+"/src/Player.jrxml");
	        HashMap param=new HashMap();
	        param.put("CountryName", "USA");
	        param.put("players", playerJRB);
	        JasperReport jr=JasperCompileManager.compileReport(jd);
	        JasperPrint jp=JasperFillManager.fillReport(jr,param,con); 
	        JasperViewer jv=new JasperViewer(jp);
	        jv.setVisible(true);
	        em.getTransaction().commit(); 
	}
}
