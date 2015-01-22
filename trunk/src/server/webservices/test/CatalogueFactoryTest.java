package server.webservices.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Test;

import server.webservices.nuage.model.Catalog;
import server.webservices.nuage.services.CatalogFactory;
import server.webservices.nuage.services.IFileHandler;

/**
 * Test class for CatalogFactory
 * 
 * @author Cedric Boulet Kessler
 */
public class CatalogueFactoryTest {
	
	/**
	 * Fake path given for the test
	 */
	private static String imagePath = "myPath";
	
	/**
	 * Fake url given for the test
	 */
	private static String urlPath = "http://localhost:1664/nuage/?number=";
	
	/**
	 * Test for checking the catalog generation
	 */
	@Test
	public void createValidCatalogTest() {
		// Arrange - Create and populate a mock FileHandler
		IFileHandler mock = mock(IFileHandler.class);
		when(mock.scanDir(imagePath)).thenReturn(Arrays.asList( 
			imagePath + "/image1.jpeg",
			imagePath + "/image2.jpeg"
		));
		
		CatalogFactory factory = new CatalogFactory(imagePath, urlPath, mock);

		// Act - Ask to generate the catalog
		Catalog catalog = factory.createCatalog();

		// Assert - Verify that the generated catalog is correct
		verify(mock, times(1)).scanDir(imagePath);
		assertEquals(2, catalog.getImages().size());
		assertEquals("image1.jpeg", catalog.getImages().get(0).getName());
		assertEquals(urlPath + "1", catalog.getImages().get(1).getUrl());
	}
}
