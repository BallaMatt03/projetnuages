# Introduction #

This following document describe the content of the Webservice Part and its
interface.

You can download the different diagrammes in the resources folder of the project.

Thanks for watching ! :)


# Documentation #

Version 1.1 (Subject Changed)

```
namespace server.webservices.nuages;

/*
 * The ressources given by the web service
 */ 
interface NuageService
{
	// GET /images application/xml
	// Returns full catalogue informations in XML format
	public String getCatalogue();

	// GET /images?number={number:int} image/jpeg
	// Returns asked image
	public File getImage(int number);

	// GET /findShapes?search={search:String}
	// Returns Zip file that contains the result of google search
	public File findShapes(String search);

	// GET /Merge?...
	// Returns Zip file that contains the result of google search
	public File Merge(File source, File selection, int xStart, int yStart, int xEnd, int yEnd);
}
```