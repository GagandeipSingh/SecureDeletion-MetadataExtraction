# Secure Deletion and Metadata Extraction Tool

This project provides a comprehensive solution for two critical aspects of data management in the digital age: secure data deletion and metadata extraction.

## Secure Deletion
The project implements Secure Deletion algorithms that ensure the complete and irreversible deletion of data from storage devices. It offers two well-known methods for secure data deletion: DoD (Department of Defense) and Gutmann. The application provides a user-friendly interface that allows users to select the specific file they want to delete, making the process efficient and straightforward.

## Metadata Extraction
The project also implements a Metadata Extraction tool that can efficiently and accurately extract metadata from a wide range of image files. The extracted metadata is presented to the user in an easy-to-read format, providing valuable insights about the data.

## Implementation

**To Run From IDE**

#Prerequisites - Java Development Kit (JDK) installed on system.(To Compile and Run)

To execute Java code directly from Integrated Development Environment (IDE) it is necessary to have resources- background image and exiftool in the same directory as the Java files.

**To Run From App.jar**

#Prerequisites - Java Runtime Environment(JRE) installed on your system.

To execute App.jar simply run it by double-clicking it or using the java -jar App.jar command.

**To create App.jar from java files**

#Prerequisites -Java Development Kit (JDK) installed on your system.

-A Java application that you want to package into a .jar file. This guide assumes you have the following files:
	MainGUI.java: The main class of your application.
	Project1.java and Project2.java: Additional classes used by your application.
	bac.png: An image used by your application.
	exiftool.exe: An executable used by your application.

#Steps - 

1. Modify Java code to access resources: Modify Java code 
-To load an image
	ImageIO.read(getClass().getResource("/bac.png")) instead of ImageIO.read(new File("bac.png")). 
-To run an .exe file
	InputStream in = getClass().getResourceAsStream("/exiftool.exe");
	Path tempExePath = Paths.get(System.getProperty("java.io.tmpdir"), "exiftool.exe");
	Files.copy(in, tempExePath, StandardCopyOption.REPLACE_EXISTING);
	Process process = new ProcessBuilder(tempExePath.toString()).start();

2. Compile your Java files: Open a terminal in the directory containing your Java files and compile them using the javac command.e.g.

	javac MainGUI.java Project1.java Project2.java

3. Create a Manifest file
-Create a new text file named Manifest.txt in the same directory as your .java files. 
-Add the following line to it: Main-Class: MainGUI. Make sure to press Enter after the line to add a newline at the end.

4. Create the .jar file
-Use the jar command to create your .jar file. 
	jar cvfm App.jar Manifest.txt *.class bac.png exiftool.exe

After these steps, you should have a .jar file named App.jar that you can run by double-clicking it or using the java -jar App.jar command.
