# ENSF380 Final Project
To run this project on a new Windows system complete the following steps:

__Install Java21__ 
1. Install Java21 by downloading the appropriate installer for your OS and following the on-screen instructions after running the installer.
1. Open System Properties and navigate to 'Environment Variables'.
2. Click 'New' under 'System variables'.
3. Enter 'JAVA_HOME' as the variable name and the Java21 installation path as the variable value.
4. Find the 'Path' variable in 'System variables' and edit it and add '%JAVA_HOME%\bin'.

__Install Eclipse IDE__
1. Download and install Eclipse by going to the Eclipse download page and following the on-screen instructions after running the installer.
2. Open Eclipse and go to 'Window' > 'Preferences' > 'Java' > "Installed JREs' and add your Java21 program.
   
__Install MySQL__
1. Install MySQL from the MySQL downloads website.
2. Complete the setup wizard ensuring you use the following username and password listed below.
3. Create username 'root' and password '30195900'.

__Run the Program__
1. Open a terminal and navigate to a directory you wish to store the project in.
2. Clone the repository using __git clone "https://github.com/aidenlambert9500/ENSF380FinalProject.git"__.
4. Open the __'advertisements.sql'__ file in MySQL Workbench and run the file.
3. Open the project in the Eclipse IDE project explorer.
4. Configure the libraries by right-clicking the project and going 'Build Path' > 'Configure Build Path'
5. Click 'Classpath' and add all of the libraries present in the folder 'libraries'
6. Click the run button

__Note:__ If you wish to run the program with command line arguments simply right-click the code > Run As > Run Configurations > Arguments. Then input the city of the weather you wish to display, the train number from 1-12, and the news keyword. The arguments must be separated by a space. The news keyword is not mandatory. If no news keyword is passed the API will search for top news articles around the world.

   
